# Resultados de pruebas - actividad autónoma S14

Fecha: 13/11/2026, America/Lima. Backend probado: `d52d514`; frontend: `0783621` (incluye `eed3cfb`). Las versiones finales etiquetadas añaden documentación y evidencias; no cambian el código probado.

## Estabilidad de navegador

Comando: `npx playwright test --repeat-each=3`. Chromium, un worker, retries=0. Suite de **12 casos**, **36 ejecuciones aprobadas**, 0 fallidas, 0 omitidas, 0 inestables, 0 reintentos. Duración de pared: **64.288 s**. Los hooks de cada caso terminaron sin errores JavaScript ni respuestas API 5xx. El log del intervalo también tiene 0 respuestas 5xx. Las esperas se basan en localizadores, respuestas y aserciones; la suite no usa `waitForTimeout`.

| Caso | Corrida 1 | Corrida 2 | Corrida 3 |
|---|---|---|---|
| una ruta protegida sin sesión lleva al login con returnUrl | Aprobada | Aprobada | Aprobada |
| credenciales incorrectas muestran el mensaje del backend | Aprobada | Aprobada | Aprobada |
| tras iniciar sesión vuelve a la página pedida | Aprobada | Aprobada | Aprobada |
| E2E-09: venta sin productos no llega a confirmación ni envía POST | Aprobada | Aprobada | Aprobada |
| E2E-10: cliente nuevo aparece inmediatamente y permite registrar venta | Aprobada | Aprobada | Aprobada |
| E2E-11: cambiar filtro en página tres de Ventas vuelve al inicio y F5 conserva | Aprobada | Aprobada | Aprobada |
| E2E-12: admin consulta ventas auditadas y conserva filtros y página en URL | Aprobada | Aprobada | Aprobada |
| E2E-13: vendedor no ve Auditoría y la ruta directa muestra acceso denegado | Aprobada | Aprobada | Aprobada |
| flujo principal: el vendedor registra una venta y el stock baja | Aprobada | Aprobada | Aprobada |
| productos se paginan en el servidor con la página en la URL | Aprobada | Aprobada | Aprobada |
| el almacenero no ve Ventas y la URL directa es denegada | Aprobada | Aprobada | Aprobada |
| el vendedor ve Productos sin botones de edición | Aprobada | Aprobada | Aprobada |

Los cinco añadidos son E2E-09 a E2E-13; los siete de la práctica se conservan. Los IDs siguen las sugerencias de la guía y no implican que existan trece casos. La venta vacía se comprueba sin POST, mediante validación de la SPA, equivalente permitido por la guía.

[Informe HTML original de las 36 ejecuciones](evidencias/s14-estabilizacion/reporte-playwright.html), [resumen por caso y corrida](evidencias/s14-estabilizacion/resultado-estabilidad.json), [JSON original](evidencias/s14-estabilizacion/resultados-playwright.json), [salida de consola](evidencias/s14-estabilizacion/playwright-estabilidad.txt) y [log HTTP del intervalo](evidencias/s14-estabilizacion/log-flujo-estabilidad.txt). Descargar el HTML para abrirlo; GitHub muestra su código fuente. No confundirlo con `docs/reporte-playwright.html`, que conserva la evidencia de 21 ejecuciones de la práctica.

La primera corrida de doce casos dio 11/12 por el nombre accesible del filtro Acción (INC-07); se diagnosticó y corrigió antes de ejecutar la corrida definitiva de 36. No hubo reintentos automáticos ni se ocultó el fallo inicial. Las capturas/trazas de diagnóstico pueden contener sesiones temporales y permanecen locales.

## Postman / Newman

Colección importable: [PharmaBackend.postman_collection.json](postman/PharmaBackend.postman_collection.json). Ejecutar de principio a fin; los dos login guardan sus tokens solo en las variables de la ejecución. No se publica un entorno con tokens.

`npx newman run docs/postman/PharmaBackend.postman_collection.json`

| Caso real | HTTP | Tiempo ms | Aserción |
|---|---|---|---|
| Login admin | 200 | 141 | Aprobada |
| Login vendedor | 200 | 101 | Aprobada |
| Productos paginados | 200 | 14 | Aprobada |
| Venta registrada | 201 | 40 | Aprobada |
| Venta sin productos | 400 | 26 | Aprobada |
| Auditoría sin autenticación | 401 | 13 | Aprobada |
| Auditoría como vendedor | 403 | 11 | Aprobada |
| Producto inexistente | 404 | 17 | Aprobada |
| Tamaño de página fuera de rango | 409 | 14 | Aprobada |

**9 peticiones y 9 aserciones aprobadas**. Cobertura combinada de Productos, Ventas y Auditoría: 200/201, 400, 401, 403, 404 y 409. [Resumen seguro](evidencias/s14-estabilizacion/resultados-postman.json); se excluyen los tokens y cuerpos de login del reporte bruto.

## Comprobaciones complementarias

`mvnw.cmd test`: 10 pruebas existentes (contexto, JWT y permisos). `npm run build`: compilación de producción aprobada; paquete inicial 379,81 kB. Dos eventos nuevos y una eliminación rechazada sin evento: [resultado](evidencias/s14-estabilizacion/eventos-y-salud.json). Los ensayos técnicos grabados ejecutan además la suite en vivo, pero no se suman a las 36 ejecuciones del informe de estabilidad.

## Reproducir

1. Oracle localhost:1522/FREEPDB1 y Redis localhost:6379; backend con perfil dev, API 8081 y JWT_SECRET local Base64 de al menos 32 bytes. El perfil dev del repositorio usa 8082; el comando de desarrollo de abajo lo ajusta al proxy de la SPA.
2. Aplicar migraciones Flyway. En una base histórica sin historial, usar una sola vez el baseline 3 documentado en la práctica; no activar baseline indiscriminadamente en una base nueva.
3. Usar DBeaver para los SQL de desarrollo de `docs/sql`; preparar los datos de `evidencias-s14/datos-integracion.json` según el catálogo actual. Los IDs corresponden a esta base de desarrollo.
4. `mvnw.cmd package -DskipTests` y `java -jar target/PharmaBackend-0.0.1-SNAPSHOT.jar --server.port=8081`. Frontend: `npm ci`, `npx playwright install chromium`, `npm start`; después `npx playwright test --repeat-each=3`.
5. Usar JDK 25 y Node 24. La configuración y permisos de usuarios de demostración provienen de las actividades previas; no son cuentas de producción. Las ventas reales de prueba consumen stock; preparar de nuevo el producto antes de repetirlas.
