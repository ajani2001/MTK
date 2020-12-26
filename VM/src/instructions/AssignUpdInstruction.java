package instructions;

import memory.ArrVar;
import memory.IntVar;
import memory.State;
import memory.Var;

import java.util.ArrayList;

public class AssignUpdInstruction extends Instruction {
    int lvalueIndex;
    int arrayIndex;
    int indexIndex;
    int newValueIndex;
    int[] nextInstructions;

    public AssignUpdInstruction(int lvalueIndex, int arrayIndex, int indexIndex, int newValueIndex, int[] nextInstructions) {
        this.lvalueIndex = lvalueIndex;
        this.arrayIndex = arrayIndex;
        this.indexIndex = indexIndex;
        this.newValueIndex = newValueIndex;
        this.nextInstructions = nextInstructions;
    }

    @Override
    public ArrayList<State> execute(State state) throws CloneNotSupportedException {
        ArrVar arrayVar = (ArrVar) state.get(arrayIndex);
        IntVar indexVar = (IntVar) state.get(indexIndex);
        Var newValueVar = state.get(newValueIndex);
        state.set(lvalueIndex, arrayVar.upd(indexVar.getValue(), newValueVar));
        return makeBranches(state, nextInstructions);
    }
}
