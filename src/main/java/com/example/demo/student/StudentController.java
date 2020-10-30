package com.example.demo.student;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("api/v1/students")
public class StudentController {

    private static final List<Student> STUNDENTS= Arrays.asList(
            new Student(1,"Mpholo Leboea"),
            new Student(2,"Nare Hopane"),
            new Student(3,"Tebog Bofelo")
    );

    @GetMapping("{studentId}")
    @ResponseStatus(HttpStatus.OK)
    public Student getStudent(@PathVariable  Integer studentId) {

        return STUNDENTS.stream()
                .filter(student -> studentId.equals(student.getStudentId()))
                .findFirst()
                .orElseThrow(()->new IllegalArgumentException("Student "+studentId+" doest not exists "));

    }

}
