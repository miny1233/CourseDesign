package miny1233;

import Model.NonTerminators;

import java.util.ArrayDeque;
import java.util.List;

public class Analyzer {
    private List<NonTerminators> AnalyzeTable;
    private NonTerminators Begin;
    public Analyzer(NonTerminators Begin,List<NonTerminators> AnalyzeTable)
    {
        this.Begin = Begin;
        this.AnalyzeTable = AnalyzeTable;
    }

    private String sentence;
    private MachineStatus machine;
    public void setSentence(String sentence)
    {
        this.sentence = sentence;
        machine = new MachineStatus();

        machine.input_str = new ArrayDeque<>();
        for (int idx = 0;idx < sentence.length();idx++)
            machine.input_str.add(sentence.getChars(idx,idx));


        machine.AnalyStack.push(Begin);
    }

    public MachineStatus getMachine() {
        return machine;
    }

    public void next()
    {

    }
}
