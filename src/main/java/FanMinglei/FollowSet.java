package FanMinglei;

import LrmTasks.FirstSet;
import LrmTasks.TypeConversion;
import Model.CharacterBase;
import Model.EmptyCharacter;
import Model.NonTerminators;
import Model.Terminators;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FollowSet {
    private TypeConversion typeConversion;

    public FollowSet(TypeConversion typeConversion){
        this.typeConversion = typeConversion;
    }

    public void getFollowSet() {
        Map<String, NonTerminators> nonTerminatorsMap = typeConversion.getNonTerminatorsMap();  //获取所有非终结符
        nonTerminatorsMap.get("E").getFollow().add(new Terminators("#"));  //规定E为文法开始符号，将#加入开始符号Follow集

        boolean change = false;
        do{
            change = false;
            for(String key: nonTerminatorsMap.keySet()){
                NonTerminators nonTerminator = nonTerminatorsMap.get(key); //获取左边非终结符

                for(var production : nonTerminator.getGrammar()){
                   for(int i=0;i< production.size();i++)
                   {
                       if((i+1)< production.size()){
                           if(production.get(i) instanceof NonTerminators){
                               //当前是非终结符
                               if(production.get(i+1) instanceof NonTerminators){
                                   //后面也是非终结符
                                   Set<CharacterBase> first = new HashSet<>(((NonTerminators) production.get(i + 1)).getFirst());  //获取其First集
                                   first.removeAll(typeConversion.getEmptyCharacterMap().values());
                                   if(((NonTerminators) production.get(i)).getFollow().addAll(first)){
                                       //除空字外的加入Follow
                                       change = true;
                                   }
                               }else if(production.get(i+1) instanceof Terminators tr){  //后面是终结符。直接加入
                                   if(((NonTerminators) production.get(i)).getFollow().add(tr)){
                                       change = true;
                                   }
                               }


                           }
                       }
                       if(i == production.size()-1 && production.get(i) instanceof NonTerminators){
                           //获取左边非终结符的Follow集，加入到当前非终结符的Follow集中
                           if(((NonTerminators) production.get(i)).getFollow().addAll(nonTerminator.getFollow())){
                               change = true;
                           }
                           if(((NonTerminators) production.get(i)).getFirst().contains(typeConversion.getEmptyCharacterMap().get("ε"))){
                               //当前非终结符的First集中含有空字
                               //获取左边非终结符
                               if((i-1 >= 0) && production.get(i-1) instanceof NonTerminators){
                                   if(((NonTerminators) production.get(i-1)).getFollow().addAll(nonTerminator.getFollow())){
                                       change = true;
                                   }
                               }
                           }
                       }
                   }
                }
            }
        }while (change);
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
        System.out.println("Follow 集合:");
        for (String nonTerm : typeConversion.getNonTerminatorsMap().keySet()) {
            System.out.println(nonTerm + ": " + typeConversion.getNonTerminatorsMap().get(nonTerm).getFollow().toString());
        }
    }
}
