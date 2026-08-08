package nyumbalink.controller;

import nyumbalink.dto.AuthResponse;
import nyumbalink.dto.LoginRequest;
import nyumbalink.dto.RegisterRequest;
import nyumbalink.entity.User;
import nyumbalink.service.JwtService;
import nyumbalink.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final JwtService jwtService;

    public AuthController(
            UserService userService,
            JwtService jwtService) {

        this.userService = userService;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthResponse register(
            @Valid @RequestBody RegisterRequest request) {

        User user = userService.register(request);

        String token =
                jwtService.generateToken(user);

        return new AuthResponse(
                token,
                user.getEmail(),
                user.getRole().name()
        );
    }

    @PostMapping("/login")
    public AuthResponse login(
            @Valid @RequestBody LoginRequest request) {

        User user =
                userService.login(
                        request.getEmail(),
                        request.getPassword()
                );

        String token =
                jwtService.generateToken(user);

        return new AuthResponse(
                token,
                user.getEmail(),
                user.getRole().name()
        );
    }
}