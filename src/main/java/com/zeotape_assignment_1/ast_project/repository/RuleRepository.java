package com.zeotape_assignment_1.ast_project.repository;

import com.zeotape_assignment_1.ast_project.entity.Rule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RuleRepository extends JpaRepository<Rule, Long> {
}
