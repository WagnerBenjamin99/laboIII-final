package ar.edu.utn.frbb.tup.controller;

import ar.edu.utn.frbb.tup.business.CarreraService;
import ar.edu.utn.frbb.tup.business.MateriaService;
import ar.edu.utn.frbb.tup.model.Carrera;
import ar.edu.utn.frbb.tup.model.Materia;
import ar.edu.utn.frbb.tup.model.dto.CarreraDto;
import ar.edu.utn.frbb.tup.model.dto.MateriaDto;
import ar.edu.utn.frbb.tup.persistence.exception.CarreraBadRequestException;
import ar.edu.utn.frbb.tup.persistence.exception.CarreraNotFoundException;
import ar.edu.utn.frbb.tup.persistence.exception.MateriaNotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@WebMvcTest(CarreraController.class)
class CarreraControllerTest {


    @MockBean
    CarreraController carreraController;

    @MockBean
    CarreraService carreraService;

    @Autowired
    private MockMvc mockMvc;

    private static ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(carreraController).build();
    }
    @Test
    void crearCarrera_Exception() throws Exception {

        when(carreraService.crearCarrera(any(CarreraDto.class))).thenReturn(new Carrera());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/carrera/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"nombre\" : \"TUP\",\n" +
                                "    \"departamentoId\" : \"1\", \n" +
                                "    \"cantidadCuatrimestres\" : cuatro,\n" + 
                                "}")
                        .accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    void crearCarrera_Success() throws Exception {
        when(carreraService.crearCarrera(any(CarreraDto.class))).thenReturn(new Carrera());
        CarreraDto carreraDto = new CarreraDto();
        carreraDto.setDepartamentoId(1);
        carreraDto.setNombre("TUP");
        carreraDto.setCantidadCuatrimestres(4);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/carrera/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(carreraDto))
                        .accept(MediaType.APPLICATION_JSON)).andExpect(status().is2xxSuccessful())
                .andReturn();

    }

    @Test
    void getAllCarreras_Success() throws Exception {
        Carrera c = new Carrera();
        c.setNombre("carrera 1");

        Carrera c2 = new Carrera();
        c2.setNombre("carrera 2");

        ArrayList<Carrera> carreras = new ArrayList<>();
        carreras.add(c);
        carreras.add(c2);

        when(carreraService.getAllCarreras()).thenReturn(carreras);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/carrera")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andReturn();

    }

    @Test
    void getAllCarreras_Exception() throws Exception {

        when(carreraController.getAllCarreras()).thenThrow(CarreraNotFoundException.class);
        assertThrows(CarreraNotFoundException.class, () -> {
            carreraController.getAllCarreras();
        });
    }

    @Test
    void getCarreraById_Success() throws Exception {
        Carrera c = new Carrera();
        c.setNombre("carrera 1");

        when(carreraService.getCarreraById(anyInt())).thenReturn(c);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/carrera/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void getCarreraById_Exception() throws Exception {

        when(carreraController.getCarreraById(anyInt())).thenThrow(CarreraNotFoundException.class);
        assertThrows(CarreraNotFoundException.class, () -> {
            carreraController.getCarreraById(1);
        });
    }

    @Test
    void agregarMateria_Success() throws Exception {
        Carrera c = new Carrera();
        c.setNombre("carrera 1");
        Materia m = new Materia("labo", 1, 1, null);
        c.agregarMateria(m);
        c.setId(1);


        when(carreraService.agregarMateria(anyInt(), anyInt())).thenReturn(c);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/carrera/{idCarrera}/materias/{idMateria}", 1, 2)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andReturn();
    }
    @Test
    void agregarMateria_Exception() throws Exception {
        when(carreraController.agregarMateria(anyInt(), anyInt())).thenThrow(CarreraBadRequestException.class);
        assertThrows(CarreraBadRequestException.class, () -> {
            carreraController.agregarMateria(1, 1);
        });
    }

    @Test
    void modificarCarrera_Success() throws Exception {
        Carrera c = new Carrera();
        c.setNombre("programacion");
        Map<String, Object> nuevosDatos = new HashMap<>();
        nuevosDatos.put("nombre", "programacion");

        Mockito.when(carreraService.modificarCarrera(nuevosDatos, 0)).thenReturn(c);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.patch("/carrera/{idCarrera}", 0)
                        .content(new ObjectMapper().writeValueAsString(nuevosDatos))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void modificarCarrera_Exception() throws Exception {
        Carrera c = new Carrera();
        c.setNombre("programacion");
        Map<String, Object> nuevosDatos = new HashMap<>();
        nuevosDatos.put("nombre", "programacion");

        Mockito.when(carreraService.modificarCarrera(nuevosDatos, 0)).thenThrow(CarreraBadRequestException.class);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.patch("/carrera/{idCarrera}", 0)
                        .content("{\n" +
                                "    \"algo\" : \"Laboratorio II\",\n" +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    void eliminarCarrera_Success() throws Exception {
        Carrera c = new Carrera();
        c.setNombre("programacion");

        Materia m = new Materia("labo III", 2, 1, null);
        when(carreraService.eliminarCarrera(anyInt())).thenReturn(c);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/carrera/{idCarrera}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void eliminarCarrera_Exception() throws MateriaNotFoundException, CarreraNotFoundException {

        when(carreraController.eliminarCarrera(anyInt())).thenThrow(CarreraNotFoundException.class);
        assertThrows(CarreraNotFoundException.class, () -> {
            carreraController.eliminarCarrera(1);
        });
    }

}