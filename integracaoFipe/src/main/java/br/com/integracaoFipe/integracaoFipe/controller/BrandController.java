package br.com.integracaoFipe.integracaoFipe.controller;

import br.com.integracaoFipe.integracaoFipe.model.Brand;
import br.com.integracaoFipe.integracaoFipe.service.BrandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BrandController {

    @Autowired
    private BrandService brandService;

    @Operation(description = "GET responsável por obter todas as marcas disponíveis na tabela FIPE")
    @GetMapping("/marca")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna uma lista com todas as marcas disponíveis")
    })
    @CrossOrigin(origins = "*")
    public ResponseEntity<List<Brand>> getBrands() {
        return brandService.getAllBrands();
    }

    @Operation(description = "POST responsável por salvar uma nova marca no cache da aplicação")
    @PostMapping("/marca")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna a própria marca caso ela seja salva com sucesso no banco de dados"),
            @ApiResponse(responseCode = "409", description = "Retorna este erro quando o código da marca já estiver cadastrado")
    })
    public ResponseEntity createBrands(@RequestBody Brand brand) {
        return brandService.createBrand(brand);
    }

    @Operation(description = "PUT responsável por atualizar uma marca no cache da aplicação")
    @PutMapping("/marca")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna a própria marca caso ela seja atualizada com sucesso no banco de dados"),
            @ApiResponse(responseCode = "409", description = "Retorna este erro quando o código da marca já estiver cadastrado")
    })
    public ResponseEntity updateBrands(@RequestBody Brand brand) {
        return brandService.updateBrand(brand);
    }



}
