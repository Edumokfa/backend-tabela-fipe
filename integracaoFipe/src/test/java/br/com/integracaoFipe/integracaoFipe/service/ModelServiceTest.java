package br.com.integracaoFipe.integracaoFipe.service;

import br.com.integracaoFipe.integracaoFipe.dao.IterationLogRepository;
import br.com.integracaoFipe.integracaoFipe.dao.ModelsRepository;
import br.com.integracaoFipe.integracaoFipe.dao.ModelsYearRepository;
import br.com.integracaoFipe.integracaoFipe.dao.VehicleRepository;
import br.com.integracaoFipe.integracaoFipe.model.IterationLog;
import br.com.integracaoFipe.integracaoFipe.model.Model;
import br.com.integracaoFipe.integracaoFipe.model.Vehicle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ModelServiceTest {

    @Mock
    private ModelsRepository modelsRepository;

    @Mock
    private IterationLogService iterationLogService;

    @Mock
    private ModelsYearRepository modelsYearRepository;

    @Mock
    private VehicleRepository vehicleRepository;

    @Mock
    private VehicleService vehicleService;

    @InjectMocks
    private ModelService modelService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        modelService.setApiExternalUrl("https://parallelum.com.br/fipe/api/v1/");
    }

    @Test
    public void testGetAllModelsFromBrand() {
        List<Model> mockModels = new ArrayList<>();
        mockModels.add(new Model("1", "Model1", 1, new ArrayList<>(), 1));
        when(modelsRepository.getModelByBrandId(1)).thenReturn(mockModels);

        ResponseEntity<List<Model>> response = modelService.getAllModelsFromBrand(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockModels, response.getBody());
        verify(modelsRepository, times(1)).getModelByBrandId(1);
    }
}