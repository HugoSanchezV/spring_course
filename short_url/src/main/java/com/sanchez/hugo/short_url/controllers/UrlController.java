package com.sanchez.hugo.short_url.controllers;

import com.sanchez.hugo.short_url.entities.Url;
import com.sanchez.hugo.short_url.services.UrlService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
public class UrlController {

    final private UrlService service;

    public UrlController(UrlService service) {
        this.service = service;
    }

    @PostMapping("/shorten")
    public ResponseEntity<Url> shorten(@RequestBody String url){
        try  {
            if (url == null || url.isBlank()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }

            String shortCode = generateShortCode();

            Url urlObject = new Url();
            urlObject.setUrl(url);
            urlObject.setShortCode(shortCode);

            Url savedUrl = service.save(urlObject);

            return ResponseEntity.status(HttpStatus.CREATED).body(savedUrl);
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/shorten/{shortCode}")
    public ResponseEntity<Url> findUrlFronShortCode(@PathVariable String shortCode) {
        try {
            if (!shortCode.matches("^[a-zA-Z0-9]{8}$")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }

            Optional<Url> urlOptional = service.findByShortCode(shortCode);
            return urlOptional.isPresent()
                    ? ResponseEntity.status(HttpStatus.OK).body(urlOptional.get())
                    : ResponseEntity.notFound().build();
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    private String generateShortCode() {
        return UUID.randomUUID().toString().substring(0, 8);
    }

    @PutMapping("/shorten/{shortCode}")
    public ResponseEntity<Url> updateShortCode(@PathVariable String shortCode) {
        try {

            if (!shortCode.matches("^[a-zA-Z0-9]{8}$")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }

            Optional<Url> urlOptional = service.findByShortCode(shortCode);

            if(urlOptional.isPresent()) {
                Url url = urlOptional.get();
                url.setShortCode(generateShortCode());
                Url urlUpdated = service.save(url);
                return ResponseEntity.status(HttpStatus.OK).body(urlUpdated);
            }
            return ResponseEntity.notFound().build();
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/shorten/{shortCode}")
    public ResponseEntity<Url> deleteUrl(@PathVariable String shortCode) {
        try {
            if (!shortCode.matches("^[a-zA-Z0-9]{8}$")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }

            Optional<Url> urlOptional = service.findByShortCode(shortCode);

            if(urlOptional.isPresent()) {
                Optional<Url> deleteOptional = service.deleteById(urlOptional.get().getId());
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }
            return ResponseEntity.notFound().build();
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
