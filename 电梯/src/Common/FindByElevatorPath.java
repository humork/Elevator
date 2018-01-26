/*文件描述： 这个文件实现了电梯的运动目标的查询方式
  作者： 胡轲
  时间：2018-1-24
  */
package Common;

import Data.ElevatorPath;
import Data.ElevatorPathElement;
import Param.SystemParam;

import java.util.Set;
//找到一个集合中的离目标数字最近的数字（比它大或者比它小）
public class FindByElevatorPath {
    //根据电梯路径里面的数据以及电梯运行方向找到其中最大或最小的楼层值
    //这个函数接收三个参数，1：电梯运动的路径

    //根据电梯路径里面的数据以及电梯运行方向返回电梯应该前往的楼层值
    public static int findTheTarget(ElevatorPath elevatorPath,ElevatorPath elevatorPath2,int num,int direction){
       /*电梯上行时*/
        if(elevatorPath.getElevatorPath().size()==0){
            return 0;
        }
        if(elevatorPath2.getElevatorPath().size()==0){
            return 0;
        }

        if(direction==SystemParam.MOVE_UP){
            for(int i = 0;i< elevatorPath.getElevatorPath().size();i++){
                //当电梯上行时，找到第一个比当前楼层高，且方向不相反的请求，如果该请求的楼层是当前路径中最高的楼层，则不用考虑方向
                if(elevatorPath.getElevatorPath().get(i).getFloorNum()>num
                        &&(elevatorPath.getElevatorPath().get(i).getDirection()!=2
                        ||elevatorPath.getElevatorPath().get(i).equals(elevatorPath.getElevatorPath().get(elevatorPath.getElevatorPath().size()-1)))){
                    return elevatorPath.getElevatorPath().get(i).getFloorNum();
                }
            }
        }
        /*电梯下行时*/
        if(direction==SystemParam.MOVE_DOWN) {
            //当电梯下行时，找到第一个比当前楼层低，且方向不相反的请求，如果该请求的楼层是当前路径中最低的楼层，则不用考虑方向
            for(int i = elevatorPath.getElevatorPath().size()-1;i>=0;i--){
                if(elevatorPath.getElevatorPath().get(i).getFloorNum()<num
                        &&(elevatorPath.getElevatorPath().get(i).getDirection()!=1
                        ||elevatorPath.getElevatorPath().get(i).equals(elevatorPath.getElevatorPath().get(0)))){
                    return elevatorPath.getElevatorPath().get(i).getFloorNum();
                }
            }
        }
        /*电梯为暂停态时*/
        if(direction==SystemParam.MOVE_PAUSE){
            /*电梯会通过优先级的路径列表找到第一个进入列表的路径元素，然后将该元素作为电梯的目标楼层*/
            for(int i=0;i<elevatorPath2.getElevatorPath().size();i++){
                if(elevatorPath2.getElevatorPath().get(i).getFloorNum()!=num){
                    return elevatorPath2.getElevatorPath().get(i).getFloorNum();
                }
            }
        }

        return 0;
    }
}
