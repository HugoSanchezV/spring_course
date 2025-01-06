package com.sanchez.hugo.short_url.services;

import com.sanchez.hugo.short_url.entities.Url;
import com.sanchez.hugo.short_url.repositories.UrlRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UrlServiceImplementation implements UrlService{

    final private UrlRepository repository;

    public UrlServiceImplementation(UrlRepository repository) {
        this.repository = repository;
    }

    @Override
    public Url save(Url url)  {
        try {
            return repository.save(url);
        }catch(Exception e){
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public Optional<Url> findByShortCode(String shortCode) {
        try {
            Optional<Url> optionalUrl = repository.findByShortCode(shortCode);
            if (optionalUrl.isPresent()) {
                return optionalUrl;
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Url> deleteById(Long id) {
        try {
            Optional<Url> optionalUrl = repository.findById(id);

            if (optionalUrl.isPresent()){
                repository.deleteById(id);
                return optionalUrl;
            }

        }catch(Exception e){
            throw new RuntimeException(e.getMessage(), e);
        }
        return Optional.empty();
    }
}
