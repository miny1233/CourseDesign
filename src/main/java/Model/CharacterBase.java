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

        CharacterBase other = (CharacterBase) obj;

        if (!this.getClass().equals(obj.getClass()))
        {
            return false;
        }

        return getVal().equals(other.getVal());
    }

    @Override
    public String toString() {
        return val;
    }

    // 禁止构造
    CharacterBase(){

    }
}
