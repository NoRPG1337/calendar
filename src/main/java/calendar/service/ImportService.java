package calendar.service;

import calendar.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class ImportService {

    private UserService userService;

    @Autowired
    public void autowire(UserService userService) {
        this.userService = userService;
    }

    @EventListener(ApplicationReadyEvent.class)
    @Order(0)
    public void importUsers() {
        User first = new User(
                "gorolevich.ip",
                Base64.getMimeEncoder().encodeToString(("Qwerty123").getBytes()),
                "Горолевич И.П."
        );
        this.userService.saveIfNotExists(first);
        User second = new User(
                "ivanov.ii",
                Base64.getMimeEncoder().encodeToString(("Qwerty123").getBytes()),
                "Иванов И.И."
        );
        this.userService.saveIfNotExists(second);
        User third = new User(
                "petrov.pp",
                Base64.getMimeEncoder().encodeToString(("Qwerty123").getBytes()),
                "Петров П.П."
        );
        this.userService.saveIfNotExists(third);
    }
}
