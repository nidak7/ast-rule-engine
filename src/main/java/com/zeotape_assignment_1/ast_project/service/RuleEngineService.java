package com.zeotape_assignment_1.ast_project.service;

import com.zeotape_assignment_1.ast_project.entity.Node;
import com.zeotape_assignment_1.ast_project.entity.Rule;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RuleEngineService {

    private static final Set<String> VALID_ATTRIBUTES = new HashSet<>(Arrays.asList("age", "department", "salary", "experience", "spend"));
    private static final Set<String> VALID_FUNCTIONS = new HashSet<>(Arrays.asList("isAdult", "hasExperience"));

    // Create a single rule from a rule string
    public Node createRule(String ruleString) {
        return parseRule(ruleString);
    }

    // Parse the rule into an AST structure
    private Node parseRule(String rule) {
        rule = rule.trim();
        if (rule.isEmpty()) {
            throw new IllegalArgumentException("Rule string cannot be empty.");
        }

        Stack<Node> nodeStack = new Stack<>();
        Stack<String> operatorStack = new Stack<>();
        String[] tokens = rule.split("(?<=\\s)(?=\\()|(?<=\\))|\\s+|(?<=[<>!=]=)|(?<=[()])|(?=[()])");

        for (int i = 0; i < tokens.length; i++) {
            String token = tokens[i].trim();
            if (token.isEmpty()) continue;

            if (token.equals("AND") || token.equals("OR")) {
                while (!operatorStack.isEmpty() && precedence(operatorStack.peek()) >= precedence(token)) {
                    processOperator(nodeStack, operatorStack);
                }
                operatorStack.push(token);
            } else if (token.equals("(")) {
                operatorStack.push(token);
            } else if (token.equals(")")) {
                while (!operatorStack.isEmpty() && !operatorStack.peek().equals("(")) {
                    processOperator(nodeStack, operatorStack);
                }
                if (operatorStack.isEmpty()) {
                    throw new IllegalStateException("Mismatched parentheses.");
                }
                operatorStack.pop();
            } else if (token.contains("(")) {
                String functionName = token.substring(0, token.indexOf("("));
                if (isValidFunction(functionName)) {
                    String param = token.substring(token.indexOf("(") + 1, token.indexOf(")")).trim();
                    Node functionNode = new Node("function", null, null, functionName);
                    functionNode.setUserValue(param);
                    nodeStack.push(functionNode);
                } else {
                    throw new IllegalArgumentException("Invalid function: " + functionName);
                }
            } else {
                if (i + 2 < tokens.length) {
                    String attribute = token;
                    validateAttribute(attribute);

                    String operator = tokens[++i].trim();
                    if (!isValidOperator(operator)) {
                        throw new IllegalArgumentException("Invalid operator: " + operator);
                    }

                    String userValue = tokens[++i].trim();
                    Node operandNode = new Node("operand", null, null, operator);
                    operandNode.setAttribute(attribute);
                    operandNode.setUserValue(userValue.replace("'", ""));
                    nodeStack.push(operandNode);
                } else {
                    throw new IllegalArgumentException("Invalid expression format, missing parts after attribute: " + token);
                }
            }
        }

        while (!operatorStack.isEmpty()) {
            processOperator(nodeStack, operatorStack);
        }

        if (nodeStack.isEmpty()) {
            throw new IllegalStateException("Failed to create rule, invalid expression.");
        }

        return nodeStack.pop();
    }

    private void processOperator(Stack<Node> nodeStack, Stack<String> operatorStack) {
        if (nodeStack.size() < 2) {
            throw new IllegalStateException("Not enough operands to apply the operator");
        }
        String operator = operatorStack.pop();
        Node rightNode = nodeStack.pop();
        Node leftNode = nodeStack.pop();
        nodeStack.push(new Node("operator", leftNode, rightNode, operator));
    }

    private int precedence(String operator) {
        if (operator.equals("AND")) {
            return 2;
        } else if (operator.equals("OR")) {
            return 1;
        }
        return 0;
    }

    // Combine multiple rules into a single combined rule
    public Node combineRules(List<String> ruleStrings) {
        List<Node> nodes = new ArrayList<>();
        for (String ruleString : ruleStrings) {
            nodes.add(createRule(ruleString));
        }

        if (nodes.isEmpty()) {
            throw new IllegalArgumentException("No rules provided for combination.");
        }

        Node combinedNode = nodes.get(0);
        for (int i = 1; i < nodes.size(); i++) {
            combinedNode = new Node("operator", combinedNode, nodes.get(i), "AND"); // You can also customize with OR
        }
        return combinedNode;
    }

    // Evaluate a rule against user data
    public boolean evaluateRule(Node node, Map<String, Object> userData) {
        if ("operator".equals(node.getType())) {
            if ("AND".equals(node.getValue())) {
                return evaluateRule(node.getLeft(), userData) && evaluateRule(node.getRight(), userData);
            } else if ("OR".equals(node.getValue())) {
                return evaluateRule(node.getLeft(), userData) || evaluateRule(node.getRight(), userData);
            }
        } else if ("operand".equals(node.getType())) {
            String condition = node.getAttribute() + " " + node.getValue() + " " + node.getUserValue();
            return evaluateCondition(condition, userData);
        } else if ("function".equals(node.getType())) {
            return evaluateFunction(node, userData);
        }
        return false;
    }

    // Evaluate condition logic
    private boolean evaluateCondition(String condition, Map<String, Object> userData) {
        String[] parts = condition.split(" ");
        String attribute = parts[0];
        String operator = parts[1];
        String value = parts[2];

        Object userValue = userData.get(attribute);

        if (userValue instanceof Integer) {
            int userVal = (Integer) userValue;
            int compareVal = Integer.parseInt(value);
            switch (operator) {
                case ">":
                    return userVal > compareVal;
                case "<":
                    return userVal < compareVal;
                case "=":
                    return userVal == compareVal;
                case ">=":
                    return userVal >= compareVal;
                case "<=":
                    return userVal <= compareVal;
                default:
                    return false;
            }
        } else if (userValue instanceof String) {
            return operator.equals("=") && userValue.equals(value);
        }
        return false;
    }

    // Evaluate function logic
    private boolean evaluateFunction(Node functionNode, Map<String, Object> userData) {
        String functionName = functionNode.getValue();
        String param = functionNode.getUserValue();

        switch (functionName) {
            case "isAdult":
                return (Integer) userData.get("age") >= 18;
            case "hasExperience":
                return (Integer) userData.get("experience") > 5;
            default:
                throw new IllegalArgumentException("Unknown function: " + functionName);
        }
    }

    // Modify an existing rule in the AST
    public Node modifyRule(Node ast, String attribute, String newOperator, String newValue) {
        if (ast == null) {
            throw new IllegalArgumentException("AST cannot be null.");
        }

        if (ast.getType().equals("operand") && ast.getAttribute().equals(attribute)) {
            ast.setValue(newOperator);
            ast.setUserValue(newValue);
            return ast;
        } else if (ast.getType().equals("operator")) {
            modifyRule(ast.getLeft(), attribute, newOperator, newValue);
            modifyRule(ast.getRight(), attribute, newOperator, newValue);
        }
        return ast;
    }

    // Validations
    private boolean isValidOperator(String operator) {
        return operator.equals(">") || operator.equals("<") || operator.equals("=") || operator.equals(">=") || operator.equals("<=");
    }

    private boolean isValidFunction(String functionName) {
        return VALID_FUNCTIONS.contains(functionName);
    }

    private void validateAttribute(String attribute) {
        if (!VALID_ATTRIBUTES.contains(attribute)) {
            throw new IllegalArgumentException("Invalid attribute: " + attribute);
        }
    }
}
