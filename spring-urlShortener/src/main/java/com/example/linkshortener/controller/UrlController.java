package com.example.linkshortener.controller;

import com.example.linkshortener.model.Url;
import com.example.linkshortener.service.UrlService;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")
public class UrlController {

    @Autowired
    private UrlService urlService;

    @PostMapping("/generate")
    public ResponseEntity<Url> generateShortLink(@RequestBody Url url) {
        Url generatedUrl = urlService.generateShortLink(url.getOriginalUrl());
        return new ResponseEntity<>(generatedUrl, HttpStatus.CREATED);
    }
//get method
    @GetMapping("/short-links/{shortLink}")
    public ResponseEntity<String> redirectToOriginalUrl(@PathVariable String shortLink) {
        Url url = urlService.getEncodedUrl(shortLink);
        if (url == null) {
            return ResponseEntity.notFound().build();
        }

        if (url.getExpirationDate().isBefore(LocalDateTime.now())) {
            return ResponseEntity.badRequest().body("The short link has expired.");
        }

        return ResponseEntity.ok(url.getOriginalUrl());
    }
}




