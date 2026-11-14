# Registro de incidencias - sesiones 13 y 14

Los siete hallazgos registrados ocurrieron en S14. No se inventan incidencias de S13 para completar el mínimo. Cada una está cerrada y conserva la prueba o verificación que valida su corrección. Los números son documentales; no se muestran en los errores de la aplicación.

## INC-01 - Entorno E2E

- **Fecha:** 12/11/2026
- **Descripción y pasos:** Ejecutar npx playwright test con los navegadores iniciales.
- **Esperado:** Abrir Chromium y ejecutar siete casos.
- **Obtenido:** Los siete casos no podían iniciar el navegador.
- **Severidad:** Alta
- **Causa raíz:** Playwright instalado requería Chromium 1234 y solo existía 1228.
- **Corrección:** Instalar Chromium requerido y declarar la dependencia reproducible.
- **Commit:** frontend b6b67fe
- **Regresión / verificación:** Toda la suite; la corrida definitiva actual aprobó 36/36.
- **Estado:** Cerrada

## INC-02 - Productos / Ventas

- **Fecha:** 12/11/2026
- **Descripción y pasos:** Abrir el listado sin parámetros de orden.
- **Esperado:** Aplicar orden por defecto y devolver la primera página.
- **Obtenido:** La consulta inicial se rechazaba por orden no permitido.
- **Severidad:** Alta
- **Causa raíz:** withComponentInputBinding asignaba undefined a inputs ausentes, reemplazando el inicializador.
- **Corrección:** Transformaciones de los inputs aplican valores por defecto.
- **Commit:** frontend bb8987f y 88a30da
- **Regresión / verificación:** Caso de paginación y verificación del avance (URL y F5).
- **Estado:** Cerrada

## INC-03 - Paginador compartido

- **Fecha:** 12/11/2026
- **Descripción y pasos:** Abrir el primer listado y comparar selector Filas con la respuesta.
- **Esperado:** Filas 20 para una página de 20.
- **Obtenido:** El selector mostraba 10.
- **Severidad:** Media
- **Causa raíz:** El value se aplicaba antes de construir las opciones del select.
- **Corrección:** Selected explícito en cada opción.
- **Commit:** frontend f8893b3
- **Regresión / verificación:** Verificación de tamaño y captura del avance; E2E de paginación.
- **Estado:** Cerrada

## INC-04 - Guion de integración

- **Fecha:** 12/11/2026
- **Descripción y pasos:** Ejecutar I-06 en docs/verificar-integracion.cjs.
- **Esperado:** Editar precio y comprobar su persistencia.
- **Obtenido:** El guion se detenía al localizar Precio; luego comparaba 4.50 como texto con 4.5.
- **Severidad:** Baja
- **Causa raíz:** Etiqueta incompleta del selector y comparación textual de números.
- **Corrección:** Usar Precio (S/) exacto y comparación numérica.
- **Commit:** backend 095a402
- **Regresión / verificación:** Guion de matriz I-06; matriz final 8/8. No era un defecto del CRUD.
- **Estado:** Cerrada

## INC-05 - Prueba del flujo de venta

- **Fecha:** 12/11/2026
- **Descripción y pasos:** Ejecutar la primera suite repetida con varios productos llamados Paracetamol.
- **Esperado:** Comparar el stock del mismo producto vendido.
- **Obtenido:** 18/21: la prueba comparaba la fila de otro producto con stock diferente.
- **Severidad:** Alta
- **Causa raíz:** Selector por nombre parcial, ambiguo frente a variantes e inactivo.
- **Corrección:** Seleccionar y comprobar nombre exacto de la misma fila.
- **Commit:** frontend 41695ff
- **Regresión / verificación:** Caso flujo principal; 21/21 tras corregir y 36/36 en la ampliación.
- **Estado:** Cerrada

## INC-06 - Auditoría SPA

- **Fecha:** 13/11/2026
- **Descripción y pasos:** Inspeccionar app.routes.ts y menú en frontend 228d4c9.
- **Esperado:** Existencia de /auditoria protegida, tabla, filtros y paginación.
- **Obtenido:** No existían ruta ni opción de menú; solo había API.
- **Severidad:** Alta
- **Causa raíz:** La actividad práctica anterior no incluía todavía la página autónoma.
- **Corrección:** Crear página lazy, permisoGuard, menú por permiso y Paginador compartido.
- **Commit:** frontend eed3cfb
- **Regresión / verificación:** E2E-12 y E2E-13. Hallazgo por inspección, no se atribuye una captura previa inexistente.
- **Estado:** Cerrada

## INC-07 - Filtro de Auditoría

- **Fecha:** 13/11/2026
- **Descripción y pasos:** Ejecutar E2E-12 con getByLabel Acción exacto.
- **Esperado:** Seleccionar la acción por su nombre accesible.
- **Obtenido:** Timeout al localizar el select; primera corrida 11/12.
- **Severidad:** Media
- **Causa raíz:** El texto accesible de la etiqueta incluía el texto de las opciones.
- **Corrección:** Añadir aria-label explícito Acción al select.
- **Commit:** frontend eed3cfb
- **Regresión / verificación:** E2E-12 pasó tras la corrección y en las tres repeticiones finales.
- **Estado:** Cerrada

## Resumen

| Severidad | Registradas | Cerradas | Abiertas |
|---|---:|---:|---:|
| Crítica | 0 | 0 | 0 |
| Alta | 4 | 4 | 0 |
| Media | 2 | 2 | 0 |
| Baja | 1 | 1 | 0 |
| Total | 7 | 7 | 0 |

| Módulo | Cantidad |
|---|---:|
| Entorno E2E | 1 |
| Productos / Ventas | 1 |
| Paginador compartido | 1 |
| Guion de integración | 1 |
| Prueba del flujo de venta | 1 |
| Auditoría SPA | 1 |
| Filtro de Auditoría | 1 |

No hay críticas ni altas abiertas. Las ejecuciones de diagnóstico no se presentan como aprobadas; el resultado aceptado es la corrida definitiva de 36. La validación personal del guion es un pendiente de entrega, no un defecto técnico encubierto como incidencia cerrada.
