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

import java.math.BigDecimal;
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

    @Operation(description = "GET responsável por filtrar entre os veículos da marca especificada pelo códigop, possibilitando uma busca mais variada de informações")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna uma lista de veículos, com todas as informações de cada um deles")
    })
    @CrossOrigin(origins = "*")
    @GetMapping("/{brandId}/filtraModelos")
    public ResponseEntity<List<Vehicle>> getModelsFiltered(@PathVariable @Parameter(description = "Código da marca") Integer brandId,
                                                           @RequestParam(required = false) @Parameter(description = "Ano inicial") Integer startYear,
                                                           @RequestParam(required = false) @Parameter(description = "Ano final") Integer endYear,
                                                           @RequestParam(required = false) @Parameter(description = "Valor mínimo") BigDecimal minValue,
                                                           @RequestParam(required = false) @Parameter(description = "Valor máximo") BigDecimal maxValue,
                                                           @RequestParam(required = false) @Parameter(description = "Tipo de combustível") Character gasType,
                                                           @RequestParam(required = false) @Parameter(description = "Tipo de veículo") String vehicleType) {
        return modelService.getFilteredModelsFromApi(brandId, startYear, endYear, minValue, maxValue, gasType, vehicleType);
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
