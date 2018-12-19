package sos.yuki;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class FileParseAndInstore {

    private ExecutorService service = Executors.newFixedThreadPool(23);
    private AtomicInteger unHandleFilesAndInstores = new AtomicInteger(0);
    private BlockingQueue<String> queue = new ArrayBlockingQueue<String>(10000000);

    public FileParseAndInstore() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
    }

    private void start(String path) throws InterruptedException {
        File file = new File(path);
        getSubDirsFile(file);
        List<Pojo> pojos = new ArrayList<Pojo>(50000);
        while(queue.size() != 0 || unHandleFilesAndInstores.get() > 0){
            String line = queue.poll();
            if(line == null) continue;
            pojos.add(parseLines(line));
            if(pojos.size() == 49995) {
                System.out.println(queue.size());
                particalInstore(pojos);
                pojos = new ArrayList<Pojo>(50000);
            }
        }
        instore(pojos);
        service.shutdown();
    }

    private void particalInstore(final List<Pojo> pojos) {
        unHandleFilesAndInstores.incrementAndGet();
        service.submit(new Runnable() {
            public void run() {
                instore(pojos);
            }
        });
    }


    private Pojo parseLines(String lines) {
        String[] split_line = lines.split("\\|");
        Pojo pojo = new Pojo();
        pojo.id = Integer.valueOf(split_line[0]);
        pojo.name = split_line[1];
        pojo.age = Integer.valueOf(split_line[2]);
        pojo.desc = split_line[3];
        return pojo;
    }

    private void getSubDirsFile(final File rootFile) {
        if(rootFile.isDirectory()){
            File[] files = rootFile.listFiles();
            for(File file:files){
                getSubDirsFile(file);
            }
        }else{
            unHandleFilesAndInstores.incrementAndGet();
            service.submit(new Runnable() {
                public void run() {
                    handleFile(rootFile);
                }
            });
        }
    }

    private void handleFile(File file){
        try{
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = null;
            while((line = reader.readLine()) != null) {
                queue.put(line);
            }
            unHandleFilesAndInstores.decrementAndGet();
        }catch(Exception e){
            System.out.println("error"+ e.getMessage());
        }
    }

    private void instore(List<Pojo> pojos) {
//        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test?serverTimezone=UTC&useServerPrepStmts=false&rewriteBatchedStatements=true", "root", "2012");
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test?serverTimezone=UTC", "root", "2012");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        PreparedStatement ps = null;
        try {
            connection.setAutoCommit(false);
            ps = connection.prepareStatement("INSERT INTO EXAM(ID, NAME, AGE, ADDRESS) VALUES(?, ?, ?, ?)");
            for(Pojo pojo:pojos){
                ps.setInt(1, pojo.id);
                ps.setString(2, pojo.name);
                ps.setInt(3, pojo.age);
                ps.setString(4, pojo.desc);
                ps.addBatch();
            }
            ps.executeBatch();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            unHandleFilesAndInstores.decrementAndGet();
            if(ps != null){
                try {
                    ps.close();
                } catch (SQLException e) {

                }
            }
            if(connection != null){
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException, InterruptedException {
        System.out.println(new SimpleDateFormat("hh:mm:ss").format(new Date()));
        new FileParseAndInstore().start("S:/filehandle");
        System.out.println(new SimpleDateFormat("hh:mm:ss").format(new Date()));
    }

    private class Pojo {
        int id;
        String name;
        int age;
        String desc;
    }
}
