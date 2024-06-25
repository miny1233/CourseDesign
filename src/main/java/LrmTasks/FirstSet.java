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
    public void computeFirstSets() {
        Map<String, NonTerminators> nonTerminatorsMap = typeConversion.getNonTerminatorsMap();

        boolean changed;

        do {
            changed = false;
            for (String nonTerm : nonTerminatorsMap.keySet()) {
                NonTerminators nonTerminator = nonTerminatorsMap.get(nonTerm);
                var firstSetSize = nonTerminator.getFirst().size();

                for (List<CharacterBase> production : nonTerminator.getGrammar()) {
                        CharacterBase symbol = production.getFirst();

                        if (symbol instanceof Terminators) {
                            // 如果是终结符，直接加入 First 集合
                            if (nonTerminator.getFirst().add((Terminators) symbol))
                            {
                                changed = true;
                            }
                        } else if (symbol instanceof NonTerminators) {
                            // 如果是非终结符，加入其 First 集合中的所有非空字符
                            nonTerminator.getFirst().addAll(((NonTerminators) symbol).getFirst());
                            boolean hasEmpty = ((NonTerminators) symbol).getFirst().contains("ε");

                            for (var term : ((NonTerminators) symbol).getFirst()) {
                                if (!Objects.equals(term.getVal(), "ε") && nonTerminator.getFirst().add(term)) {
                                    changed = true;
                                }
                            }

                        } else if (symbol instanceof EmptyCharacter) {
                            // 如果是空字符
                            if (nonTerminator.getFirst().add(symbol)) {
                                changed = true;
                            }
                        }
                }

                if (nonTerminator.getFirst().size() != firstSetSize) {
                    changed = true;
                }
            }
        } while (changed);
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
        typeConversion.saveGrammar(grammar);
        typeConversion.removeEmptyTerminators();

        FirstSet firstSet = new FirstSet(typeConversion);
        firstSet.computeFirstSets();

        System.out.println("First 集合:");
        for (String nonTerm : typeConversion.getNonTerminatorsMap().keySet()) {
            System.out.println(nonTerm + ": " + typeConversion.getNonTerminatorsMap().get(nonTerm).getFirst().toString());
        }
    }
}