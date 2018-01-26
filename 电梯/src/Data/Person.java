/*作者：胡轲
  创作时间：2017-11-20 21：57
  最后修改时间：2017-11-20 21：57
  文件描述：定义了与人有关的一些参数
 */

package Data;
import Common.RandomValue;

public class Person{
    private int currentFloor;  //当前楼层
    private int destFloor;     //目的楼层
    private int direction;
    public Person(SystemData systemData){
        destFloor = RandomValue.getRandom(1,systemData.getMaxFloor());    //随机生成人的目标楼层
        while(destFloor==currentFloor){
            destFloor = RandomValue.getRandom(1, systemData.getMaxFloor());
        }
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public void setCurrentFloor(int currentFloor) {
        this.currentFloor = currentFloor;
    }

    public int getDestFloor() {
        return destFloor;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

}
