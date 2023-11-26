package br.com.integracaoFipe.integracaoFipe.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
public class VehicleId implements Serializable {

    @JsonProperty("codigoFipe")
    private String fipeCode;
    @JsonProperty("codigoAno")
    private String yearId;
}