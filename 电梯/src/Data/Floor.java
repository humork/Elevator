/*作者：胡轲
  创作时间：2017-11-20 21：57
  文件描述：定义了与楼层有关的一些参数,每层楼随机产生1-10个人要乘坐电梯
 */
package Data;
import Common.RandomValue;
import Param.SystemParam;

import java.util.Vector;

public class Floor {

    private int floorNum;    //楼层数
    private int currentPersonNum;  //现有的人数
    private Vector<Person> people;   //电梯内部的人数

    public Floor(SystemData systemData,int i) {
        //将人队列初始化
        people = new Vector<>();
        //楼层赋值
        setFloorNum(i);

        //随机生成该楼层的数值
        int temp = RandomValue.getRandom(1,10);
        for(int j =0;j<temp;j++){
            Person person = new Person(systemData);
            //设置人的当前楼层为楼层的高度
            person.setCurrentFloor(i);
            //设置人的活动方向 判断依据是根据人的目标楼层和当前楼层的高度
            if(person.getDestFloor()>person.getCurrentFloor()){
                person.setDirection(SystemParam.MOVE_UP);
            }else{
                person.setDirection(SystemParam.MOVE_DOWN);
            }
            people.add(person);
//            System.out.println("---------------------");
//            System.out.println("currentFloor: " + person.getCurrentFloor());
//            System.out.println("destFloor: " + person.getDestFloor());
//            System.out.println("direction: " + person.getDirection());
//            System.out.println("---------------------");
        }
    }

    public Vector<Person> getPerson(){
        return people;
    }

    public Person deletePerson(int index){
        return people.remove(index);
    }

    public void addPerson(Person person){
        people.add(person);
    }

    public int getCurrentPersonNum() {
        return currentPersonNum;
    }

    public void setCurrentPersonNum(int currentPersonNum) {
        this.currentPersonNum = currentPersonNum;
    }

    public int getFloorNum() {
        return floorNum;
    }

    public void setFloorNum(int floorNum) {
        this.floorNum = floorNum;
    }

}