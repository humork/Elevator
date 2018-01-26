/*文件描述： 这个文件用线程实现了楼层的运动方式
  作者： 胡轲
  时间：2018-1-24
  */

package Active;

import Common.ElevatorLog;
import Data.*;
import Param.SystemParam;

import java.util.ArrayList;
import java.util.Vector;

public class FloorAction implements Runnable{
    private Floor floor;
    private SystemData systemData;//楼层
    private Vector<Group> groups;       //电梯组
    private int peopleNumUP;    //该层想要上楼的人数
    private int peopleNumDown;  //该层想要下楼的人数
    private Elevator elevator;

    public Elevator getElevator() {
        return elevator;
    }

    public void setElevator(Elevator elevator) {
        this.elevator = elevator;
    }

    public SystemData getSystemData() {
        return systemData;
    }

    public void setSystemData(SystemData systemData) {
        this.systemData = systemData;
    }


    public Vector<Group> getGroups() {
        return groups;
    }

    public void setGroups(Vector<Group> groups) {
        this.groups = groups;
    }


    public Floor getFloor() {
        return floor;
    }

    public void setFloor(Floor floor) {
        this.floor = floor;
    }



    public FloorAction(Floor floor, Vector<Group> groups,SystemData systemData){
        setFloor(floor);
        setGroups(groups);
        setSystemData(systemData);
        System.out.println(systemData.getCurrentPeople());
    }

    /*根据楼层的人的目的楼层来更新电梯外部的控制面板*/
    private void updateOuterPanel(){
        /*统计该楼层每层的目标人数*/
        peopleNumUP = 0;
        peopleNumDown = 0;
        /*遍历楼层的人数VECTOR*/
        for(int i = 0;i< floor.getPerson().size();i++){
            /*找到有多少人向上，有多少人向下*/
            if(floor.getPerson().get(i).getDirection()==SystemParam.MOVE_UP){
                peopleNumUP++;
            }
        }
        peopleNumDown = floor.getPerson().size() - peopleNumUP;
//        System.out.println("----------------");
//        System.out.println("当前楼层"+ floor.getFloorNum());
//        System.out.println("向下人数："+peopleNumDown);
//        System.out.println("向上人数："+peopleNumUP);

        //如果该楼层人数大于1会将请求发送给该楼的每一个电梯
        if(peopleNumUP>1){
            for(int i = 0;i< groups.size();i++){
                groups.get(i).receiveCalling(new ElevatorPathElement(floor.getFloorNum(),SystemParam.MOVE_UP));
            }
            //如果该层楼人数等于1只会将请求发给该楼的第一个电梯
        }else if(peopleNumUP==1){
            groups.get(0).receiveCalling(new ElevatorPathElement(floor.getFloorNum(),SystemParam.MOVE_UP));
        }


        //向下的请求同理
        if(peopleNumDown>1){
            for(int i = 0;i< groups.size();i++){
                groups.get(i).receiveCalling(new ElevatorPathElement(floor.getFloorNum(),SystemParam.MOVE_DOWN));
            }
        }else if(peopleNumDown==1){
            groups.get(0).receiveCalling(new ElevatorPathElement(floor.getFloorNum(),SystemParam.MOVE_DOWN));
        }
    }

    /*出电梯操作，电梯到达目的楼层后，人出电梯，电梯内部人员-1*/
    public  synchronized void goOutElevator(Elevator elevator){

//        if(elevator.getDoorStatus()==false){
//            return;
//        }
        /*NUM1和NUM2分别为操作前后的人数*/
        int num1 = elevator.getPerson().size();
       // System.out.println("before Goout elevator"+elevator.getPerson().size());
        for(int i=0;i<elevator.getPerson().size();i++){
            /*如果有人的目的楼层等于当前楼层将这个人从电梯中移除*/
            if(elevator.getPerson().get(i).getDestFloor()==elevator.getCurrentFloor()){
                elevator.getPerson().remove(i--);
            }
        }
        int num2 = elevator.getPerson().size();


        //打印日志信息，做差可以知道有多少人出了电梯
        ElevatorLog.logInFile(num1-num2,2);
    }

    /*进电梯操作*/
    private synchronized void elevatorOperation(){

        /*首先判断哪个电梯到了楼层*/
        for(int i = 0; i< getGroups().size();i++){
            for(int j = 0;j< getGroups().get(i).getElevators().size();j++){
                if(getGroups().get(i).getElevators().get(j).getCurrentFloor()== floor.getFloorNum()){
                    setElevator(getGroups().get(i).getElevators().get(j));
                }
            }
        }



        int num1 = floor.getPerson().size();
        /*如果存在该电梯，且电梯内部人数不为0*/
        if(elevator!=null) {
            if(elevator.getPerson().size()!=0){
                //执行出电梯操作
                goOutElevator(elevator);
            }
            Person person = null;
            //遍历人
            for(int i = 0;i< floor.getPerson().size();i++){
                person = floor.getPerson().get(i);
                //System.out.println(person.getDirection());
                if(person!=null) {
                    //如果允许进电梯的话，执行进电梯操作
                    if(goInElevator(person)==true){
                        //将该人添加进电梯队列中
                        elevator.addPerson(person);
                        //将该人的目标楼层添加进电梯的路径列表中
                        elevator.getElevatorPath().add(person.getDestFloor(),0);
                        elevator.getElevatorPathPiority().add(person.getDestFloor(),0,0);
                        //从该楼层将该人删除
                        floor.getPerson().remove(person);
                    }
                }
            }
            //同理通过做差，即可知道有多少人进了电梯
            int num2 = floor.getPerson().size();
            ElevatorLog.logInFile(num1-num2,3);
        }
    }

    //判断可否进行入电梯操作
    private synchronized boolean goInElevator(Person person){
        if(floor.getPerson().size()==0){
            System.out.println(1);
            return false;
        }

        /*判断电梯是否满人*/
      //  System.out.println("goInElevator() personSize"+elevator.getPerson().size());
        if(elevator.getPerson().size()==systemData.getCurrentPeople()){
            //System.out.println(elevator.getPerson().size());
           // System.out.println("123213213");
            return false;
        }
        /*判断电梯门是否是打开状态*/
//        if(elevator.getDoorStatus()==false){
//            return false;
//        }
        /*如果电梯没有跟人的方向相同*/
        if(elevator.getDirection()!=SystemParam.MOVE_PAUSE){
            if(elevator.getDirection()!=person.getDirection()){
                return false;
            }

        }
        return true;
    }
    @Override
    public void run() {
        while(true){
            try {
                Thread.sleep(systemData.getPauseTime());
            }catch (InterruptedException e){

            }
            updateOuterPanel();
            elevatorOperation();
            //System.out.println(floor.people.size());
        }
    }
}
