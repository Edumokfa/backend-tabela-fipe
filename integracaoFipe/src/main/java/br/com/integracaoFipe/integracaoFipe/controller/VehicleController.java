package br.com.integracaoFipe.integracaoFipe.controller;

import br.com.integracaoFipe.integracaoFipe.service.VehicleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class VehicleController {

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @Operation(description = "GET responsável por contar quantos veículos existem no cache da aplicação")
    @GetMapping("/veiculos/quantidade")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna sucesso caso seja possível contar quantos veículos existem no cache da aplicação"),
    })
    @CrossOrigin(origins = "*")
    public ResponseEntity countVehicles() {
        return vehicleService.countVehicles();
    }

    @Operation(description = "GET responsável por contar quantos veículos existem no cache da aplicação")
    @GetMapping("/veiculos/grafico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna sucesso caso seja possível contar quantos veículos existem no cache da aplicação"),
    })
    @CrossOrigin(origins = "*")
    public ResponseEntity getTop10Vehicles() {
        return vehicleService.getTop10ModelsWithModelYears();
    }
}