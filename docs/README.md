# Evidencias del Proyecto Integrador - actividad autónoma S14

**Estudiante:** James Christopher Tequen Oncoy. **Curso:** Lenguaje de Programación 2. **Docente:** Benjamin David Reyna Barreto. **Fecha del informe:** 13/11/2026, America/Lima.

## Entrega y estado real

- [PDF único de la actividad autónoma](Tequen_LP2_S14_Autonoma.pdf): informe y evidencias juntos.
- [Backend v1.0-demo](https://github.com/LeonardoTA/PharmaBackend/tree/v1.0-demo) y [frontend v1.0-demo](https://github.com/LeonardoTA/PharmaFrontEnd/tree/v1.0-demo). Rama feature/integracion-estabilizacion-Tequen; se conserva la continuidad de las prácticas.
- [Video de respaldo](evidencias/s14-estabilizacion/demostracion-respaldo.webm), ejecución técnica automatizada, sin narración. Descargar para reproducir si GitHub no lo previsualiza.
- Parte técnica: auditoría protegida, dos eventos nuevos, trazabilidad, 12 casos E2E repetidos tres veces (36/36), Postman 9/9, siete incidencias cerradas y documentación.
- **Pendiente personal antes de entregar:** realizar y registrar dos ensayos orales cronometrados, tres recorridos manuales y revisar las respuestas con palabras propias. Los recorridos automatizados no se presentan como manuales. La subida al aula virtual la hace el estudiante. No se afirma que la rúbrica esté íntegramente cumplida mientras falte esta validación.

## Índice requerido

| Documento / carpeta | Contenido |
|---|---|
| [arquitectura.png](arquitectura.png) | Una lámina de componentes y despliegue local / Nginx |
| [auditoria.md](auditoria.md) | Siete eventos, columnas/bitácora/logs, AFTER_COMMIT, exclusiones y caso trazado |
| [resultados-pruebas.md](resultados-pruebas.md) | Versiones, 36 ejecuciones, duración, informe HTML y Postman |
| [incidencias.md](incidencias.md) | Siete fichas completas y resúmenes por severidad/módulo |
| [guion-demostracion.md](guion-demostracion.md) | Seis bloques de diez minutos y registro honesto de ensayos/recorridos |
| [checklist-demo.md](checklist-demo.md) | Arranque, datos, cuentas locales, sesiones y plan B |
| [defensa-tecnica.md](defensa-tecnica.md) | Diez preguntas y respuestas vinculadas a este código |
| [u1-backend](evidencias/u1-backend/README.md) | Capturas históricas dentro del informe original S5 |
| [u2-spa-segura](evidencias/u2-spa-segura/README.md) | Capturas históricas de permisos S11 |
| [s13-optimizacion](evidencias/s13-optimizacion/README.md) | Evidencia histórica de cambios/invalidación y Actuator |
| [s14-estabilizacion](evidencias/s14-estabilizacion/README.md) | Matriz, auditoría, Playwright, Postman y video actual |
| [Colección Postman](postman/PharmaBackend.postman_collection.json) | 200/201, 400, 401, 403, 404, 409 en Productos/Ventas/Auditoría |

## Cambios propios de esta actividad

Backend: publicar CLIENTE_REGISTRADO y CATEGORIA_ELIMINADA desde los servicios existentes, reutilizando el listener AFTER_COMMIT. No se creó una migración nueva. Frontend: ruta /auditoria, menú por USUARIOS_ADMINISTRAR, tabla, filtros URL, paginación compartida y nombre accesible Acción. Cinco casos E2E nuevos se añaden a los siete de la guía práctica. La actividad autónoma especifica comportamientos y documentación; no aporta código completo para esta nueva página, por lo que se escribió la implementación necesaria sobre la base de la práctica. Mensajes de la web sin códigos HTTP ni IDs de incidencias.

## Versionamiento verificable

Tres días reales por repositorio, sin retroceder fechas:

| Repositorio | 05/11/2026 | 08/11/2026 | 12/11/2026 | 13/11/2026 |
|---|---|---|---|---|
| Backend | Historial S11 existente | 75f614d (S13) | e7d93a9 (práctica S14) | d52d514 (dos eventos) y commit final docs |
| Frontend | Historial S11 existente | 5182732 (S13) | 228d4c9 (práctica S14) | eed3cfb, 0783621 y commit final docs |

La condición de tres días se cumple con 08, 12 y 13 de noviembre; no se inventan tres días nuevos dentro de esta tarea. v1.0-demo apunta al commit final que contiene la entrega en cada repositorio.

## Diagrama y control visual

Se usa Archify para el diagrama técnico: [artefacto explorable](arquitectura.html), [fuente](arquitectura.archify.json), [recibo de comprobación visual](arquitectura.visual-check.json). Validación de la especificación 9/9 sin errores; comprobación de navegador aprobada a 1440x900, 1600x1000, 1920x1080 y 2048x1320, claro/oscuro; revisión visual del agente aprobada. El recibo automatizado conserva visualReview=pending porque no acredita una revisión humana. Artefacto SHA256 de972dfdf9277cd5ebba4e6ee95c676d9466ec69e28b6014648874c7148b9a9a (798197 bytes); fuente SHA256 1d29e4151157ad7d778a19719ef289b133bd10c4ff771794470f3a5a3dcc09ab. Controles fijos del visor en inglés, contenido en español. Arquitectura.png exporta el SVG real, sin reconstruirlo.

## Qué subir

Al aula virtual: Tequen_LP2_S14_Autonoma.pdf. El PDF ya enlaza los dos repositorios con v1.0-demo, este índice y el video. Si el aula tiene campos para enlaces, pegar también esos enlaces. La guía no exige un ZIP adicional ni otro PDF de evidencias. Completar primero los registros personales indicados y actualizar la documentación si cambia el resultado.
