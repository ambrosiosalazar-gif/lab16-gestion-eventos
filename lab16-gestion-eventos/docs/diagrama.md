# Diagrama de microservicios y bases de datos

```mermaid
flowchart LR
  C[Postman / Cliente] --> G[API Gateway :8080]
  G --> E[evento-service :8081]
  G --> A[asistente-service :8082]
  G --> I[inscripcion-service :8083]
  I -- REST --> E
  I -- REST --> A
  E --> DE[(bd_evento)]
  A --> DA[(bd_asistente)]
  I --> DI[(bd_inscripcion)]
  R[Eureka Server :8761] -.registro.- G
  R -.registro.- E
  R -.registro.- A
  R -.registro.- I
```

```
Postman -> Gateway:8080 -> evento:8081      -> bd_evento
                        -> asistente:8082   -> bd_asistente
                        -> inscripcion:8083 -> bd_inscripcion
                              |-- REST --> evento / asistente (valida existencia, estado y cupo)
Todos se registran en Eureka:8761
```
Tablas: `evento(id, nombre, lugar, fecha, capacidad, estado)`, `asistente(id, nombre, apellido, email UNIQUE, telefono)`, `inscripcion(id, id_evento, id_asistente, fecha, estado)` — sin FK física entre BDs; la integridad se valida por REST.
