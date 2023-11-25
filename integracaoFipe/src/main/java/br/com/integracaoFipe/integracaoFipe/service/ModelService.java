package br.com.integracaoFipe.integracaoFipe.service;

import br.com.integracaoFipe.integracaoFipe.dao.ModelsRepository;
import br.com.integracaoFipe.integracaoFipe.dao.ModelsYearRepository;
import br.com.integracaoFipe.integracaoFipe.dao.VehicleRepository;
import br.com.integracaoFipe.integracaoFipe.model.*;
import br.com.integracaoFipe.integracaoFipe.utils.BaseApiCommunication;
import br.com.integracaoFipe.integracaoFipe.utils.ListUtil;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ModelService extends BaseApiCommunication {

    private final ModelsRepository modelsRepository;
    private final ModelsYearRepository modelsYearRepository;
    private final VehicleRepository vehicleRepository;
    private final VehicleService vehicleService;

    public ModelService(ModelsRepository modelsRepository, ModelsYearRepository modelsYearRepository, VehicleRepository vehicleRepository, VehicleService vehicleService) {
        this.modelsRepository = modelsRepository;
        this.modelsYearRepository = modelsYearRepository;
        this.vehicleRepository = vehicleRepository;
        this.vehicleService = vehicleService;
    }

    public ResponseEntity<List<Model>> getAllModelsFromBrand(Integer brandId) {
        List<Model> allModels = modelsRepository.getModelByBrandId(brandId);
        if (ListUtil.isNotEmpty(allModels)) {
            boolean hasMonthDiffActual = allModels.stream().anyMatch(model -> model.getModelYears().stream().anyMatch(year -> year.getVehicle().isMonthDiffActual()));
            if (hasMonthDiffActual) {
                allModels.clear();
            }
        }
        if (ListUtil.isEmpty(allModels)) {
            allModels = getModelsFromApi(brandId);
        }
        return ResponseEntity.ok(allModels);
    }

    public List<Model> getModelsFromApi(Integer brandId) {
        ParameterizedTypeReference responseModelType = new ParameterizedTypeReference<Map<String, List<Model>>>() {
        };
        Map<String, List<Model>> modelResponseBody = getMapListDataFromUrl(getModelsApiUrl(brandId), responseModelType);
        if (modelResponseBody != null && modelResponseBody.containsKey("modelos")) {
            List<Model> modelsList = modelResponseBody.get("modelos");
            modelsList.forEach(m -> {
                m.setBrandId(brandId);
                List<ModelYear> modelYears = getModelYearsFromBrand(brandId, m.get_id());
                modelsYearRepository.saveAll(modelYears);
                m.setModelYears(modelYears);
            });
            modelsRepository.saveAll(modelsList);
            return modelsList;
        }
        return new ArrayList<>();
    }

    private List<ModelYear> getModelYearsFromBrand(Integer brandId, String modelId) {
        ParameterizedTypeReference responseModelYearType = new ParameterizedTypeReference<List<ModelYear>>() {};
        List<ModelYear> modelYears = getListDataFromUrl(getModelsApiUrl(brandId) + "/" + modelId + "/anos", responseModelYearType);
        modelYears.forEach(my -> {
            Vehicle vehicle = (Vehicle) getDataFromUrl(getModelsApiUrl(brandId) + "/" + modelId + "/anos/" + my.getCodigo(), Vehicle.class);
            vehicle.setBrandId(brandId);
            vehicle.set_id(new VehicleId(vehicle.getFipeCode(), my.getCodigo()));
            vehicleRepository.save(vehicle);
            my.set_id(new ModelYearId(modelId, my.getCodigo()));
            my.setVehicle(vehicle);
        });
        return modelYears;
    }

    public ResponseEntity<List<Vehicle>> getFilteredModelsFromApi(Integer brandId, Integer startYear, Integer endYear, BigDecimal minValue, BigDecimal maxValue, Character gasType, String vehicleType) {
        List<Vehicle> filteredVehicles = vehicleService.getFilteredVehicle(brandId, startYear, endYear, minValue, maxValue, gasType, vehicleType);

        if (ListUtil.isNotEmpty(filteredVehicles)) {
            boolean hasMonthDiffActual = filteredVehicles.stream().anyMatch(vehicle -> vehicle.isMonthDiffActual());
            if (hasMonthDiffActual) {
                filteredVehicles.clear();
            }
        }

        if (ListUtil.isEmpty(filteredVehicles)) {
            boolean existsModelFromBrandId = modelsRepository.existsModelByBrandId(brandId);
            if (!existsModelFromBrandId) {
                getModelsFromApi(brandId);
            }
            filteredVehicles = vehicleService.getFilteredVehicle(brandId, startYear, endYear, minValue, maxValue, gasType, vehicleType);
        }
        return ResponseEntity.ok(filteredVehicles);
    }

    public void deleteAllModels() {
        modelsRepository.deleteAll();
    }

    private String getModelsApiUrl(Integer brandId) {
        return "/carros/marcas/" + brandId + "/modelos";
    }
}