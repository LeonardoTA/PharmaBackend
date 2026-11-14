# Defensa técnica - diez preguntas

Guion de estudio redactado para este código. Debo revisarlo y expresarlo con mis propias palabras al sustentar; no acredita por sí mismo una defensa oral realizada.

## 1. ¿Por qué pagino en el servidor y qué consulta Oracle?
No envío miles de productos para que el navegador descarte casi todos. El servidor filtra y ordena antes de devolver solo la página. Hibernate genera ORDER BY, OFFSET y FETCH FIRST; normalmente ejecuta también COUNT para el total, aunque una página parcial permite inferirlo. Añado id como desempate para que nombres repetidos no salten entre páginas. Código: `util/PaginacionUtil.java`, `service/impl/ProductoServiceImpl.java`, `repository/ProductoRepository.java`.

## 2. ¿Cómo evito una venta a medias y auditoría de una venta fallida?
Registrar usa @Transactional: cabecera, detalles y descuento de stock participan en la misma transacción. Si una línea falla, se revierte todo. Publicar un evento dentro del método no lo guarda aún; AuditoriaListener espera AFTER_COMMIT y registrar auditoría abre REQUIRES_NEW. Si esta segunda escritura falla, el log lo deja visible, pero la venta ya está confirmada; no prometo una atomicidad distribuida que este diseño no tiene. Código: `service/impl/VentaServiceImpl.java`, `auditoria/AuditoriaListener.java`, `service/impl/AuditoriaServiceImpl.java`.

## 3. ¿Qué pasa si vence el token durante la venta?
AuthService programa el cierre y elimina la sesión al vencer; conserva returnUrl, no una venta sin confirmar. Si una solicitud llega primero, JwtAuthenticationFilter rechaza la expiración, la API responde 401 y errorInterceptor cierra la sesión con aviso. Se inicia sesión y vuelve a la ruta para completar de nuevo la operación. No se reenvía automáticamente el POST de venta ni se promete recuperar el formulario. Si el servidor llegó a confirmar antes de la expiración, reviso el historial antes de repetir. Código frontend: `core/auth/auth-service.ts`, `core/interceptors/error-interceptor.ts`; backend `security/JwtAuthenticationFilter.java`, `security/JwtService.java`.

## 4. ¿Por qué invalido productos al vender?
La venta reduce stock; una página cacheada anterior seguiría ofreciendo una disponibilidad vieja. @CacheEvict sobre productos borra sus variantes paginadas tras la operación correcta. La próxima lectura consulta Oracle y repuebla Redis. Sin ello, la SPA mostraría stock desactualizado hasta el TTL. La caché nunca sustituye la validación de stock en la venta. Código: `service/impl/VentaServiceImpl.java`, `service/impl/ProductoServiceImpl.java`, `config/CacheConfig.java`.

## 5. ¿En qué se diferencian log, columnas y bitácora?
Las columnas dicen quién creó o modificó la fila actual. La bitácora conserva hechos históricos del negocio y su entidad. El log ayuda a diagnosticar peticiones y tiempos. RequestId relaciona el evento con la solicitud sin guardar el token. Código: `config/AuditoriaConfig.java`, `entity/AuditoriaEvento.java`, `config/RequestIdFilter.java` y `auditoria/EventoAuditoria.java`.

## 6. ¿Cómo decido si una prueba es estable?
Debe esperar un estado observable, usar selectores inequívocos y preparar datos conocidos. Ejecuto doce casos tres veces, sin retries ni pausas arbitrarias. INC-05 comparaba dos productos distintos por un nombre parcial; corregí el selector exacto. INC-07 era un nombre accesible ambiguo; lo hice explícito. Los resultados definitivos son 36/36, no una selección de los intentos que pasaron. Código frontend: `e2e/flujo-venta.spec.ts`, `e2e/autonoma.spec.ts`, `e2e/apoyo.ts`, `playwright.config.ts`.

## 7. ¿Ocultar el menú impide entrar a Auditoría?
No alcanza. El menú mejora la experiencia, permisoGuard protege la navegación y SecurityConfig/PreAuthorize comprueban USUARIOS_ADMINISTRAR en la API. Un vendedor autenticado recibe 403; sin autenticación válida recibe 401. Código frontend: `core/config/menu.ts`, `core/guards/permiso-guard.ts`, `app.routes.ts`; backend `config/SecurityConfig.java`, `controller/AuditoriaController.java`.

## 8. ¿Por qué pongo filtros en la URL?
La URL describe la consulta; F5 o compartirla reproduce usuario, acción, fechas y página. Cambiar un filtro vuelve a la primera página para evitar una página vacía fuera del rango nuevo. rxResource vuelve a consultar según los inputs, y linkedSignal conserva el contenido anterior durante la carga. Código frontend: `features/auditoria/auditoria.ts`, `features/auditoria/auditoria.html`, `shared/components/paginador/`.

## 9. ¿Por qué el total de venta lo calcula el backend?
El navegador puede ser alterado. El servicio toma precios vigentes del catálogo, valida estado y stock y calcula subtotales con BigDecimal. El request lleva producto y cantidad, no un precio confiable enviado por el cliente. Código: `dto/VentaRequestDTO.java`, `dto/DetalleVentaRequestDTO.java`, `service/impl/VentaServiceImpl.java`.

## 10. ¿Cómo identifico lo que presentaré?
Los dos repositorios tienen v1.0-demo, sin alterar fechas de los commits. El índice enlaza reportes y video correspondientes al código probado; los hashes de pruebas siguen en resultados-pruebas.md. El PDF indica los pendientes personales en lugar de atribuirme ensayos que todavía debo realizar. Código y evidencia: `docs/README.md`, `docs/resultados-pruebas.md`, tags de ambos repositorios.

Rutas Java relativas a `src/main/java/pe/edu/upeu/PharmaBackend/`; rutas SPA relativas a `src/app/` del frontend. Los enlaces entre repositorios están en el índice.
