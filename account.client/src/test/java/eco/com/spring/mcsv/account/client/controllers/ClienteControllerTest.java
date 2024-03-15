package eco.com.spring.mcsv.account.client.controllers;

import eco.com.spring.mcsv.account.client.models.Cliente;
import eco.com.spring.mcsv.account.client.models.Persona;
import eco.com.spring.mcsv.account.client.repositories.ClienteRepository;
import eco.com.spring.mcsv.account.client.services.ClienteService;
import eco.com.spring.mcsv.account.client.services.impl.ClienteServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


/**
 * @author edisoncsi on 14/3/24
 * @project TestBackendJava
 */

public class ClienteControllerTest {

    @Mock
    private ClienteService clienteService;

    @InjectMocks
    private ClienteServiceImpl clienteServiceImpl;

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteController clienteController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testListById_ClienteEncontrado() {
        // Simula el nombre del cliente y el cliente encontrado
        String nombreCliente = "ClienteExistente";
        Cliente cliente = new Cliente(1,"AAAAA","TRUE",new Persona());

        // Define el comportamiento del servicio de cliente
        when(clienteService.listarClienteNombre(nombreCliente)).thenReturn(1);

        // Llama al método del controlador
        ResponseEntity<?> response = clienteController.listById(nombreCliente);

        // Verifica que se devuelva una respuesta exitosa con el código de estado HTTP 200 OK
        assertEquals(HttpStatus.OK, response.getStatusCode());
        // Verifica que el cuerpo de la respuesta contiene el cliente esperado
        assertEquals(1, response.getBody());
    }


    @Test
    void testListar() {
        // Mocking del método de servicio para devolver una lista de clientes
        List<Cliente> mockCliente = Arrays.asList(
                createMockCliente(1L, "","",new Persona()),
                createMockCliente(2L, "","",new Persona())
                // Add more sample Price objects as needed for testing different scenarios
        );
        when(clienteService.listar()).thenReturn(mockCliente);

        // Llamar al endpoint para obtener ResponseEntity
        ResponseEntity<?> responseEntity = clienteController.list();

        // Assertions
        assertEquals(200, responseEntity.getStatusCodeValue()); // Assuming 200 for OK status
        assertEquals(mockCliente, responseEntity.getBody()); // Check if the returned list matches the mock data
    }

    @Test
    void testListarTest() {
        // crea data de ejemplo
        List<Cliente> mockCliente = Arrays.asList(
                createMockCliente(1L, "","",new Persona()),
                createMockCliente(2L, "","",new Persona())

        );

        // Mock del comportamiento del repositorio
        when(clienteRepository.findAll()).thenReturn(mockCliente);

        // Llame al método para probar
        List<Cliente> result = clienteServiceImpl.listar();
        assertEquals(2,result.size());
    }



    // Método auxiliar para crear un objeto cliente simulado
    private Cliente createMockCliente(long id, String password, String estado,Persona personaId) {
        Cliente cliente = new Cliente();
        cliente.setId(id);
        cliente.setPassword(password);
        cliente.setEstado(estado);
        cliente.setPersonaId(personaId);
        return cliente;
    }




}