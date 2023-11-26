package br.com.integracaoFipe.integracaoFipe.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "iteration_log")
public class IterationLog implements Serializable {

    @Id
    @JsonProperty("codigo")
    private int _id;
    @JsonProperty("data")
    private Date data;
    @JsonProperty("tipo")
    private String tipo;
}
