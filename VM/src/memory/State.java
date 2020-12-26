package memory;

public class State {
    int instructionIndex;
    int constPoolSize;
    int varPoolSize;
    Var[] constants;
    Var[] variables;

    public State(int[] constValues, int[] varRanks) {
        constPoolSize = constValues.length;
        varPoolSize = varRanks.length;
        constants = new Var[constPoolSize];
        variables = new Var[varPoolSize];
        for(int i = 0; i < constPoolSize; ++i)
            constants[i] = new IntVar(constValues[i]);
        for(int i = 0; i < varPoolSize; ++i)
            variables[i] = varRanks[i] == 0? new IntVar(0): new ArrVar(varRanks[i]);
    }

    public void set(int index, Var value) {
        if(index < constPoolSize) {
            throw new IllegalArgumentException();
        } else {
            if(variables[index-constPoolSize].getType() != value.getType())
                throw new IllegalArgumentException();
            variables[index-constPoolSize] = value;
        }
    }

    public Var get(int index) {
        if(index < constPoolSize) {
            return constants[index];
        } else {
            return variables[index-constPoolSize];
        }
    }

    public int getInstructionIndex() {
        return instructionIndex;
    }

    public void setInstructionIndex(int instructionIndex) {
        this.instructionIndex = instructionIndex;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        State cloned = (State) super.clone();
        for(int i = 0; i < varPoolSize; ++i)
            cloned.variables[i] = (Var) this.variables[i].clone();
        return cloned;
    }
}
