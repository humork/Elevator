/*文件描述： 这个文件实现了可视化的系统条件的输入，
            如电梯的数量，组的数量，楼层数量

            大部分代码由Eclipse的WindowBuilder生成

  作者： 胡轲
  时间：2018-1-26
  */

package UserInterface;
import Data.Elevator;
import Data.Group;
import Data.Floor;
import Data.SystemData;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.SystemColor;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Vector;


public class MainWindow extends JFrame {

    private JPanel contentPane;
    private JTextField textField;
    private JTextField textField_1;
    private JTextField textField_2;
    private JTextField textField_3;
    private JTextField textField_4;
    /**
     * Create the frame.
     */
    public MainWindow() {

        setTitle("\u521D\u59CB\u5316\u754C\u9762");
        setBackground(SystemColor.textHighlight);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 467, 319);
        contentPane = new JPanel();
        contentPane.setBackground(SystemColor.textHighlight);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNewLabel = new JLabel("\u7535\u68AF\u4EFF\u771F\u7CFB\u7EDF");
        lblNewLabel.setForeground(SystemColor.activeCaption);
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setFont(new Font("微软雅黑", Font.PLAIN, 35));
        lblNewLabel.setBounds(116, 10, 227, 42);
        contentPane.add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("\u505C\u9760\u65F6\u95F4");
        lblNewLabel_1.setForeground(SystemColor.inactiveCaption);
        lblNewLabel_1.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        lblNewLabel_1.setBounds(44, 179, 80, 28);
        contentPane.add(lblNewLabel_1);

        JLabel label = new JLabel("\u697C\u5C42\u6570");
        label.setForeground(SystemColor.inactiveCaption);
        label.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        label.setBounds(51, 62, 73, 28);
        contentPane.add(label);

        textField = new JTextField();
        textField.setBounds(139, 69, 66, 21);
        contentPane.add(textField);
        textField.setColumns(10);

        textField_1 = new JTextField();
        textField_1.setColumns(10);
        textField_1.setBounds(139, 224, 66, 21);
        contentPane.add(textField_1);

