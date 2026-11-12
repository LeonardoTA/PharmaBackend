# Práctica S14: pasos 1 a 4

Estudiante: James Christopher Tequen Oncoy. Curso: Lenguaje de Programación 2.
Docente: Benjamin David Reyna Barreto. Fecha: 12 de noviembre de 2026.

## Alcance autorizado

Ramas de trabajo, carga de volumen y mediciones, paginador reutilizable, Productos y Ventas con paginación del servidor y estado en la URL. Corresponde a los criterios 1 a 5 de la lista de cotejo. Los pasos 5 a 11, la auditoría, la matriz de ocho integraciones y las siete pruebas E2E repetidas tres veces quedan fuera de esta entrega parcial.

## Puesta en marcha

1. Iniciar Oracle local (1522/FREEPDB1) y Redis (6379).
2. En DBeaver, conexión PharmaDB, ejecutar `docs/sql/volumen_pruebas.sql` y `docs/sql/verificar_volumen.sql` del backend. Solo esquema PHARMADB de desarrollo. Script idempotente, no es una migración.
3. Backend: `mvnw.cmd package -DskipTests`; `java -jar target/PharmaBackend-0.0.1-SNAPSHOT.jar --server.port=8081`. Perfil dev, SQL visible. Mantener la clave JWT fuera del repositorio.
4. Frontend: `npm ci`; `npx ng serve --proxy-config proxy.conf.json`. SPA: http://localhost:4200. El proxy apunta a 8081. En producción se conserva el puerto 8082 detrás de Nginx.
5. Para repetir mediciones con caché vacía sin borrar otras claves, usar un valor nuevo para `SPRING_CACHE_REDIS_KEY_PREFIX` al arrancar la instancia de pruebas. Esta evidencia usó `s14-evidencia::`.

## Resultados medidos

- Carga en DBeaver: 5.000 productos y 20.000 ventas nuevas sin detalle. Totales: 5.017 y 20.015.
- Página 0 de Productos: 20 filas, 5.170 bytes, 695,44 ms. Primera petición de esa clave de caché. Son mediciones puntuales de desarrollo, no una media ni una comparación de rendimiento antes/después.
- Tamaño 100: 100 filas; página 249: 20 filas. La última página real es 250: 17 filas, 50,97 ms. La guía supone 5.000 filas; se conservaron los 17 productos preexistentes.
- Tamaño 500, campo no permitido y página negativa: HTTP 409. El número de estado se conserva en el protocolo y no aparece en los mensajes visuales.
- Ventas del 01 al 12 de noviembre: 16.562 coincidencias, 829 páginas de 20. Las ventas sintéticas sirven para el listado y no validan reportes comerciales.
- Chromium: 8 verificaciones de paginación aprobadas, sin errores de JavaScript. Compilación Angular de producción aprobada (inicial: 379,55 kB).
- Maven: 10 pruebas existentes aprobadas; empaquetado aprobado. No representan la suite del paso 9.

## Código de la guía y adaptaciones necesarias

Se incorporaron `Pagina`, `ConsultaPagina` y los tres archivos del paginador de la guía. Productos usa `input`, `rxResource`, `linkedSignal` y navegación con `queryParamsHandling: 'merge'`; se conservaron las columnas, acciones y `SiPermiso`. El mismo paginador se usa en Ventas.

El proyecto anterior devolvía arreglos completos y paginaba en el navegador. Fue necesario añadir `PaginaResponseDTO`, `PaginacionUtil`, repositorios con `Pageable`, validación de tamaño 1-100 y listas blancas de orden. El desempate por id evita que filas con igual precio/stock cambien de posición. En Ventas se pagina antes de cargar los detalles: no se usa un fetch de colección que pagine en memoria.

La guía ubica la auditoría en el paso 5, aunque su script menciona una migración V4 del paso 4. Para respetar el corte, el SQL omite `creado_por` y usa `fecha_registro`, el nombre real de la entidad local. La carga es idempotente y usa fechas y total constantes para reconocer sus propias ventas sin detalle. No se agregó V4 ni columnas de auditoría.

Con `withComponentInputBinding`, los parámetros ausentes se entregan como undefined. Se transformaron orden y dirección a sus valores por defecto para que `/productos` y `/ventas` no envíen `undefined`. El `linkedSignal` consulta `hasValue()` antes de leer el recurso para poder mostrar errores sin lanzar una excepción. Se añadió selección explícita a las opciones del tamaño porque inicialmente el navegador mostraba 10 aunque la respuesta tenía 20.

El filtro de categoría se conserva y ahora se aplica en el servidor, en lugar de filtrar solo la página visible. En Ventas se mantienen cliente, estado, desde, hasta, página, tamaño y orden en URL; Buscar aplica los controles y vuelve a la página inicial. No hay buscador de texto en estos listados, por lo que no corresponde debounceTime.

El entorno de desarrollo usa el proxy existente `/api/v1`, y los mensajes visuales de error muestran solo la explicación sin código HTTP ni identificador de seguimiento; el backend conserva la trazabilidad.

## Evidencias y repositorios

El PDF reúne el informe y las evidencias de este alcance. Los PNG completos y JSON de resultados están en `docs/evidencias-s14/` del backend. Las imágenes del PDF son recortes señalados, sin modificar los valores mostrados.

- Backend: https://github.com/LeonardoTA/PharmaBackend/tree/feature/integracion-estabilizacion-Tequen
- Frontend: https://github.com/LeonardoTA/PharmaFrontEnd/tree/feature/integracion-estabilizacion-Tequen
- Ambas ramas conservan el trabajo S13. Backend no tenía develop: se creó desde la base S13 previa, sin cambiar main. La base develop existente del frontend se conserva.
