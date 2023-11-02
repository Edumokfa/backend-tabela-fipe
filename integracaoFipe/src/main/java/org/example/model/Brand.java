package org.example.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Brand implements Serializable {

    @JsonProperty("codigo")
    private int id;
    @JsonProperty("nome")
    private String name;
}
