package br.com.integracaoFipe.integracaoFipe.dao;

import br.com.integracaoFipe.integracaoFipe.model.Model;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ModelsRepository extends MongoRepository<Model, String> {

    public List<Model> getModelByBrandId(Integer brandId);

    public boolean existsModelByBrandId(Integer brandId);

}
