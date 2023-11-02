package org.example.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Model implements Serializable {

    @JsonProperty("codigo")
    private Integer id;
    @JsonProperty("nome")
    private String name;
    @JsonProperty("codigoMarca")
    private Integer brandId;
    @JsonProperty("veiculos")
    private List<Vehicle> modelYears;
}
