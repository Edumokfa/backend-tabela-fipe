package org.example.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.service.ModelService;
import org.example.utils.JsonUtil;

@Path("/model")
public class FindModel {

    private ModelService modelService;

    public FindModel(){
        this.modelService = new ModelService();
    }

    @Path("{brandId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getModels(@PathParam("brandId") Integer brandId) {
        String response = JsonUtil.convertToJson(modelService.getAllModelsFromBrand(brandId));
        return Response.ok(response).build();
    }
}
