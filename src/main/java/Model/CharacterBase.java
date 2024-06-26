package Model;

public class CharacterBase {
    String val;
    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    @Override
    public boolean equals(Object obj) {

        if (obj instanceof CharacterBase other) {
            return getVal().equals(other.getVal());
        }

        return false;
    }

    @Override
    public String toString() {
        return val;
    }

    @Override
    public int hashCode() {
        return val.hashCode();
    }

    // 禁止构造
    CharacterBase(){

    }
}
