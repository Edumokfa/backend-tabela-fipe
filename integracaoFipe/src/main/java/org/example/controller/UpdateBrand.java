package org.example.controller;

import jakarta.ws.rs.core.Response;
import org.example.model.Brand;
import org.example.service.BrandService;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.glassfish.grizzly.http.util.HttpStatus;

@Path("/brand")
public class UpdateBrand {

    private final BrandService brandService;

    public UpdateBrand() {
        this.brandService = new BrandService();
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateBrands(Brand brand) {
        Brand updatedBrand = brandService.updateBrand(brand);
        if (updatedBrand != null) {
            return Response.ok(updatedBrand).build();
        }
        return Response.status(HttpStatus.NOT_FOUND_404.getStatusCode()).build();
    }
}
