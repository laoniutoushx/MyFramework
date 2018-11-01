package sos.isayama;

import java.io.File;
import java.io.IOException;

public class TestHugeFile {
    public static void main(String[] args) throws IOException {
        File file = new File("S:\\huge.txt");


        FileFactory factory = new FileFactory();

//        factory.makeFile(file);

        factory.allocateStartPosAndThreads(file, 10);
    }
}
