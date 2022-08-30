package ru.msaggik.spring.SpringBootSecurityPreAuthorise.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import ru.msaggik.spring.SpringBootSecurityPreAuthorise.security.PersonDetails;
import ru.msaggik.spring.SpringBootSecurityPreAuthorise.services.AdminService;

@Controller
public class HelloController {

    private final AdminService adminService;
    @Autowired
    public HelloController(AdminService adminService) {
        this.adminService = adminService;
    }


    @GetMapping("/hello")
    public String sayHello() {
        return "hello";
    }

    // реализация метода для доступа в java потоку
    @GetMapping("/showUserInfo")
    public String showUserInfo() {
        // получение доступа к объекту authentication
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // получение доступа к personDetails (принципалу)
        PersonDetails personDetails = (PersonDetails)authentication.getPrincipal();
        System.out.println(personDetails.getPerson()); // вывод на экран полей пользователя
        return "hello";
    }

    @GetMapping("/admin") // страница для админа
    public String adminPage() {
        // вызов метода AdminService
        adminService.doAdminStuff();

        return "admin";
    }
}
