package Model;

public class CharacterBase {
    public String getVal() {
        return null;
    }

    public void setVal(String val) {

    }

    @Override
    public boolean equals(Object obj) {

        CharacterBase other = (CharacterBase) obj;

        if (!this.getClass().equals(obj.getClass()))
        {
            return false;
        }

        if (getVal() == other.getVal())
        {
            return true;
        }

        return false;
    }
}
