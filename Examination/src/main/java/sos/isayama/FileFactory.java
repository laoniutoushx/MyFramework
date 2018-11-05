package sos.isayama;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.concurrent.*;
import java.util.concurrent.CountDownLatch;

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
    public void allocateStartPosAndThreads(File targetFile, int threadCount) throws IOException, InterruptedException {
        CountDownLatch latch = new CountDownLatch(threadCount);
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
            service.execute(new ParticalCalculator(startPos, endPos, targetFile, latch));

            startPos = endPos + 1;
        }
        latch.await(10, TimeUnit.SECONDS);     // 挂起当前主线程，直到 latch = 0
        service.shutdown();
        System.out.println("程序结束");

    }

    private class ParticalCalculator implements Runnable {
        private long startPos;
        private long endPos;
        private MappedByteBuffer mapBuffer;
        private CountDownLatch latch;

        public ParticalCalculator(long startPos, long endPos, File targetFile, CountDownLatch latch) throws IOException {
            this.startPos = startPos;
            this.endPos = endPos;
            this.mapBuffer = new RandomAccessFile(targetFile, "r").getChannel()
                    .map(FileChannel.MapMode.READ_ONLY, startPos, endPos - startPos);
            this.latch = latch;
        }

        public void run() {
            try{
                byte[] temp = new byte[(int) (endPos - startPos)];
                mapBuffer.get(temp);
                System.out.println(new String(temp));

                latch.countDown();


            }catch(Exception e){
                System.out.println(e.getMessage());
            }

        }
    }
}
