package br.com.integracaoFipe.integracaoFipe.service;

import br.com.integracaoFipe.integracaoFipe.model.Brand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class WeeklySearch {

    @Autowired
    private BrandService brandService;
    @Autowired
    private ModelService modelService;

    @Scheduled(fixedDelay = 7, timeUnit = TimeUnit.DAYS)
    @Transactional
    public void executeWeeklyTask() {
        brandService.deleteAllBrands();
        List<Brand> brands = brandService.getBrandsFromApi();
        modelService.deleteAllModels();
        brands.forEach(brand -> {
            modelService.getModelsFromApi(brand.get_id());
        });
    }
}