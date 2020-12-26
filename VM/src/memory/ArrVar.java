package memory;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class ArrVar extends Var {
    TreeMap<Integer, Var> values;

    public ArrVar(int type) {
        super(type);
        values = new TreeMap<>();
        if (type <= 0)
            throw new IllegalArgumentException();
    }

    public Var app(Integer index) throws CloneNotSupportedException {
        if(!values.containsKey(index))
            throw new IllegalArgumentException();
        return (Var) values.get(index).clone();
    }

    public Var upd(Integer index, Var value) throws CloneNotSupportedException {
        if(value.getType() != this.getType()-1)
            throw new IllegalArgumentException();
        ArrVar cloned = (ArrVar) this.clone();
        cloned.values.put(index, (Var) value.clone());
        return cloned;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append('[');
        Iterator<Integer> iter = values.keySet().iterator();
        Integer key;
        if(iter.hasNext()) {
            key = iter.next();
            builder.append(values.get(key).toString());
        }
        while (iter.hasNext()) {
            key = iter.next();
            builder.append(", ").append(values.get(key).toString());
        }
        builder.append(']');
        return builder.toString();
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        ArrVar cloned = (ArrVar) super.clone();
        cloned.values = (TreeMap<Integer, Var>) this.values.clone();
        for(Map.Entry<Integer, Var> entry: this.values.entrySet())
            cloned.values.put(entry.getKey(), (Var) entry.getValue().clone());
        return cloned;
    }
}
