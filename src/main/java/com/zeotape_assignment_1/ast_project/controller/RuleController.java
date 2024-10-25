package com.zeotape_assignment_1.ast_project.controller;

import com.zeotape_assignment_1.ast_project.entity.Node;
import com.zeotape_assignment_1.ast_project.service.RuleEngineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/rules")
public class RuleController {

    @Autowired
    private RuleEngineService ruleEngineService;

    
    @PostMapping("/create")
    public ResponseEntity<Node> createRule(@RequestBody Map<String, String> request) {
        String rule = request.get("rule");
        Node ast = ruleEngineService.createRule(rule);
        return ResponseEntity.ok(ast);
    }

   
    @PostMapping("/combine")
    public ResponseEntity<Node> combineRules(@RequestBody List<String> rules) {
        Node combinedAST = ruleEngineService.combineRules(rules);
        return ResponseEntity.ok(combinedAST);
    }

  
    @PostMapping("/evaluate")
    public ResponseEntity<Boolean> evaluateRule(@RequestBody Map<String, Object> request) {
        Node ast = ruleEngineService.createRule((String) request.get("rule"));
        Map<String, Object> userData = (Map<String, Object>) request.get("userData");
        boolean result = ruleEngineService.evaluateRule(ast, userData);
        return ResponseEntity.ok(result);
    }


    @PutMapping("/modify")
    public ResponseEntity<Node> modifyRule(@RequestBody Map<String, String> request) {
        String rule = request.get("rule");
        String attribute = request.get("attribute");
        String newOperator = request.get("newOperator");
        String newValue = request.get("newValue");

        Node ast = ruleEngineService.createRule(rule);
        Node modifiedAST = ruleEngineService.modifyRule(ast, attribute, newOperator, newValue);

        return ResponseEntity.ok(modifiedAST);
    }
}
