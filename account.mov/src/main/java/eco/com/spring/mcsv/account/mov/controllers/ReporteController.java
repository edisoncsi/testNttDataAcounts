package eco.com.spring.mcsv.account.mov.controllers;

import eco.com.spring.mcsv.account.mov.dtos.DepositoRetiros;
import eco.com.spring.mcsv.account.mov.dtos.ReporteDto;
import eco.com.spring.mcsv.account.mov.services.MovimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author edisoncsi on 14/3/24
 * @project TestBackendJava
 */

@RestController
@RequestMapping("/reportes")
public class ReporteController {

    @Autowired
    private MovimientoService movimientoService;

    @PostMapping
    public ResponseEntity<?> listReport(@Valid @RequestBody DepositoRetiros findData, BindingResult result) {

        if (result.hasErrors()) {
            return (ResponseEntity<?>) validationErrors(result);
        }

        List<Object[]> listReport = movimientoService.listarMovimientos(findData);
        if (listReport.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<ReporteDto> newReport = listReport.stream()
                .map(ss -> {
                    ReporteDto reporteDto = new ReporteDto();
                    reporteDto.setFecha(String.valueOf(ss[0]));
                    reporteDto.setCliente(String.valueOf(ss[1]));
                    reporteDto.setNumeroCuenta(String.valueOf(ss[2]));
                    reporteDto.setTipoCuenta(String.valueOf(ss[3]));
                    reporteDto.setSaldoInicial(String.valueOf(ss[4]));
                    reporteDto.setEstado(String.valueOf(ss[5]));
                    reporteDto.setMovimiento(String.valueOf(ss[6]));
                    reporteDto.setSaldo(String.valueOf(ss[7]));
                    return reporteDto;
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(newReport);

    }

    private Map<String, String> validationErrors(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach(err -> errors.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage()));
        return errors;
    }
}
