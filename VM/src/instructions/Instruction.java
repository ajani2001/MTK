package instructions;

import memory.State;

import java.util.ArrayList;

public abstract class Instruction {
    public abstract ArrayList<State> execute(State state) throws Exception;

    protected ArrayList<State> makeBranches(State state, int[] nextInstructions) throws CloneNotSupportedException {
        if(nextInstructions.length == 0) return new ArrayList<State>();
        ArrayList<State> result = new ArrayList<State>(nextInstructions.length);
        state.setInstructionIndex(nextInstructions[0]);
        result.add(state);
        for(int i = 1; i < nextInstructions.length; ++i) {
            State cloned = (State) state.clone();
            cloned.setInstructionIndex(nextInstructions[i]);
            result.add(cloned);
        }
        return result;
    }
}
