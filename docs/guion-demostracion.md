# Guion de demostración - 10 minutos

James Christopher Tequen Oncoy. Lenguaje de Programación 2. Docente: Benjamin David Reyna Barreto.

| Tiempo | Bloque | Acciones y explicación |
|---|---|---|
| 0:00-1:00 | Contexto | Explicar ventas y stock de la botica, vendedor/almacén/admin y mostrar arquitectura.png: SPA, API JWT, Oracle y Redis. |
| 1:00-4:30 | Flujo principal | Login vendedor; buscar Lucía Mamani Quispe por DNI 78521469; agregar Paracetamol 500 mg, 2 unidades a S/4,50; revisar y confirmar sin impresión automática; mostrar comprobante total S/9,00 y creado_por; abrir Productos categoría Vitaminas para Humanos y verificar stock 100 a 98. |
| 4:30-6:00 | Seguridad | Vendedor sin menú Auditoría; abrir URL y mostrar acceso denegado. En Postman mostrar 401 con token vencido y 403 con vendedor vigente; explicar interceptor, sin mostrar JWT. |
| 6:00-7:30 | Volumen | Admin: Productos página 2, más de 5.000 filas; F5 conserva URL. Mostrar Actuator health UP y Redis UP con consulta autorizada, y aumento de cache.gets con result:hit al repetir página. Mostrar build de producción y despliegue Nginx de S13 si se usa ese entorno; en este video se utiliza ng serve. |
| 7:30-8:30 | Auditoría | Filtrar vendedor/VENTA_REGISTRADA; localizar la venta recién creada. En DBeaver ejecutar trazabilidad_demo.sql; comparar creado_por y el requestId del evento con el log. Si la venta cambia en un ensayo, actualizar solo el id de la consulta. |
| 8:30-10:00 | Calidad | Ejecutar npx playwright test en vivo; doce aprobadas. Abrir incidencias.md: siete cerradas, cero altas/críticas abiertas. Cerrar con las tres corridas de estabilidad y dónde están las evidencias. |

La explicación oral completa distribuye diez minutos; el video técnico automatizado es más breve y no contiene narración. Los tiempos de tabla son el plan, no mediciones de un ensayo personal ya realizado.

## Ensayos personales cronometrados - validación pendiente

| Ensayo | Fecha | Duración real | Desvíos y ajuste |
|---|---|---|---|
| 1 | Pendiente de realizar por el estudiante | Pendiente | Registrar después de ensayar los seis bloques en voz alta. |
| 2 | Pendiente de realizar por el estudiante | Pendiente | Registrar después de corregir el primer ensayo. |

No se sustituyen por automatización ni se inventan fechas. La guía exige completar ambos antes de entregar. [Ensayos técnicos automatizados medidos](evidencias/s14-estabilizacion/ensayos-tecnicos.json) verifican navegación y suite, no la exposición personal.

## Tres verificaciones manuales del flujo - validación pendiente

Preparar stock 100 desde la edición del producto o ejecutar el SQL en DBeaver antes de cada recorrido. Registrar por UI la venta de dos unidades y comprobar 98, comprobante y evento. No borrar ventas anteriores.

| Recorrido manual | Fecha | Duración | Desvíos |
|---|---|---|---|
| 1 | Pendiente del estudiante | Pendiente | Registrar resultado observado. |
| 2 | Pendiente del estudiante | Pendiente | Registrar resultado observado. |
| 3 | Pendiente del estudiante | Pendiente | Registrar resultado observado. |

Hay además [tres recorridos técnicos automatizados](evidencias/s14-estabilizacion/recorridos-tecnicos.json), desde stock preparado por API, con venta, total y requestId medidos. Se presentan como comprobación técnica adicional; no acreditan la ejecución manual exigida.

## Respaldo

[Video técnico de respaldo](evidencias/s14-estabilizacion/demostracion-respaldo.webm), sin voz, captura real del navegador, duración inferior a diez minutos. Incluye contexto, venta/comprobante/stock, URL denegada, respuestas reales de API, paginación, Redis/Actuator, auditoría y suite en vivo. Para la prueba de expiración se firma localmente un token de ensayo con exp en el pasado; la firma se verifica con la clave de desarrollo y la API responde realmente. No cambia el reloj ni se publica el JWT. No equivale a una sesión personal del estudiante.
