package ru.msaggik.spring.SpringBootSecurityPreAuthorise.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.msaggik.spring.SpringBootSecurityPreAuthorise.models.Person;
import ru.msaggik.spring.SpringBootSecurityPreAuthorise.services.RegistrationService;
import ru.msaggik.spring.SpringBootSecurityPreAuthorise.util.PersonValidator;

import javax.validation.Valid;

@Controller
@RequestMapping("/auth")
public class AuthController {
    // внедрение валидатора
    private final PersonValidator personValidator;
    // внедрение сервиса регистрации
    private final RegistrationService registrationService;

    @Autowired
    public AuthController(PersonValidator personValidator, RegistrationService registrationService) {
        this.personValidator = personValidator;
        this.registrationService = registrationService;
    }


    // метод представления для аутентификации
    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }
    // страница регистрации нового пользователя
    // (аннотация @ModelAttribute("person") в модель Person положит пустого пользователя)
    @GetMapping("/registration")
    public String registrationPage(@ModelAttribute("person") Person person) {
        return "/auth/registration";
    }
    // метод приёма данных с формы регистрации
    @PostMapping("/registration")
    public String performRegistration(@ModelAttribute("person") @Valid Person person,
                                      BindingResult bindingResult) {
        // проверка пользователя на исключение повторной регистрации
        // в bindingResult будет помещаться возможная ошибка
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors())
            return "/auth/registration";
        // вызов метода регистрации пользователя
        registrationService.register(person);
        return "redirect:/auth/login";
    }
}
