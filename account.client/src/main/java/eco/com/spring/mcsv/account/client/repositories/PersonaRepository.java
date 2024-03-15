package eco.com.spring.mcsv.account.client.repositories;

import eco.com.spring.mcsv.account.client.models.Persona;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author edisoncsi on 14/3/24
 * @project TestBackendJava
 */

@Repository

public interface PersonaRepository extends CrudRepository<Persona,String> {

}
