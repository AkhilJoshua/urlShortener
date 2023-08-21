package com.example.linkshortener.repository;

import com.example.linkshortener.model.Url;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UrlRepository extends JpaRepository<Url, Long> {
    Url findByShortLink(String shortLink);
}
