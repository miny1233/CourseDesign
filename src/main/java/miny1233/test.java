package miny1233;

import Model.CharacterBase;
import Model.EmptyCharacter;
import Model.NonTerminators;

import java.util.ArrayList;
import java.util.List;

public class test {
    public static void main(String[] args) throws Exception {
        Analyzer analyzer = new Analyzer(MakeTable());
        analyzer.setSentence("#");
        MachineStatus machineStatus;

        machineStatus = analyzer.getMachine();
        analyzer.next();
        machineStatus = analyzer.getMachine();

        return;
    }

    static NonTerminators MakeTable()
    {
        NonTerminators T = new NonTerminators("T");
        EmptyCharacter emp = new EmptyCharacter();

        var de = new ArrayList<CharacterBase>();
        de.add(emp);
        T.getMapping().put('#',de);

        return T;
    }

}
