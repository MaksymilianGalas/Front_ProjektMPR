package com.example.students.service;

import com.example.students.frontend.StudentDto;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.UUID;
@Component
public class StudentClient {
    private final WebClient webClient;
    private static final String API_URL = "http://localhost:8080/students";

    public StudentClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public StudentDto getStudentById(UUID id) {
        return webClient.get()
                .uri(API_URL + "/{id}", id)
                .retrieve()
                .bodyToMono(StudentDto.class)
                .block();
    }
}