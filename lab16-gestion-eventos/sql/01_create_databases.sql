-- Ejecutar en phpMyAdmin (XAMPP) o: mysql -u root < 01_create_databases.sql
-- Una BD por microservicio (database-per-service). Las tablas las crea Hibernate (ddl-auto=update en dev).
CREATE DATABASE IF NOT EXISTS bd_evento      CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS bd_asistente   CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS bd_inscripcion CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
-- Para perfil test:
CREATE DATABASE IF NOT EXISTS bd_evento_test      CHARACTER SET utf8mb4;
CREATE DATABASE IF NOT EXISTS bd_asistente_test   CHARACTER SET utf8mb4;
CREATE DATABASE IF NOT EXISTS bd_inscripcion_test CHARACTER SET utf8mb4;
