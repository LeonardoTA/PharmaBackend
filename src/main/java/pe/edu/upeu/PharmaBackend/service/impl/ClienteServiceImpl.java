package pe.edu.upeu.PharmaBackend.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upeu.PharmaBackend.dto.ClienteRequestDTO;
import pe.edu.upeu.PharmaBackend.dto.ClienteResponseDTO;
import pe.edu.upeu.PharmaBackend.entity.Cliente;
import pe.edu.upeu.PharmaBackend.exception.RecursoNoEncontradoException;
import pe.edu.upeu.PharmaBackend.exception.ReglaNegocioException;
import pe.edu.upeu.PharmaBackend.repository.ClienteRepository;
import pe.edu.upeu.PharmaBackend.service.service.ClienteService;

@Service
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteServiceImpl(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    @Transactional
    public ClienteResponseDTO create(ClienteRequestDTO t) {
        String dni = t.getDni().trim();
        String email = t.getEmail().trim();

        if (clienteRepository.existsByDni(dni)) {
            throw new ReglaNegocioException("Ya existe un cliente con el DNI " + dni);
        }

        if (clienteRepository.existsByEmailIgnoreCase(email)) {
            throw new ReglaNegocioException("Ya existe un cliente con el email " + email);
        }

        Cliente cliente = new Cliente();
        cliente.setDni(dni);
        cliente.setNombres(t.getNombres().trim());
        cliente.setApellidos(t.getApellidos().trim());
        cliente.setEmail(email);
        cliente.setTelefono(t.getTelefono() != null ? t.getTelefono().trim() : null);
        cliente.setDireccion(t.getDireccion() != null ? t.getDireccion().trim() : null);
        cliente.setEstado(t.getEstado());

        Cliente guardado = clienteRepository.save(cliente);

        return convertirResponse(guardado);
    }

    @Override
    @Transactional
    public ClienteResponseDTO update(Long id, ClienteRequestDTO t) {
        Cliente cliente = clienteRepository.findById(id).orElseThrow(() ->
                new RecursoNoEncontradoException("Cliente no encontrado con id: " + id));

        String dni = t.getDni().trim();
        String email = t.getEmail().trim();

        if (clienteRepository.existsByDniAndIdNot(dni, id)) {
            throw new ReglaNegocioException("Ya existe otro cliente con el DNI " + dni);
        }

        if (clienteRepository.existsByEmailIgnoreCaseAndIdNot(email, id)) {
            throw new ReglaNegocioException("Ya existe otro cliente con el email " + email);
        }

        cliente.setDni(dni);
        cliente.setNombres(t.getNombres().trim());
        cliente.setApellidos(t.getApellidos().trim());
        cliente.setEmail(email);
        cliente.setTelefono(t.getTelefono() != null ? t.getTelefono().trim() : null);
        cliente.setDireccion(t.getDireccion() != null ? t.getDireccion().trim() : null);
        cliente.setEstado(t.getEstado());

        Cliente actualizado = clienteRepository.save(cliente);

        return convertirResponse(actualizado);
    }

    @Override
    @Transactional(readOnly = true)
    public ClienteResponseDTO read(Long id) {
        Cliente cliente = clienteRepository.findById(id).orElseThrow(() ->
                new RecursoNoEncontradoException("Cliente no encontrado con id: " + id));

        return convertirResponse(cliente);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Cliente cliente = clienteRepository.findById(id).orElseThrow(() ->
                new RecursoNoEncontradoException("Cliente no encontrado con id: " + id));

        clienteRepository.delete(cliente);
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<ClienteResponseDTO> readAll() {
        return clienteRepository.findAll()
                .stream()
                .map(this::convertirResponse)
                .toList();
    }

    private ClienteResponseDTO convertirResponse(Cliente cliente) {
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
}
