package br.com.integracaoFipe.integracaoFipe.service;

import br.com.integracaoFipe.integracaoFipe.dao.ModelsRepository;
import br.com.integracaoFipe.integracaoFipe.dao.ModelsYearRepository;
import br.com.integracaoFipe.integracaoFipe.dao.VehicleRepository;
import br.com.integracaoFipe.integracaoFipe.model.*;
import br.com.integracaoFipe.integracaoFipe.utils.BaseApiCommunication;
import br.com.integracaoFipe.integracaoFipe.utils.ListUtil;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class VehicleService {

    private final MongoTemplate mongoTemplate;

    public VehicleService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
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
}