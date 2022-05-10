package calendar.response.projection;

import calendar.entity.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "User projection for event creation")
public class UserOptionProjection {

    @ApiModelProperty(value = "User ID")
    private Long id;

    @ApiModelProperty(value = "User credentials")
    private String credentials;

    public UserOptionProjection(User user) {
        this.id = user.getId();
        this.credentials = user.getCredentials();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCredentials() {
        return credentials;
    }

    public void setCredentials(String credentials) {
        this.credentials = credentials;
    }
}
