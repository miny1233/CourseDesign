package LrmTasks;

import Model.NonTerminators;
import Model.Terminators;
import Model.CharacterBase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TypeConversion {
    private Map<String, NonTerminators> NonTerminatorsMap = new HashMap<>();
    private Map<String, Terminators> TerminatorsMap = new HashMap<>();

    private boolean isNonTerminator(String symbol) {
        return symbol.matches("^[A-Z]('?[A-Z]*)?$");
    }

    public Map<String, NonTerminators> getNonTerminatorsMap() {
        return NonTerminatorsMap;
    }

    public Map<String, Terminators> getTerminatorsMap() {
        return TerminatorsMap;
    }

    //用于String到Object的类型转换
    public void ConverseGrammar(String grammar) {
        String[] rules = grammar.split("\n");
        for (String rule : rules) {
            String[] parts = rule.split("->");
            if (parts.length != 2) {
                throw new IllegalArgumentException("文法错误: " + rule);
            }
            String leftPart = parts[0].trim();
            NonTerminators nonTerminators = NonTerminatorsMap.computeIfAbsent(leftPart, k -> {
                NonTerminators nt = new NonTerminators();
                nt.setVal(leftPart);
                return nt;
            });

            String[] rightParts = parts[1].trim().split(" ");
            for (String rightPart : rightParts) {
                if (isNonTerminator(rightPart)) {
                    NonTerminators rightN = NonTerminatorsMap.computeIfAbsent(rightPart, k -> {
                        NonTerminators nt = new NonTerminators();
                        nt.setVal(rightPart);
                        return nt;
                    });
                    nonTerminators.getMapping().computeIfAbsent('N', k -> new ArrayList<>()).add(rightN);
                } else {
                    Terminators terminator = TerminatorsMap.computeIfAbsent(rightPart, k -> {
                        Terminators t = new Terminators();
                        t.setVal(rightPart);
                        return t;
                    });
                    nonTerminators.getMapping().computeIfAbsent('T', k -> new ArrayList<>()).add(terminator);
                }
            }
        }
    }

    //用于保存文法
    public List<List<CharacterBase>> saveGrammar(String grammar) {
        ConverseGrammar(grammar);
        List<List<CharacterBase>> grammarList = new ArrayList<>();

        for (NonTerminators nonT : NonTerminatorsMap.values()) {
            List<CharacterBase> gra = new ArrayList<>();
            gra.add(nonT);
            Map<Character, List<CharacterBase>> mapping = nonT.getMapping();
            for (Map.Entry<Character, List<CharacterBase>> entry : mapping.entrySet()) {
                gra.addAll(entry.getValue());
            }
            grammarList.add(gra);
        }

        return grammarList;
    }

    public static void main(String[] args) {
        TypeConversion typeConversion = new TypeConversion();
        String grammar = "S -> + E'\nE' -> a\nB -> B' +";
        List<List<CharacterBase>> grammarList = typeConversion.saveGrammar(grammar);

        System.out.println("Non-terminators:");
        for (String key : typeConversion.getNonTerminatorsMap().keySet()) {
            System.out.println(key);
        }

        System.out.println("Terminators:");
        for (String key : typeConversion.getTerminatorsMap().keySet()) {
            System.out.println(key);
        }

        System.out.println("Grammar List:");
        for (List<CharacterBase> list : grammarList) {
            for (CharacterBase character : list) {
                System.out.print(character.getVal() + " ");
            }
            System.out.println();
        }
    }
    //先不要动这个代码
}
