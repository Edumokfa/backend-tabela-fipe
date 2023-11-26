package br.com.integracaoFipe.integracaoFipe.service;

import br.com.integracaoFipe.integracaoFipe.dao.BrandsRepository;
import br.com.integracaoFipe.integracaoFipe.dao.IterationLogRepository;
import br.com.integracaoFipe.integracaoFipe.model.Brand;
import br.com.integracaoFipe.integracaoFipe.model.IterationLog;
import br.com.integracaoFipe.integracaoFipe.utils.BaseApiCommunication;
import br.com.integracaoFipe.integracaoFipe.utils.ListUtil;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BrandService extends BaseApiCommunication {
    private final BrandsRepository brandsRepository;
    private final IterationLogService iterationLogService;

    public BrandService(BrandsRepository brandsRepository, IterationLogService iterationLogService) {
        this.brandsRepository = brandsRepository;
        this.iterationLogService = iterationLogService;
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
        Integer brandId = brand.get_id();
        Integer finalBrandId = brandId;

        if (brandId == null) {
            brandId = allBrands.stream().map(Brand::get_id).max(Integer::compare).orElse(0);
            brand.set_id(brandId + 1);
        }

        boolean existsBrandId = allBrands.stream().anyMatch(b -> b.get_id() == finalBrandId);

        if (existsBrandId) {
            return ResponseEntity.status(HttpStatus.CONFLICT.value()).body("O código " + brand.get_id() + " já existe");
        }

        brandsRepository.save(brand);
        return ResponseEntity.ok(brand);
    }

    private List<Brand> getBrandsFromDatabase() {
        return brandsRepository.findAll();
    }

    public ResponseEntity updateBrand(Brand brand) {
        Optional<Brand> brandOnDb = brandsRepository.findById(brand.get_id());
        if (!brandOnDb.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        brandsRepository.save(brand);
        return ResponseEntity.ok(brand);
    }

    public ResponseEntity countBrands() {
        return ResponseEntity.ok(brandsRepository.count());
    }

    public ResponseEntity deleteBrand(Integer brandId) {
        Optional<Brand> brandOnDb = brandsRepository.findById(brandId);
        if (!brandOnDb.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        brandsRepository.delete(brandOnDb.get());
        return ResponseEntity.ok().build();
    }

    public List<Brand> getBrandsFromApi() {
        List<Brand> brands = new ArrayList<>();
        ParameterizedTypeReference responseType = new ParameterizedTypeReference<List<Brand>>() {};
        List<Brand> carBrands = getListDataFromUrl("/carros/marcas", responseType);
        List<Brand> bikeBrands = getListDataFromUrl("/motos/marcas", responseType);
        List<Brand> truckBrands = getListDataFromUrl("/caminhoes/marcas", responseType);
        carBrands.forEach(c -> c.setType("Carro"));
        bikeBrands.forEach(c -> c.setType("Moto"));
        truckBrands.forEach(c -> c.setType("Caminhão"));
        brands.addAll(carBrands);
        brands.addAll(bikeBrands);
        brands.addAll(truckBrands);

        brandsRepository.saveAll(brands);
        iterationLogService.createIterationLog("B");

        return brands;
    }
}