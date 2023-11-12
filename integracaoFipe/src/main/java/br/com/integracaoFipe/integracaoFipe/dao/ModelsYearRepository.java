package br.com.integracaoFipe.integracaoFipe.dao;

import br.com.integracaoFipe.integracaoFipe.model.ModelYear;
import br.com.integracaoFipe.integracaoFipe.model.ModelYearId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ModelsYearRepository extends MongoRepository<ModelYear, ModelYearId> {

}
