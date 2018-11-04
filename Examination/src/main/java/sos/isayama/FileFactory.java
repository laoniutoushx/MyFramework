package sos.isayama;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FileFactory {

    private ExecutorService service = Executors.newFixedThreadPool(8);

    public void makeFile(File file) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        for(int i = 0; i < 10000000; i++){
            writer.write(i + "|haruhi" + i + "|12|大师傅但是" + i);
            writer.newLine();
        }
        writer.close();
    }

    /**
     * @title:
     * @desc:   分配每个线程的起始位置与结束位置
     * @param:
     * @return:
     * @auther: Suzumiya Haruhi
     * @date:   2018/11/1 20:43
     **/
    public void allocateStartPosAndThreads(File targetFile, int threadCount) throws IOException {
        RandomAccessFile random = new RandomAccessFile(targetFile, "r");
        long startPos = 0, endPos = 0;

        long perSize = targetFile.length() / threadCount;

        for(int i = 0; i < threadCount; i++){
            endPos = (i + 1) * perSize;
            random.seek(endPos);

            char temp = (char) random.read();
            while(temp != '\n' && temp != '\r'){
                endPos++;
                if(endPos >= targetFile.length() - 1){
                    endPos = targetFile.length() - 1;
                    break;
                }
                random.seek(endPos);
                temp = (char) random.read();
            }
            System.out.println("start:" + startPos + ", endPos:" + endPos);
//            service.execute(new ParticalCalculator(startPos, endPos, targetFile));

            startPos = endPos + 1;
        }

    }

    private class ParticalCalculator implements Runnable {
        private long startPos;
        private long endPos;
        private FileChannel channel;

        public ParticalCalculator(long startPos, long endPos, File targetFile) throws IOException {
            this.startPos = startPos;
            this.endPos = endPos;
            this.channel = new FileOutputStream(targetFile).getChannel();
            this.channel.position(startPos);
        }

        public void run() {
            try{
                ByteBuffer buffer = ByteBuffer.allocate(4096);
                byte[] temp = new byte[4096];
                channel.read(buffer);
                buffer.rewind();
                buffer.get(temp);
                buffer.clear();
                System.out.println(new String(temp));

            }catch(Exception e){

            }

        }
    }
}
