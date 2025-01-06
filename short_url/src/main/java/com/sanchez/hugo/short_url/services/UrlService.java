package com.sanchez.hugo.short_url.services;

import com.sanchez.hugo.short_url.entities.Url;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UrlService {
    Url save(Url url);
    Optional<Url> findByShortCode(String shortCode);
    Optional<Url> deleteById(Long id);

}
