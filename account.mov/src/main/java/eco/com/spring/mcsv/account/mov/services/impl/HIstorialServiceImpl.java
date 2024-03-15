package eco.com.spring.mcsv.account.mov.services.impl;

import eco.com.spring.mcsv.account.mov.models.Historial;
import eco.com.spring.mcsv.account.mov.repositories.HistorialRepository;
import eco.com.spring.mcsv.account.mov.services.HIstorialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author edisoncsi on 14/3/24
 * @project TestBackendJava
 */

@Service
public class HIstorialServiceImpl implements HIstorialService {

    @Autowired
    private HistorialRepository historialRepository;
    @Override
    public List<Historial> listar() {
        return (List<Historial>) historialRepository.findAll();
    }

    @Override
    public void insert(Historial historial) {
        historialRepository.save(historial);
    }
}
