package instructions;

import memory.ArrVar;
import memory.IntVar;
import memory.State;
import memory.Var;

import java.util.ArrayList;

public class AssignAppInstruction extends Instruction {
    int lvalueIndex;
    int arrayIndex;
    int indexIndex;
    int[] nextInstructions;

    public AssignAppInstruction(int lvalueIndex, int arrayIndex, int indexIndex, int[] nextInstructions) {
        this.lvalueIndex = lvalueIndex;
        this.arrayIndex = arrayIndex;
        this.indexIndex = indexIndex;
        this.nextInstructions = nextInstructions;
    }

    @Override
    public ArrayList<State> execute(State state) throws CloneNotSupportedException {
        ArrVar arrayVar = (ArrVar) state.get(arrayIndex);
        IntVar indexVar = (IntVar) state.get(indexIndex);
        state.set(lvalueIndex, arrayVar.app(indexVar.getValue()));
        return makeBranches(state, nextInstructions);
    }
}
