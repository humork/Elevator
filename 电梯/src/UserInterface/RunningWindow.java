/*文件描述： 这个文件实现了系统运行的结果展示，
            将若干elevator对象和floor对象的成果展示在界面上
  作者： 胡轲
  时间：2018-1-26
  */

package UserInterface;

import Active.ElevatorAction;
import Active.FloorAction;
import Common.RandomValue;
import Data.*;
import Param.SystemParam;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.SystemColor;
import java.util.Random;
import java.util.Vector;

public class RunningWindow extends JFrame implements Runnable{

    private JPanel contentPane;
    private int elevatorNum;
    private int maxFloorNum;
    private int waitTime;
    private int groupNum;
    private int peopleNum;
    private Vector<Group> groupsData;
    private Vector<Floor> floors;
    private Vector<JPanel> jPanelList;
    private Vector<JTextField> jTextField;
    private Vector<JLabel[]> elevators;
    private Vector<JLabel[]> buttonUps;
    private Vector<JLabel[]> buttonDowns;

    /**
     * Create the frame.
     */
//    public void CreateRunningWindow(MainWindow mainWindow,JPanel jPanel,ArrayList<Group> groups,ArrayList<Floor> floors){
//        if(runningWindow!=null){
//            JOptionPane.showMessageDialog(jPanel, "提示消息", "标题",JOptionPane.WARNING_MESSAGE);
//            return;
//        }
//        runningWindow = new RunningWindow(mainWindow,groups,floors);
//    }


