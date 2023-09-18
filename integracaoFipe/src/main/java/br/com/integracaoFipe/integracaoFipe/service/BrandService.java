package br.com.integracaoFipe.integracaoFipe.service;

import br.com.integracaoFipe.integracaoFipe.dao.BrandsRepository;
import br.com.integracaoFipe.integracaoFipe.model.Brand;
import br.com.integracaoFipe.integracaoFipe.utils.BaseApiCommunication;
import br.com.integracaoFipe.integracaoFipe.utils.ListUtil;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BrandService extends BaseApiCommunication {

    private final BrandsRepository brandsRepository;

    public BrandService(BrandsRepository brandsRepository) {
        this.brandsRepository = brandsRepository;
    }

    public ResponseEntity<List<Brand>> getAllBrands() {
        List<Brand> allBrands = getBrandsFromDatabase();
        if (ListUtil.isEmpty(allBrands)) {
            allBrands = getBrandsFromApi();
        }
        return ResponseEntity.ok(allBrands);
    }

    public ResponseEntity createBrand(Brand brand) {
        List<Brand> allBrands = getAllBrands().getBody();
        final int brandId = brand.get_id();
        boolean existsBrandId = allBrands.stream().anyMatch(b -> b.get_id() == brandId);
        if (existsBrandId) {
            return ResponseEntity.status(HttpStatus.CONFLICT.value()).body("O código " + brand.get_id() + " já existe");
        }
        brandsRepository.save(brand);
        return ResponseEntity.ok(brand);
    }

    private List<Brand> getBrandsFromDatabase() {
        return brandsRepository.findAll();
    }

    private List<Brand> getBrandsFromApi() {
        List<Brand> brands = new ArrayList<>();
        ParameterizedTypeReference responseType = new ParameterizedTypeReference<List<Brand>>() {};
        brands.addAll(getListDataFromUrl("/carros/marcas", responseType));
        brands.addAll(getListDataFromUrl("/motos/marcas", responseType));
        brands.addAll(getListDataFromUrl("/caminhoes/marcas", responseType));
        brandsRepository.saveAll(brands);
        return brands;
    }
}