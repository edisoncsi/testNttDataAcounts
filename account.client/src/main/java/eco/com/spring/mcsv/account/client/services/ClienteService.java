package eco.com.spring.mcsv.account.client.services;

import eco.com.spring.mcsv.account.client.dtos.UsuarioDto;
import eco.com.spring.mcsv.account.client.models.Cliente;

import java.util.List;
import java.util.Optional;

/**
 * @author edisoncsi on 13/3/24
 * @project TestBackendJava
 */

public interface ClienteService {
    List<Cliente> listar();
    Optional<Cliente> listarCliente(Long id);

    int listarClienteNombre(String nombreCliente);

    void insert(Cliente cliente);

    Optional<Cliente> updateCliente(Long id, UsuarioDto cliente);

    boolean getClienteById(Long id);

    void deleteCLiente(Long id);
}
