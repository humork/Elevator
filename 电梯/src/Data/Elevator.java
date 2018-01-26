/*作者：胡轲
  创作时间：2017-11-20 21：57
  文件描述：定义了与电梯有关的一些参数
 */

package Data;

import java.util.ArrayList;
import java.util.Vector;

public class Elevator{

    private int elevatorID;   //电梯号
    private int groupID;      //组号
    private int currentFloor;   //当前楼层
    private int waitTime;     //等待时间
    private int state;          //电梯状态 0：运行 1：维修
    private int direction;      //电梯运行方向 0：向上 1：暂停 2：向下；
    private int targetFloor; //目标楼层
    volatile private ElevatorPath elevatorPath;     //电梯的路径（排序版）
    volatile private ElevatorPath elevatorPathPoirity;   //电梯的路径（优先级） 优先级的高低按照进队列的顺序进行排序
    private Vector<Person> peopleInElevator;  //电梯内部最多可以容下10人

    public Elevator(){
        elevatorID = 1;
        groupID = 1;
        currentFloor = 1;
        waitTime = 3000;
        state = 0;
        direction = 1;
        targetFloor = 1;
        peopleInElevator = new Vector<>();
        elevatorPath = new ElevatorPath();
        elevatorPathPoirity = new ElevatorPath();
    }



    public ElevatorPath getElevatorPathPiority() {
        return elevatorPathPoirity;
    }

    public void setElevatorPathPiority(ElevatorPath elevatorPathPoirity) {
        this.elevatorPathPoirity = elevatorPathPoirity;
    }

    public int getElevatorID() {
        return elevatorID;
    }

    public void setElevatorID(int elevatorID) {
        this.elevatorID = elevatorID;
    }

    public int getGroupID() {
        return groupID;
    }

    public void setGroupID(int groupID) {
        this.groupID = groupID;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public void setCurrentFloor(int currentFloor) {
        this.currentFloor = currentFloor;
    }

    public int getElevatorState() { return state; }

    public void setState(int state) {
        this.state = state;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getWaitTime() {
        return waitTime;
    }

    public void setWaitTime(int waitTime) {
        this.waitTime = waitTime;
    }

    public Vector<Person> getPerson(){
        return peopleInElevator;
    }

    public void addPerson(Person person){
        peopleInElevator.add(person);
    }
    /*进电梯操作，输入一个人对象，将其添加到person数组的最后一位*/

    public int getTargetFloor() {
        return targetFloor;
    }

    public void setTargetFloor(int targetFloor) {
        this.targetFloor = targetFloor;
    }

    public ElevatorPath getElevatorPath() {
        return elevatorPath;
    }

    public void setElevatorPath(ElevatorPath elevatorPath) {
        this.elevatorPath = elevatorPath;
    }
}
