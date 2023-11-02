package org.example.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.utils.MoneySerializer;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Vehicle implements Serializable {

    @JsonIgnore
    private Integer id;
    @JsonProperty("CodigoFipe")
    private String fipeCode;
    @JsonProperty("TipoVeiculo")
    private String type;
    @JsonProperty("Valor")
    @JsonSerialize(using = MoneySerializer.class)
    private BigDecimal value;
    @JsonProperty("CodigoMarca")
    private Integer brandId;
    @JsonProperty("CodigoModelo")
    private Integer modelId;
    @JsonProperty("AnoModelo")
    private Integer year;
    @JsonProperty("Combustivel")
    private String gas;

}