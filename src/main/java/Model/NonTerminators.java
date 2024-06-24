package Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NonTerminators extends CharacterBase{
    private String val;
    private Map<Character, List<CharacterBase>> mapping = new HashMap<>();

    private List<Terminators> first = new ArrayList<>();

    private List<Terminators> follow = new ArrayList<>();

    private List<List<CharacterBase>> grammar;

    public void setMapping(Map<Character, List<CharacterBase>> mapping) {
        this.mapping = mapping;
    }

    public Map<Character, List<CharacterBase>> getMapping() {
        return mapping;
    }

    public List<Terminators> getFirst() {
        return first;
    }

    public void setFirst(List<Terminators> first) {
        this.first = first;
    }

    public List<Terminators> getFollow() {
        return follow;
    }

    public void setFollow(List<Terminators> follow) {
        this.follow = follow;
    }

    public List<List<CharacterBase>> getGrammar() {
        return grammar;
    }

    public void setGrammar(List<List<CharacterBase>> grammar) {
        this.grammar = grammar;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    public NonTerminators(String val) {
        setVal(val);
    }

}
