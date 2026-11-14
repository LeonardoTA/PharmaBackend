-- Desarrollo: ejecutar en DBeaver, conexión PharmaDB. Cambia un solo producto.
-- Después renovar la caché de desarrollo; preferir editar stock en la SPA para invalidación automática.
update pharmadb.productos set stock=100 where id=5018 and nombre='Paracetamol 500 mg';
commit;
select id, nombre, stock, precio from pharmadb.productos where id=5018;
