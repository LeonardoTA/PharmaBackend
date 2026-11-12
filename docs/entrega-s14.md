# Práctica S14: integración y estabilización completa

Estudiante: James Christopher Tequen Oncoy. Asignatura: Lenguaje de Programación 2.
Docente: Benjamin David Reyna Barreto. Fecha: 12 de noviembre de 2026.

## Entrega

- Código de los once pasos en la rama feature/integracion-estabilizacion-Tequen de ambos repositorios, con más de tres commits y PR hacia develop.
- Un único PDF, `docs/Tequen_LP2_S14_Practica.pdf`, con tabla de evidencias, matriz de integración, informe de Playwright, incidencias y lista de cotejo.
- `e2e/`: los siete casos de la guía, configuración con un worker y cero reintentos.
- `docs/incidencias.md`: cinco incidencias reales cerradas con causa, corrección y commit.
- `docs/matriz-integracion.md`: ocho integraciones verificadas. Datos medidos: JSON y PNG en `docs/evidencias-s14/` del backend.
- Informe Playwright original completo y sin tokens: `docs/reporte-playwright.html` del backend. Abrirlo con un navegador después de descargarlo.

## Verificación final

Comando: `npx playwright test --repeat-each=3`. Resultado definitivo: **7/7, 7/7 y 7/7; 21/21**, sin omitidas, inestables ni reintentos. Cada caso vigila errores JavaScript y respuestas API 5xx; ambos controles terminaron en cero. La primera corrida de estabilidad falló al comparar productos distintos por nombre parcial (INC-05); se corrigió y se ejecutó otra vez la suite completa antes de aceptar el resultado final.

Matriz: **8/8**, con administrador y vendedor. Se verificó el stock sin F5, el incremento del reporte, los eventos de venta y precio con usuario/requestId, y un vencimiento real del JWT de un minuto durante la venta; al reingresar volvió a `/ventas/nueva`. Se restauró después el TTL normal de 60 minutos y el precio del producto de prueba.

Auditoría: el login fallido persiste en su propia transacción. Una venta rechazada después de modificar una primera línea hizo rollback: el stock y el número de eventos de venta se conservaron. La venta confirmada genera evento AFTER_COMMIT, y el evento de eliminación también se verificó. Administrador consulta la bitácora; vendedor/almacén reciben 403 y anónimo 401. Las columnas de las cuatro entidades guardan el usuario del token. La pantalla de edición del producto y el comprobante muestran el usuario correspondiente.

Maven: 10 pruebas existentes aprobadas. Compilación Angular de producción aprobada: paquete inicial 379,55 kB.

## Arranque y base de datos

1. Iniciar Oracle en localhost:1522/FREEPDB1 y Redis en 6379.
2. Configurar los secretos locales; no publicarlos. La SPA de desarrollo usa el proxy existente hacia la API 8081. El perfil de producción conserva 8082 detrás de Nginx.
3. Backend: `mvnw.cmd package -DskipTests` y `java -jar target/PharmaBackend-0.0.1-SNAPSHOT.jar --server.port=8081`.
4. **Solo la primera vez sobre la base de desarrollo heredada sin historial:** arrancar con la variable `FLYWAY_BASELINE_ON_MIGRATE=true`. El esquema existente se registra como baseline 3 y se ejecuta V4. Después retirar esa variable. El valor predeterminado es false. No se recrean tablas existentes ni se simulan migraciones V1 a V3 que este proyecto no tenía registradas.
5. En DBeaver, conexión PharmaDB, ejecutar `docs/sql/comprobar_auditoria.sql` y `docs/sql/historial_flyway.sql` del backend. Debe haber siete columnas de usuario, la tabla auditoria_eventos y un historial con baseline 3 y V4 correcta. El esquema se inspeccionó realmente en DBeaver y se conserva su captura.
6. La carga de `docs/sql/volumen_pruebas.sql` es idempotente, solo para PHARMADB de desarrollo y posterior a V4; no es una migración. `docs/sql/verificar_volumen.sql` confirma 5.000 productos y 20.000 ventas sintéticas sin detalle. Estas ventas sirven para el listado y no validan reportes comerciales. Los datos heredados pueden conservar columnas de auditoría nulas: no se inventaron sus autores.
7. Frontend: `npm ci`, `npx playwright install chromium`, `npm start`. Ejecutar `npm run e2e:estable`. El entorno probado usa Paracetamol 500 mg activo con al menos seis unidades; `E2E_PRODUCTO` permite indicar otro nombre exacto visible en la primera página. Cliente de prueba: un apellido Mamani. No usar productos distintos para la selección y la verificación.
8. Para repetir la matriz, con ambos repositorios hermanos y la dependencia Playwright instalada: `node docs/verificar-integracion.cjs` desde el backend. Crea datos de desarrollo y una venta real. El modo `expiracion` requiere una instancia temporal con JWT_EXPIRACION_MINUTOS=1; restaurar después a 60. Los datos identificados de esta ejecución están en `docs/evidencias-s14/datos-integracion.json`.

No se ejecutó un vaciado global de Redis. Para aislar mediciones se usó un prefijo de caché propio. Los tiempos publicados son medidas puntuales locales con el estado de caché existente, no una media ni una comparación antes/después.

## Adaptaciones mínimas del código de la guía

Se copiaron el paginador, los modelos, la migración V4, AuditoriaConfig, la entidad y repositorio de eventos, EventoAuditoria, AuditoriaServiceImpl, AuditoriaListener y las siete pruebas. Se completaron las piezas que la guía pide describiendo sus contratos: DTO, interfaz y controlador de consulta de auditoría.

La API anterior devolvía arreglos completos: se añadió `PaginaResponseDTO`, `PaginacionUtil` y consultas con Pageable, tamaño 1-100, lista blanca de orden y desempate por id. Productos filtra categorías en servidor y Ventas conserva filtros, tamaño y orden en URL. La guía requiere auditoría desde el usuario del token; se completaron DTO y mappers, y saveAndFlush asegura que la respuesta de una actualización ya contenga modificadoPor.

Spring Boot 4 requiere el starter de Flyway y el módulo Oracle; se añadieron porque el proyecto no tenía Flyway configurado. La base heredada se baselinó en 3 y V4 se aplicó una vez. El SQL usa `fecha_registro`, que es el nombre real local. La carga sigue siendo idempotente.

En Angular se adaptaron rutas/importaciones y los valores predeterminados de query params; hasValue protege la lectura de recursos fallidos y selected sincroniza el tamaño visual. Se conservaron SiPermiso, la impresión y el flujo de revisión/confirmación de la venta. Las pruebas reflejan esa confirmación y seleccionan el producto exacto. Los mensajes visuales no muestran el número HTTP ni el identificador de error; la API mantiene su trazabilidad.

## Enlaces

- Backend: https://github.com/LeonardoTA/PharmaBackend/tree/feature/integracion-estabilizacion-Tequen
- Frontend: https://github.com/LeonardoTA/PharmaFrontEnd/tree/feature/integracion-estabilizacion-Tequen
- PR backend: https://github.com/LeonardoTA/PharmaBackend/pull/2
- PR frontend: https://github.com/LeonardoTA/PharmaFrontEnd/pull/5

El backend creó develop desde la base S13 previa. El develop remoto del frontend conserva su base SPA y el PR también incluye las dependencias S11/S13 existentes; no se reescribieron ni se fusionaron esas ramas.
