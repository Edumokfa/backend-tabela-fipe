package br.com.integracaoFipe.integracaoFipe.dao;

import br.com.integracaoFipe.integracaoFipe.model.Brand;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BrandsRepository extends MongoRepository<Brand, Integer> {

}
