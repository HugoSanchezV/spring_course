package com.sanchez.hugo.short_url.repositories;

import com.sanchez.hugo.short_url.entities.Url;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UrlRepository extends JpaRepository<Url, Long> {
    Optional<Url> findByShortCode(String shortCode);
}
