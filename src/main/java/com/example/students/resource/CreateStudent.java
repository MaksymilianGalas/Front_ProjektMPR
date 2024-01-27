package com.example.students.resource;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateStudent {

    @NotBlank
    private String name;
    private StudentUnit unit;
    private Long index;
    private String email;
    private String phoneNumber;

}
