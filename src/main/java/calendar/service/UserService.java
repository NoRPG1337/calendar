package calendar.service;

import calendar.entity.User;
import calendar.repository.UserRepository;
import calendar.request.AuthRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;

@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public void autowire(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void save(User user) {
        this.userRepository.save(user);
    }

    public User findById(Long id) {
        return this.userRepository.findById(id).orElse(null);
    }

    public User findByLogin(String login) {
        return this.userRepository.findByLogin(login);
    }

    public User getCurrentAuthUser() {
        return findByLogin(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    public void saveIfNotExists(User user) {
        if (this.findByLogin(user.getLogin()) == null) {
            this.save(user);
        }
    }

    public boolean validateRequest(AuthRequest request, User user) {
        int requestHash = this.hash(request.getLogin(), request.getPassword());
        int dbHash = this.hash(user.getLogin(), new String(Base64.getMimeDecoder().decode(user.getPassword())));

        return requestHash == dbHash;
    }

    private int hash(String login, String password) {
        int result = 31;
        result = 31 * result + (login == null ? 0 : login.hashCode());
        result = 31 * result + (password == null ? 0 : password.hashCode());

        return result;
    }
}
