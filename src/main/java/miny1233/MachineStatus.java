package miny1233;

import Model.CharacterBase;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class MachineStatus {
    public Stack<CharacterBase> AnalyStack = new Stack<>();
    public Queue<Character> input_str = new LinkedList<>();
}
