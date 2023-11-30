package br.com.integracaoFipe.integracaoFipe.controller;

import br.com.integracaoFipe.integracaoFipe.model.Brand;
import br.com.integracaoFipe.integracaoFipe.model.Vehicle;
import br.com.integracaoFipe.integracaoFipe.service.VehicleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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

    @Operation(description = "PUT responsável por atualizar os veículos no cache da aplicação")
    @PutMapping("/veiculos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna sucesso caso o veículo seja atualizado com sucesso"),
    })
    @CrossOrigin(origins = "*")
    public ResponseEntity updateVehicles(@RequestBody Vehicle vehicle) {
        return vehicleService.updateVehicle(vehicle);
    }

    @Operation(description = "DELETE responsável por atualizar os veículos no cache da aplicação")
    @DeleteMapping("/veiculos/{codigoFipe}/{codigoAno}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna sucesso caso o veículo seja atualizado com sucesso"),
    })
    @CrossOrigin(origins = "*")
    public ResponseEntity deleteVehicles(@PathVariable @Parameter(description = "Código da fipe") String codigoFipe, @PathVariable @Parameter(description = "Código da ano") String codigoAno) {
        vehicleService.deleteVehicle(codigoFipe, codigoAno);
        return ResponseEntity.ok().build();
    }
}