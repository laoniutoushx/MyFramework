package sos.isayama;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileFactory {



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
     * @desc:   分配每个线程的其实位置与结束位置
     * @param:
     * @return:
     * @auther: Suzumiya Haruhi
     * @date:   2018/11/1 20:43
     **/
    public void allocateStartPosAndThreads(File targetFile, int threadCount) throws IOException {
        long perSize = targetFile.length() / threadCount;

        String seperator = File.separator;

        ByteBuffer byteBuffer = ByteBuffer.allocate(4096);
        FileChannel channel = new FileInputStream(targetFile).getChannel();

        for(int i = 0; i < threadCount; i++) {
            byte[] tempByte = new byte[4096];
            if ((channel.read(byteBuffer)) != -1) {        // 未读取到文件末尾
                int tempStrSize = byteBuffer.position();
                byteBuffer.rewind();    // position 置为0
                byteBuffer.get(tempByte);
                byteBuffer.clear();
                String tempString = new String(tempByte, 0, tempStrSize);

                int fixPos = tempString.indexOf(seperator);

                long endPos = perSize * i + fixPos;
                long startPos = endPos + 1;
            }
        }
    }

    private class ParticalCalculator implements Runnable {
        private long startPos;
        private long endPos;

        public ParticalCalculator(long startPos, long endPos) {
            this.startPos = startPos;
            this.endPos = endPos;
        }

        public void run() {

        }
    }

}
