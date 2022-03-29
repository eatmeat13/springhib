package hiber;

import hiber.config.AppConfig;
import hiber.model.Car;
import hiber.model.User;
import hiber.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.persistence.NoResultException;
import java.sql.SQLException;

public class MainApp {
    public static void main(String[] args) throws SQLException {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);

        UserService userService = context.getBean(UserService.class);

        User Leon = new User("Leonid", "Myasoedov", "myasoedov.leon@mail.ru");
        User petya = new User("Petya", "Sidorov", "wertuiyu");
        User olga = new User("Olga", "Petrova", "sdfgsdfg");
        User sveta = new User("Svetlana", "Ivanova", "ivanova@mail.io");

        Car volvo = new Car("Audi A", 7);
        Car bmw = new Car("Toyota", 1);
        Car suzuki = new Car("Opel", 4);
        Car lada = new Car("Mers", 2);

        userService.add(Leon.setCar(volvo).setUser(Leon));
        userService.add(petya.setCar(bmw).setUser(petya));
        userService.add(olga.setCar(suzuki).setUser(olga));
        userService.add(sveta.setCar(lada).setUser(sveta));

        // пользователи с машинами
        for (User user : userService.listUsers()) {
            System.out.println(user + " " + user.getCar());
        }

        // достать юзера, владеющего машиной по ее модели и серии
        System.out.println(userService.getUserByCar("Opel", 4));

        // нет такого юзера с такой машиной
        try {
            User notFoundUser = userService.getUserByCar("GAZ", 4211);
        } catch (NoResultException e) {
            System.out.println("User not found");
        }

        context.close();
    }
}

