package eco.com.spring.mcsv.account.mov.dtos;

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
public class DepositoRetiros {

    private String cliente;
    private String fecha;
}
