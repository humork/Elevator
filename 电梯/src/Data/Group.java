/*作者：胡轲
  创作时间：2018-1-25 21：56
  最后修改时间：2018-1-25 21：56
  文件描述：定义了“电梯组” 并实现了相关功能
 */

package Data;
import java.util.Vector;

public class Group{

    public Group(Vector<Elevator> elevators){
        this.elevators = elevators;
    }

    public Vector<Elevator> getElevators() {
        return elevators;
    }

    public void setElevators(Vector<Elevator> elevators) {
        this.elevators = elevators;
    }

    /*接收外部传入的响应请求，并分配给组内电梯*/
    public synchronized void receiveCalling(ElevatorPathElement e){

        int floorNum = e.getFloorNum();
        int direction = e.getDirection();
        /*分配依据是找到组内电梯中路径长度最小的数*/
        /*这么做是为了增大电梯效率*/
        /*如果该电梯已经存在该请求，则忽略该请求*/
        int min = 10000;
        /*第一次遍历取得电梯组内路径长度最小的值*/
        for(int i = 0; i< elevators.size(); i++){
            if(elevators.get(i).getElevatorPath().elevatorPath.size()<min){
                min = elevators.get(i).getElevatorPath().getElevatorPath().size();
            }
        }
        /*确定它是哪个电梯*/
        Elevator elevator = null;
        for(int i = 0;i <elevators.size();i++){
            if(elevators.get(i).getElevatorPath().elevatorPath.size()==min){
                elevator = elevators.get(i);
              //  System.out.println(i);
            }
        }

        /*将该元素加入两个路径队列中*/
        if(elevator!=null){
            elevator.getElevatorPath().add(e.getFloorNum(),e.getDirection());
            elevator.getElevatorPathPiority().add(e.getFloorNum(),e.getDirection(),0);
        }
    }

    private Vector<Elevator> elevators;
}
