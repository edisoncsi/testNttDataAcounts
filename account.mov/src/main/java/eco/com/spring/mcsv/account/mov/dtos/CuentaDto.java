package eco.com.spring.mcsv.account.mov.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @author edisoncsi on 14/3/24
 * @project TestBackendJava
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonSerialize
public class CuentaDto {

    @NotNull
    @JsonProperty("numero_cuenta")
    private  String numeroCuenta;

    @NotNull
    @JsonProperty("tipo_cuenta")
    private String tipoCuenta;

    @NotNull
    @JsonProperty("saldo_inicial")
    private Float saldoInicial;

    @NotNull
    private String estado;

    @NotNull
    @JsonProperty("cliente_nombre")
    private String clienteNombre;
}
