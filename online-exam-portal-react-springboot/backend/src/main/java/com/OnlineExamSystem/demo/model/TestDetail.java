package com.OnlineExamSystem.demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "testdetail")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
public class TestDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String testname;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "teacherid", referencedColumnName = "id")
    private Register teacher;
    @OneToMany(mappedBy = "testDetail", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnoreProperties("testDetail")
    private List<Test> tests;

}
