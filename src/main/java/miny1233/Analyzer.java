package miny1233;

import Model.CharacterBase;
import Model.NonTerminators;
import Model.Terminators;

import java.util.ArrayDeque;
import java.util.ListIterator;

public class Analyzer {

    private final NonTerminators Begin;
    public Analyzer(NonTerminators Begin)
    {
        this.Begin = Begin;
    }

    private MachineStatus machine;
    public void setSentence(String sentence)
    {
        machine = new MachineStatus();

        machine.input_str = new ArrayDeque<>();
        for (var ch : sentence.toCharArray())
            machine.input_str.add(ch);

        machine.AnalyStack.push(Begin);
    }

    public MachineStatus getMachine() {
        return machine;
    }

    public void next() throws Exception {
        // 分析完成
        if (machine.AnalyStack.isEmpty())
        {
            return;
        }

        var topC = machine.AnalyStack.peek();
        // 推导到终结符
        if (topC instanceof Terminators terminators)
        {
            if (terminators.toString().toCharArray()[0] != machine.input_str.peek())
            {
                throw new Exception("分析出错！");
            } else {
                machine.AnalyStack.pop();
                machine.input_str.poll();
            }
        }

        if (topC instanceof NonTerminators nonTerminators)
        {
            var nextChar = machine.input_str.peek();
            var derivation = nonTerminators.getMapping().get(nextChar);

            if (derivation.isEmpty())
            {
                throw new Exception("分析错误!缺少推导！");
            }

            machine.AnalyStack.pop();

            ListIterator<CharacterBase> iterator = derivation.listIterator();
            while(iterator.hasPrevious())
            {
                machine.AnalyStack.push(iterator.previous());
            }
        }

    }
}
