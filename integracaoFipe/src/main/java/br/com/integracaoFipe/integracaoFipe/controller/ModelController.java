package br.com.integracaoFipe.integracaoFipe.controller;

import br.com.integracaoFipe.integracaoFipe.model.Model;
import br.com.integracaoFipe.integracaoFipe.model.Vehicle;
import br.com.integracaoFipe.integracaoFipe.service.ModelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/marcas")
public class ModelController {

    @Autowired
    private ModelService modelService;

    @Operation(description = "GET responsável por obter todos os modelos detalhados da marca especificada pelo código")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna uma lista de modelos, com todos os anos de cada modelo, e todos os veículos de cada ano")
    })
    @CrossOrigin(origins = "*")
    @GetMapping("/{brandId}/modelos")
    public ResponseEntity<List<Model>> getModels(@PathVariable @Parameter(description = "Código da marca") Integer brandId) {
        return modelService.getAllModelsFromBrand(brandId);
    }

    @Operation(description = "GET responsável por contar quantos modelos existem no cache da aplicação")
    @GetMapping("/modelos/quantidade")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna sucesso caso seja possível contar quantos modelos existem no cache da aplicação"),
    })
    @CrossOrigin(origins = "*")
    public ResponseEntity countModels() {
        return modelService.countModels();
    }
}
