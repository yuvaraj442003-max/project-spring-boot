package com.OnlineExamSystem.demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Test {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String question;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private String answer;

    @Column(columnDefinition = "INT")
    private int level;
    @ManyToOne
    @JoinColumn(name = "test_id", referencedColumnName = "id", nullable = false)
    @JsonIgnoreProperties("tests")
    @JsonBackReference
    private TestDetail testDetail;


    @Override
    public String toString() {
        return "Test{" +
                "id=" + id +
                ", question='" + question + '\'' +
                ", option1='" + option1 + '\'' +
                ", option2='" + option2 + '\'' +
                ", option3='" + option3 + '\'' +
                ", option4='" + option4 + '\'' +
                ", answer='" + answer + '\'' +
                ", level=" + level +
                ", testDetail=" + testDetail +
                '}';
    }


}
