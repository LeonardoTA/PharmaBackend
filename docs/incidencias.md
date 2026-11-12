# Incidencias de estabilización S14

| ID | Módulo | Descripción y pasos | Severidad | Causa | Corrección (commit) | Estado |
|---|---|---|---|---|---|---|
| INC-01 | Entorno E2E | Ejecutar la suite inicial: los siete casos no pueden abrir Chromium. | Alta | La dependencia Playwright instalada requiere Chromium 1234 y el equipo solo conserva 1228. No es un error de la aplicación. | Instalar Chromium con npx playwright install chromium y declarar la dependencia reproducible: frontend b6b67fe. Suite completa y tres corridas aprobadas. | Cerrada |
| INC-02 | Productos / Ventas | Abrir la lista sin parámetros: la consulta inicial fue rechazada por orden no permitido, según la comprobación del avance. | Alta | withComponentInputBinding entrega undefined para parámetros ausentes y reemplaza el valor inicial del input. | Transformaciones de orden y dirección con valores por defecto: frontend bb8987f y 88a30da. Verificación de URL y F5 aprobada. | Cerrada |
| INC-03 | Paginador | La primera captura mostraba Filas 10 aunque la respuesta traía 20 registros. | Media | El select recibió value antes de crear sus opciones. | Selección explícita [selected] en cada opción: frontend f8893b3. Captura final del avance y comprobación de tamaño aprobadas. | Cerrada |
| INC-04 | Guion de verificación de la matriz | I-06 se detiene al localizar el campo Precio, antes de enviar el cambio. | Baja | El guion busca Precio, pero la etiqueta es Precio (S/); además compara el texto 4.50 con el valor equivalente 4.5. No falló la edición del producto. | Etiqueta exacta y comparación numérica del precio: backend 095a402, docs/verificar-integracion.cjs. Matriz 8/8. | Cerrada |
| INC-05 | Prueba E2E del flujo de venta | La primera corrida de estabilidad da 18/21: el flujo vende un producto cuyo stock inicial es 15, pero compara otra fila con stock 30. | Alta | El selector parcial Paracetamol puede identificar productos diferentes; el catálogo también contiene el producto inactivo creado para comprobar I-02. | Nombre exacto al seleccionar y verificar la misma celda de stock: frontend 41695ff. Toda la suite aprobó tres veces: 21/21. | Cerrada |

Los números de incidencia se usan solo en documentación y commits. No aparecen en los mensajes de la web. Se conservan los resultados de cada corrida de diagnóstico fuera del entregable, sin publicarlos como pruebas aprobadas.

Resultado final: 5 registradas / 5 cerradas. No quedan incidencias críticas ni altas abiertas. La primera corrida de estabilidad fue 18/21 y se corrigió antes de la corrida definitiva 21/21.
