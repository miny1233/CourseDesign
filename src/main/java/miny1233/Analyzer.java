package miny1233;

import Model.NonTerminators;
import Model.Terminators;

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
        for (var ch : sentence.toCharArray())
            machine.input_str.add(ch);

        machine.AnalyStack.push(Begin);
    }

    public MachineStatus getMachine() {
        return machine;
    }

    public void next()
    {
        var topC = machine.AnalyStack.peek();

        if (topC instanceof Terminators)
        {
            var terminators = (Terminators) topC;
            // if ()

        }

    }
}
