package pe.edu.upeu.PharmaBackend.mapper;

import pe.edu.upeu.PharmaBackend.dto.ClienteRequestDTO;
import pe.edu.upeu.PharmaBackend.dto.ClienteResponseDTO;
import pe.edu.upeu.PharmaBackend.entity.Cliente;

public class ClienteMapper {

    public static ClienteResponseDTO ConvertToResponse(Cliente cliente) {
        return new ClienteResponseDTO(
                cliente.getId(),
                cliente.getDni(),
                cliente.getNombres(),
                cliente.getApellidos(),
                cliente.getEmail(),
                cliente.getTelefono(),
                cliente.getDireccion(),
                cliente.getEstado(),
                cliente.getFechaCreacion(),
                cliente.getFechaModificacion()
        );
    }

    public static Cliente ConvertFromRequest(Cliente cliente, ClienteRequestDTO request) {
        cliente.setDni(request.getDni() != null ? request.getDni().trim() : null);
        cliente.setNombres(request.getNombres() != null ? request.getNombres().trim() : null);
        cliente.setApellidos(request.getApellidos() != null ? request.getApellidos().trim() : null);
        cliente.setEmail(request.getEmail() != null ? request.getEmail().trim() : null);
        cliente.setTelefono(request.getTelefono() != null ? request.getTelefono().trim() : null);
        cliente.setDireccion(request.getDireccion() != null ? request.getDireccion().trim() : null);
        cliente.setEstado(request.getEstado());
        return cliente;
    }

    public static ClienteResponseDTO convertToResponse(Cliente cliente) {
        return ConvertToResponse(cliente);
    }

    public static Cliente convertFromRequest(Cliente cliente, ClienteRequestDTO request) {
        return ConvertFromRequest(cliente, request);
    }
}
