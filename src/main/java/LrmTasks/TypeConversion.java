package LrmTasks;

import Model.NonTerminators;
import Model.Terminators;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TypeConversion {
    private Map<String, NonTerminators> NonTerminatorsMap = new HashMap<>();
    private Map<String, Terminators> TerminatorsMap = new HashMap<>();

    private boolean isNonTerminator(String symbol) {
        return symbol.matches("^[A-Z]('?[A-Z]*)*$");
    }

    public Map<String, NonTerminators> getNonTerminatorsMap() {
        return NonTerminatorsMap;
    }

    public Map<String, Terminators> getTerminatorsMap() {
        return TerminatorsMap;
    }

    public void ConverseGrammar(String grammar){
        String[] rules = grammar.split("\n");
        for (String rule : rules) {
            String[] parts = rule.split("->");
            if(parts.length != 2){
                throw new IllegalArgumentException("文法错误: " + rule);
            }
            String leftParts = parts[0].trim();
            NonTerminators nonTerminators = NonTerminatorsMap.computeIfAbsent(leftParts,NonTerminators::new);

            String[] rightParts = parts[1].trim().split(" ");
            for (String rightPart : rightParts) {
                if(isNonTerminator(rightPart)){
                    NonTerminators rightN = NonTerminatorsMap.computeIfAbsent(rightPart,NonTerminators::new);
                    NonTerminators.getMapping().computeIfAbsent('N', k -> new ArrayList<>()).add(rightN);
                } else {
                Terminators terminator = TerminatorsMap.computeIfAbsent(rightPart, Terminators::new);
                NonTerminators.getMapping().computeIfAbsent('T', k -> new ArrayList<>()).add(terminator);
                }
            }
        }

    }
    public static void main(String[] args) {
        TypeConversion typeConversion = new TypeConversion();
        String grammar = "S -> E'\nE' -> a\nB -> b";
        typeConversion.ConverseGrammar(grammar);

        System.out.println("Non-terminators:");
        for (String key : typeConversion.getNonTerminatorsMap().keySet()) {
            System.out.println(key);
        }

        System.out.println("Terminators:");
        for (String key : typeConversion.getTerminatorsMap().keySet()) {
            System.out.println(key);
        }
    }
}
