package LrmTasks;

import Model.EmptyCharacter;
import Model.NonTerminators;
import Model.Terminators;
import Model.CharacterBase;

import java.util.*;

public class TypeConversion {
    private Map<String, NonTerminators> NonTerminatorsMap = new HashMap<>();
    private Map<String, Terminators> TerminatorsMap = new HashMap<>();
    private Map<String, EmptyCharacter> EmptyCharacterMap = new HashMap<>();

    // 判断是否是非终结符
    private boolean isNonTerminator(String symbol) {
        return symbol.matches("^[A-Z]('?[A-Z]*)?$");
    }

    public void removeEmptyTerminators() {
        TerminatorsMap.remove(" ");
    }

    public Map<String, NonTerminators> getNonTerminatorsMap() {
        return NonTerminatorsMap;
    }
    public Map<String, EmptyCharacter> getEmptyCharacterMap() {
        return EmptyCharacterMap;
    }

    public Map<String, Terminators> getTerminatorsMap() {
        return TerminatorsMap;
    }

    // 用于String到Object的类型转换
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
                String trimmedAlternative = alternative.trim(); // 修剪替代选项的空格
                if (trimmedAlternative.equals("ε")) {
                    nonTerminators.getMapping().computeIfAbsent('E', k -> new ArrayList<>()).add(null);  // 空字符使用 null 表示
                    EmptyCharacter emptyChar = EmptyCharacterMap.computeIfAbsent("ε", k -> {
                        EmptyCharacter ec = new EmptyCharacter();
                        ec.setVal("ε");
                        return ec;
                    });
                } else {
                    for (int i = 0; i < trimmedAlternative.length(); ) {
                        if (Character.isUpperCase(trimmedAlternative.charAt(i))) {
                            StringBuilder nonTerminator = new StringBuilder();
                            nonTerminator.append(trimmedAlternative.charAt(i));
                            i++;
                            while (i < trimmedAlternative.length() && (Character.isUpperCase(trimmedAlternative.charAt(i)) || trimmedAlternative.charAt(i) == '\'')) {
                                nonTerminator.append(trimmedAlternative.charAt(i));
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
                            terminator.append(trimmedAlternative.charAt(i));
                            i++;
                            while (i < trimmedAlternative.length() && !Character.isUpperCase(trimmedAlternative.charAt(i)) && trimmedAlternative.charAt(i) != '|') {
                                terminator.append(trimmedAlternative.charAt(i));
                                i++;
                            }
                            String terminatorStr = terminator.toString().trim(); // 修剪终结符的空格
                            if (!terminatorStr.isEmpty()) {
                                for (int j = 0; j < terminatorStr.length(); j++) {
                                    String singleTerminator = String.valueOf(terminatorStr.charAt(j));
                                    Terminators t = TerminatorsMap.computeIfAbsent(singleTerminator, k -> {
                                        Terminators term = new Terminators();
                                        term.setVal(singleTerminator);
                                        return term;
                                    });
                                    nonTerminators.getMapping().computeIfAbsent('T', k -> new ArrayList<>()).add(t);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // 用于保存文法
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
                "F->( E )|i\n" +
                "Q->+ * i";
        List<List<CharacterBase>> grammarList = typeConversion.saveGrammar(grammar);
        typeConversion.removeEmptyTerminators();

        System.out.println("非终结符:");
        for (String key : typeConversion.getNonTerminatorsMap().keySet()) {
            System.out.println(key);
        }

        System.out.println("终结符:");
        for (String key : typeConversion.getTerminatorsMap().keySet()) {
            System.out.println(key);
        }

        System.out.println("空字符:");
        for (String key : typeConversion.getEmptyCharacterMap().keySet()) {
            System.out.println(key);
        }
        System.out.println("文法列表:");
        for (List<CharacterBase> list : grammarList) {
            for (CharacterBase character : list) {
                if (character == null) {
                    System.out.print("ε ");
                } else {
                    System.out.print(character.getVal() + " ");
                }
            }
            System.out.println();
        }
    }
}