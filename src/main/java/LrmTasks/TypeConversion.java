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

            String[] alternatives = parts[1].trim().split("\\|");
            for (String alternative : alternatives) {
                for (int i = 0; i < alternative.length(); ) {
                    if (Character.isUpperCase(alternative.charAt(i))) {
                        StringBuilder nonTerminator = new StringBuilder();
                        nonTerminator.append(alternative.charAt(i));
                        i++;
                        while (i < alternative.length() && (Character.isUpperCase(alternative.charAt(i)) || alternative.charAt(i) == '\'')) {
                            nonTerminator.append(alternative.charAt(i));
                            i++;
                        }
                        NonTerminators rightN = NonTerminatorsMap.computeIfAbsent(nonTerminator.toString(), k -> {
                            NonTerminators nt = new NonTerminators();
                            nt.setVal(nonTerminator.toString());
                            return nt;
                        });
                        nonTerminators.getMapping().computeIfAbsent('N', k -> new ArrayList<>()).add(rightN);
                    } else {
                        StringBuilder terminator = new StringBuilder();
                        terminator.append(alternative.charAt(i));
                        i++;
                        while (i < alternative.length() && !Character.isUpperCase(alternative.charAt(i)) && alternative.charAt(i) != '|') {
                            terminator.append(alternative.charAt(i));
                            i++;
                        }
                        Terminators t = TerminatorsMap.computeIfAbsent(terminator.toString(), k -> {
                            Terminators term = new Terminators();
                            term.setVal(terminator.toString());
                            return term;
                        });
                        nonTerminators.getMapping().computeIfAbsent('T', k -> new ArrayList<>()).add(t);
                    }
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
        String grammar = "E->T E'\n" +
                "E'->+ T E'|ε\n" +
                "T->F T'\n" +
                "T'->* F T'|ε\n" +
                "F->( E )|i";
        List<List<CharacterBase>> grammarList = typeConversion.saveGrammar(grammar);

        System.out.println("非终结符:");
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
}