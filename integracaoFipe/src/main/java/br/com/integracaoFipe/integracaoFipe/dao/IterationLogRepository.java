package br.com.integracaoFipe.integracaoFipe.dao;

import br.com.integracaoFipe.integracaoFipe.model.Brand;
import br.com.integracaoFipe.integracaoFipe.model.IterationLog;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IterationLogRepository extends MongoRepository<IterationLog, Integer> {
    @Query(value = "{}", sort = "{'_id': -1}", fields = "{'_id': 1}")
    List<IterationLog> findTopId();

    @Query(value = "{'tipo': ?0}", sort = "{'_id': -1}", fields = "{}")
    List<IterationLog> findTopId(@Param("tipo") String tipo);
}
