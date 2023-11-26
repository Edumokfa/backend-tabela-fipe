package br.com.integracaoFipe.integracaoFipe.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "models")
public class Model implements Serializable {

    @Id
    @JsonProperty("codigo")
    private String _id;
    @JsonProperty("nome")
    private String name;
    @JsonProperty("codigoMarca")
    private Integer brandId;
    @DBRef
    @JsonProperty("anos")
    private List<ModelYear> modelYears;
    @JsonIgnore
    private Integer modelCount;
}
