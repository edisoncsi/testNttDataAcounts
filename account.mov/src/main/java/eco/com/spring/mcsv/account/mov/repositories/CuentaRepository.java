package eco.com.spring.mcsv.account.mov.repositories;

import eco.com.spring.mcsv.account.mov.models.Cuenta;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author edisoncsi on 14/3/24
 * @project TestBackendJava
 */

@Repository
public interface CuentaRepository extends CrudRepository<Cuenta,Long> {
    Optional<Cuenta> findByNumeroCuenta(String numCuenta);
}
