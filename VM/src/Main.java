import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        if(args.length != 1) {
            System.out.println("Usage: VM prog.bytecode");
        }

        FileInputStream fis = new FileInputStream(args[0]);
        DataInputStream dis = new DataInputStream(fis);
        VM machine = new VM();
        machine.loadProgram(dis);
        dis.close();
        System.out.println("loaded");
        machine.runProgram();
        System.out.println("finished");
    }
}
