package pe.edu.upeu.PharmaBackend.config;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;
/*
 * Agrega el boton "Authorize" en Swagger UI: se pega el token del login
 * (sin la palabra Bearer) y Swagger lo envia en cada peticion.
 */
@Configuration
@OpenAPIDefinition(
        info = @Info(title = "PharmaBackend API", version = "v1"),
        security = @SecurityRequirement(name = "bearerAuth")
)
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)
public class OpenApiConfig {
}
