package eco.com.spring.mcsv.account.mov.dtos;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author edisoncsi on 14/3/24
 * @project TestBackendJava
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonSerialize
public class MovimientoDto {

    @NotNull
    @JsonProperty("tipo_movimiento")
    private String tipoMovimiento;

    @NotNull
    private Float valor;

    private Float saldo;

    @NotNull
    private String estado;

    @NotNull
    @JsonProperty("numero_cuenta")
    private  String numeroCuenta;
}
