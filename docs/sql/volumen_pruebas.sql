alter session set current_schema = PHARMADB;
-- Solo en desarrollo. Ejecutar en DBeaver conectado como PHARMADB.
-- Adaptación para pasos 1-4: no requiere creado_por (auditoría del paso 5).
-- La fecha local de Venta es fecha_registro. No forma parte de migraciones.
-- Idempotente: inserta únicamente las filas sintéticas que faltan.
insert into productos (nombre, precio, stock, estado, categoria_id, fecha_creacion)
select 'Producto de prueba ' || lpad(n, 5, '0'),
       round(dbms_random.value(1, 200), 2), trunc(dbms_random.value(0, 500)),
       true, (select min(id) from categorias), systimestamp
from (select level n from dual connect by level <= 5000) carga
where not exists (select 1 from productos p where p.nombre = 'Producto de prueba ' || lpad(carga.n, 5, '0'));
insert into ventas (fecha_registro, total, estado, cliente_id)
select timestamp '2026-11-12 12:00:00' - numtodsinterval(n, 'MINUTE'),
       12.34, 'REGISTRADA', (select min(id) from clientes)
from (select level n from dual connect by level <= 20000) carga
where not exists (select 1 from ventas v where v.fecha_registro = timestamp '2026-11-12 12:00:00' - numtodsinterval(carga.n, 'MINUTE') and v.total = 12.34 and v.cliente_id = (select min(id) from clientes) and not exists (select 1 from detalle_ventas d where d.venta_id = v.id));
commit;
-- Estas ventas sin detalle son solo para el listado; no validan reportes.
select 'Productos' entidad, count(*) cantidad from productos
union all select 'Ventas', count(*) from ventas;

