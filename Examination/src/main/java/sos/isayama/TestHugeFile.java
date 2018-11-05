package sos.isayama;

import java.io.File;
import java.io.IOException;

public class TestHugeFile {
    public static void main(String[] args) throws IOException, InterruptedException {
        File file = new File("S:\\huge.txt");
        System.out.println("file length:" + file.length());

        FileFactory factory = new FileFactory();

//        factory.makeFile(file);

        factory.allocateStartPosAndThreads(file, 6);
    }
}
