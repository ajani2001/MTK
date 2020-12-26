package memory;

public class Var implements Cloneable {
    int type;
    protected Var(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
