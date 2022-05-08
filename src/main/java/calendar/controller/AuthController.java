package calendar.controller;

import calendar.config.security.JwtTokenProvider;
import calendar.entity.User;
import calendar.exception.BadRequestException;
import calendar.exception.UnauthorizedException;
import calendar.request.AuthRequest;
import calendar.response.AuthResponse;
import calendar.response.DataResponse;
import calendar.response.projection.UserProjection;
import calendar.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private UserService userService;
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    public void autowire(
            UserService userService,
            JwtTokenProvider jwtTokenProvider
    ) {
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping(value = "")
    public ResponseEntity<AuthResponse> auth(
            @RequestBody AuthRequest request
    ) throws BadRequestException {
        User user = this.userService.findByLogin(request.getLogin());

        if (user == null) {
            throw new BadRequestException("");
        }

        if (!this.userService.validateRequest(request, user)) {
            throw new BadRequestException("");
        }

        String token = this.jwtTokenProvider.generateToken(user.getLogin());

        return new ResponseEntity<>(new AuthResponse(token, ""), HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<DataResponse> auth() throws BadRequestException, UnauthorizedException {
        User user = this.userService.getCurrentAuthUser();

        if (user == null) {
            throw new UnauthorizedException("");
        }

        return new ResponseEntity<>(
                new DataResponse<>(new UserProjection(user), ""),
                HttpStatus.OK);
    }
}
