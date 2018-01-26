package Data;
import java.util.Vector;


public class ElevatorPath {

    Vector<ElevatorPathElement> elevatorPath;

    public ElevatorPath(){
        elevatorPath = new Vector<>();
    }

    public Vector<ElevatorPathElement> getElevatorPath() {
        return elevatorPath;
    }
    //电路路径元素进入电梯路径
    public synchronized void add(int floorNum,int direction){
        if(elevatorPath==null){
            return;
        }
        //如果不存在该元素，将该元素加入电梯中
        if(!exist(floorNum,direction))
            elevatorPath.add(new ElevatorPathElement(floorNum, direction));

        //对整个路径元素进行排序
        elevatorPathElementSort();
    }
    /*加入队列优先级版本，不在队列内部进行排序*/
    public synchronized void add(int floorNum,int direction,int num){
        if(elevatorPath==null){
            return;
        }
        if(!exist(floorNum,direction))
            elevatorPath.add(new ElevatorPathElement(floorNum, direction));
    }


    //通过楼层和方向来判断电梯路径中是否存在元素
    public synchronized boolean exist(int floorNum,int direction){

        /*判断路径不为空切路径长度不为0*/
        if(elevatorPath==null){
            return false;
        }
        if(elevatorPath.size()==0){
            return false;
        }

        /*遍历电梯的路径，判断其内部是否存在该元素*/
        for(int i = 0 ;i<elevatorPath.size();i++){
            if(floorNum == elevatorPath.get(i).getFloorNum()
                    &&direction == elevatorPath.get(i).getDirection()){
                return true;
            }
        }
        return false;
    }

    //从电梯路径中删除元素
    public synchronized void remove(int floorNum,int elevatorDirection){
        //把该层与电梯同向的请求从路径中移除
       // System.out.println("REMOVE()!!!!!!!!!!");
        /*如果路径长度为0 返回*/
        if(elevatorPath.size()==0){
            return;
        }
        /*如果路径长度为1，删除唯一的值*/
        if(elevatorPath.size()==1){
            elevatorPath.remove(0);
            return;
        }

        /*如果楼层是整个楼层的最大楼层，不用考虑其方向，一律响应*/
        int max = 0;
        for(int i = 0;i< elevatorPath.size();i++){
            if(elevatorPath.get(i).getFloorNum()>max){
                max = elevatorPath.get(i).getFloorNum();
            }
        }
        if(max == floorNum){
            for(int i = 0;i<elevatorPath.size();i++){
                if(elevatorPath.get(i).getFloorNum()==max){
                    elevatorPath.remove(i);
                }
            }
        }

        /*其他一般情况会将与电梯相同楼层且同向以及来自内部的请求移除*/
        for(int i = 0;i<elevatorPath.size();i++){
            if(elevatorPath.get(i).equals(floorNum,elevatorDirection)){
                elevatorPath.remove(i--);
            }
        }
        for(int i = 0;i<elevatorPath.size();i++){
            if(elevatorPath.get(i).equals(floorNum,0)){
                elevatorPath.remove(i--);
            }
        }
        return;
    }



    //路径内部排序 将路径按照从小到大的顺序排序，相同楼层值的元素按照：内部---外部向上---外部向下的顺序进行排序
    public synchronized void elevatorPathElementSort(){
        //排序算法基于冒泡排序 设置一个中间变量E
        for(int i = 0; i<elevatorPath.size();i++){
            for(int j = 0; j< elevatorPath.size();j++){
                if(elevatorPath.get(i).getFloorNum()<elevatorPath.get(j).getFloorNum()){
                    ElevatorPathElement e = new ElevatorPathElement(elevatorPath.get(i).getFloorNum(),elevatorPath.get(i).getDirection());
                    elevatorPath.get(i).setDirection(elevatorPath.get(j).getDirection());
                    elevatorPath.get(i).setFloorNum(elevatorPath.get(j).getFloorNum());
                    elevatorPath.get(j).setDirection(e.getDirection());
                    elevatorPath.get(j).setFloorNum(e.getFloorNum());
                }
                if(elevatorPath.get(i).getFloorNum()==elevatorPath.get(j).getFloorNum()){
                    if(elevatorPath.get(i).getDirection()<elevatorPath.get(j).getDirection()){
                        ElevatorPathElement e = new ElevatorPathElement(elevatorPath.get(i).getFloorNum(),elevatorPath.get(i).getDirection());
                        elevatorPath.get(i).setDirection(elevatorPath.get(j).getDirection());
                        elevatorPath.get(j).setDirection(e.getDirection());
                    }
                }
            }
        }
    }
}
