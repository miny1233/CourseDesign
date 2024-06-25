package miny1233;

import Model.CharacterBase;
import Model.EmptyCharacter;
import Model.NonTerminators;
import Model.Terminators;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class test {
    public static void main(String[] args) throws Exception {
        Analyzer analyzer = new Analyzer(MakeTable());
        analyzer.setSentence("i#");
        MachineStatus machineStatus;

        while(true) {
            machineStatus = analyzer.getMachine();
            analyzer.next();
        }
    }

    static NonTerminators MakeTable()
    {
        NonTerminators T = new NonTerminators("T");
        NonTerminators F = new NonTerminators("F");

        EmptyCharacter emp = new EmptyCharacter();
        // T -> FT | e
        T.getMapping().put('#', Arrays.asList(new CharacterBase[]{emp}));
        T.getMapping().put('i', Arrays.asList(new CharacterBase[]{F,T}));
        // F -> i
        F.getMapping().put('i', Arrays.asList(new CharacterBase[]{new Terminators("i")}));

        return T;
    }

}
