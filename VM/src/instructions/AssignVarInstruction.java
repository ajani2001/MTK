package instructions;

import memory.State;
import memory.Var;

import java.util.ArrayList;

public class AssignVarInstruction extends Instruction {
    int lvalueIndex;
    int rvalueIndex;
    int[] nextInstructions;

    public AssignVarInstruction(int lvalueIndex, int rvalueIndex, int[] nextInstructions) {
        this.lvalueIndex = lvalueIndex;
        this.rvalueIndex = rvalueIndex;
        this.nextInstructions = nextInstructions;
    }

    @Override
    public ArrayList<State> execute(State state) throws CloneNotSupportedException {
        Var var2 = state.get(rvalueIndex);
        state.set(lvalueIndex, (Var) var2.clone());
        return makeBranches(state, nextInstructions);
    }
}
