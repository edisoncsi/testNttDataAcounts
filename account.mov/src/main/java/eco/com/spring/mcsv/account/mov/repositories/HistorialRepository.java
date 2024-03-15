package eco.com.spring.mcsv.account.mov.repositories;

import eco.com.spring.mcsv.account.mov.models.Historial;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author edisoncsi on 14/3/24
 * @project TestBackendJava
 */

@Repository
public interface HistorialRepository extends CrudRepository <Historial, Long> {
}
