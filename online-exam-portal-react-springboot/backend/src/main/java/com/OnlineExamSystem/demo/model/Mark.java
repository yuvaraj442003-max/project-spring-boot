package com.OnlineExamSystem.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Mark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private Register userid;

    @ManyToOne
    @JoinColumn(name = "test_id", referencedColumnName = "id")
    private TestDetail testDetail;

    private int mark;

    private LocalDateTime date;

    private int cheatingCount;
}
