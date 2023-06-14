package afirma.service;

import afirma.Repository.UserRepository;
import afirma.model.Usuario;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<Usuario> obtenerUsuario(Long id) {
        return userRepository.findById(id);
    }

    /**
     * Ingresa usuario a la bd con los datos ingresados
     * @param usuario
     * @return mensaje de confirmacion de creado o error de id repetido
     */
    public ResponseEntity<String> registrarUsuario(Usuario usuario){
        if (userRepository.existsById(usuario.getId())) {
            String s = "El ID " + usuario.getId() + " ya está en uso";
            return ResponseEntity.ok(s);
        }else{
            userRepository.save(usuario);
            return ResponseEntity.ok("Usuario creado exitosamente.");
        }
    }

    /**
     * Busca usuario por id y luego actualiza los datos con el request ingresado, esto sin contar el id
     * @param id
     * @param usuarioActualizado
     * @return mensaje de confirmacion en caso de que exista o de error en caso de que no
     */
    public ResponseEntity<String> actualizarUsuario(Long id, Usuario usuarioActualizado) {
        Optional<Usuario> datosUsuario = userRepository.findById(id);

        if (datosUsuario.isPresent()) {
            Usuario usuarioExistente = datosUsuario.get();

            usuarioExistente.setNombre(usuarioActualizado.getNombre());
            usuarioExistente.setApellido(usuarioActualizado.getApellido());
            usuarioExistente.setCorreoElectronico(usuarioActualizado.getCorreoElectronico());
            usuarioExistente.setFechaNac(usuarioActualizado.getFechaNac());

            userRepository.save(usuarioExistente);
            return ResponseEntity.ok("Usuario actualizado exitosamente.");
        } else {
            String s = "El usuario con el ID " + usuarioActualizado.getId() + " no existe";
            return ResponseEntity.ok(s);
        }
    }

    /**
     * Busca usuario por id y borra el registro
     * @param id
     * @return mensaje de confirmacion en caso de que exista o de error en caso de que no
     */
    public ResponseEntity<String> eliminarUsuario(Long id) {
        Optional<Usuario> datosUsuario = userRepository.findById(id);

        if (datosUsuario.isPresent()) {
            Usuario usuarioAEliminar = datosUsuario.get();
            userRepository.delete(usuarioAEliminar);
            return ResponseEntity.ok("Usuario eliminado exitosamente.");
        }else{
            String s = "El usuario con el ID " + id + " no existe";
            return ResponseEntity.ok(s);
        }
    }
}
