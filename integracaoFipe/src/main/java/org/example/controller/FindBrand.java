package org.example.controller;

import jakarta.ws.rs.core.Response;
import org.example.service.BrandService;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("/brand")
public class FindBrand {

    private BrandService brandService;

    public FindBrand() {
        this.brandService = new BrandService();
    }

    @GET
    public Response getBrands() {
        return Response.ok(brandService.getAllBrands()).build();
    }
}
