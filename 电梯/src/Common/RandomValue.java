/*
时间：2017-11-21 12:03
作者： 胡轲
文件描述：产生一个随机数
 */
package Common;
import java.util.Random;
public class RandomValue {
    /*该函数会返回一个bottom到top的随机值*/
    static public int getRandom(int bottom,int top){
        Random random = new Random();
        int value = random.nextInt(top)%(top-bottom+1)+bottom;
        return value;
    }
}
