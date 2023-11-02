package org.example.service;

import org.example.model.Brand;
import org.example.response.ResponseEntity;
import org.example.utils.ListUtil;
import org.glassfish.grizzly.http.util.HttpStatus;

import java.util.ArrayList;
import java.util.List;

public class BrandService {

    List<Brand> brandList = new ArrayList<>();

    public BrandService(){
        brandList.add(new Brand(1, "Volkswagen"));
        brandList.add(new Brand(2, "Fiat"));
        brandList.add(new Brand(3, "Chevrolet"));
    }

    public List<Brand> getAllBrands() {
        return brandList;
    }

    public Brand createBrand(Brand brand) {
        boolean hasBrand = ListUtil.isNotEmpty(brandList) && brandList.stream().anyMatch(b -> b.getId() == brand.getId() || b.getName().equals(brand.getName()));
        if (hasBrand) {
            return null;
        }
        brandList.add(brand);
        return brand;
    }

    public Brand updateBrand(Brand brand) {
        Brand existentBrand = brandList.stream().filter(b -> b.getId() == brand.getId() || b.getName().equals(brand.getName())).findFirst().orElse(null);
        if (existentBrand == null) {
            return null;
        }

        return brand;
    }

    public boolean deleteBrand(Integer brandId) {
        Brand existentBrand = brandList.stream().filter(b -> b.getId() == brandId).findFirst().orElse(null);
        if (existentBrand == null) {
            return false;
        }
        brandList.remove(existentBrand);
        return true;
    }
}