/*文件描述： 这个文件用线程实现了电梯的运动方式
  作者： 胡轲
  时间：2018-1-24
  */
package Active;

import Common.ElevatorLog;
import Common.FindByElevatorPath;
import Data.Elevator;
import Param.SystemParam;
import Data.Person;


public class ElevatorAction implements Runnable {
    private Elevator elevator;
    public ElevatorAction(Elevator elevator){
        this.elevator = elevator;
    }

    private synchronized void updateElevatorTargetFloor(){

        //根据两个路径，以及电梯的运动方向和电梯的当前楼层，计算出电梯运动的目标楼层
        int targetFloor = FindByElevatorPath.findTheTarget(elevator.getElevatorPath(),
                elevator.getElevatorPathPiority(),
                elevator.getCurrentFloor(),
                elevator.getDirection());
        //如果返回值不为0，则更新电梯的目标楼层
        if(targetFloor != 0){
            elevator.setTargetFloor(targetFloor);
        }
    }


    /*电梯每移动一次后更新电梯的运动方向*/
    private synchronized void updateElevatorMovingDirection(){

        //如果路径列表为0的话，将电梯置为暂停状态
        if(elevator.getElevatorPath().getElevatorPath().size()==0){
            elevator.setDirection(SystemParam.MOVE_PAUSE);
            return;
        }
        //如果电梯的目标楼层为当前楼层，将电梯置为暂停状态
        if(elevator.getTargetFloor()==elevator.getCurrentFloor()){
            elevator.setDirection(SystemParam.MOVE_PAUSE);
            return;
        }
        //如果目标楼层比当前楼层大，电梯为向上运动状态
        if(elevator.getCurrentFloor()>elevator.getTargetFloor()){
            elevator.setDirection(SystemParam.MOVE_DOWN);
            return;
        }
        //如果目标楼层比当前楼层大，电梯为向下运动状态
        if(elevator.getCurrentFloor()<elevator.getTargetFloor()){
            elevator.setDirection(SystemParam.MOVE_UP);
            return;
        }
    }


    /*电梯根据targetFloor进行移动,每次移动暂停若干秒*/
    private void elevatorMoving(){
            /*当电梯的当前目录不等于电梯的目标目录时，电梯会一直移动*/
            if(elevator.getCurrentFloor()!=elevator.getTargetFloor()) {

                /*如果电梯是朝上运动的话，电梯楼层会每次自增1*/
                if (elevator.getDirection() == SystemParam.MOVE_UP) {
                    elevator.setCurrentFloor(elevator.getCurrentFloor() + 1);
                    /*然后将电梯内部的人楼层自增1*/
                    for(int i = 0;i< elevator.getPerson().size();i++){
                        elevator.getPerson().get(i).setCurrentFloor(
                                elevator.getPerson().get(i).getCurrentFloor()+1);
                    }
                }
                /*方向相反的情况*/
                if(elevator.getDirection() == SystemParam.MOVE_DOWN) {
                    elevator.setCurrentFloor(elevator.getCurrentFloor() - 1);
                    for(int i = 0;i< elevator.getPerson().size();i++){
                        elevator.getPerson().get(i).setCurrentFloor(
                                elevator.getPerson().get(i).getCurrentFloor()-1);
                    }
                }
//                if(elevator.getElevatorPath().exist(elevator.getCurrentFloor(),elevator.getDirection())
//                        ||elevator.getElevatorPath().exist(elevator.getCurrentFloor(),0)){
//                    elevator.setDoorStatus(true);
//                }
                //记录电梯当前的运动层数
                ElevatorLog.logInFile(elevator.getCurrentFloor(),1);

                //将电梯运动的路径从路径列表里删除
                elevator.getElevatorPath().remove(elevator.getCurrentFloor(),elevator.getDirection());
                elevator.getElevatorPathPiority().remove(elevator.getCurrentFloor(),elevator.getDirection());

            }
    }
    /*将楼层中的人添加进电梯中，同时更新电梯的路径列表*/
    private synchronized void personToElevator(Person person){
        for(int i = 0;i< elevator.getPerson().size();i++){
            elevator.getElevatorPath().add(elevator.getPerson().get(i).getDestFloor(),0);
        }
    }

    @Override
    public void run() {
        while(true){
            //检查是否处于报警状态，如果是在报警状态则将电梯置为维修状态
            if(elevator.getElevatorState()==SystemParam.FIXING){

                //将路径清空
                elevator.setElevatorPath(null);
                elevator.setElevatorPathPiority(null);
                //将电梯里的人清空
                while(elevator.getPerson().size()!=0){
                    elevator.getPerson().remove(0);
                }
                //线程睡眠5s代表维修过程
                try{
                    Thread.sleep(5000);
                }catch (InterruptedException e){

                }
                //之后将电梯停在1楼
                elevator.setCurrentFloor(1);
                //将电梯置为可以运行状态
                elevator.setState(SystemParam.RUNNING);
                elevator.setDirection(0);
            }else{
                updateElevatorTargetFloor();
                updateElevatorMovingDirection();
                if(elevator.getDirection()!= SystemParam.MOVE_PAUSE){
                    elevatorMoving();
                }

                try{
                    Thread.sleep(elevator.getWaitTime());
                }catch(InterruptedException e){

                }
            }
        }
    }
}
