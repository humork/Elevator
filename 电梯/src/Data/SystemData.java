/*作者：胡轲
  创作时间：2017-11-20 21：56
  文件描述：定义了可以外部输入参数的访问和修改方式
 */

package Data;
public class SystemData {
    private int elevatorNum;   //电梯数量
    private int groupNum;     //组的数量
    private int maxFloor;    //最大楼层
    private int pauseTime;   //单位毫秒
    private int currentPeople;  //模拟人数

    public SystemData(int elevatorNum,int groupNum,int maxFloor,int pauseTime,int currentPeople){
        this.elevatorNum = elevatorNum;
        this.maxFloor = maxFloor;
        this.pauseTime = pauseTime;
        this.groupNum = groupNum;
        this.currentPeople = currentPeople;
    }


    public int getElevatorNum() {
        return elevatorNum;
    }

    public void setElevatorNum(int elevatorNum) {
        this.elevatorNum = elevatorNum;
    }

    public int getGroupNum() {
        return groupNum;
    }

    public void setGroupNum(int groupNum) {
        this.groupNum = groupNum;
    }

    public int getMaxFloor() {
        return maxFloor;
    }

    public void setMaxFloor(int maxFloor) {
        this.maxFloor = maxFloor;
    }

    public int getPauseTime() {
        return pauseTime;
    }

    public void setPauseTime(int pauseTime) {
        this.pauseTime = pauseTime;
    }

    public int getCurrentPeople() {
        return currentPeople;
    }

    public void setCurrentPeople(int currentPeople) {
        this.currentPeople = currentPeople;
    }
}
