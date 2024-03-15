package eco.com.spring.mcsv.account.mov.services;

import eco.com.spring.mcsv.account.mov.models.Historial;

import java.util.List;

/**
 * @author edisoncsi on 14/3/24
 * @project TestBackendJava
 */

public interface HIstorialService {
    List<Historial> listar();

    void insert(Historial historial);
}
