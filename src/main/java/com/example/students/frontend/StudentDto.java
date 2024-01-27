package com.example.students.frontend;

import com.example.students.resource.StudentUnit;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

//@Getter
@Setter
public class StudentDto {

    private UUID id;
    private String name;
    private StudentUnit unit;
    private Long index;

    private String email;
    private String phoneNumber;
    public StudentDto() {
    }

    public StudentDto(UUID id, String name, StudentUnit unit, Long index) {
        this.id = id;
        this.name = name;
        this.unit = unit;
        this.index = index;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUnit(StudentUnit unit) {
        this.unit = unit;
    }

    public void setIndex(Long index) {
        this.index = index;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
