select v.id as VENTA, v.creado_por as AUTOR_VENTA, v.total, a.accion, a.usuario as AUTOR_EVENTO, a.request_id
from pharmadb.ventas v join pharmadb.auditoria_eventos a on a.entidad_id=v.id and a.entidad='Venta'
where v.id=20068 and a.accion='VENTA_REGISTRADA';
