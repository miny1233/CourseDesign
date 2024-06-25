package LrmTasks;

import Model.CharacterBase;
import Model.EmptyCharacter;
import Model.NonTerminators;
import Model.Terminators;

import java.util.*;

public class FirstSet {
    private TypeConversion typeConversion;

    public FirstSet(TypeConversion typeConversion) {
        this.typeConversion = typeConversion;
    }

    // 计算所有非终结符的 First 集合
    public Map<String, Set<String>> computeFirstSets() {
        Map<String, NonTerminators> nonTerminatorsMap = typeConversion.getNonTerminatorsMap();
        Map<String, Set<String>> firstSets = new HashMap<>();

        // 初始化 First 集合
        for (String nonTerm : nonTerminatorsMap.keySet()) {
            firstSets.put(nonTerm, new HashSet<>());
        }

        boolean changed;
        do {
            changed = false;
            for (String nonTerm : nonTerminatorsMap.keySet()) {
                NonTerminators nonTerminator = nonTerminatorsMap.get(nonTerm);
                List<List<CharacterBase>> productions = typeConversion.saveGrammar(nonTerm + "->" + String.join("|", getProductionStrings(nonTerminator)));

                for (List<CharacterBase> production : productions) {
                    if (!production.get(0).getVal().equals(nonTerm)) {
                        continue; // 忽略不相关的生产式
                    }
                    for (int i = 1; i < production.size(); i++) { // 跳过第一个元素
                        CharacterBase symbol = production.get(i);
                        if (symbol instanceof Terminators) {
                            // 如果是终结符，直接加入 First 集合
                            if (firstSets.get(nonTerm).add(symbol.getVal())) {
                                changed = true;
                            }
                            break;
                        } else if (symbol instanceof NonTerminators) {
                            // 如果是非终结符，加入其 First 集合中的所有非空字符
                            Set<String> symbolFirstSet = firstSets.get(symbol.getVal());
                            boolean hasEmpty = symbolFirstSet.contains("ε");

                            for (String term : symbolFirstSet) {
                                if (!term.equals("ε") && firstSets.get(nonTerm).add(term)) {
                                    changed = true;
                                }
                            }
                            if (!hasEmpty) {
                                break;
                            }
                        } else if (symbol instanceof EmptyCharacter) {
                            // 如果是空字符
                            if (firstSets.get(nonTerm).add("ε")) {
                                changed = true;
                            }
                            break;
                        }
                    }
                }
            }
        } while (changed);

        return firstSets;
    }

    public List<List<CharacterBase>> getFirstSetsAsList(Map<String, Set<String>> firstSets) {
        List<List<CharacterBase>> firstSetsList = new ArrayList<>();

        for (Map.Entry<String, Set<String>> entry : firstSets.entrySet()) {
            List<CharacterBase> firstSet = new ArrayList<>();
            firstSet.add(new NonTerminators(entry.getKey())); // 添加非终结符

            for (String symbol : entry.getValue()) {
                if (symbol.equals("ε")) {
                    firstSet.add(new EmptyCharacter());
                } else {
                    firstSet.add(new Terminators(symbol));
                }
            }

            firstSetsList.add(firstSet);
        }

        return firstSetsList;
    }

    // 辅助方法，获取 NonTerminators 的所有生产式字符串形式
    private List<String> getProductionStrings(NonTerminators nonTerminator) {
        List<String> productions = new ArrayList<>();
        for (Map.Entry<Character, List<CharacterBase>> entry : nonTerminator.getMapping().entrySet()) {
            StringBuilder sb = new StringBuilder();
            for (CharacterBase cb : entry.getValue()) {
                if (cb == null) {
                    sb.append("ε");
                } else {
                    sb.append(cb.getVal());
                }
            }
            productions.add(sb.toString());
        }
        return productions;
    }

    public static void main(String[] args) {
        TypeConversion typeConversion = new TypeConversion();
        String grammar = "E->T E'\n" +
                "E'->+ T E'|ε\n" +
                "T->F T'\n" +
                "T'->* F T'|ε\n" +
                "F->( E )|i\n";
        typeConversion.ConverseGrammar(grammar);
        List<List<CharacterBase>> grammarList = typeConversion.saveGrammar(grammar);
        typeConversion.removeEmptyTerminators();

        FirstSet firstSet = new FirstSet(typeConversion);
        Map<String, Set<String>> firstSets = firstSet.computeFirstSets();

        System.out.println("First 集合:");
        for (Map.Entry<String, Set<String>> entry : firstSets.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
}