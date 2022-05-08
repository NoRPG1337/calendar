package calendar.response.projection;

import calendar.entity.User;

public class UserProjection {

    private Long id;

    private String credentials;

    private String avatar;

    public UserProjection(User user) {
        this.id = user.getId();
        this.credentials = user.getCredentials();
        this.avatar = "https://gravatar.com/avatar/86f21cc36fa2383c3488837f6cc6ad57?s=800&d=identicon";
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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
