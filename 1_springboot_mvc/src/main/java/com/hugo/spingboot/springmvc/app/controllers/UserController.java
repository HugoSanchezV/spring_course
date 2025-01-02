package com.hugo.spingboot.springmvc.app.controllers;

import com.hugo.spingboot.springmvc.app.entities.User;
import com.hugo.spingboot.springmvc.app.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller// grupo de ruts con inicial app, prefijo
@RequestMapping("/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping({"/view", "/another"})
    public String view(Model model) {
        model.addAttribute("title", "Hola Mundo Spring Boot");
        model.addAttribute("message", "Esta es una aplicacion de ejemplo en sping boot");
        model.addAttribute("user", new User("Hugo", "Sanchez Velazquez"));

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
    public String form(@PathVariable Long id, Model model, RedirectAttributes redirecct) {
        Optional<User> optionalUser = service.findById(id);

        if(optionalUser.isPresent()) {
            model.addAttribute("title", "Editar Usuario");
            model.addAttribute("user", optionalUser.get());
            return "form";
        } else {
            redirecct.addFlashAttribute("error", "No se ha encontrado el registro");
            return "redirect:/users/";
        }
    }

    @PostMapping("/form")
    public String form(User user, Model model, RedirectAttributes redirect) {
        String message = (user.getId() != null && user.getId() > 0) ? "El usuario ha sido actualizado": "El usuario ha sido creado" ;

        service.save(user);
        redirect.addFlashAttribute("success", message);
        return "redirect:/users/";
    }

   @GetMapping("/delete/{id}")
   public String delete(@PathVariable Long id, RedirectAttributes redirect) {

        Optional<User> optionalUser = service.findById(id);

        if(optionalUser.isPresent()) {
            service.remove(id);
            redirect.addFlashAttribute("success", "El usuario " + optionalUser.get().getName() + " Ha sido eliminado");
        } else {
            redirect.addFlashAttribute("error", "Error con  usuario " + optionalUser.get().getName() + ", no ha sido eliminado");
        }
       return "redirect:/users/";
   }
}
