package calendar.controller;

import calendar.config.security.JwtTokenProvider;
import calendar.entity.User;
import calendar.exception.BadRequestException;
import calendar.exception.UnauthorizedException;
import calendar.helper.Message;
import calendar.request.AuthRequest;
import calendar.response.AuthResponse;
import calendar.response.BaseResponse;
import calendar.response.DataResponse;
import calendar.response.projection.UserProjection;
import calendar.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<BaseResponse> auth(
            @RequestBody AuthRequest request
    ) throws BadRequestException {
        User user = this.userService.findByLogin(request.getLogin());

        if (user == null) {
            throw new BadRequestException(Message.userNotFound(request.getLogin()));
        }

        if (!this.userService.validateRequest(request, user)) {
            throw new BadRequestException(Message.USER_INVALID_PASSWORD);
        }

        String token = this.jwtTokenProvider.generateToken(user.getLogin());

        return new ResponseEntity<>(new AuthResponse(token, Message.AUTH_SUCCESS), HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<BaseResponse> auth() throws UnauthorizedException {
        User user = this.userService.getCurrentAuthUser();

        if (user == null) {
            throw new UnauthorizedException(Message.AUTH_ERROR);
        }

        return new ResponseEntity<>(
                new DataResponse<>(new UserProjection(user), Message.AUTH_SUCCESS),
                HttpStatus.OK);
    }
}
