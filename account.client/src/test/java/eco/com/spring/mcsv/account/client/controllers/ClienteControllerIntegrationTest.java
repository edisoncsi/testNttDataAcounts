package eco.com.spring.mcsv.account.client.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import eco.com.spring.mcsv.account.client.dtos.UsuarioDto;

import eco.com.spring.mcsv.account.client.models.Cliente;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.validation.BindingResult;

/**
 * @author edisoncsi on 14/3/24
 * @project TestBackendJava
 */

@ExtendWith(SpringExtension.class)
@WebMvcTest(ClienteController.class)

public class ClienteControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private ClienteController clienteController;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    //@Test
    public void testCreateUsuario_ValidCommand() throws Exception {
        // Simula un DTO de usuario válido
        UsuarioDto usuarioDto = new UsuarioDto();
        usuarioDto.setNombre("Nombre de Prueba");

        // Realiza la solicitud POST con el DTO de usuario como JSON
        mockMvc.perform(post("/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(usuarioDto)))
                // Verifica que se reciba una respuesta exitosa con el código de estado HTTP 201 CREATED
                .andExpect(status().isCreated());
    }

   // @Test
    public void testCreateUsuario_InvalidCommand() throws Exception {
        // Simula un DTO de usuario inválido (sin nombre)
        UsuarioDto usuarioDto = null;

        // Realiza la solicitud POST con el DTO de usuario como JSON
        mockMvc.perform(post("/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(usuarioDto)))
                // Verifica que se reciba una respuesta de error de solicitud con el código de estado HTTP 400 BAD REQUEST
                .andExpect(status().isBadRequest());
    }


   // @Test
    public void testCreateTaskValidInput() {
        // Create a sample Task
        UsuarioDto usuarioDto = createMockPersona( "nombre",  "genero", 211, "identificacion", "direccion", "telefono", "password",  "estado");

        UsuarioDto userDto = new UsuarioDto();
        userDto.setNombre(usuarioDto.getNombre());
        userDto.setGenero(usuarioDto.getGenero());
        userDto.setEdad(usuarioDto.getEdad());
        userDto.setIdentificacion(usuarioDto.getIdentificacion());
        userDto.setDireccion(usuarioDto.getDireccion());
        userDto.setTelefono(usuarioDto.getTelefono());
        userDto.setPassword(usuarioDto.getPassword());
        userDto.setEstado(usuarioDto.getEstado());

        Cliente cliente = new Cliente();
        cliente.setPassword(usuarioDto.getPassword());
        cliente.setEstado(usuarioDto.getEstado());

        // Crea un BindingResult simulado sin errores
        BindingResult mockBindingResult = mock(BindingResult.class);
        when(mockBindingResult.hasErrors()).thenReturn(false);

        // Llame al método createTask con entrada válida
        ResponseEntity<?> responseEntity = clienteController.createUsuario(usuarioDto, mockBindingResult);

        // Verifique que la respuesta tenga un estado 201 Creado
        Assertions.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());

    }


    // Método utilitario para convertir un objeto a JSON
    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private UsuarioDto createMockPersona(String nombre, String genero, int edad,String identificacion,String direccion,String telefono,String password, String estado) {
        UsuarioDto persona = new UsuarioDto();
        persona.setNombre(nombre);
        persona.setGenero(genero);
        persona.setEdad(edad);
        persona.setIdentificacion(identificacion);
        persona.setDireccion(direccion);
        persona.setTelefono(telefono);
        persona.setPassword(password);
        persona.setEstado(estado);
        return persona;

    }
}
