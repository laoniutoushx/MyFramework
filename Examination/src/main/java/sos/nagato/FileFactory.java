package sos.nagato;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class FileFactory {

    private ExecutorService service = null;
    private BlockingQueue<Pojo> queue = null;
    private AtomicInteger fileLevel = null;

    public FileFactory() {
        this.service = Executors.newFixedThreadPool(20);
        this.queue = new ArrayBlockingQueue<Pojo>(5000);
        this.fileLevel = new AtomicInteger();
    }

    public long parseDirPathFile(String rootDirPath) {

        File rootDir = new File(rootDirPath);
        parseCurDir(rootDir);

        long totalSize = 0;
        List<Pojo> list = new ArrayList<Pojo>();

        System.out.println(this.fileLevel.get());

        while(fileLevel.get() > 0 || queue.size() > 0){
            try {
                Pojo pojo = queue.poll(1000, TimeUnit.SECONDS);
                System.out.println("size:" + queue.size());
                list.add(pojo);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.service.shutdown();
        instoreFileData(list);
        return totalSize;
    }

    private void parseCurDir(final File rootDir) {
        this.fileLevel.incrementAndGet();
        this.service.execute(new Runnable() {
            public void run() {
                parseSubDir(rootDir);
            }
        });
    }

    private void parseSubDir(File rootDir) {
        if(rootDir != null){
            if(rootDir.isDirectory()){
                File[] children = rootDir.listFiles();
                if(children != null){
                    for(final File child:children){
                        if(child.isDirectory()){
                            parseCurDir(child);
                        }else{
                            parseFile(child);
                        }
                    }
                }
            }else{
                parseFile(rootDir);
            }
        }
        this.fileLevel.decrementAndGet();
    }

    private void parseFile(File file) {
        if(file != null){
            try {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String line = null;
                while((line = reader.readLine()) != null){
                    String[] strs = line.split("\\|");
                    Pojo pojo = new Pojo(Integer.valueOf(strs[0]), strs[1], strs[2], Integer.valueOf(strs[3]));
                    try {
                        this.queue.put(pojo);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
                if(i % 500 == 0){
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

    private void instoreFileData(List<Pojo> list) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test?serverTimezone=UTC", "root", "2012");
            connection.setAutoCommit(false);
            PreparedStatement statement = connection.prepareStatement("insert into user values(?, ?, ?, ?)");
            int i = 0;
            for(Pojo pojo:list){
                if(i % 10000 == 0){
                    statement.executeBatch();
                    connection.commit();
                    statement = connection.prepareStatement("insert into user values(?, ?, ?, ?)");
                }
                statement.setObject(1, pojo.getId());
                statement.setObject(2, pojo.getName());
                statement.setObject(3, pojo.getAddress());
                statement.setObject(4, pojo.getAge());
                statement.addBatch();
                i++;
            }
            statement.executeBatch();
            connection.commit();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private class Pojo {
        private int id;
        private String name;
        private String address;
        private int age;

        public Pojo(int id, String name, String address, int age) {
            this.id = id;
            this.name = name;
            this.address = address;
            this.age = age;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getAddress() {
            return address;
        }

        public int getAge() {
            return age;
        }
    }
}
