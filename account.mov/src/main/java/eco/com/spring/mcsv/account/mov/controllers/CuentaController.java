package eco.com.spring.mcsv.account.mov.controllers;


import eco.com.spring.mcsv.account.mov.dtos.CuentaDto;
import eco.com.spring.mcsv.account.mov.models.Cuenta;
import eco.com.spring.mcsv.account.mov.responses.BaseResponse;
import eco.com.spring.mcsv.account.mov.services.CuentaService;
import eco.com.spring.mcsv.account.mov.services.KafkaConsumerService;
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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author edisoncsi on 14/3/24
 * @project TestBackendJava
 */

@RestController
@RequestMapping("/cuentas")
public class CuentaController {
    private final Logger logger = Logger.getLogger(CuentaController.class.getName());

    @Autowired
    private CuentaService cuentaService;

    @Autowired
    private KafkaConsumerService kafkaConsumerService;

    @GetMapping
    public ResponseEntity<?> list() {
        return ResponseEntity.ok(cuentaService.listar());
    }

    @GetMapping({"/{numCuenta}"})
    public ResponseEntity<?> numeroCuenta(@PathVariable("numCuenta") String numCuenta) {
        return ResponseEntity.ok(cuentaService.numeroCuenta(numCuenta));
    }

    @PostMapping
    public ResponseEntity<?> createCuenta(@Valid @RequestBody CuentaDto command, BindingResult result) {

        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(validationErrors(result));
        }

        try {
            Long clienteId= kafkaConsumerService.recibirMensaje("Edison");
            if(null != clienteId)
            {
                Optional<Cuenta> temp = cuentaService.numeroCuenta(command.getNumeroCuenta());

                if(temp.isPresent()){
                    return new ResponseEntity<>(new BaseResponse("El numero de cuenta ya existe"), HttpStatus.NOT_FOUND);
                }

                if(command.getSaldoInicial() <= 0.0f)
                {
                    return new ResponseEntity<>(new BaseResponse("El saldo inicial debe ser positivo"), HttpStatus.NOT_FOUND);
                }

                Cuenta cuenta = new Cuenta();
                cuenta.setNumeroCuenta(command.getNumeroCuenta());
                cuenta.setTipoCuenta(command.getTipoCuenta());
                cuenta.setSaldoInicial(command.getSaldoInicial());
                cuenta.setEstado(command.getEstado());
                cuenta.setCliente(command.getClienteNombre());
                cuentaService.insert(cuenta);
            }
            else{
                return new ResponseEntity<>(new BaseResponse("El cliente no existe"), HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(new BaseResponse("Datos creados exitosamente"), HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return handleBadRequest(e);
        } catch (DataAccessException e) {
            return handleInternalError(e);
        }
    }

    @PutMapping({"/{cuentaId}"})
    public ResponseEntity<?> updateCuenta(@PathVariable("cuentaId") Long cuentaId, @Valid @RequestBody CuentaDto command, BindingResult result){
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(validationErrors(result));
        }

        try {
            Optional<Cuenta> cuenta = cuentaService.updateCuenta(cuentaId, command);
            if (cuenta.isPresent())
                return new ResponseEntity<>(new BaseResponse("Se actualizo exitosamente"), HttpStatus.OK);
            else
                return new ResponseEntity<>(new BaseResponse("La cuenta no existe"), HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            return handleBadRequest(e);
        } catch (DataAccessException e) {
            return handleInternalError(e);
        }
    }

    @DeleteMapping({"/{cuentaId}"})
    public ResponseEntity<?> delete(@PathVariable("cuentaId") Long cuentaId) {
        try {
            if (cuentaService.getCuentaById(cuentaId)){
                cuentaService.deleteCuenta(cuentaId);
                return new ResponseEntity<>(new BaseResponse("Se ha eliminado exitosamente"), HttpStatus.OK);
            }
            else
                return new ResponseEntity<>(new  BaseResponse("El cliente no existe"), HttpStatus.NOT_FOUND);
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
