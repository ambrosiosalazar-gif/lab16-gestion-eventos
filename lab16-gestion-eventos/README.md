# Laboratorio 16 – Gestión de eventos (Microservicios con Spring Boot)

**Estudiante:** Yldefonso  |  **Sección:** ______

Eventos, asistentes e inscripciones con Spring Boot 3.3.5, Java 17, Spring Cloud 2023.0.3 (Eureka + Gateway), JPA/Hibernate y MySQL (XAMPP).

## Arquitectura

| Módulo | Puerto | Base de datos | Rol |
|---|---|---|---|
| eureka-server | 8761 | – | Registro y descubrimiento |
| api-gateway | 8080 | – | Punto único de entrada |
| evento-service | 8081 | bd_evento | CRUD Evento |
| asistente-service | 8082 | bd_asistente | CRUD Asistente |
| inscripcion-service | 8083 | bd_inscripcion | Inscripciones; consulta por REST a evento y asistente |

Diagrama: `docs/diagrama.md`.

## Cómo ejecutar
1. Iniciar **MySQL** en XAMPP (puerto 3306, usuario `root` sin clave).
2. Ejecutar `sql/01_create_databases.sql` en phpMyAdmin.
3. Compilar: `mvn clean package -DskipTests` (en la raíz).
4. Levantar **en este orden** (una terminal cada uno):
   ```
   mvn -pl eureka-server spring-boot:run
   mvn -pl evento-service spring-boot:run
   mvn -pl asistente-service spring-boot:run
   mvn -pl inscripcion-service spring-boot:run
   mvn -pl api-gateway spring-boot:run
   ```
5. Verificar en http://localhost:8761 que aparecen los 4 servicios.
6. Importar `postman/Lab16.postman_collection.json` y ejecutar en orden (usa el gateway `http://localhost:8080`; también puedes probar directo a 8081/8082/8083).

Perfil: `SPRING_PROFILES_ACTIVE=prod mvn -pl evento-service spring-boot:run` (por defecto `dev`).

## Endpoints
| Servicio | Endpoints |
|---|---|
| Evento | `GET/POST /api/eventos`, `GET/PUT/DELETE /api/eventos/{id}` |
| Asistente | `GET/POST /api/asistentes`, `GET/PUT/DELETE /api/asistentes/{id}` |
| Inscripción | `GET/POST /api/inscripciones`, `GET/DELETE /{id}`, `GET /evento/{idEvento}`, `PUT /{id}/estado` |

Ejemplo `POST /api/eventos`:
```json
{"nombre":"Congreso de Software","lugar":"Auditorio Central","fecha":"2027-03-15","capacidad":100,"estado":"ACTIVO"}
```

## Perfiles (application.yml + application-{dev,test,prod}.yml)
| Valor | dev | test | prod |
|---|---|---|---|
| BD | `bd_xxx` local | `bd_xxx_test` | `${DB_URL}` |
| Usuario / clave | root / vacío | root / vacío | `${DB_USER}` / `${DB_PASSWORD}` |
| `ddl-auto` | update | create-drop | validate |
| `show-sql` | true | true | false |
| Logs | DEBUG | DEBUG | WARN / INFO |
| Eureka | habilitado | deshabilitado | `${EUREKA_URL}` |
| Puerto | fijo | fijo | `${PORT}` |

> Para `prod` con `validate`, las tablas deben existir: ejecutar antes en dev o usar `sql/02_tablas_referencia.sql`.

## Validaciones y manejo de errores
- Evento: nombre y lugar obligatorios, fecha no pasada, **capacidad > 0**, estado válido.
- Asistente: nombre, apellido, email válido y único, teléfono 9–15 dígitos.
- Inscripción: ids obligatorios; evento ACTIVO, no duplicada, **cupo disponible** (cuenta inscripciones CONFIRMADAS vs. capacidad).
- `@RestControllerAdvice` responde un JSON uniforme (`timestamp, status, error, message, details`):

| Caso | HTTP |
|---|---|
| Campos inválidos / JSON mal formado | 400 |
| Recurso inexistente | 404 |
| Email duplicado, cupo agotado, inscripción repetida | 409 |
| Microservicio dependiente caído | 503 |
| Error no controlado | 500 |

## Prueba de aceptación
Crear evento → crear asistente → registrar inscripción (201) → probar: capacidad -5 (400), evento 9999 (404), email duplicado (409), inscripción duplicada/cupo agotado (409). Todo está en la colección Postman con tests automáticos.

## Evidencias a entregar
- [x] Código fuente y este README
- [x] `docs/diagrama.md`
- [ ] Capturas de Postman (correr la colección: 1 éxito + 1 error mínimo) y de Eureka y phpMyAdmin
- [x] `docs/conclusiones.md` y guía de sustentación
