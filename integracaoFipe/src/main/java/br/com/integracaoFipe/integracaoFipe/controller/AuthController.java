package br.com.integracaoFipe.integracaoFipe.controller;

import br.com.integracaoFipe.integracaoFipe.dto.LoginParams;
import br.com.integracaoFipe.integracaoFipe.dto.ResponseLogin;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AuthController {

    @Operation(description = "GET responsável por efetuar a autenticação no front-end")
    @CrossOrigin(origins = "*")
    @PostMapping("/login")
    public ResponseEntity<?> getAuthenticate(@RequestBody LoginParams params) {
        String login = params.getLogin();
        String password = params.getPassword();
        if (login == null || login.isEmpty() || password == null || password.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (login.equals("admin") && password.equals("admin") ) {
            ResponseLogin response = new ResponseLogin();
            response.setUser("admin");
            response.setToken("token_seguro");
            return new ResponseEntity<ResponseLogin>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}