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
     * @desc:   分配每个线程的起始位置与结束位置
     * @param:
     * @return:
     * @auther: Suzumiya Haruhi
     * @date:   2018/11/1 20:43
     **/
    public void allocateStartPosAndThreads(File targetFile, int threadCount) throws IOException {

        String seperator = System.getProperty("line.separator");

        ByteBuffer byteBuffer = ByteBuffer.allocate(256);
        FileChannel channel = new FileInputStream(targetFile).getChannel();
        long perSize = channel.size() / threadCount;

        long startPos = channel.position(), endPos = channel.position() + 15;

        for(int i = 1; i < threadCount; i++) {

            channel.position(startPos);
            ByteBuffer startLine = ByteBuffer.allocate(15);
            channel.read(startLine);
            startLine.rewind();
            byte[] startLineByte = new byte[15];
            startLine.get(startLineByte);
            System.out.println(new String(startLineByte, 0, startLineByte.length));


            System.out.println("read start pos:" + startPos);

            byte[] tempByte = new byte[256];
            channel.position(i * perSize);

            if ((channel.read(byteBuffer)) != -1) {        // 未读取到文件末尾
                int tempStrSize = byteBuffer.position();
                byteBuffer.rewind();    // position 置为0
                byteBuffer.get(tempByte);
                byteBuffer.clear();
                String tempString = new String(tempByte, 0, tempStrSize);
                //System.out.println(tempString);
                int fixPos = tempString.indexOf(seperator);
                tempByte.

                if(fixPos == -1){
                    fixPos = 0;
                }

                // read start pos && line end pos
                endPos = channel.position() + fixPos;
                System.out.println("read end pos:" + endPos);

                //  && line separator length
                startPos = endPos + 4;
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

    public static void main(String[] args) {
        String str = "harusdfsdfsdfhi";
        System.out.println(str.lastIndexOf("ru"));
        String lineSe = System.getProperty("line.separator");
        System.out.println(lineSe.getBytes().length);
    }

}
