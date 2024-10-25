package com.zeotape_assignment_1.ast_project.entity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Node {
    private String type;
    private Node left;
    private Node right;
    private String value;
    private String attribute;
    private String userValue;

    public Node(String type, Node left, Node right, String value) {
        this.type = type;
        this.left = left;
        this.right = right;
        this.value = value;
    }


    @Override
    public String toString() {
        if (type.equals("operator")) {
            return "Operator: " + value;
        } else if (type.equals("operand")) {
            return "Operand: " + attribute + " " + value + " " + userValue;
        }
        return "Unknown node type";
    }
}



