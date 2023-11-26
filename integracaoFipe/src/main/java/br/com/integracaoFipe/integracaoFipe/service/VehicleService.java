package br.com.integracaoFipe.integracaoFipe.service;

import br.com.integracaoFipe.integracaoFipe.dao.ModelsRepository;
import br.com.integracaoFipe.integracaoFipe.dao.VehicleRepository;
import br.com.integracaoFipe.integracaoFipe.dto.GraphicData;
import br.com.integracaoFipe.integracaoFipe.model.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VehicleService {

    private final MongoTemplate mongoTemplate;
    private final VehicleRepository vehicleRepository;
    private final ModelsRepository modelsRepository;


    public VehicleService(MongoTemplate mongoTemplate, VehicleRepository vehicleRepository, ModelsRepository modelsRepository) {
        this.mongoTemplate = mongoTemplate;
        this.vehicleRepository = vehicleRepository;
        this.modelsRepository = modelsRepository;
    }

    public ResponseEntity countVehicles() {
        return ResponseEntity.ok(vehicleRepository.count());
    }

    public ResponseEntity getTop10ModelsWithModelYears() {
        Criteria unwindModelYearsCriteria = Criteria.where("modelYears").exists(true);

        AggregationOperation group = Aggregation.group("_id").count().as("modelYearCount");
        AggregationOperation sort = Aggregation.sort(org.springframework.data.domain.Sort.Direction.DESC, "modelYearCount");
        AggregationOperation limit = Aggregation.limit(10);

        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(unwindModelYearsCriteria),
                Aggregation.unwind("modelYears"),
                group,
                sort,
                limit
        );

        AggregationResults<Model> results = mongoTemplate.aggregate(aggregation, "models", Model.class);
        List<GraphicData> graphicData = new ArrayList<>();
        for (Model model: results.getMappedResults()) {
            Model findModel = modelsRepository.findById(model.get_id()).orElse(null);
            GraphicData data = new GraphicData();
            if (findModel != null) {
                data.setMarca(findModel.getModelYears().get(0).getVehicle().getModel());
                data.setQuantidadeModelos(findModel.getModelYears().size());
            }
            graphicData.add(data);
        }
        return ResponseEntity.ok(graphicData);
    }
}