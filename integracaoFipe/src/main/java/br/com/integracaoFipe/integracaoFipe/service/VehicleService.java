package br.com.integracaoFipe.integracaoFipe.service;

import br.com.integracaoFipe.integracaoFipe.dao.VehicleRepository;
import br.com.integracaoFipe.integracaoFipe.model.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class VehicleService {

    private final MongoTemplate mongoTemplate;
    private final VehicleRepository vehicleRepository;


    public VehicleService(MongoTemplate mongoTemplate, VehicleRepository vehicleRepository) {
        this.mongoTemplate = mongoTemplate;
        this.vehicleRepository = vehicleRepository;
    }

    public List<Vehicle> getFilteredVehicle(
            Integer brandId,
            Integer startYear,
            Integer endYear,
            BigDecimal minValue,
            BigDecimal maxValue,
            Character gasType,
            String type) {

        Criteria criteria = Criteria.where("brandId").is(brandId);

        if (startYear != null && endYear != null) {
            criteria.and("year").gte(startYear).lte(endYear);
        }

        if (minValue != null && maxValue != null){
            criteria.and("value").gte(minValue).lte(maxValue);
        }

        if (gasType != null) {
            criteria.and("gasType").is(gasType);
        }
        if (type != null) {
            String typeFilter;
            if (type.equals("Carro")) {
                typeFilter = "1";
            } else if (type.equals("Moto")) {
                typeFilter = "2";
            } else if (type.equals("Caminhao")) {
                typeFilter = "3";
            } else {
                return new ArrayList<>();
            }
            criteria.and("type").is(typeFilter);
        }

        Query query = new Query(criteria);
        return mongoTemplate.find(query, Vehicle.class);
    }

    public ResponseEntity countVehicles() {
        return ResponseEntity.ok(vehicleRepository.count());
    }
}