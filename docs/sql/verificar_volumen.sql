select 'Productos (total)' entidad, count(*) cantidad from pharmadb.productos
union all
select 'Productos cargados', count(*) from pharmadb.productos
where nombre like 'Producto de prueba %'
union all
select 'Ventas (total)', count(*) from pharmadb.ventas
union all
select 'Ventas de carga sin detalle', count(*) from pharmadb.ventas v
where v.total = 12.34
  and v.cliente_id = (select min(id) from pharmadb.clientes)
  and v.fecha_registro between timestamp '2026-11-12 12:00:00' - numtodsinterval(20000, 'MINUTE')
                          and timestamp '2026-11-12 12:00:00' - numtodsinterval(1, 'MINUTE')
  and not exists (select 1 from pharmadb.detalle_ventas d where d.venta_id = v.id);
