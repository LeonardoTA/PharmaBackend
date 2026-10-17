package pe.edu.upeu.PharmaBackend.enums;
/*
 * Permisos finos de la aplicacion.
 *
 * Cada permiso se convierte en una "authority" de Spring Security con
 * el mismo nombre (por ejemplo, VENTAS_REGISTRAR) y se usa para
 * proteger endpoints con hasAuthority(...).
 */
public enum Permiso {
    CATALOGO_LEER,
    CATALOGO_ESCRIBIR,
    CLIENTES_LEER,
    CLIENTES_ESCRIBIR,
    VENTAS_LEER,
    VENTAS_REGISTRAR,
    REPORTES_VER,
    USUARIOS_ADMINISTRAR
}
