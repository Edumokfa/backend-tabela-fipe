package org.example.service;

import org.example.model.*;
import org.example.response.ResponseEntity;
import org.glassfish.grizzly.http.util.HttpStatus;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ModelService {

    private List<Model> modelList = new ArrayList<>();

    public ModelService() {
        modelList.add(new Model(1, "Gol", 1, getListGol()));
        modelList.add(new Model(2, "Voyage", 1, getListVoyage()));
        modelList.add(new Model(3, "Corsa", 3, getListCorsa()));
        modelList.add(new Model(4, "Palio", 2, getListPalio()));
    }

    private List<Vehicle> getListGol(){
        List<Vehicle> golList = new ArrayList<>();
        golList.add(new Vehicle(1, "GolG6", "C", new BigDecimal("30000"), 1, 1, 2013, "Gasolina"));
        golList.add(new Vehicle(2, "GolG5", "C", new BigDecimal("27000"), 1, 1, 2011, "Gasolina"));
        golList.add(new Vehicle(3, "GolG3", "C", new BigDecimal("17000"), 1, 1, 2005, "Gasolina"));
        return golList;
    }

    private List<Vehicle> getListVoyage(){
        List<Vehicle> voyageList = new ArrayList<>();
        voyageList.add(new Vehicle(4, "VoyageG6", "C", new BigDecimal("41000"), 1, 1, 2013, "Gasolina"));
        voyageList.add(new Vehicle(5, "VoyageG5", "C", new BigDecimal("34000"), 1, 1, 2011, "Gasolina"));
        return voyageList;
    }

    private List<Vehicle> getListCorsa(){
        List<Vehicle> voyageList = new ArrayList<>();
        voyageList.add(new Vehicle(6, "CorsaG1", "C", new BigDecimal("12000"), 1, 1, 1995, "Gasolina"));
        voyageList.add(new Vehicle(7, "CorsaG3", "C", new BigDecimal("21000"), 1, 1, 2003, "Gasolina"));
        return voyageList;
    }

    private List<Vehicle> getListPalio(){
        List<Vehicle> voyageList = new ArrayList<>();
        voyageList.add(new Vehicle(8, "PalioG1", "C", new BigDecimal("12000"), 1, 1, 1996, "Gasolina"));
        voyageList.add(new Vehicle(9, "PalioG2", "C", new BigDecimal("21000"), 1, 1, 2001, "Gasolina"));
        return voyageList;
    }

    public List<Model> getAllModelsFromBrand(Integer brandId) {
        List<Model> modelFromBrand = modelList.stream().filter(model -> model.getBrandId().equals(brandId)).toList();
        return modelFromBrand;
    }
}