        JButton btnNewButton = new JButton("GO");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {

                /*从输入的数据中取得相应的数值*/
                int maxFloor = Integer.valueOf(textField.getText().toString());
                int maxPeople = Integer.valueOf(textField_2.getText().toString());
                int groupsNum = Integer.valueOf(textField_1.getText().toString());
                int pauseTime = Integer.valueOf(textField_4.getText().toString());
                int elevatorNum = Integer.valueOf(textField_3.getText().toString());

                /*判断这些数值的合法性*/
                if(alertParam(elevatorNum,groupsNum,maxFloor,pauseTime,maxPeople)==true){

                    /*将输入的数据进行封装，以便传递数据*/
                    SystemData systemData = new SystemData(elevatorNum,groupsNum,maxFloor,pauseTime,maxPeople);
                    /*通过输入elevator的数量动态生成elevator对象*/
                    Vector<Elevator> elevators = new Vector<>();
                    for(int i = 0;i<elevatorNum;i++){
                        Elevator elevator = new Elevator();
                        elevator.setWaitTime(pauseTime);
                        //设置电梯的序号以及电梯组号
                        elevator.setElevatorID(i);
                        elevator.setGroupID(i%groupsNum);
                        //将电梯加入电梯列表中
                        elevators.add(elevator);
                    }
                    Vector<Elevator> elevators1 = new Vector<>();
                    Vector<Elevator> elevators2 = new Vector<>();
                    Vector<Elevator> elevators3 = new Vector<>();
                    Vector<Elevator> elevators4 = new Vector<>();

                    for(int i = 0;i< elevatorNum;i++){
                        if(elevators.get(i).getGroupID()==0){
                            elevators1.add(elevators.get(i));
                        }
                        if(elevators.get(i).getGroupID()==1){
                            elevators2.add(elevators.get(i));
                        }
                        if(elevators.get(i).getGroupID()==2){
                            elevators3.add(elevators.get(i));
                        }
                        if(elevators.get(i).getGroupID()==3){
                            elevators4.add(elevators.get(i));
                        }
                    }
                     /*通过输入groups的数量动态生成group对象*/ /*暴力穷举吧*/
                    Vector<Group> groups = new Vector<>();
                    if(elevators1.size()!=0){
                        Group group = new Group(elevators1);
                        groups.add(group);
                    }
                    if(elevators2.size()!=0){
                        Group group = new Group(elevators1);
                        groups.add(group);
                    }
                    if(elevators3.size()!=0){
                        Group group = new Group(elevators1);
                        groups.add(group);
                    }
                    if(elevators4.size()!=0){
                        Group group = new Group(elevators1);
                        groups.add(group);
                    }

                     /*通过输入floors的数量动态生成floor对象*/
                    Vector<Floor> floors = new Vector<>();
                    for(int i = 0;i<maxFloor;i++){
                        Floor floor = new Floor(systemData,i+1);
                        floors.add(floor);
                    }

                    RunningWindow runningWindow = new RunningWindow(systemData,groups,floors);
                    new Thread(runningWindow).start();
                    runningWindow.setVisible(true);
                    dispose();
                }
            }
        });

        btnNewButton.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        btnNewButton.setBounds(348, 247, 93, 23);
        contentPane.add(btnNewButton);

        JLabel label_1 = new JLabel("\u5206\u7EC4\u6570\u91CF");
        label_1.setForeground(SystemColor.inactiveCaption);
        label_1.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        label_1.setBounds(44, 217, 80, 28);
        contentPane.add(label_1);

        JLabel label_2 = new JLabel("\u642D\u8F7D\u4EBA\u6570");
        label_2.setForeground(SystemColor.inactiveCaption);
        label_2.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        label_2.setBounds(44, 100, 80, 28);
        contentPane.add(label_2);

        JLabel label_3 = new JLabel("\u7535\u68AF\u6570\u91CF");
        label_3.setForeground(SystemColor.inactiveCaption);
        label_3.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        label_3.setBounds(44, 138, 80, 28);
        contentPane.add(label_3);

        textField_2 = new JTextField();
        textField_2.setColumns(10);
        textField_2.setBounds(139, 107, 66, 21);
        contentPane.add(textField_2);

        textField_3 = new JTextField();
        textField_3.setColumns(10);
        textField_3.setBounds(139, 145, 66, 21);
        contentPane.add(textField_3);

        textField_4 = new JTextField();
        textField_4.setColumns(10);
        textField_4.setBounds(139, 186, 66, 21);
        contentPane.add(textField_4);

        JLabel label_4 = new JLabel("\uFF081-10\uFF09");
        label_4.setForeground(SystemColor.inactiveCaption);
        label_4.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        label_4.setBounds(206, 62, 93, 28);
        contentPane.add(label_4);

        JLabel label_5 = new JLabel("\uFF081-10\uFF09");
        label_5.setForeground(SystemColor.inactiveCaption);
        label_5.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        label_5.setBounds(206, 100, 93, 28);
        contentPane.add(label_5);

        JLabel label_6 = new JLabel("\uFF081-4\uFF09");
        label_6.setForeground(SystemColor.inactiveCaption);
        label_6.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        label_6.setBounds(206, 138, 93, 28);
        contentPane.add(label_6);

        JLabel lblMs = new JLabel("ms");
        lblMs.setForeground(SystemColor.inactiveCaption);
        lblMs.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        lblMs.setBounds(216, 179, 93, 28);
        contentPane.add(lblMs);

        JLabel label_8 = new JLabel("\uFF081-4\uFF09");
        label_8.setForeground(SystemColor.inactiveCaption);
        label_8.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        label_8.setBounds(206, 217, 93, 28);
        contentPane.add(label_8);
    }


    /*判断输入的数据是否与对话框中给的提示信息相符合*/
    boolean alertParam(int elevatorNum,int groupNum,int maxFloor,int pauseTime,int currentPeople){
        if(elevatorNum<1||elevatorNum>4){
            JOptionPane.showMessageDialog(contentPane, "电梯数量输入错误", "waring",JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if(groupNum<1||groupNum>4){
            JOptionPane.showMessageDialog(contentPane, "电梯组数量输入错误", "waring",JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if(groupNum>elevatorNum){
            JOptionPane.showMessageDialog(contentPane, "电梯组数量输入错误", "waring",JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if(maxFloor<1||maxFloor>10){
            JOptionPane.showMessageDialog(contentPane, "电梯数量输入错误", "waring",JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if(currentPeople<1||currentPeople>10){
            JOptionPane.showMessageDialog(contentPane, "人数数量输入错误", "waring",JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

}
