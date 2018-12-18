package sos.yuki;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MakeFakeFile {
    public static void main(String[] args) throws IOException {
        File file = new File("S:/filehandle/haruhi.txt");
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        for(int i = 0; i < 333333; i++){
            String id = String.valueOf(i);
            String name = "haruhi" + i;
            String age = "12";
            String desc = "i am bone of sliver";
            String line = id + "|" + name + "|" + age + "|" + desc;
            writer.write(line);
            writer.newLine();
        }

        writer.close();

        file = new File("S:/filehandle/nagato.txt");
        writer = new BufferedWriter(new FileWriter(file));
        for(int i = 333334; i < 666666; i++){
            String id = String.valueOf(i);
            String name = "nagato" + i;
            String age = "13";
            String desc = "i am bone of builder";
            String line = id + "|" + name + "|" + age + "|" + desc;
            writer.write(line);
            writer.newLine();
        }

        writer.close();

        file = new File("S:/filehandle/yuki.txt");
        writer = new BufferedWriter(new FileWriter(file));
        for(int i = 666667; i < 999999; i++){
            String id = String.valueOf(i);
            String name = "yuki" + i;
            String age = "17";
            String desc = "i am bone of master";
            String line = id + "|" + name + "|" + age + "|" + desc;
            writer.write(line);
            writer.newLine();
        }

        writer.close();
    }
}
