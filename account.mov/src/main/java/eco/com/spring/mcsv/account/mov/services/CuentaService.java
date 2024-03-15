package eco.com.spring.mcsv.account.mov.services;

import eco.com.spring.mcsv.account.mov.dtos.CuentaDto;
import eco.com.spring.mcsv.account.mov.models.Cuenta;

import java.util.List;
import java.util.Optional;

/**
 * @author edisoncsi on 14/3/24
 * @project TestBackendJava
 */

public interface CuentaService {
    List<Cuenta> listar();
    Optional<Cuenta> listarCuenta(Long id);

    Optional<Cuenta> numeroCuenta(String numCuenta);

    void insert(Cuenta cuenta);

    Optional<Cuenta> updateCuenta(Long id, CuentaDto cuenta);

    boolean getCuentaById(Long id);

    void deleteCuenta(Long id);
}
