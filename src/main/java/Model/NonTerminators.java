package Model;

import java.util.*;

public class NonTerminators extends CharacterBase{
    private Map<Character, List<CharacterBase>> mapping = new HashMap<>(); //预测分析表

    private Set<CharacterBase> first = new HashSet<>();

    private Set<CharacterBase> follow = new HashSet<>();

    private List<List<CharacterBase>> grammar = new ArrayList<>();

    public void setMapping(Map<Character, List<CharacterBase>> mapping) {
        this.mapping = mapping;
    }

    public Map<Character, List<CharacterBase>> getMapping() {
        return mapping;
    }

    public Set<CharacterBase> getFirst() {
        return first;
    }

    public Set<CharacterBase> getFollow() {
        return follow;
    }

    public void setFirst(Set<CharacterBase> first) {
        this.first = first;
    }

    public void setFollow(Set<CharacterBase> follow) {
        this.follow = follow;
    }

    public List<List<CharacterBase>> getGrammar() {
        return grammar;
    }

    public void setGrammar(List<List<CharacterBase>> grammar) {
        this.grammar = grammar;
    }

    public NonTerminators(String val) {
        super();
        super.val = val;
    }

    public NonTerminators() {
        super();
    }

}
