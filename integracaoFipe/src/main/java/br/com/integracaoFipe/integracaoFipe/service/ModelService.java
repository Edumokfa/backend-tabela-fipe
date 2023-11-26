package br.com.integracaoFipe.integracaoFipe.service;

import br.com.integracaoFipe.integracaoFipe.dao.*;
import br.com.integracaoFipe.integracaoFipe.model.*;
import br.com.integracaoFipe.integracaoFipe.utils.BaseApiCommunication;
import br.com.integracaoFipe.integracaoFipe.utils.ListUtil;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ModelService extends BaseApiCommunication {

    private final ModelsRepository modelsRepository;
    private final ModelsYearRepository modelsYearRepository;
    private final VehicleRepository vehicleRepository;
    private final IterationLogService iterationLogService;
    private final BrandsRepository brandsRepository;

    public ModelService(ModelsRepository modelsRepository, ModelsYearRepository modelsYearRepository, VehicleRepository vehicleRepository, IterationLogService iterationLogService, BrandsRepository brandsRepository) {
        this.modelsRepository = modelsRepository;
        this.modelsYearRepository = modelsYearRepository;
        this.vehicleRepository = vehicleRepository;
        this.iterationLogService = iterationLogService;
        this.brandsRepository = brandsRepository;
    }

    public ResponseEntity<List<Model>> getAllModelsFromBrand(Integer brandId) {
        List<Model> allModels = modelsRepository.getModelByBrandId(brandId);
        if (ListUtil.isEmpty(allModels)) {
            allModels = getModelsFromApi(brandId);
        }
        allModels.stream().forEach(model -> model.setModelYears(model.getModelYears().stream().filter(modelYear -> modelYear.getVehicle() != null).collect(Collectors.toList())));
        return ResponseEntity.ok(allModels);
    }

    @Transactional
    public List<Model> getModelsFromApi(Integer brandId) {
        ParameterizedTypeReference responseModelType = new ParameterizedTypeReference<Map<String, List<Model>>>() {};
        Brand brand = brandsRepository.findById(brandId).get();
        Map<String, List<Model>> modelResponseBody = getMapListDataFromUrl(brand.getTypeUnformatted() + getModelsApiUrl(brandId), responseModelType);
        if (modelResponseBody != null && modelResponseBody.containsKey("modelos")) {
            List<Model> modelsList = modelResponseBody.get("modelos");
            modelsList.forEach(m -> {
                m.setBrandId(brandId);
                List<ModelYear> modelYears = getModelYearsFromBrand(brand, m.get_id());
                modelsYearRepository.saveAll(modelYears);
                m.setModelYears(modelYears);
            });

            modelsRepository.saveAll(modelsList);
            iterationLogService.createIterationLog("M");
            return modelsList;
        }
        return new ArrayList<>();
    }

    private List<ModelYear> getModelYearsFromBrand(Brand brand, String modelId) {
        ParameterizedTypeReference responseModelYearType = new ParameterizedTypeReference<List<ModelYear>>() {};
        List<ModelYear> modelYears = getListDataFromUrl( brand.getTypeUnformatted() + getModelsApiUrl(brand.get_id()) + "/" + modelId + "/anos", responseModelYearType);
        modelYears.forEach(my -> {
            Vehicle vehicle = (Vehicle) getDataFromUrl(brand.getTypeUnformatted() + getModelsApiUrl(brand.get_id()) + "/" + modelId + "/anos/" + my.getCodigo(), Vehicle.class);
            vehicle.setBrandId(brand.get_id());
            vehicle.set_id(new VehicleId(vehicle.getFipeCode(), my.getCodigo()));
            vehicleRepository.save(vehicle);
            my.set_id(new ModelYearId(modelId, my.getCodigo()));
            my.setVehicle(vehicle);
        });
        return modelYears;
    }

    public ResponseEntity countModels() {
        return ResponseEntity.ok(modelsRepository.count());
    }

    private String getModelsApiUrl(Integer brandId) {
        return "/marcas/" + brandId + "/modelos";
    }
}