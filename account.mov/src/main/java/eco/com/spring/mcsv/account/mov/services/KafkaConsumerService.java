package eco.com.spring.mcsv.account.mov.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

/**
 * @author edisoncsi on 14/3/24
 * @project TestBackendJava
 */

@Service
public class KafkaConsumerService {
    @Autowired
    private RestTemplate restTemplate;

    @Value("${cliente.kafka.topic}")
    private String topic;

    @Value("${cliente.api.url}")
    private String clienteApiUrl;

    @KafkaListener(topics = "${cliente.kafka.topic}")
    public Long recibirMensaje(String nombreCliente) {
        // Realizar una solicitud HTTP GET para obtener los datos del cliente
        ResponseEntity<Long> response = restTemplate.getForEntity(clienteApiUrl + "/" + nombreCliente, Long.class);
        return Objects.requireNonNull(response.getBody());
    }
}
