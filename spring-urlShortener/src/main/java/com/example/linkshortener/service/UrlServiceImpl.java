package com.example.linkshortener.service;

import com.example.linkshortener.model.Url;
import com.example.linkshortener.repository.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

import com.google.common.hash.Hashing;

@Service
public class UrlServiceImpl implements UrlService {

    @Autowired
    private UrlRepository urlRepository;

    @Override
    public Url generateShortLink(String originalUrl) {
        String shortLink = encodeUrl(originalUrl);

        LocalDateTime creationDate = LocalDateTime.now();
        LocalDateTime expirationDate = creationDate.plusMinutes(5);

        Url url = new Url(null, originalUrl, shortLink, creationDate, expirationDate);
        return urlRepository.save(url);
    }

    @Override
    public Url getEncodedUrl(String shortLink) {
        return urlRepository.findByShortLink(shortLink);
    }

    private String encodeUrl(String url) {
        String encodedUrl = "";
        LocalDateTime time = LocalDateTime.now();
        encodedUrl = Hashing.murmur3_32()
                .hashString(url.concat(time.toString()), StandardCharsets.UTF_8)
                .toString();
        return encodedUrl;
    }
}



