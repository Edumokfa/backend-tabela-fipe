package br.com.integracaoFipe.integracaoFipe.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
public class VehicleId implements Serializable {

    private String fipeCode;
    private String yearId;
}