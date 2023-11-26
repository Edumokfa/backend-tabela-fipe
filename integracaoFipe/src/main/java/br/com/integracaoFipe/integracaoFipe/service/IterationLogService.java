package br.com.integracaoFipe.integracaoFipe.service;

import br.com.integracaoFipe.integracaoFipe.dao.IterationLogRepository;
import br.com.integracaoFipe.integracaoFipe.model.IterationLog;
import br.com.integracaoFipe.integracaoFipe.utils.ListUtil;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class IterationLogService {
    private final IterationLogRepository iterationLogRepository;

    public IterationLogService(MongoTemplate mongoTemplate, IterationLogRepository iterationLogRepository) {
        this.iterationLogRepository = iterationLogRepository;
    }

    public void createIterationLog(String tipo) {
        IterationLog iterationLog = new IterationLog();
        List<IterationLog> ultimoLog = iterationLogRepository.findTopId();
        if (ListUtil.isNotEmpty(ultimoLog)) {
            iterationLog.set_id(ultimoLog.get(0).get_id() + 1);
        } else {
            iterationLog.set_id(1);
        }
        iterationLog.setData(new Date());
        iterationLog.setTipo(tipo);
        iterationLogRepository.save(iterationLog);
    }

    public ResponseEntity getLastIterationPerType(String tipo) {
        List<IterationLog> ultimoLog = iterationLogRepository.findTopId(tipo);
        if (ListUtil.isNotEmpty(ultimoLog)) {
            return ResponseEntity.ok(ultimoLog.get(0));
        }

        return ResponseEntity.noContent().build();
    }
}