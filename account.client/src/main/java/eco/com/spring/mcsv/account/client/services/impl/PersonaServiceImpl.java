package eco.com.spring.mcsv.account.client.services.impl;

import eco.com.spring.mcsv.account.client.models.Persona;
import eco.com.spring.mcsv.account.client.repositories.PersonaRepository;
import eco.com.spring.mcsv.account.client.services.PersonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author edisoncsi on 14/3/24
 * @project TestBackendJava
 */

@Service
public class PersonaServiceImpl implements PersonaService {

    @Autowired
    private PersonaRepository personaRepository;
    @Override
    public void insert(Persona persona) {
        personaRepository.save(persona);
    }
}
