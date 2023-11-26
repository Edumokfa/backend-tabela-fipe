package br.com.integracaoFipe.integracaoFipe.controller;

import br.com.integracaoFipe.integracaoFipe.service.IterationLogService;
import br.com.integracaoFipe.integracaoFipe.service.VehicleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class IterationLogController {

    private final IterationLogService iterationLogService;

    public IterationLogController(IterationLogService iterationLogService) {
        this.iterationLogService = iterationLogService;
    }

    @Operation(description = "GET responsável por retornar o log de iteração da última atualização")
    @GetMapping("/logs/{tipo}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna sucesso caso seja possível buscar o último log de integração com a tabela Fipe"),
            @ApiResponse(responseCode = "204", description = "Retorna caso nenhuma integração do tipo tenha sido realizada"),
    })
    @CrossOrigin(origins = "*")
    public ResponseEntity getLastIterationLog(@PathVariable @Parameter(description = "Tipo do Log") String tipo) {
        return iterationLogService.getLastIterationPerType(tipo);
    }
}