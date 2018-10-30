package sos.nagato;

import java.io.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class FileFactory {

    private ExecutorService service = null;
    private BlockingQueue<String> queue = null;
    private AtomicInteger level = null;

    public FileFactory() {
        this.service = Executors.newFixedThreadPool(20);
        this.queue = new ArrayBlockingQueue<String>(5000);
        this.level = new AtomicInteger();
    }

    public static int parseDirPathFile(String rootDirPath) {


        parseSubDir(rootDirPath);

    }

    private static void parseSubDir(String rootDirPath) {
        
    }

    public String makeFile(String dir) throws IOException {
        if(dir != null && !"".equals(dir)){
            File rootDir = new File("S:\\exam\\");
            if(!rootDir.exists()){
                rootDir.mkdir();
            }
            File subDir = null;
            File dataFile = null;
            FileOutputStream outputStream = null;
            for(int i = 0, j = 0; i < 100 * 10000; i++){
                if(i % 50000 == 0){
                    subDir = new File(rootDir.getAbsolutePath() + "\\" + j++);
                    subDir.mkdir();
                    dataFile = new File(subDir.getAbsolutePath() + "\\haruhi" + j + ".txt");
                    if(outputStream != null){
                        outputStream.close();
                    }
                    outputStream = new FileOutputStream(dataFile);
                }
                outputStream.write((i + "|haruhi" + i + "|送到附近的上了飞机|23\r\n").getBytes("UTF-8"));
            }
            outputStream.close();
            return rootDir.getAbsolutePath();
        }
        return null;
    }
}
