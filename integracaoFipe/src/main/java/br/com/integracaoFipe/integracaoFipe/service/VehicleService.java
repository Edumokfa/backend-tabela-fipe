package br.com.integracaoFipe.integracaoFipe.service;

import br.com.integracaoFipe.integracaoFipe.dao.BrandsRepository;
import br.com.integracaoFipe.integracaoFipe.dao.ModelsRepository;
import br.com.integracaoFipe.integracaoFipe.dao.VehicleRepository;
import br.com.integracaoFipe.integracaoFipe.dto.GraphicData;
import br.com.integracaoFipe.integracaoFipe.model.*;
import br.com.integracaoFipe.integracaoFipe.utils.ListUtil;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class VehicleService {

    private final MongoTemplate mongoTemplate;
    private final VehicleRepository vehicleRepository;
    private final BrandsRepository brandsRepository;


    public VehicleService(MongoTemplate mongoTemplate, VehicleRepository vehicleRepository, BrandsRepository brandsRepository) {
        this.mongoTemplate = mongoTemplate;
        this.vehicleRepository = vehicleRepository;
        this.brandsRepository = brandsRepository;
    }

    public ResponseEntity countVehicles() {
        return ResponseEntity.ok(vehicleRepository.count());
    }

    public ResponseEntity getTop10ModelsWithModelYears() {
        AggregationOperation group = Aggregation.group("brandId").count().as("modelCount");
        AggregationOperation sort = Aggregation.sort(org.springframework.data.domain.Sort.Direction.DESC, "modelCount");
        AggregationOperation limit = Aggregation.limit(10);

        Aggregation aggregation = Aggregation.newAggregation(
                group,
                sort,
                limit
        );

        AggregationResults<Model> results = mongoTemplate.aggregate(aggregation, "models", Model.class);

        if (results == null) {
            return ResponseEntity.ok(new ArrayList<>());
        }

        List<GraphicData> graphicData = new ArrayList<>();
        for (Model model: results.getMappedResults()) {
            Brand findBrand = brandsRepository.findById(Integer.parseInt(model.get_id())).orElse(null);
            GraphicData data = new GraphicData();
            if (findBrand != null) {
                data.setMarca(findBrand.getName());
                data.setQuantidadeModelos(model.getModelCount());
                graphicData.add(data);
            }
        }
        return ResponseEntity.ok(graphicData);
    }

    public ResponseEntity updateVehicle(Vehicle vehicle){
        return ResponseEntity.ok(vehicleRepository.save(vehicle));
    }

    public void deleteVehicle(String codigoFipe, String codigoAno){
        VehicleId id = new VehicleId(codigoFipe, codigoAno);
        vehicleRepository.deleteById(id);
    }
}