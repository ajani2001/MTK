package instructions;

import memory.State;

import java.util.ArrayList;

public class PrintInstruction extends Instruction {
    int varIndex;
    int[] labels;

    public PrintInstruction(int varIndex, int[] labels) {
        this.varIndex = varIndex;
        this.labels = labels;
    }

    @Override
    public ArrayList<State> execute(State state) throws CloneNotSupportedException {
        System.out.println(state.get(varIndex).toString());
        return makeBranches(state, labels);
    }
}
