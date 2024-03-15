package eco.com.spring.mcsv.account.mov.repositories;

import eco.com.spring.mcsv.account.mov.dtos.ReporteDto;
import eco.com.spring.mcsv.account.mov.models.Movimiento;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author edisoncsi on 14/3/24
 * @project TestBackendJava
 */

@Repository
public interface MovimientoRepository extends CrudRepository<Movimiento,Long> {

    @Query(value = "SELECT DATE_FORMAT(h.fecha,'%d/%m/%Y') AS fecha,\n" +
            "       c.cliente,\n" +
            "       c.numero_cuenta,\n" +
            "       c.tipo_cuenta,\n" +
            "       c.saldo_inicial,\n" +
            "       c.estado,\n" +
            "       h.valor as movimiento,\n" +
            "       m.saldo\n" +
            "FROM cuentas c\n" +
            "JOIN movimientos m ON c.id = m.cuenta_id\n" +
            "JOIN historial h ON m.id = h.movimiento_id\n" +
            "WHERE c.cliente = :cliente\n" +
            "  AND DATE_FORMAT(h.fecha, '%d-%m-%Y') = :fecha" ,  nativeQuery = true)
    List<Object[]> listMovimientosReporte(String cliente,  String fecha);
}
