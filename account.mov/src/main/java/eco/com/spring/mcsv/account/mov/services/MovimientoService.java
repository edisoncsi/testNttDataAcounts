package eco.com.spring.mcsv.account.mov.services;

import eco.com.spring.mcsv.account.mov.dtos.DepositoRetiros;
import eco.com.spring.mcsv.account.mov.dtos.MovimientoDto;
import eco.com.spring.mcsv.account.mov.dtos.ReporteDto;
import eco.com.spring.mcsv.account.mov.models.Movimiento;

import java.util.List;
import java.util.Optional;

/**
 * @author edisoncsi on 14/3/24
 * @project TestBackendJava
 */

public interface MovimientoService {
    List<Movimiento> listar();
    Optional<Movimiento> listarMovimiento(Long id);

    void insert(Movimiento movimiento);

    Optional<Movimiento> updateMovimiento(String id, MovimientoDto movimiento);

    List<Object[]> listarMovimientos(DepositoRetiros dto);

    boolean getMovimientoById(Long id);

    void deleteMovimiento(Long id);
}
