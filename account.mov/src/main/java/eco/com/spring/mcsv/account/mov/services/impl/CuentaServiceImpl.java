package eco.com.spring.mcsv.account.mov.services.impl;

import eco.com.spring.mcsv.account.mov.dtos.CuentaDto;
import eco.com.spring.mcsv.account.mov.models.Cuenta;
import eco.com.spring.mcsv.account.mov.repositories.CuentaRepository;
import eco.com.spring.mcsv.account.mov.services.CuentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author edisoncsi on 14/3/24
 * @project TestBackendJava
 */

@Service
public class CuentaServiceImpl implements CuentaService {

    @Autowired
    private CuentaRepository cuentaRepository;
    @Override
    public List<Cuenta> listar() {
        return (List<Cuenta>) cuentaRepository.findAll();
    }

    @Override
    public Optional<Cuenta> listarCuenta(Long id) {
        return cuentaRepository.findById(id);
    }

    @Override
    public Optional<Cuenta> numeroCuenta(String numCuenta) {
        return cuentaRepository.findByNumeroCuenta(numCuenta);
    }

    @Override
    public void insert(Cuenta cuenta) {
        cuentaRepository.save(cuenta);
    }

    @Override
    public Optional<Cuenta> updateCuenta(Long id, CuentaDto cuenta) {
        Optional<Cuenta> cuentaFromDb = cuentaRepository.findById(id);

        if(cuentaFromDb.isPresent()){
            Cuenta _cuenta = cuentaFromDb.get();
            _cuenta.setNumeroCuenta(cuenta.getNumeroCuenta());
            _cuenta.setTipoCuenta(cuenta.getTipoCuenta());
            _cuenta.setSaldoInicial(cuenta.getSaldoInicial());
            _cuenta.setCliente(cuenta.getClienteNombre());
            _cuenta.setEstado(cuenta.getEstado());
            cuentaRepository.save(_cuenta);
        }
        return cuentaFromDb;
    }

    @Override
    public boolean getCuentaById(Long id) {
        return cuentaRepository.findById(id).isPresent();
    }

    @Override
    public void deleteCuenta(Long id) {
        cuentaRepository.deleteById(id);
    }
}
