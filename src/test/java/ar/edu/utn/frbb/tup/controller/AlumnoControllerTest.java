package ar.edu.utn.frbb.tup.controller;

import ar.edu.utn.frbb.tup.business.AlumnoService;
import ar.edu.utn.frbb.tup.business.MateriaService;
import ar.edu.utn.frbb.tup.business.exception.AsignaturaBadRequestException;
import ar.edu.utn.frbb.tup.model.Alumno;
import ar.edu.utn.frbb.tup.model.Asignatura;
import ar.edu.utn.frbb.tup.model.Materia;
import ar.edu.utn.frbb.tup.model.dto.AlumnoDto;
import ar.edu.utn.frbb.tup.model.dto.MateriaDto;
import ar.edu.utn.frbb.tup.model.exception.CorrelatividadException;
import ar.edu.utn.frbb.tup.model.exception.CorrelatividadesNoAprobadasException;
import ar.edu.utn.frbb.tup.model.exception.EstadoIncorrectoException;
import ar.edu.utn.frbb.tup.persistence.exception.AlumnoBadRequestException;
import ar.edu.utn.frbb.tup.persistence.exception.AlumnoNotFoundException;
import ar.edu.utn.frbb.tup.persistence.exception.AsignaturaNotFoundException;
import ar.edu.utn.frbb.tup.persistence.exception.MateriaBadRequestException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AlumnoController.class)
class AlumnoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AlumnoService alumnoService;
    @MockBean
    private AlumnoController alumnoController;

    private static ObjectMapper mapper = new ObjectMapper();

    @Test
    void getAlumno_Success() throws Exception {
        Alumno a = new Alumno("benjamin", "wagner", 45376271);
        when(alumnoService.buscarPorId(anyInt())).thenReturn(a);

        mockMvc.perform(MockMvcRequestBuilders.get("/alumno/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getAlumno_Exception() throws Exception {
        Alumno a = new Alumno("benjamin", "wagner", 45376271);
        when(alumnoController.getAlumno(anyInt())).thenThrow(AlumnoNotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders.get("/alumno/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
    @Test
    void crearAlumno_Success() throws Exception {
        AlumnoDto alumnoDto = new AlumnoDto();
        alumnoDto.setNombre("Benja");
        alumnoDto.setApellido("Wagner");
        alumnoDto.setDni(246541641);
        when(alumnoService.crearAlumno(any(AlumnoDto.class))).thenReturn(new Alumno());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/alumno/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(alumnoDto))
                        .accept(MediaType.APPLICATION_JSON)).andExpect(status().is2xxSuccessful())
                .andReturn();
    }

    @Test
    void crearAlumno_Exception() throws Exception {

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/alumno/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"nombre\" : \"Benjamin\",\n" +
                                "    \"apellido\" : \"Wagner\", \n" +
                                "    \"dni\" : \"44793793\", \n" +
                                "}")
                        .accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    void buscarAlumno_Success() throws Exception {
        Alumno a = new Alumno("benjamin", "wagner", 45376271);
        when(alumnoService.buscarPorId(anyInt())).thenReturn(a);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/alumno/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void buscarAlumno_Exception() throws Exception {
        Alumno a = new Alumno("benjamin", "wagner", 45376271);
        when(alumnoController.getAlumno(anyInt())).thenThrow(AlumnoNotFoundException.class);
        assertThrows(AlumnoNotFoundException.class, () -> {
            alumnoController.getAlumno(1);
        });
    }
    @Test
    void borrarAlumno_Success() throws Exception {
        Alumno a = new Alumno("benjamin", "wagner", 45376271);
        when(alumnoService.borrarAlumno(anyInt())).thenReturn(a);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/alumno/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andReturn();
    }
    @Test
    void borrarAlumno_Exception() throws Exception {
        Alumno a = new Alumno("benjamin", "wagner", 45376271);
        when(alumnoController.borrarAlumno(anyInt())).thenThrow(AlumnoNotFoundException.class);
        assertThrows(AlumnoNotFoundException.class, () -> {
            alumnoController.borrarAlumno(1);
        });
    }

    @Test
    void editarAlumno_Success() throws Exception {
        HashMap<String, Object> nuevosDatos = new HashMap<>();
        nuevosDatos.put("nombre", "gsisgis");

        Alumno esperado = new Alumno();
        esperado.setNombre("Laboratorio 1");
        esperado.setApellido("Wagner");
        esperado.setDni(42246524);


        when(alumnoService.editarAlumno(1, nuevosDatos)).thenReturn(esperado);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.patch("/alumno/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(nuevosDatos))
                        .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void editarAlumno_Exception() throws Exception {
        HashMap<String, Object> nuevosDatos = new HashMap<>();
        nuevosDatos.put("algo", "gsisgis");


        when(alumnoService.editarAlumno(1, nuevosDatos)).thenThrow(AlumnoBadRequestException.class);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.patch("/alumno/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"algo\" : \"sshks\",\n" +
                                "}")
                        .accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
                .andReturn();
    }
    @Test
    void pasarNota_Success() throws Exception, EstadoIncorrectoException, CorrelatividadException {
        Asignatura a = new Asignatura(new Materia("Laboratorio II", 1, 2, null));

        when(alumnoService.aprobarAsignatura(anyInt(), anyInt(), anyInt())).thenReturn(a);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/alumno/{idAlumno}/asignatura/{idAsignatura}", 1, 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("estadoAsignatura", String.valueOf('C'))
                        .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andReturn();

    }

    @Test
    void pasarNota_Exception() throws Exception, EstadoIncorrectoException, CorrelatividadException {
        Asignatura a = new Asignatura(new Materia("Laboratorio II", 1, 2, null));

        when(alumnoController.pasarNota(anyInt(), anyInt(), anyInt(), eq('A'))).thenThrow(AlumnoBadRequestException.class);
        assertThrows(AlumnoBadRequestException.class, () -> {
            alumnoController.pasarNota(1, 1, 10, 'A');
        });

    }
}