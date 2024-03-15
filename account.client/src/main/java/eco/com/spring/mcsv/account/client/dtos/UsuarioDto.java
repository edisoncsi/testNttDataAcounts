package eco.com.spring.mcsv.account.client.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author edisoncsi on 14/3/24
 * @project TestBackendJava
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDto {

    private String nombre;
    private String genero;
    private int edad;
    private String identificacion;
    private String direccion;
    private String telefono;
    private String password;
    private String estado;
}
