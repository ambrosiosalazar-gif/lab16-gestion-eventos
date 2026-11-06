package pe.edu.lab16.inscripcion.client;

import pe.edu.lab16.inscripcion.exception.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
public class AsistenteClient {
    private final RestTemplate rest;
    private final String url;
    public AsistenteClient(RestTemplate rest, @Value("${clients.asistente-url}") String url) { this.rest = rest; this.url = url; }

    public void validarExiste(Long id) {
        try {
            rest.getForObject(url + "/api/asistentes/" + id, String.class);
        } catch (HttpClientErrorException.NotFound e) {
            throw new ResourceNotFoundException("Asistente no encontrado con id " + id);
        } catch (Exception e) {
            throw new ServiceUnavailableException("asistente-service no disponible");
        }
    }
}
