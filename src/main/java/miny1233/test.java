package miny1233;

import Model.CharacterBase;
import Model.EmptyCharacter;
import Model.NonTerminators;
import Model.Terminators;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class test {
    public static void main(String[] args) {

        System.out.println(Standardizer.standardize("E->F+FT    DA-a"));

        Analyzer analyzer = new Analyzer(MakeTable());
        analyzer.setSentence("iiiii");

        try {
            do {
                //System.out.println(analyzer.getMachine().toString());
            } while (analyzer.next());
        } catch (Exception e) {
            //System.out.println(e.toString());
            System.out.println("分析错误，发生在解析: " + analyzer.getMachine().input_str.peek());
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
