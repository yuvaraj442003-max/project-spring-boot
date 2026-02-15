package com.OnlineExamSystem.demo.model;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Register {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String password;
    private String institute;
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('student','teacher')")
    private Role role;
    private String empid;

    @JsonManagedReference
    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL)
    private List<TestDetail> testDetails;


    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] image;


    public enum Role {
        student, teacher
    }


}
