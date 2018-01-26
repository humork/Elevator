/*日志保存操作*/

package Common;
import java.io.*;

public class ElevatorLog {
    public static void logInFile(int num,int i){
        try {
            //读取一个文件，如果不存在就创建该文件
            File file = new File("log.txt");
            if(!file.exists()){
                file.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(file,true);
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(fos));

            if(i==1){
                bufferedWriter.newLine();
                bufferedWriter.write("电梯运行到了"+num+"楼\n");
            }
            if(i==2){
                bufferedWriter.newLine();
                bufferedWriter.write(num+"人出了电梯\n");
            }
            if(i==3){
                bufferedWriter.newLine();
                bufferedWriter.write(num+"人进了电梯\n");
            }

            bufferedWriter.close();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
