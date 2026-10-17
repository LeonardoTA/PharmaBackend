SELECT u.username, r.nombre AS rol,
       COUNT(*) OVER (PARTITION BY u.id) AS filas_usuario_roles,
       CASE WHEN u.username = 'maria' THEN u.password END AS hash_bcrypt
FROM pharmadb.usuarios u
JOIN pharmadb.usuario_roles ur ON ur.usuario_id = u.id
JOIN pharmadb.roles r ON r.id = ur.rol_id
WHERE u.username IN ('maria', 'vendedor')
ORDER BY u.username, r.nombre;
