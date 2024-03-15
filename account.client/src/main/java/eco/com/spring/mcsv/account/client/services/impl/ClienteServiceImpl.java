package eco.com.spring.mcsv.account.client.services.impl;

import eco.com.spring.mcsv.account.client.dtos.UsuarioDto;
import eco.com.spring.mcsv.account.client.models.Cliente;
import eco.com.spring.mcsv.account.client.models.Persona;
import eco.com.spring.mcsv.account.client.repositories.ClienteRepository;
import eco.com.spring.mcsv.account.client.repositories.PersonaRepository;
import eco.com.spring.mcsv.account.client.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author edisoncsi on 13/3/24
 * @project TestBackendJava
 */

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private PersonaRepository personaRepository;
    @Override
    public List<Cliente> listar() {
        return (List<Cliente>) clienteRepository.findAll();
    }

    @Override
    public Optional<Cliente> listarCliente(Long id) {
        return clienteRepository.findById(id);
    }

    @Override
    public int listarClienteNombre(String nombreCliente) {
        return clienteRepository.findByNombre(nombreCliente);
    }

    @Override
    public void insert(Cliente cliente) {
        clienteRepository.save(cliente);
    }

    @Override
    public Optional<Cliente> updateCliente(Long id, UsuarioDto cliente) {

        Optional<Cliente> clientFromDb = clienteRepository.findById(id);

        if(clientFromDb.isPresent()){
            Cliente _cliente = clientFromDb.get();
            Optional<Persona> personaFromDb = personaRepository.findById(_cliente.getPersonaId().getId());

            if(personaFromDb.isPresent()) {
                Persona persona = personaFromDb.get();
                persona.setNombre(cliente.getNombre());
                persona.setGenero(cliente.getGenero());
                persona.setEdad(cliente.getEdad());
                persona.setIdentificacion(cliente.getIdentificacion());
                persona.setDireccion(cliente.getDireccion());
                persona.setTelefono(cliente.getTelefono());

                _cliente.setPassword(cliente.getPassword());
                _cliente.setEstado(cliente.getEstado());
                _cliente.setPersonaId(persona);
                clienteRepository.save(_cliente);
            }
        }
        return clientFromDb;
    }

    @Override
    public boolean getClienteById(Long id) {
        return clienteRepository.findById(id).isPresent();
    }

    @Override
    public void deleteCLiente(Long id) {
        clienteRepository.deleteById(id);
    }
}
