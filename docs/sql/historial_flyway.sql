select "version" as VERSION, "type" as TIPO,
       "description" as DESCRIPCION, "success" as CORRECTA
from pharmadb."flyway_schema_history"
order by "installed_rank";
