package com.zeotape_assignment_1.ast_project.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@Table(name = "rules")
@AllArgsConstructor
@NoArgsConstructor
public class Rule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 1000)
    private String ruleString;

    @Column(length = 1000)
    private String astRepresentation;

    @Column(name = "`condition`")
    private String condition;

    private String operator;

    public Rule(String ruleString, String astRepresentation) {
        this.ruleString = ruleString;
        this.astRepresentation = astRepresentation;
    }
}

