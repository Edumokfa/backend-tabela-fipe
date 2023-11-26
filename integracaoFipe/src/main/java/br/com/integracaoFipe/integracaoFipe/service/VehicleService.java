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

    public ResponseEntity countVehicles() {
        return ResponseEntity.ok(vehicleRepository.count());
    }
}