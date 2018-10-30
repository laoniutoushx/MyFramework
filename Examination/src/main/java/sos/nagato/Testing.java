package sos.nagato;

import java.io.IOException;

public class Testing {
    public static void main(String[] args) throws IOException {
        FileFactory factory = new FileFactory();
        String rootDirPath = factory.makeFile("S:\\");

        int count = FileFactory.parseDirPathFile(rootDirPath);
    }
}
