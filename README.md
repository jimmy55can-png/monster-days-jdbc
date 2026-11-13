# Monster Days – Módulo Jugador (JDBC)

Evidencia GA7-220501096-AA2-EV01 · SENA · Análisis y Desarrollo de Software

Módulo de gestión de jugadores del videojuego tower defense **Monster Days**, con conexión a MySQL por JDBC y CRUD completo (insertar, consultar, actualizar, eliminar).

## Estructura
- `com.monsterdays.jugador.conexion` → `ConexionBD` (conexión JDBC)
- `com.monsterdays.jugador.modelo` → `Jugador` (entidad)
- `com.monsterdays.jugador.dao` → `JugadorDao` (CRUD con PreparedStatement)
- `com.monsterdays.jugador.principal` → `Main` (menú de consola)
- `sql/monster_days_jugador.sql` → script de la tabla

## Estándares de codificación
- Paquetes: minúsculas, sin guiones bajos (`com.monsterdays.jugador.dao`)
- Clases: PascalCase (`JugadorDao`)
- Métodos y variables: camelCase en español (`insertarJugador`, `nombreUsuario`)
- Constantes: MAYÚSCULAS_CON_GUION_BAJO (`SQL_INSERTAR`)
- Columnas SQL: snake_case (`nombre_usuario`)

## Ejecución
1. Ejecutar `sql/monster_days_jugador.sql` en MySQL Workbench.
2. (Opcional) definir variables de entorno `MD_DB_URL`, `MD_DB_USER`, `MD_DB_PASSWORD`.
3. Compilar y ejecutar con Maven: `mvn compile exec:java -Dexec.mainClass=com.monsterdays.jugador.principal.Main`
   o abrir el proyecto en NetBeans/IntelliJ/Eclipse y ejecutar `Main`.

## Versionamiento
Proyecto versionado con Git; repositorio remoto en el enlace de `ENLACE_REPOSITORIO.txt`.
