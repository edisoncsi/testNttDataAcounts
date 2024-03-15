package eco.com.spring.mcsv.account.mov.services.impl;

import eco.com.spring.mcsv.account.mov.dtos.DepositoRetiros;
import eco.com.spring.mcsv.account.mov.dtos.MovimientoDto;
import eco.com.spring.mcsv.account.mov.dtos.ReporteDto;
import eco.com.spring.mcsv.account.mov.models.Cuenta;
import eco.com.spring.mcsv.account.mov.models.Historial;
import eco.com.spring.mcsv.account.mov.models.Movimiento;
import eco.com.spring.mcsv.account.mov.repositories.CuentaRepository;
import eco.com.spring.mcsv.account.mov.repositories.HistorialRepository;
import eco.com.spring.mcsv.account.mov.repositories.MovimientoRepository;
import eco.com.spring.mcsv.account.mov.services.MovimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author edisoncsi on 14/3/24
 * @project TestBackendJava
 */

@Service
public class MovimientoServiceImpl implements MovimientoService {

    @Autowired
    private MovimientoRepository movimientoRepository;

    @Autowired
    private HistorialRepository historialRepository;

    @Autowired
    private CuentaRepository cuentaRepository;
    @Override
    public List<Movimiento> listar() {
        return (List<Movimiento>) movimientoRepository.findAll();
    }

    @Override
    public Optional<Movimiento> listarMovimiento(Long id) {
        return movimientoRepository.findById(id);
    }

    @Override
    public void insert(Movimiento movimiento) {
        movimientoRepository.save(movimiento);
    }



    @Override
    public Optional<Movimiento> updateMovimiento(String id, MovimientoDto command) {
        Optional<Cuenta> cuentaFromDb = cuentaRepository.findByNumeroCuenta(id);
        Optional<Movimiento> movimientoFromDb = Optional.empty();

        if(cuentaFromDb.isPresent()){
            Cuenta _cuenta = cuentaFromDb.get();
            movimientoFromDb = movimientoRepository.findById(_cuenta.getId());

            if(movimientoFromDb.isPresent()) {
                Movimiento _movimiento = movimientoFromDb.get();

                float saldo = _movimiento.getSaldo() + command.getValor();

                if( saldo < 0  )
                    return Optional.empty();

                _movimiento.setSaldo(saldo);
                _movimiento.setCuentaId(_cuenta);
                movimientoRepository.save(_movimiento);

                Historial historial = new Historial();
                historial.setValor(command.getValor());
                historial.setFecha(new Date());
                historial.setMovimientoId(_movimiento);
                historial.setSaldo(saldo);
                historialRepository.save(historial);

            }
        }
        return movimientoFromDb;
    }

    @Override
    public List<Object[]> listarMovimientos(DepositoRetiros dto) {
        return movimientoRepository.listMovimientosReporte(dto.getCliente(),dto.getFecha());
    }

    @Override
    public boolean getMovimientoById(Long id) {
        return movimientoRepository.findById(id).isPresent();
    }

    @Override
    public void deleteMovimiento(Long id) {
        movimientoRepository.deleteById(id);
    }
}
