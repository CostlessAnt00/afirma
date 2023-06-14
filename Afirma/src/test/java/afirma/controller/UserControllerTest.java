package afirma.controller;

import afirma.model.Usuario;
import afirma.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class UsersControllerTest {

    @Mock
    private UserService userService;

    private UsersController usersController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        usersController = new UsersController(userService);
    }

    /**
     * Debe retornar un usuario existente
     */
    @Test
    void obtenerUsuarios() {
        long userId = 1L;
        Usuario usuarioMock = new Usuario();

        when(userService.obtenerUsuario(userId)).thenReturn(Optional.of(usuarioMock));

        ResponseEntity<Usuario> response = usersController.obtenerUsuarios(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(usuarioMock, response.getBody());
    }

    /**
     * Debe registrar al usuario
     */
    @Test
    void registrarUsuario() {
        Usuario usuarioMock = new Usuario();
        ResponseEntity<String> respuestaEsperada = new ResponseEntity<>("Usuario registrado", HttpStatus.OK);

        when(userService.registrarUsuario(usuarioMock)).thenReturn(respuestaEsperada);

        ResponseEntity<String> response = usersController.registrarUsuario(usuarioMock);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(respuestaEsperada.getBody(), response.getBody());
    }


    /**
     * Debe actualizar usuario existente
     */
    @Test
    void actualizarUsuarioPorId_DebeActualizarUsuarioExistente() {
        long userId = 1L;
        Usuario usuarioMock = new Usuario(); // Crea un objeto de prueba
        ResponseEntity<String> respuestaEsperada = new ResponseEntity<>("Usuario actualizado", HttpStatus.OK);

        when(userService.actualizarUsuario(userId, usuarioMock)).thenReturn(respuestaEsperada);

        ResponseEntity<String> response = usersController.actualizarUsuarioPorNombre(userId, usuarioMock);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(respuestaEsperada.getBody(), response.getBody());
    }


    /**
     * Debe eliminar usuario existente
     */
    @Test
    void eliminarUsuario() {
        long userId = 1L;
        ResponseEntity<String> respuestaEsperada = new ResponseEntity<>("Usuario eliminado", HttpStatus.OK);

        when(userService.eliminarUsuario(userId)).thenReturn(respuestaEsperada);

        ResponseEntity<String> response = usersController.eliminarUsuario(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(respuestaEsperada.getBody(), response.getBody());
    }



}
