package calendar.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Authorization request")
public class AuthRequest {

    @ApiModelProperty(value = "login", example = "ivanov.ii")
    private String login;

    @ApiModelProperty(value = "password", example = "Qwerty123")
    private String password;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
