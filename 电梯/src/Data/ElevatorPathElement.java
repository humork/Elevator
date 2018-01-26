/*
* 文件描述：电梯路径元素
* 作者：胡轲
* 时间： 2018-1-23
*
* */


package Data;

public class ElevatorPathElement{
    public int getFloorNum() {
        return floorNum;
    }

    public void setFloorNum(int floorNum) {
        this.floorNum = floorNum;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public boolean equals(int floorNum,int direction){
        if(this.getFloorNum()==floorNum&&
                this.getDirection()==direction){
            return true;
        }else{
            return false;
        }
    }

    public ElevatorPathElement(int floorNum,int direction){
        this.floorNum = floorNum;
        this.direction = direction;
    }

    private int floorNum;    //请求的楼层数
    private int direction;   //请求的方向 0:内部控制器的请求楼层 1：外部控制器的请求楼层（向上）2：外部控制器请求楼层（向下）
}

