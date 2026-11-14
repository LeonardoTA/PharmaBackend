# Lista de verificación previa

Estado técnico comprobado el 13/11/2026, America/Lima. Repetir la lista antes de exponer el 19/11; no se presume que los servicios seguirán encendidos.

- [x] Oracle oracle-local y Redis deploy-redis-1 encendidos para las pruebas.
- [x] Backend perfil dev; override server.port=8081 para el proxy local. Puerto 8082 reservado por configuración para despliegue Nginx. TTL JWT normal 60 minutos.
- [x] Actuator health UP; consulta de caché autorizada responde 200. Datos medidos en eventos-y-salud.json y demo-rendimiento.json.
- [x] SPA ng serve en 4200 y compilación de producción aprobada (379,81 kB iniciales).
- [x] Datos conocidos: cliente Lucía Mamani Quispe, DNI 78521469, id 11; producto Paracetamol 500 mg, id 5018, precio 4,50; categoría 27. No hay etiquetas de sesión en estos datos.
- [x] Se preparó stock 100 antes de cada recorrido técnico; cada venta de 2 descontó a 98. La suite posterior vuelve a consumir stock; prepararlo otra vez antes de exponer.
- [x] Usuarios locales: admin/Admin2026*, vendedor/Vendedor2026*, almacen/Almacen2026*. Son credenciales de demostración autorizadas, no de producción.
- [x] Los contextos de navegador usados para pruebas y grabación se cerraron al terminar. No representan sesiones que el estudiante pudiera tener en otro navegador.
- [ ] Antes de exponer, cerrar las sesiones personales y abrir una ventana limpia en login.
- [ ] Preparar pestañas: SPA, arquitectura.png, Postman, DBeaver, log filtrado por requestId, consola Playwright e incidencias.
- [ ] Realizar y registrar dos ensayos orales cronometrados y tres recorridos manuales en guion-demostracion.md.
- [x] Plan B: video local/WebM del repositorio, capturas de auditoría, cuadrícula de DBeaver y reporte HTML descargado. Si cae Internet, usar archivos locales; si cae Oracle/Redis/API, explicar la causa y mostrar el respaldo sin presentarlo como ejecución en vivo.
- [ ] Verificar acceso a los dos tags y al video desde el aula virtual, subir el PDF antes del 18/11/2026 a las 23:59.

## Preparación de stock usando DBeaver

Abrir `sql/preparar_demo.sql` en la conexión PharmaDB y ejecutar. El SQL actualiza solo el producto conocido de demostración y confirma el cambio; no borra ventas ni eventos. Al modificar stock por SQL se debe renovar la caché: reiniciar el backend de desarrollo con un prefijo nuevo de Redis, o preferir editar stock desde la SPA (admin), que invalida la caché automáticamente. No ejecutar FLUSHALL ni borrar claves ajenas.
