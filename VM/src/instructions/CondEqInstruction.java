package instructions;

import memory.IntVar;
import memory.State;

import java.util.ArrayList;

public class CondEqInstruction extends Instruction {
    int var1Index;
    int var2Index;
    int[] labelsTrue;
    int[] labelsFalse;

    public CondEqInstruction(int var1Index, int var2Index, int[] labelsTrue, int[] labelsFalse) {
        this.var1Index = var1Index;
        this.var2Index = var2Index;
        this.labelsTrue = labelsTrue;
        this.labelsFalse = labelsFalse;
    }

    @Override
    public ArrayList<State> execute(State state) throws CloneNotSupportedException {
        IntVar var1 = (IntVar) state.get(var1Index);
        IntVar var2 = (IntVar) state.get(var2Index);
        if(var1.getValue() == var2.getValue())
            return makeBranches(state, labelsTrue);
        else
            return makeBranches(state, labelsFalse);
    }
}
