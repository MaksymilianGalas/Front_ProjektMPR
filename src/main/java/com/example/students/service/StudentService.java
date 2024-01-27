package com.example.students.service;

import com.example.students.exception.ResourceNotFoundException;
import com.example.students.frontend.StudentDto;
import com.example.students.resource.CreateStudent;
import lombok.extern.java.Log;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@Log
@Service
public class StudentService {
    private static final String API_URL = "http://localhost:8080/students";
    private final WebClient webClient;
    private final RestTemplate restTemplate;
    private JdbcTemplate jdbcTemplate;


    public StudentService(WebClient webClient, RestTemplate restTemplate, JdbcTemplate jdbcTemplate) {
        this.webClient = webClient;
        this.restTemplate = restTemplate;
        this.jdbcTemplate = jdbcTemplate;
    }

    public void createStudent(CreateStudent createStudent) {
        webClient.post()
                .bodyValue(createStudent)
                .retrieve()
                .toBodilessEntity()
                .subscribe(response -> log.info("Student save properly"));
        log.info("Response returned");
    }

    public ResponseEntity<Void> updateStudent(UUID id, StudentDto studentDto) {
        String url = String.format("%s/%s", API_URL, id);
        HttpEntity<StudentDto> requestEntity = new HttpEntity<>(studentDto);
        restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Void.class);
        return ResponseEntity.noContent().build();
    }

    public void deleteByName(String name) {
        webClient.delete()
                .uri(uriBuilder -> uriBuilder.path("/").queryParam("name", name).build())
                .retrieve()
                .toBodilessEntity()
                .subscribe(response -> log.info("Student deleted properly"));
        log.info("Response returned");
    }

    public StudentDto getStudentById(UUID id) {
        try{
            return webClient.get()
                    .uri("/{id}", id)
                    .retrieve()
                    .bodyToMono(StudentDto.class)
                    .block(Duration.of(10, ChronoUnit.SECONDS));
        }
        catch (WebClientResponseException e){ throw new ResourceNotFoundException("Student with id " + id + " not found"); }
        catch (HttpServerErrorException e){ throw new RuntimeException("Error during sending request"); }
    }

    public List<StudentDto> getStudentsByEmail(String email) {
        try {
            return webClient.get()
                    .uri(uriBuilder -> uriBuilder.queryParam("email", email).build())
                    .retrieve()
                    .bodyToFlux(StudentDto.class)
                    .collectList()
                    .block(Duration.of(10, ChronoUnit.SECONDS));
        } catch (WebClientResponseException e) {
            throw new ResourceNotFoundException("Error during sending request");
        }
    }

    public List<StudentDto> getAll() {
        return webClient.get()
                .uri(API_URL )
                .retrieve()
                .bodyToFlux(StudentDto.class)
                .collectList()
                .block(Duration.of(10, ChronoUnit.SECONDS));
    }
}
