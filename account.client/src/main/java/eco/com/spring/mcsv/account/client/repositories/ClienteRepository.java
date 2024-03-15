package eco.com.spring.mcsv.account.client.repositories;

import eco.com.spring.mcsv.account.client.models.Cliente;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author edisoncsi on 13/3/24
 * @project TestBackendJava
 */

@Repository
public interface ClienteRepository extends CrudRepository<Cliente,Long> {
      @Query(value = "select a.id from clientes a, personas p where a.persona_id = p.id and p.nombre = ?1" ,  nativeQuery = true)
      int findByNombre(String nombre);
}