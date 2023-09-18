package br.com.integracaoFipe.integracaoFipe.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
public class ModelYearId implements Serializable {

    private String modelId;
    private String yearId;
}