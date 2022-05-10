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
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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
    @ApiOperation(value = "Authorization by login and password")
    public ResponseEntity<BaseResponse> auth(
            @ApiParam(value = "Authorization request") @Validated @RequestBody AuthRequest request
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

    @GetMapping("/by-token")
    @ApiOperation(value = "Authorization by token")
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