    public RunningWindow(SystemData systemData, Vector<Group> groups, Vector<Floor> floors){

        /*将传入的systemData值储存在RunningWindow内*/
        elevatorNum = systemData.getElevatorNum();
        maxFloorNum = systemData.getMaxFloor();
        peopleNum = systemData.getCurrentPeople();
        waitTime = systemData.getPauseTime();
        groupNum = systemData.getGroupNum();

        this.groupsData = groups;
        this.floors = floors;
        /*---------------------------启动后台-----------------------------------*/
        /*-------------启动所有电梯----------------*/
        for(int i = 0;i< groupsData.size();i++){
            for(int j = 0;j< groupsData.get(i).getElevators().size();j++){
                ElevatorAction elevatorAction = new ElevatorAction(
                        groupsData.get(i).getElevators().get(j)
                );
                new Thread(elevatorAction).start();
            }
        }
        /*-------------启动所有楼层----------------*/
        for(int i = 0;i< floors.size();i++){
            FloorAction floorAction = new FloorAction(floors.get(i),groups,systemData);
            new Thread(floorAction).start();
        }



         /*-------------启动所有楼层----------------*/
        /*---------------------------启动后台-----------------------------------*/
        jTextField = new Vector<>();
        elevators = new Vector<>();
        buttonUps = new Vector<>();
        buttonDowns = new Vector<>();
        jPanelList = new Vector<>();

        setResizable(false);
        setTitle("\u8FD0\u884C\u9875\u9762");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1366, 768);
        contentPane = new JPanel();
        contentPane.setBackground(SystemColor.textHighlight);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNewLabel = new JLabel("\u7535 \u68AF \u4EFF \u771F \u7CFB \u7EDF");
        lblNewLabel.setBackground(SystemColor.textHighlight);
        lblNewLabel.setForeground(SystemColor.inactiveCaption);
        lblNewLabel.setFont(new Font("微软雅黑", Font.PLAIN, 35));
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setBounds(193, 10, 986, 101);
        contentPane.add(lblNewLabel);
        CreateFloor();
    }

    //统计每层楼的人数并展示在面板上
    public void ControlCalcPersonPerFloor(){
        Vector<Integer> currentPeople = new Vector<>();
        //首先将每楼层中的人数统计在一个Integer类型的ArrayList中
        for(int i = 0;i< floors.size();i++){
            currentPeople.add(floors.get(i).getPerson().size());
        }


        System.out.println(jTextField.size());
        //然后将这些数值赋值给jTextField
        for(int i = 0;i<jTextField.size();i++){
            jTextField.get(floors.size()-i-1).setText(currentPeople.get(i).toString());
        }
    }

    //统计每个电梯当前停靠的位置，并储存在一个ArrayList中
    public Vector<Integer> CalcElevatorFloorNum(){
        Vector<Integer> integers = new Vector<>();
        for(int i= 0;i< groupsData.size();i++){
            //
            for(int j = 0;j< groupsData.get(i).getElevators().size();j++){
                integers.add(groupsData.get(i).getElevators().get(j).getCurrentFloor());
            }
        }
        return integers;
    }

    //通过传递的电梯停靠列表将电梯展示在面板中（将每一列不在当前楼层的电梯隐藏）
    public void ShowElevator(Vector<Integer> integers){
        for(int i = 0;i< elevators.size();i++){
            for(int j = 0; j< 4;j++){
                elevators.get(i)[j].setVisible(false);
            }
        }
        for(int i = 0;i<integers.size();i++){
            elevators.get(maxFloorNum-integers.get(i))[i].setVisible(true);
        }
    }


    //控制显示界面的情况
    public void ControlElevator(){
        checkButton();
        ControlCalcPersonPerFloor();
        ShowElevator(CalcElevatorFloorNum());
    }


    //创建楼层，每个楼层其实是一个JPanel
    public void CreateFloor(){

        int x = 10;
        int y = 110;
        int Width = 1330;
        int Height = (768-110)/getMaxFloorNum()-10;

        for(int i = 0;i< getMaxFloorNum();i++){
            JPanel panel = new JPanel();
            jPanelList.add(panel);
            panel.setBounds(x,y,Width,Height-5);
            panel.setBackground(SystemColor.inactiveCaption);
            panel.setLayout(null);
            contentPane.add(panel);

            //在JPanel上有一个JLabel和一个JTextField来显示当前楼层内有多少人
            JLabel label = new JLabel("当前人数：");
            label.setBounds(15, 0, 100, 80);
            label.setFont(new Font("微软雅黑", Font.PLAIN, 15));
            panel.add(label);

            JTextField textField = new JTextField("0");
            textField.setFont(new Font("微软雅黑", Font.PLAIN, 15));
            textField.setEditable(false);
            textField.setHorizontalAlignment(SwingConstants.CENTER);
            textField.setBounds(100, 30, 35, 25);
            jTextField.add(textField);
            panel.add(textField);
            createElevator(panel);
            createButton(panel);
            y = y + Height;
        }
    }


    //创建外部控制程序
    public void createButton(JPanel panel){
        JLabel[] buttonUp = new JLabel[4];
        JLabel[] buttonDown = new JLabel[4];

        for(int i = 0;i < 4 ;i++){
            Icon icon = new ImageIcon("2.jpg");
            Icon icon1 = new ImageIcon("3.jpg");
            buttonUp[i] = new JLabel(icon);
            buttonDown[i] = new JLabel(icon1);
            buttonUp[i].setVisible(false);
            buttonDown[i].setVisible(false);
            int width = 50;
            int height = 50;
            int buttonUpX = 315 + i*(245+width);
            int buttonUpY = 15;
            int buttonDownX = buttonUpX + width+10;
            int buttonDownY = buttonUpY;
            buttonUp[i].setBounds(buttonUpX,buttonUpY,width,height);
            buttonDown[i].setBounds(buttonDownX,buttonDownY,width,height);
            panel.add(buttonUp[i]);
            panel.add(buttonDown[i]);
        }
        buttonUps.add(buttonUp);
        buttonDowns.add(buttonDown);

    }

    //创建电梯，电梯实则是一个JLabel元素，
    public void createElevator(JPanel panel){
        JLabel[] elevator = new JLabel[4];
        for(int i = 0;i< 4;i++){
            Icon icon = new ImageIcon("1.jpg");
            elevator[i] = new JLabel(icon);
            int width = 160;
            int height = 70;
            int x = 145+ i*(width+135);
            int y = 5;
            elevator[i].setBounds(x, y, width, height);
            elevator[i].setVisible(false);
            panel.add(elevator[i]);
        }
        elevators.add(elevator);
    }
    /*通过获得各个电梯的列表 将按钮的显示情况显示在界面上*/
    public void checkButton(){

        for(int i = 0;i< buttonUps.size();i++){
            for(int j = 0;j< 4 ;j++){
                buttonUps.get(i)[j].setVisible(false);
            }
        }
        for(int i = 0;i< buttonDowns.size();i++){
            for(int j = 0;j< 4 ;j++){
                buttonDowns.get(i)[j].setVisible(false);
            }
        }

        /*首先将group里面的电梯数量提出出去*/
        Vector<Elevator> elevators = new Vector<>();
        for(int i = 0;i< groupsData.size();i++){
            for(int j = 0;j< groupsData.get(i).getElevators().size();j++){
                elevators.add(groupsData.get(i).getElevators().get(j));
            }
        }
        /*对Elevator类型的队列进行遍历，提出每一个elevator的路径元素的floorNUM和direction*/
        for(int i = 0;i < elevators.size();i++){
            if(elevators.get(i).getElevatorPath().getElevatorPath().size()==0){
                return;
            }
            for(int j = 0;j< elevators.get(i).getElevatorPath().getElevatorPath().size();j++){
                int floorNum = elevators.get(i).getElevatorPath().getElevatorPath().get(j).getFloorNum();
                int direction = elevators.get(i).getElevatorPath().getElevatorPath().get(j).getDirection();
                //elevators.get(maxFloorNum-arrayList.get(i))[i].setVisible(true);
                /*如果方向向上的话*/
                if(direction==1){
                    buttonUps.get(maxFloorNum-floorNum)[i].setVisible(true);
                }
                /*如果方向向下的话*/
                if(direction==2){
                    buttonDowns.get(maxFloorNum-floorNum)[i].setVisible(true);
                }
            }
        }
    }

    public int getMaxFloorNum() {
        return maxFloorNum;
    }

    public void run(){
        while(true) {

            /*报警模块 随机生成一个20以内的数，当该数为1的时候启动报警模块，报警模块会将电梯的状态置为FIXING*/
            int x = RandomValue.getRandom(1,20);
            if(x==1){
                /*这块由于时间关系，总是将0号电梯设置为报警状态*/
                groupsData.get(0).getElevators().get(0).setState(SystemParam.FIXING);
            }

            try{
                Thread.sleep(groupsData.get(0).getElevators().get(0).getWaitTime());
            }catch (InterruptedException e){

            }
            ControlElevator();
        }
    }
}
