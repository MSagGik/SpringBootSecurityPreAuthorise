package ru.msaggik.spring.SpringBootSecurityPreAuthorise.services;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    // данный метод должен выполняться только администратором
    @PreAuthorize("hasRole('ROLE_ADMIN')") // аннотация доступа к методу
    public void doAdminStuff() {
        System.out.println("Only admin here");
    }
}
