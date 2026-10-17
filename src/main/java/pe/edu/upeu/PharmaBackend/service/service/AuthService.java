package pe.edu.upeu.PharmaBackend.service.service;
import pe.edu.upeu.PharmaBackend.dto.auth.LoginRequestDTO;
import pe.edu.upeu.PharmaBackend.dto.auth.LoginResponseDTO;
import pe.edu.upeu.PharmaBackend.dto.auth.UsuarioResponseDTO;
public interface AuthService {
    void cambiarPassword(String username, pe.edu.upeu.PharmaBackend.dto.auth.CambioPasswordRequestDTO request);
    LoginResponseDTO login(LoginRequestDTO request);
    UsuarioResponseDTO usuarioActual(String username);
}
