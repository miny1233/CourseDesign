package miny1233;

import Model.CharacterBase;
import Model.NonTerminators;

import java.util.*;

public class MachineStatus {
    public Stack<CharacterBase> AnalyStack = new Stack<>();
    public Queue<Character> input_str = new LinkedList<>();
    public NonTerminators first;
    public List<CharacterBase> der = new ArrayList<>();

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder("符号栈: ");
        output.append(AnalyStack.toString());
        output.append(" 输入串: ").append(input_str.toString());

        if (first != null) {
            output.append("产生式: ").append(first.toString()).append(" -> ");
            for (CharacterBase character : der) {
                output.append(character.toString());
            }
            first = null;
        }

        return output.toString();
    }
}
