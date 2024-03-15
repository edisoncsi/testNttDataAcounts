package eco.com.spring.mcsv.account.mov.controllers;

import eco.com.spring.mcsv.account.mov.dtos.MovimientoDto;
import eco.com.spring.mcsv.account.mov.models.Cuenta;
import eco.com.spring.mcsv.account.mov.models.Historial;
import eco.com.spring.mcsv.account.mov.models.Movimiento;
import eco.com.spring.mcsv.account.mov.responses.BaseResponse;
import eco.com.spring.mcsv.account.mov.services.CuentaService;
import eco.com.spring.mcsv.account.mov.services.HIstorialService;
import eco.com.spring.mcsv.account.mov.services.MovimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
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
@RequestMapping("/movimientos")
public class MovimientosController {
    private final Logger logger = Logger.getLogger(MovimientosController.class.getName());

    @Autowired
    private MovimientoService movimientoService;

    @Autowired
    private HIstorialService hIstorialService;

    @Autowired
    private CuentaService cuentaService;


    @GetMapping
    public ResponseEntity<?> list() {
        return ResponseEntity.ok(movimientoService.listar());
    }

    @PostMapping
    public ResponseEntity<?> createMovimiento(@Valid @RequestBody MovimientoDto command, BindingResult result) {

        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(validationErrors(result));
        }

        try {
            Optional<Cuenta> cuenta = cuentaService.numeroCuenta(command.getNumeroCuenta());
            if(cuenta.isPresent()){
                Cuenta cuen = cuenta.get();

                Optional<Movimiento> movimientoFromDb = movimientoService.listarMovimiento(cuen.getId());
                if (movimientoFromDb.isPresent())
                {
                    try {
                        Optional<Movimiento> movimiento = movimientoService.updateMovimiento(cuen.getNumeroCuenta(), command);
                        if (movimiento.isPresent())
                            return new ResponseEntity<>(new BaseResponse("Se actualizo exitosamente"), HttpStatus.OK);
                        else
                            return new ResponseEntity<>(new BaseResponse("Datos incorrectos en el valor"), HttpStatus.NOT_FOUND);
                    } catch (IllegalArgumentException e) {
                        return handleBadRequest(e);
                    } catch (DataAccessException e) {
                        return handleInternalError(e);
                    }
                }
                else{
                    float saldo =cuen.getSaldoInicial() + command.getValor();

                    if( saldo < 0  )
                    {
                        return new ResponseEntity<>(new BaseResponse("No existe saldo suficiente"), HttpStatus.NOT_FOUND);
                    }

                    Movimiento movimiento = new Movimiento();
                    movimiento.setTipoMovimiento(command.getTipoMovimiento());
                    movimiento.setSaldo(saldo);
                    movimiento.setEstado(command.getEstado());
                    movimiento.setCuentaId(cuen);
                    movimientoService.insert(movimiento);

                    Historial historial = new Historial();
                    historial.setValor(command.getValor());
                    historial.setFecha(new Date());
                    historial.setMovimientoId(movimiento);
                    historial.setSaldo(saldo);
                    hIstorialService.insert(historial);
                }
            }else{
                return new ResponseEntity<>(new BaseResponse("La cuenta no existe"), HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(new BaseResponse("Datos creados exitosamente"), HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return handleBadRequest(e);
        } catch (DataAccessException e) {
            return handleInternalError(e);
        }
    }

    @PutMapping({"/{cuentaNumero}"})
    public ResponseEntity<?> updateMovimiento(@PathVariable("cuentaNumero") String cuentaNumero, @Valid @RequestBody MovimientoDto command, BindingResult result){
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(validationErrors(result));
        }

        try {
            Optional<Movimiento> movimiento = movimientoService.updateMovimiento(cuentaNumero, command);
            if (movimiento.isPresent())
                return new ResponseEntity<>(new BaseResponse("Se actualizo exitosamente"), HttpStatus.OK);
            else
                return new ResponseEntity<>(new BaseResponse("La cuenta no existe"), HttpStatus.NOT_FOUND);
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
