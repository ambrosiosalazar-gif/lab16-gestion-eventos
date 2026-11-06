package pe.edu.lab16.inscripcion.client;

import pe.edu.lab16.inscripcion.dto.EventoDto;
import pe.edu.lab16.inscripcion.exception.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
public class EventoClient {
    private final RestTemplate rest;
    private final String url;
    public EventoClient(RestTemplate rest, @Value("${clients.evento-url}") String url) { this.rest = rest; this.url = url; }

    public EventoDto obtener(Long id) {
        try {
            return rest.getForObject(url + "/api/eventos/" + id, EventoDto.class);
        } catch (HttpClientErrorException.NotFound e) {
            throw new ResourceNotFoundException("Evento no encontrado con id " + id);
        } catch (Exception e) {
            throw new ServiceUnavailableException("evento-service no disponible");
        }
    }
}
