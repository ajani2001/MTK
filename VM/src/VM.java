import instructions.*;
import memory.State;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.util.ArrayList;

public class VM {
    ArrayList<Instruction> instructions;
    ArrayList<State> states;

    int[] readInts(DataInputStream inputStream, int length) throws IOException {
        int[] result = new int[length];
        for(int i = 0; i < length; ++i)
            result[i] = inputStream.readInt();
        return result;
    }

    public void loadProgram(DataInputStream inputStream) throws IOException {
        int constantPoolSize = inputStream.readInt();
        int[] constants = new int[constantPoolSize];
        for(int i = 0; i < constantPoolSize; ++i)
            constants[i] = inputStream.readInt();
        int varPoolSize = inputStream.readInt();
        int[] varRanks = new int[varPoolSize];
        for(int i = 0; i < varPoolSize; ++i)
            varRanks[i] = inputStream.readInt();
        states.add(new State(constants, varRanks));
        while (true) {
            int instrCode;
            try {
                instrCode = inputStream.readUnsignedByte();
            } catch (EOFException e) {
                break;
            }
            switch (instrCode) {
                case 0: {
                    int lvalue = inputStream.readInt();
                    int rvalue = inputStream.readInt();
                    int labelsLength = inputStream.readInt();
                    int[] labels = readInts(inputStream, labelsLength);
                    instructions.add(new AssignVarInstruction(lvalue, rvalue, labels));
                    break;
                }
                case 1: {
                    int[] args = readInts(inputStream, 3);
                    int labelsLength = inputStream.readInt();
                    int[] labels = readInts(inputStream, labelsLength);
                    instructions.add(new AssignSumInstruction(args[0], args[1], args[2], labels));
                    break;
                }
                case 2: {
                    int[] args = readInts(inputStream, 3);
                    int labelsLength = inputStream.readInt();
                    int[] labels = readInts(inputStream, labelsLength);
                    instructions.add(new AssignSubInstruction(args[0], args[1], args[2], labels));
                    break;
                }
                case 3: {
                    int[] args = readInts(inputStream, 3);
                    int labelsLength = inputStream.readInt();
                    int[] labels = readInts(inputStream, labelsLength);
                    instructions.add(new AssignMulInstruction(args[0], args[1], args[2], labels));
                    break;
                }
                case 4: {
                    int[] args = readInts(inputStream, 3);
                    int labelsLength = inputStream.readInt();
                    int[] labels = readInts(inputStream, labelsLength);
                    instructions.add(new AssignDivInstruction(args[0], args[1], args[2], labels));
                    break;
                }
                case 5: {
                    int[] args = readInts(inputStream, 3);
                    int labelsLength = inputStream.readInt();
                    int[] labels = readInts(inputStream, labelsLength);
                    instructions.add(new AssignAppInstruction(args[0], args[1], args[2], labels));
                    break;
                }
                case 6: {
                    int[] args = readInts(inputStream, 4);
                    int labelsLength = inputStream.readInt();
                    int[] labels = readInts(inputStream, labelsLength);
                    instructions.add(new AssignUpdInstruction(args[0], args[1], args[2], args[3], labels));
                    break;
                }
                case 7: {
                    int[] args = readInts(inputStream, 2);
                    int labelsTrueLength = inputStream.readInt();
                    int[] labelsTrue = readInts(inputStream, labelsTrueLength);
                    int labelsFalseLength = inputStream.readInt();
                    int[] labelsFalse = readInts(inputStream, labelsFalseLength);
                    instructions.add(new CondEqInstruction(args[0], args[1], labelsTrue, labelsFalse));
                    break;
                }
                case 8: {
                    int[] args = readInts(inputStream, 2);
                    int labelsTrueLength = inputStream.readInt();
                    int[] labelsTrue = readInts(inputStream, labelsTrueLength);
                    int labelsFalseLength = inputStream.readInt();
                    int[] labelsFalse = readInts(inputStream, labelsFalseLength);
                    instructions.add(new CondNeqInstruction(args[0], args[1], labelsTrue, labelsFalse));
                    break;
                }
                case 9: {
                    int[] args = readInts(inputStream, 2);
                    int labelsTrueLength = inputStream.readInt();
                    int[] labelsTrue = readInts(inputStream, labelsTrueLength);
                    int labelsFalseLength = inputStream.readInt();
                    int[] labelsFalse = readInts(inputStream, labelsFalseLength);
                    instructions.add(new CondGtInstruction(args[0], args[1], labelsTrue, labelsFalse));
                    break;
                }
                case 10: {
                    int[] args = readInts(inputStream, 2);
                    int labelsTrueLength = inputStream.readInt();
                    int[] labelsTrue = readInts(inputStream, labelsTrueLength);
                    int labelsFalseLength = inputStream.readInt();
                    int[] labelsFalse = readInts(inputStream, labelsFalseLength);
                    instructions.add(new CondGteInstruction(args[0], args[1], labelsTrue, labelsFalse));
                    break;
                }
                case 11: {
                    int[] args = readInts(inputStream, 1);
                    int labelsLength = inputStream.readInt();
                    int[] labels = readInts(inputStream, labelsLength);
                    instructions.add(new PrintInstruction(args[0], labels));
                    break;
                }
                default: throw new IOException("Unexpected token");
            }
        }
    }

    public void runProgram() {
        while (states.size() != 0) {
            ArrayList<State> newStates = new ArrayList<State>();
            for(State oldState: states) {
                if (oldState.getInstructionIndex() < instructions.size()) {
                    try {
                        newStates.addAll(instructions.get(oldState.getInstructionIndex()).execute(oldState));
                    } catch (Exception e) {
                        System.err.println("Instruction "+oldState.getInstructionIndex()+": "+e.getMessage());
                    }
                }
            }
            states = newStates;
        }
    }
}
