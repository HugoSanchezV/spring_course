package com.hugo.spingboot.springmvc.app.controllers;

import com.hugo.spingboot.springmvc.app.entities.User;
import com.hugo.spingboot.springmvc.app.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/users") // grupo de ruts con inicial app, prefijo
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping({"/view", "/another"})
    public String view(Model model) {
        model.addAttribute("title", "Hola Mundo Spring Boot");
        model.addAttribute("message", "Esta es una aplicacion de ejemplo en sping boot");
        model.addAttribute("user", new User("Hugo", "Sanchez"));

        return "view";
    }

    @GetMapping("/")
    public String list(Model model) {
        model.addAttribute("title", "Listado de usuarios");
        model.addAttribute("users", service.findAll());
        return "list";
    }

    @GetMapping("/form")
    public String form(Model model) {
        model.addAttribute("title", "Formulario Usuario");
        model.addAttribute("user", new User());
        return "form";
    }

    @GetMapping("/form/edit/{id}")
    public String form(@PathVariable Long id, Model model) {
        Optional<User> optionalUser = service.findById(id);

        if(optionalUser.isPresent()) {
            model.addAttribute("title", "Editar Usuario");
            model.addAttribute("user", optionalUser.get());
            return "form";
        } else {
            return "redirect:/users";
        }
    }

    @PostMapping
    public String form(User user, Model model) {
        service.save(user);
        return "redirect:/users";
    }

   @GetMapping("/delete/{id}")
   public String delete(@PathVariable Long id) {
        service.remove(id);
        return "redirect:/users";
   }
}
