package br.com.integracaoFipe.integracaoFipe.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "vehicle")
public class Vehicle implements Serializable {

    @Id
    @JsonIgnore
    private VehicleId _id;
    @JsonProperty("CodigoFipe")
    private String fipeCode;
    @JsonProperty("TipoVeiculo")
    private String type;
    @JsonProperty("Valor")
    private String value;
    @JsonProperty("Marca")
    private String brand;
    @JsonProperty("CodigoMarca")
    private Integer brandId;
    @JsonProperty("Modelo")
    private String model;
    @JsonProperty("AnoModelo")
    private Integer year;
    @JsonProperty("Combustivel")
    private String gas;
    @JsonProperty("SiglaCombustivel")
    private String gasType;
    @JsonProperty("MesReferencia")
    private String refMonth;

    @JsonIgnore
    public boolean isMonthDiffActual(){
        if (refMonth == null) {
            return false;
        }
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("MMMM", Locale.getDefault());
        String actualMonthName = currentDate.format(format);
        return refMonth.contains(actualMonthName);
    }
}