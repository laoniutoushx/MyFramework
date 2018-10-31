package sos.nagato;

import java.io.IOException;

public class Testing {
    public static void main(String[] args) throws IOException {
        long startTime = System.currentTimeMillis();
        FileFactory factory = new FileFactory();
        String rootDirPath = factory.makeFile("S:\\");
        long count = factory.parseDirPathFile(rootDirPath);
//        long count = factory.parseDirPathFile("S:\\exam");
        long endTime = System.currentTimeMillis();
        System.out.println(count);

        System.out.println("time:" + (endTime - startTime) / 1000 / 60 + "mi");
        System.out.println("time:" + (endTime - startTime) / 1000 + "s");
        System.out.println("time:" + (endTime - startTime) + "ms");
    }
}
