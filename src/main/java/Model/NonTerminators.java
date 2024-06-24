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

    public Map<Character, List<CharacterBase>> getMapping() {
        return mapping;
    }

    public void setMapping(Map<Character, List<CharacterBase>> mapping) {
        this.mapping = mapping;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    public NonTerminators(String val) {
        this.val = val;
    }

}
