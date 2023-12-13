package ar.edu.utn.frbb.tup.controller;

import ar.edu.utn.frbb.tup.business.MateriaService;
import ar.edu.utn.frbb.tup.model.Materia;
import ar.edu.utn.frbb.tup.model.dto.MateriaDto;
import ar.edu.utn.frbb.tup.persistence.exception.MateriaBadRequestException;
import ar.edu.utn.frbb.tup.persistence.exception.MateriaNotFoundException;
import ar.edu.utn.frbb.tup.persistence.impl.MateriaDaoMemoryImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MateriaController.class)

public class MateriaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MateriaService materiaService;
    @MockBean
    private MateriaController materiaController;

    private static ObjectMapper mapper = new ObjectMapper();


    @BeforeEach
    public void setUp(){

    }
    @Test
    public void crearMateria_Success() throws Exception {
        MateriaDto materiaDto = new MateriaDto();
        materiaDto.setAnio(1);
        materiaDto.setCuatrimestre(2);
        materiaDto.setNombre("Laboratorio II");
        materiaDto.setProfesorId(345);

        Materia m = new Materia("Laboratorio II", 1, 2, null);

        when(materiaService.crearMateria(any(MateriaDto.class))).thenReturn(new Materia());

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/materia/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(materiaDto))
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().is2xxSuccessful())
                .andReturn();

    }

    @Test
    public void crearMateria_Exception() throws Exception {

        when(materiaService.crearMateria(any(MateriaDto.class))).thenReturn(new Materia());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/materia/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"nombre\" : \"Laboratorio II\",\n" +
                                "    \"anio\" : \"segundo\", \n" +
                                "    \"cuatrimestre\" : 1,\n" +
                                "    \"profesorId\" : 2 \n"+
                                "}")
                        .accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    void getMaterias_Success() throws Exception {

        List<Materia> materias = new ArrayList<>();
        when(materiaService.getAllMaterias()).thenReturn(materias);

        mockMvc.perform(MockMvcRequestBuilders.get("/materia")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(materias))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @Test
    public void getMaterias_Exception() throws Exception {
        when(materiaController.getMaterias()).thenThrow(MateriaNotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders.get("/materia")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }
    @Test
    public void modificarMateria_Success() throws Exception {
        HashMap<String, Object> nuevosDatos = new HashMap<>();
        nuevosDatos.put("nombre", "gsisgis");

        Materia esperada = new Materia();
        esperada.setNombre("Laboratorio 1");
        esperada.setAnio(2023);
        esperada.setCuatrimestre(1);
        esperada.setMateriaId(1);

        Mockito.when(materiaService.modificarMateria(nuevosDatos, 0)).thenReturn(esperada);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.patch("/materia/{id}", 0)
                        .content(new ObjectMapper().writeValueAsString(nuevosDatos))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

    }
        @Test
        public void modificarMateria_Exception() throws Exception {
            HashMap<String, Object> nuevosDatos = new HashMap<>();
            nuevosDatos.put("algo", "gsisgis");

            Materia  esperada = new Materia();
            esperada.setNombre("Laboratorio 1");
            esperada.setAnio(2023);
            esperada.setCuatrimestre(1);
            esperada.setMateriaId(1);

            when(materiaService.modificarMateria(nuevosDatos, 1)).thenThrow(MateriaBadRequestException.class);
            MvcResult result = mockMvc.perform(MockMvcRequestBuilders.patch("/materia/{id}", 1)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\n" +
                                    "    \"algo\" : \"Laboratorio II\",\n" +
                                    "}")
                            .accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
                    .andReturn();
    }

    @Test
    public void getMateriasOrdenadas_Success() throws Exception {
        Materia materia1 = new Materia("labo I", 1, 1, null);
        Materia materia2 = new Materia("labo II", 1, 2, null);
        ArrayList<Materia> ordenadas = new ArrayList<>();
        ordenadas.add(materia1);
        ordenadas.add(materia2);

        when(materiaService.ordenarMaterias("nombre_asc")).thenReturn(ordenadas);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/materia/")
                        .param("ordenamiento", "nombre_asc")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void getMateriasOrdenadas_Exception() throws Exception {

        when(materiaService.ordenarMaterias("asc")).thenThrow(MateriaBadRequestException.class);
        assertThrows(MateriaBadRequestException.class, () -> {
            materiaService.ordenarMaterias("asc");
        });

    }

    @Test
    public void getMateriaById_Success() throws Exception {
        Materia m = new Materia("labo III", 2, 1, null);
        when(materiaService.getMateriaById(anyInt())).thenReturn(m);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/materia/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void getMateriaById_Exception() throws Exception {
        Materia m = new Materia("labo III", 2, 1, null);
        when(materiaController.getMateriaById(anyInt())).thenThrow(MateriaNotFoundException.class);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/materia/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void borrarMateria_Success() throws Exception {
        Materia m = new Materia("labo III", 2, 1, null);
        when(materiaService.borrarMateria(anyInt())).thenReturn(m);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/materia/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void borrarMateria_Exception() throws Exception {
        Materia m = new Materia("labo III", 2, 1, null);
        when(materiaController.borrarMateria(anyInt())).thenThrow(MateriaNotFoundException.class);
        assertThrows(MateriaNotFoundException.class, () -> {
            materiaController.borrarMateria(1);
        });
    }


}
