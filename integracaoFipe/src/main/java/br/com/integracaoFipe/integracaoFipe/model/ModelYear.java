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
@Document(collection = "modelYears")
public class ModelYear implements Serializable {

    @Id
    @JsonIgnore
    private ModelYearId _id;
    @JsonProperty("codigo")
    private String codigo;
    @JsonProperty("nome")
    private String name;
    @DBRef
    @JsonProperty("veiculo")
    private Vehicle vehicle;
}
