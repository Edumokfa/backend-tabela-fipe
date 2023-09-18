package br.com.integracaoFipe.integracaoFipe.dao;

import br.com.integracaoFipe.integracaoFipe.model.Vehicle;
import br.com.integracaoFipe.integracaoFipe.model.VehicleId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VehicleRepository extends MongoRepository<Vehicle, VehicleId> {

}
