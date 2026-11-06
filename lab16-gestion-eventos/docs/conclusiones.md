# Conclusiones y guía de sustentación

## Cinco conclusiones
1. Separar Evento, Asistente e Inscripción permite desarrollar, desplegar y escalar cada servicio de forma independiente (bajo acoplamiento).
2. Una BD por servicio evita dependencias ocultas; como no hay FK entre BDs, la integridad se garantiza validando por REST.
3. Los DTO con Bean Validation protegen las entidades y rechazan datos inválidos en el borde, antes de tocar la BD.
4. `@RestControllerAdvice` centraliza los errores con códigos HTTP correctos (400, 404, 409, 503) y respuestas uniformes.
5. Los perfiles YAML (dev/test/prod) permiten cambiar BD, credenciales, DDL y logs sin tocar el código; Eureka + Gateway quitan URLs fijas y centralizan el acceso.

## Respuesta modelo a la pregunta de sustentación
**¿Por qué separé en estos servicios?** Cada uno es un concepto de negocio con su propio ciclo de cambio; Inscripción depende de los otros dos solo por REST, y si uno cae el resto sigue (inscripción responde 503 controlado).
**¿Cómo persisto?** Spring Data JPA + Hibernate sobre MySQL; cada servicio con su propia BD y puerto; entidad ↔ DTO convertidos en la capa service; inscripción guarda solo `id_evento` e `id_asistente`.
**¿Qué pasa con información inválida?** `@Valid` falla → `MethodArgumentNotValidException` → el handler global responde **400** con el detalle por campo; recurso inexistente → **404**; violación de regla (duplicado, cupo) → **409**. Nunca se llega a guardar el dato.
