package FanMinglei;

import LrmTasks.FirstSet;
import LrmTasks.TypeConversion;
import Model.CharacterBase;
import Model.EmptyCharacter;
import Model.NonTerminators;
import Model.Terminators;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AnalyticsTable {
    private TypeConversion typeConversion;

    public AnalyticsTable(TypeConversion typeConversion){
        this.typeConversion = typeConversion;
    }

    public void getAnalyticsTable(){
        //获取所有非终结符
        Map<String, NonTerminators> nonTerminatorsMap = typeConversion.getNonTerminatorsMap();
        for(var nonTerminators : nonTerminatorsMap.keySet()){
            NonTerminators nonTerminator = nonTerminatorsMap.get(nonTerminators); //获取到当前非终结符
            List<List<CharacterBase>> grammar = nonTerminator.getGrammar();  //当前非终结符的产生式右部
            for(int i=0;i< grammar.size();i++){
                var innerList = grammar.get(i);
                if(innerList.get(0) instanceof EmptyCharacter){
                    //推出空
                    //获取当前非终结符的Follow集
                    Set<CharacterBase> followSet = nonTerminator.getFollow();
                    //将follow中元素所在列都设置为空
                    for(var followItem : followSet){
                        nonTerminator.getMapping().put(followItem.getVal().charAt(0),innerList);
                    }
                }else if(innerList.get(0) instanceof Terminators){
                    //产生式右部的第一个字符是终结符
                    nonTerminator.getMapping().put(innerList.get(0).getVal().charAt(0),innerList);
                } else if (innerList.get(0) instanceof NonTerminators) {
                    //产生式右部的第一个字符是非终结符
                    //获取其First集
                    Set<CharacterBase> firstSet = ((NonTerminators) innerList.get(0)).getFirst();
                    //将First集中元素所在列都置为innerList
                    for(var firstItem : firstSet){
                        nonTerminator.getMapping().put(firstItem.getVal().charAt(0),innerList);
                    }
                }
            }

        }
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
        new FollowSet(typeConversion).getFollowSet();
        new AnalyticsTable(typeConversion).getAnalyticsTable();
        System.out.println("预测分析表 集合:");
        Map<String, Terminators> terminatorsMap = typeConversion.getTerminatorsMap();  //获取所有终结符
        System.out.print("    ");
        for(var terminator : terminatorsMap.keySet()){
            System.out.print(terminatorsMap.get(terminator) + "\t  ");
        }
        System.out.println();
        for (String nonTerm : typeConversion.getNonTerminatorsMap().keySet()) {
            System.out.print(nonTerm + ": " );
            //获取当前非终结符
            NonTerminators nonTerminators = typeConversion.getNonTerminatorsMap().get(nonTerm);
            for(var terminators:terminatorsMap.keySet()){
                System.out.print(nonTerminators.getMapping().get(terminators.charAt(0)) + " ");
            }
            System.out.println();
        }
    }
}
