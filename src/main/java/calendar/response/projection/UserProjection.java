package calendar.response.projection;

import calendar.entity.User;
import de.bripkens.gravatar.DefaultImage;
import de.bripkens.gravatar.Gravatar;
import de.bripkens.gravatar.Rating;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "User projection")
public class UserProjection {

    @ApiModelProperty(value = "User credentials")
    private String credentials;

    @ApiModelProperty(value = "User generated avatar")
    private String avatar;

    public UserProjection(User user) {
        this.credentials = user.getCredentials();
        this.avatar = new Gravatar()
                .setSize(50)
                .setHttps(true)
                .setRating(Rating.PARENTAL_GUIDANCE_SUGGESTED)
                .setStandardDefaultImage(DefaultImage.IDENTICON)
                .getUrl(user.getLogin());
    }

    public String getCredentials() {
        return credentials;
    }

    public void setCredentials(String credentials) {
        this.credentials = credentials;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
