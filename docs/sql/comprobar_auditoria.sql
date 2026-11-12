select 'Columnas de usuario' comprobacion, count(*) cantidad
from all_tab_columns where owner = 'PHARMADB'
and table_name in ('CATEGORIAS','PRODUCTOS','CLIENTES','VENTAS')
and column_name in ('CREADO_POR','MODIFICADO_POR')
union all
select 'Tabla de eventos', count(*) from all_tables
where owner = 'PHARMADB' and table_name = 'AUDITORIA_EVENTOS'
union all
select 'Historial Flyway', count(*) from all_tables
where owner = 'PHARMADB' and table_name = 'flyway_schema_history';
