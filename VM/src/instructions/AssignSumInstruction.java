package instructions;

import memory.IntVar;
import memory.State;

import java.util.ArrayList;

public class AssignSumInstruction extends Instruction {
    int lvalueIndex;
    int val1Index;
    int val2Index;
    int[] nextInstructions;

    public AssignSumInstruction(int lvalueIndex, int val1Index, int val2Index, int[] nextInstructions) {
        this.lvalueIndex = lvalueIndex;
        this.val1Index = val1Index;
        this.val2Index = val2Index;
        this.nextInstructions = nextInstructions;
    }

    @Override
    public ArrayList<State> execute(State state) throws CloneNotSupportedException {
        IntVar val1 = (IntVar) state.get(val1Index);
        IntVar val2 = (IntVar) state.get(val2Index);
        state.set(lvalueIndex, new IntVar(val1.getValue()+val2.getValue()));
        return makeBranches(state, nextInstructions);
    }
}
