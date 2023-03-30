package ua.edu.ontu.service;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriUtils;

import java.nio.charset.StandardCharsets;

@Service
class DownloadService {

    public ResponseEntity<ByteArrayResource> createResponse(ByteArrayResource resource, HttpHeaders headers) {
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(resource.contentLength())
                .body(resource);
    }

    public String encodeString(String filename) {
        return UriUtils.encode(filename, StandardCharsets.UTF_8);
    }

}
