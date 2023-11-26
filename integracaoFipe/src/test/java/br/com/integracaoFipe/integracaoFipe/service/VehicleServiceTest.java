package br.com.integracaoFipe.integracaoFipe.service;

import br.com.integracaoFipe.integracaoFipe.dao.BrandsRepository;
import br.com.integracaoFipe.integracaoFipe.dao.VehicleRepository;
import br.com.integracaoFipe.integracaoFipe.dto.GraphicData;
import br.com.integracaoFipe.integracaoFipe.model.Brand;
import br.com.integracaoFipe.integracaoFipe.model.Model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.mongodb.assertions.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class VehicleServiceTest {
    @InjectMocks
    private VehicleService vehicleService;
    @Mock
    private  MongoTemplate mongoTemplate;

    @Mock
    private  VehicleRepository vehicleRepository;

    @Mock
    private  BrandsRepository brandsRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        vehicleService = new VehicleService(mongoTemplate, vehicleRepository, brandsRepository);
    }

    @Test
    public void testCountVehicles() {
        long expectedCount = 10L;
        when(vehicleRepository.count()).thenReturn(expectedCount);

        ResponseEntity responseEntity = vehicleService.countVehicles();

        verify(vehicleRepository, times(1)).count();

        assertEquals(200, responseEntity.getStatusCodeValue());

        assertEquals(expectedCount, responseEntity.getBody());
    }

    @Test
    public void testGetTop10ModelsWithModelYears() {
        List<Model> mockModels = new ArrayList<>();
        AggregationResults<Model> aggregationResults = mock(AggregationResults.class);
        when(aggregationResults.getMappedResults()).thenReturn(mockModels);

        // Mocking the brands repository
        when(brandsRepository.findById(anyInt())).thenReturn(java.util.Optional.of(new Brand(1, "brand1", "Brand 1")));

        // Calling the method to be tested
        ResponseEntity responseEntity = vehicleService.getTop10ModelsWithModelYears();

        // Verifying the response status code is OK
        assertEquals(200, responseEntity.getStatusCodeValue());

        // Verifying the response body contains the expected data
        List<GraphicData> graphicData = (List<GraphicData>) responseEntity.getBody();
        assertNotNull(graphicData);
        assertEquals(mockModels.size(), graphicData.size());
    }
}