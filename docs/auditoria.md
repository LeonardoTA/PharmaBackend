# Auditoría de PharmaSoft

Se conserva el autor y momento de los cambios relevantes para responder quién vendió, qué cambió y desde qué solicitud. El historial permite investigar una diferencia de stock sin almacenar credenciales ni copiar datos personales del cliente en cada evento.

| Acción | Cuándo se registra | Detalle guardado |
|---|---|---|
| VENTA_REGISTRADA | Venta confirmada, AFTER_COMMIT | Total e ítems; id de la venta en entidadId |
| PRECIO_MODIFICADO | Precio cambia y se confirma el producto, AFTER_COMMIT | Precio anterior y nuevo |
| PRODUCTO_ELIMINADO | Eliminación confirmada, AFTER_COMMIT | Nombre del producto |
| CLIENTE_REGISTRADO | Alta confirmada, AFTER_COMMIT (nuevo) | «Cliente registrado»; id sin DNI, correo ni teléfono |
| CATEGORIA_ELIMINADA | Eliminación confirmada, AFTER_COMMIT (nuevo) | Nombre de categoría; id |
| LOGIN_EXITOSO | Autenticación correcta | Sin detalle ni entidadId; username y requestId |
| LOGIN_FALLIDO | Autenticación rechazada | Sin detalle ni contraseña; username intentado y requestId |

Todos guardan fecha del servidor, usuario, acción, entidad, entidadId, detalle acotado a 500 caracteres y requestId de la solicitud. Los login se registran directamente en transacción propia: no son cambios de negocio que deban esperar a un commit. Un login fallido debe dejar rastro aunque se lance AuthenticationException y falle la autenticación.

## Tres niveles diferentes

- Las columnas `creado_en`, `actualizado_en`, `creado_por` y `actualizado_por` describen la fila actual. Spring Data obtiene el usuario del contexto autenticado; Venta conserva creado_por. No reconstruyen por sí solas todos los cambios anteriores.
- La bitácora `auditoria_eventos` es un historial de hechos de negocio, consultable por el administrador. Conserva varios hechos para una misma entidad, incluso si se elimina.
- Los logs son diagnóstico operativo: ruta, estado, duración y mensajes de servicio. El MDC vincula las líneas mediante requestId. Rotación/retención de logs y bitácora se administran por separado.

## Transacciones y límites

Los servicios publican EventoAuditoria dentro de la transacción de negocio. AuditoriaListener usa `@TransactionalEventListener(phase=AFTER_COMMIT)` y solo entonces invoca AuditoriaServiceImpl.registrar con `REQUIRES_NEW`. Un rollback no confirma ni la venta ni su evento. Si falla el almacén de auditoría después del commit, la venta ya existe; el listener registra el fallo en el log. Esta solución no garantiza entrega de eventos ante caída del proceso: una garantía más fuerte requeriría outbox/reintento persistente, fuera del alcance de la guía.

Verificación nueva: alta de cliente desde la SPA generó CLIENTE_REGISTRADO. Se creó y eliminó una categoría temporal sin productos; apareció CATEGORIA_ELIMINADA. Al intentar eliminar la categoría 27 con productos, se obtuvo 409 y el conteo de eventos de eliminación permaneció igual. [Datos medidos](evidencias/s14-estabilizacion/eventos-y-salud.json).

## Seguridad y pantalla

`/auditoria` usa permisoGuard y USUARIOS_ADMINISTRAR; el menú verifica el mismo permiso. El backend vuelve a exigirlo: ocultar el menú no basta. La página incluye fecha, usuario, acción, entidad, id, detalle y requestId; usuario, acción, desde, hasta, página y tamaño viven en la URL. El rango usa inicio inclusivo y fin exclusivo del día siguiente; se reutiliza Paginador. Pruebas E2E-12/E2E-13 y Postman comprueban administrador, vendedor y anónimo.

No se guardan contraseñas, hashes BCrypt, tokens JWT, secretos, cabeceras Authorization, cookies ni cuerpos completos de solicitudes. El detalle de cliente no copia DNI, dirección, correo o teléfono. El username intentado se limita a 50 caracteres; no debe contener credenciales. Las capturas no muestran tokens. Los errores de la SPA muestran explicaciones sin códigos HTTP ni números de incidencia; los códigos de protocolo se conservan en la documentación técnica y Postman.

## Caso trazado de la demostración

Ver [venta y requestId](evidencias/s14-estabilizacion/venta-demostracion.json), [pantalla de auditoría](evidencias/s14-estabilizacion/demo-auditoria-2.png), [DBeaver: venta y evento](evidencias/s14-estabilizacion/dbeaver-trazabilidad.png), [líneas del log](evidencias/s14-estabilizacion/trazabilidad-log.txt) y [SQL de solo lectura](sql/trazabilidad_demo.sql). Los tres soportes corresponden a la misma venta grabada en el video. La cuadrícula de DBeaver une ventas.creado_por con el evento sin alterar datos.

Código: `src/main/java/pe/edu/upeu/PharmaBackend/{auditoria,config,service/impl}`; nueva página: frontend `src/app/features/auditoria/`.
