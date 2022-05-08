package calendar.config.security;

import calendar.entity.User;
import calendar.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    private UserService userService;

    @Autowired
    public void autowire(UserService userService) {
        this.userService = userService;
    }

    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userService.findByLogin(username);
        return CustomUserDetails.toUserDetails(user);
    }
}
