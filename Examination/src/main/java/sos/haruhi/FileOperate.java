package sos.haruhi;

import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName FileOperate
 * @Description TODO
 * @Author Suzumiya Haruhi
 * @Date 2018/10/25 21:05
 * @Version 10032
 **/
public class FileOperate {
    public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException {
        FileOperate fileOperate = new FileOperate();

        String path = fileOperate.makeFile();

        List<Bean> beans = fileOperate.parseFile(path);

        DaoOperator daoOperator = new DaoOperator();
        daoOperator.insert(daoOperator.getConnection(), beans);
    }

    private String makeFile() throws IOException {
        File file = new File("F:\\huge.txt");
        FileOutputStream out = new FileOutputStream(file);
        for(int i = 0; i < 1000000; i++){
            out.write((i + "|haruhi" + i + "|22|all看见士大夫立刻觉得舒服了肯定就是\r\n").getBytes());
        }
        return file.getAbsolutePath();
    }

    private List<Bean> parseFile(String path) throws IOException {
        List<Bean> list = new ArrayList<Bean>();
        File file = new File(path);
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line = null;
        while((line = reader.readLine()) != null){
            Bean bean = buildBean(line);
            list.add(bean);
        }
        return list;
    }

    private Bean buildBean(String line) {
        Bean bean = new Bean();
        String[] fields = line.split("\\|");
        bean.setId(Integer.valueOf(fields[0]));
        bean.setName(fields[1]);
        bean.setAge(Integer.valueOf(fields[2]));
        bean.setAddress(fields[3]);
        return bean;
    }
}
