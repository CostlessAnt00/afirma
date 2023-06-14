package afirma.controller;

import afirma.model.Usuario;
import afirma.service.UserService;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/afirma")
public class UsersController {

    private final UserService userService;

    public UsersController(UserService userService) {
        this.userService = userService;
    }

    /**
     *
     * @param id
     * @return usuario encontrado o excepsion 404 en caso de no encontrarla
     */
    @GetMapping("/get-user")
    public ResponseEntity<Usuario> obtenerUsuarios(@RequestParam Long id) {
        Optional<Usuario> usuario = userService.obtenerUsuario(id);
        return usuario.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     *
     * @param request
     * @return mensaje de confirmación o error de la operacion
     */
    @PostMapping("/insert-user")
    public ResponseEntity<String> registrarUsuario(@RequestBody Usuario request) {
            return userService.registrarUsuario(request);
    }

    @PutMapping("/update-user")
    public ResponseEntity<String> actualizarUsuarioPorNombre(@RequestParam Long id,@RequestBody Usuario request) {
            return userService.actualizarUsuario(id, request);
    }

    @DeleteMapping("/delete-user")
    public ResponseEntity<String> eliminarUsuario(@Param("id") Long id) {
            return userService.eliminarUsuario(id);
    }

}
