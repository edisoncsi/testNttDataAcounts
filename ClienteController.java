package eco.com.spring.mcsv.account.client.controllers;

import eco.com.spring.mcsv.account.client.dtos.UsuarioDto;
import eco.com.spring.mcsv.account.client.models.Cliente;
import eco.com.spring.mcsv.account.client.models.Persona;
import eco.com.spring.mcsv.account.client.responses.BaseResponse;

import eco.com.spring.mcsv.account.client.services.ClienteService;
import eco.com.spring.mcsv.account.client.services.PersonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author edisoncsi on 13/3/24
 * @project TestBackendJava
 */

@RestController
@RequestMapping("/clientes")
@CrossOrigin(origins = "http://host.docker.internal:5001")
public class ClienteController {
    private final Logger logger = Logger.getLogger(ClienteController.class.getName());

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private PersonaService personaService;


    @GetMapping
    public ResponseEntity<?> list() {
        return ResponseEntity.ok(clienteService.listar());
    }

    @GetMapping({"/{nombreCliente}"})
    public ResponseEntity<?> listById(@PathVariable("nombreCliente") String nombreCliente) {
        return ResponseEntity.ok(clienteService.listarClienteNombre(nombreCliente));
    }

    @PostMapping
    public ResponseEntity<?> createUsuario(@Valid @RequestBody UsuarioDto command, BindingResult result) {
        UUID uuid = UUID.randomUUID();
        String uuidAsString = uuid.toString();

        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(validationErrors(result));
        }

        try {
            Persona persona = new Persona();
            persona.setId(uuidAsString);
            persona.setNombre(command.getNombre());
            persona.setGenero(command.getGenero());
            persona.setEdad(command.getEdad());
            persona.setIdentificacion(command.getIdentificacion());
            persona.setDireccion(command.getDireccion());
            persona.setTelefono(command.getTelefono());
            personaService.insert(persona);

            Cliente cliente =new Cliente();
            cliente.setPassword(command.getPassword());
            cliente.setEstado(command.getEstado());
            cliente.setPersonaId(persona);
            clienteService.insert(cliente);

            return new ResponseEntity<>(new BaseResponse("Datos creados exitosamente"), HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return handleBadRequest(e);
        } catch (DataAccessException e) {
            return handleInternalError(e);
        }
    }

    @PutMapping({"/{clienteId}"})
    public ResponseEntity<?> updateCliente(@PathVariable("clienteId") Long clienteId, @Valid @RequestBody UsuarioDto command, BindingResult result){
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(validationErrors(result));
        }

        try {
            Optional<Cliente> cliente = clienteService.updateCliente(clienteId, command);
            if (cliente.isPresent())
                return new ResponseEntity<>(new BaseResponse("Se actualizo exitosamente"), HttpStatus.OK);
            else
                return new ResponseEntity<>(new BaseResponse("El cliente no existe"), HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            return handleBadRequest(e);
        } catch (DataAccessException e) {
            return handleInternalError(e);
        }
    }

    @DeleteMapping({"/{clienteId}"})
    public ResponseEntity<?> delete(@PathVariable("clienteId") Long clienteId) {
        try {
            if (clienteService.getClienteById(clienteId)){
                clienteService.deleteCLiente(clienteId);
                return new ResponseEntity<>(new BaseResponse("Se ha eliminado exitosamente"), HttpStatus.OK);
            }
            else
                return new ResponseEntity<>(new BaseResponse("El cliente no existe"), HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            return handleBadRequest(e);
        } catch (DataAccessException e) {
            return handleInternalError(e);
        }
    }

    private ResponseEntity<?> handleBadRequest(Exception e) {
        logger.log(Level.WARNING,  String.format("Bad request: %s " , e.getMessage()));
        return ResponseEntity.badRequest().body(new BaseResponse(e.getMessage()));
    }

    private ResponseEntity<?> handleInternalError(Exception e) {
        logger.log(Level.SEVERE, String.format("Internal server error:  %s",  e.getMessage()), e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new BaseResponse("Internal server error"));
    }

    private Map<String, String> validationErrors(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach(err -> errors.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage()));
        return errors;
    }

}
