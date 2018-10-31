package sos.isayama;

import java.io.File;

public class TestHugeFile {
    public static void main(String[] args) {
        File file = new File("S:\\huge.txt");
        new FileFactory().config(file);
    }
}
