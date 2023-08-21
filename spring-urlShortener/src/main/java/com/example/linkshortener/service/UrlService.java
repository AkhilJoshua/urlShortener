package com.example.linkshortener.service;

import com.example.linkshortener.model.Url;

public interface UrlService {
    Url generateShortLink(String originalUrl);

    Url getEncodedUrl(String shortLink);
}

