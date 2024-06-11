package vn.com.kns.portalapi;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import vn.com.kns.portalapi.application.service.category.country.CountryServiceImpl;
import vn.com.kns.portalapi.application.service.category.country.dto.CountryDto;
import vn.com.kns.portalapi.application.service.category.unit.UnitServiceImpl;
import vn.com.kns.portalapi.application.service.category.unit.dto.UnitDto;
import vn.com.kns.portalapi.application.service.category.unit.dto.UnitInputDto;
import vn.com.kns.portalapi.core.entity.other.Country;
import vn.com.kns.portalapi.core.entity.other.Unit;
import vn.com.kns.portalapi.data.repository.other.CountryRepository;
import vn.com.kns.portalapi.data.repository.other.UnitRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
public class PortalapiApplicationTests {

    @Autowired
    private MockMvc mockMvc;
    @Mock
    private ModelMapper modelMapper;

    @Mock
    private UnitRepository unitRepository;
    @InjectMocks
    private UnitServiceImpl unitService;

    @Mock
    private CountryRepository countryRepository;
    @InjectMocks
    private CountryServiceImpl countryService;


//    @Test
//    void indexPage() throws Exception {
//        this.mockMvc.perform(get("/"))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(content().string(containsString("Welcome to")));
//    }

    @Test
    void countryService() {
        // 0. ensure is not null
        assertThat(countryRepository).isNotNull();
        assertThat(countryService).isNotNull();

        // 1. create mock data
        List<Country> countryMockData = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            countryMockData.add(new Country("country code " + i, "country name " + i));
        }

        // 2. define behavior of Repository
        when(countryRepository.findAll()).thenReturn(countryMockData);

        // 3. call service method
        List<CountryDto> countryData = countryService.getAll();

        // 4. assert the result
        assertThat(countryData.size()).isEqualTo(countryMockData.size());

        // 4.1 ensure repository is called
        verify(countryRepository).findAll();
    }


    @Test
    void unitService() {
        // 1. create mock data
        List<Unit> mockBooks = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            mockBooks.add(new Unit("unit name " + i));
        }
        UnitInputDto input = new UnitInputDto(null, "unit name");
        UnitDto output = new UnitDto("unit name");
        Long deleteId = 1L;

        // 2. define behavior of Repository
        when(unitRepository.findAll()).thenReturn(mockBooks);
        when(unitService.createOrEdit(input)).thenReturn(output);
        willDoNothing().given(unitRepository).deleteById(deleteId);

        // 3. call service method
        List<UnitDto> actualBooks = unitService.getAll();
        UnitDto outputData = unitService.createOrEdit(input);
        unitService.deleteById(deleteId);

        // 4. assert the result
        assertThat(actualBooks.size()).isEqualTo(mockBooks.size());
        assertThat(outputData).isNotNull();
        assertThat(outputData.getName()).contains(input.getName());

        // 4.1 ensure repository is called
        verify(unitRepository).findAll();
        verify(unitRepository, times(1)).deleteById(deleteId);
    }


    @Test
    void unitGetAllService() {
        // 1. create mock data
        List<Unit> mocks = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            mocks.add(new Unit("unit name " + i));
        }

        // 2. define behavior of Repository
        when(unitRepository.findAll()).thenReturn(mocks);

        // 3. call service method
        List<UnitDto> data = unitService.getAll();

        // 4. assert the result
        assertThat(data.size()).isEqualTo(mocks.size());

        // 4.1 ensure repository is called
        verify(unitRepository).findAll();
    }

    @Test
    void unitGetByIdService() {
        // 1. create mock data
        Unit mock = new Unit("unit name");
        UnitDto mockDto = new UnitDto("unit name");

        // 2. define behavior of Repository
        when(unitRepository.findById(1L)).thenReturn(Optional.of(mock));
        when(unitService.getById(1L)).thenReturn(mockDto);

        // 3. call service method
        UnitDto data = unitService.getById(1L);

        // 4. assert the result
        assertThat(data).isNotNull();

        // 4.1 ensure repository is called
        verify(unitRepository, times(2)).findById(1L);
    }

    @Test
    void unitDeleteByIdService() {
        // 1. create mock data
        Long mockId = 1L;

        // 2. define behavior of Repository
        willDoNothing().given(unitRepository).deleteById(mockId);

        // 3. call service method
        unitService.deleteById(mockId);

        // 4. assert the result

        // 4.1 ensure repository is called
        verify(unitRepository).deleteById(mockId);
    }

    @Test
    void unitCreateService() {
        // 1. create mock data
        Unit mock = new Unit("unit name");
        UnitInputDto mockInput = new UnitInputDto(null, "unit name");
        UnitDto mockOutput = new UnitDto("unit name");

        // 2. define behavior of Repository
        when(unitRepository.save(mock)).thenReturn(mock);
//        when(unitService.createOrEdit(mockInput)).thenReturn(mockOutput);

        // 3. call service method
//        UnitDto outputData = unitService.createOrEdit(mockInput);
        Unit outputData2 = unitService.createUnit(mock);

        // 4. assert the result
//        assertThat(outputData).isNotNull();
//        assertThat(outputData.getName()).contains(mockInput.getName());
        assertThat(outputData2).isNotNull();
        assertThat(outputData2.getName()).contains(mockInput.getName());

        // 4.1 ensure repository is called
        verify(unitRepository).save(mock);
    }

    @Test
    void unitCreateService2() {
        // 1. create mock data
        Unit mock = new Unit("unit name");
        mock.setId(1L);
        UnitDto mockDto = new UnitDto("unit name");
        mockDto.setId(1L);
        UnitInputDto mockInput = new UnitInputDto(1L, "unit name");

        // 2. define behavior of Repository
        when(unitRepository.save(mock)).thenReturn(mock);
//        when(unitRepository.findByName(anyString())).thenReturn(Optional.of(mock));
        when(unitService.createOrEdit(mockInput)).thenReturn(mockDto);
//        when(unitService.createUnit2(mockInput)).thenReturn(mockDto);

        // 3. call service method
//        Unit outputData = unitRepository.save(mock);
//        UnitDto outputData = unitService.createUnit2(mockInput);
        UnitDto outputData = unitService.createOrEdit(mockInput);
//        Unit outputData2 = unitRepository.findByName(mock.getName()).get();

        // 4. assert the result
        assertThat(outputData).isNotNull();
        assertThat(outputData.getName()).contains(mock.getName());
//        assertThat(outputData2).isNotNull();
//        assertThat(outputData2.getName()).contains(mock.getName());

        // 4.1 ensure repository is called
        verify(unitRepository).save(mock);
//        verify(unitRepository,times(1)).save(mock);
    }


}
