package org.example.controller;

import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.model.Brand;
import org.example.service.BrandService;
import org.glassfish.grizzly.http.util.HttpStatus;

@Path("/brand")
public class CreateBrand {

    private BrandService brandService;

    public CreateBrand(){
        this.brandService = new BrandService();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response createBrands(Brand brand) {
        Brand updatedBrand = brandService.createBrand(brand);
        if (updatedBrand != null) {
            return Response.ok(updatedBrand).build();
        }
        return Response.status(HttpStatus.CONFLICT_409.getStatusCode()).build();
    }
}