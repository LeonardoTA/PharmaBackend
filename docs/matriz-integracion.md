# Matriz de integración S14

| ID | Origen y destino | Comprobación y resultado | Estado |
|---|---|---|---|
| I-01 | Clientes → Venta | Cliente recién creado en la SPA aparece en la nueva venta | Cumple |
| I-02 | Catálogo → Venta | Solo se ofrecen productos activos con stock al precio vigente | Cumple |
| I-03 | Venta → Catálogo | El stock disminuye dos unidades sin F5 | Cumple |
| I-04 | Venta → Reportes | La venta incrementa el reporte del administrador | Cumple |
| I-05 | Venta → Auditoría | Evento de venta confirmado con usuario y requestId | Cumple |
| I-06 | Catálogo → Auditoría | Precio auditado con valores anterior y nuevo | Cumple |
| I-07 | Seguridad → todos | Vencimiento real: login y regreso a la nueva venta | Cumple |
| I-08 | Paginación → filtros | Cambiar fechas desde la página 5 vuelve a la primera | Cumple |

Resultado: 8/8. Detalles medidos y datos de cada verificación: `evidencias-s14/matriz-integracion.json`. Administrador y vendedor reales; expiración I-07 con JWT de un minuto, sin cambiar el reloj ni el token. La duración normal de 60 minutos se restauró al terminar.
