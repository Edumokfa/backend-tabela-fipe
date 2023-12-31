package br.com.integracaoFipe.integracaoFipe.service;

import br.com.integracaoFipe.integracaoFipe.dao.BrandsRepository;
import br.com.integracaoFipe.integracaoFipe.dao.IterationLogRepository;
import br.com.integracaoFipe.integracaoFipe.model.Brand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class BrandServiceTest {

    @InjectMocks
    private BrandService brandService;
    @Mock
    private BrandsRepository brandsRepository;
    @Mock
    private IterationLogService iterationLogService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        brandService = new BrandService(brandsRepository, iterationLogService);
        brandService.setApiExternalUrl("https://parallelum.com.br/fipe/api/v1/");
    }

    @Test
    public void testGetAllBrandsFromDatabase() {
        List<Brand> mockBrands = new ArrayList<>();
        mockBrands.add(new Brand(1, "Brand1", "Moto"));
        mockBrands.add(new Brand(2, "Brand2", "Carro"));

        when(brandsRepository.findAll()).thenReturn(mockBrands);

        ResponseEntity<List<Brand>> response = brandService.getAllBrands();

        assertEquals(2, response.getBody().size());
        assertEquals("Brand1", response.getBody().get(0).getName());
        assertEquals("Brand2", response.getBody().get(1).getName());

        verify(brandsRepository, times(1)).findAll();
    }

    @Test
    public void testCreateBrand() {
        Brand newBrand = new Brand(600, "NewBrand", "Carro");

        when(brandsRepository.save(newBrand)).thenReturn(newBrand);

        ResponseEntity response = brandService.createBrand(newBrand);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(newBrand, response.getBody());

        verify(brandsRepository, times(1)).save(newBrand);
    }

    @Test
    public void testCreateBrandWithExistingId() {
        Brand existingBrand = new Brand(1, "ExistingBrand", "Moto");

        List<Brand> mockBrands = new ArrayList<>();
        mockBrands.add(existingBrand);

        when(brandsRepository.findAll()).thenReturn(mockBrands);

        ResponseEntity response = brandService.createBrand(existingBrand);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("O código 1 já existe", response.getBody());

        verify(brandsRepository, never()).save(any());
    }
    @Test
    public void testUpdateBrand() {
        Brand brandToUpdate = new Brand(1, "Toyota", "Car");

        when(brandsRepository.findById(brandToUpdate.get_id())).thenReturn(Optional.of(brandToUpdate));
        when(brandsRepository.save(Mockito.any(Brand.class))).thenReturn(brandToUpdate);

        ResponseEntity responseEntity = brandService.updateBrand(brandToUpdate);

        assertEquals(ResponseEntity.ok(brandToUpdate), responseEntity);
        verify(brandsRepository).findById(brandToUpdate.get_id());
        verify(brandsRepository).save(brandToUpdate);
    }

    @Test
    public void testDeleteBrand() {
        Integer brandIdToDelete = 1;
        Brand brandToDelete = new Brand(brandIdToDelete, "Toyota", "Car");

        when(brandsRepository.findById(brandIdToDelete)).thenReturn(Optional.of(brandToDelete));
        ResponseEntity responseEntity = brandService.deleteBrand(brandIdToDelete);

        assertEquals(ResponseEntity.ok().build(), responseEntity);
        verify(brandsRepository).findById(brandIdToDelete);
        verify(brandsRepository).delete(brandToDelete);
    }

}