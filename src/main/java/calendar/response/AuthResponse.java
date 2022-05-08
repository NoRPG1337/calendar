package calendar.response;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class AuthResponse extends BaseResponse {

    private String accessToken;

    private Date expiresIn;

    public AuthResponse(String accessToken, String message) {
        super(message);
        this.accessToken = accessToken;
        this.expiresIn = Date.from(LocalDate.now().plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Date getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Date expiresIn) {
        this.expiresIn = expiresIn;
    }
}
