/**
 * 项目名：student
 * 修改历史：
 * 作者： LZ
 * 创建时间： 2018年8月27日-下午1:37:39
 */
package main.view;

//常数引用
import main.util.Constants;

//GUI
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

//DAO
import main.dao.BaseDAO;
import main.dao.DAO;
import main.dao.StudentDAO;

//MODEL
import main.model.Student;

/**
 * 模块说明： 添加界面
 * UPDATE与DELETE以此为原版
 */

public class AddView extends JFrame {
    private MainView mv;

    private JPanel jPanelCenter,jPanelSouth;
    private JTextField name,sno,sex,department,hometown,mark,email,tel;
    private JButton addButton,exitButton;

    public AddView(MainView mv) {
        this.mv = mv;
        init();
    }

    private void init() {
        this.setTitle(Constants.ADDVIEW_TITLE);
        /**中间Panel**/
        jPanelCenter = new JPanel();
        jPanelCenter.setLayout(new GridLayout(9, 2));

        jPanelCenter.add(new JLabel(Constants.STUDENT_NAME));
        name = new JTextField();
        jPanelCenter.add(name);

        jPanelCenter.add(new JLabel(Constants.STUDENT_SNO));
        sno = new JTextField();
        jPanelCenter.add(sno);

        jPanelCenter.add(new JLabel(Constants.STUDENT_SEX));
        sex = new JTextField();
        jPanelCenter.add(sex);

        jPanelCenter.add(new JLabel(Constants.STUDENT_DEPARTMETN));
        department = new JTextField();
        jPanelCenter.add(department);

        jPanelCenter.add(new JLabel(Constants.STUDENT_HOMETOWN));
        hometown = new JTextField();
        jPanelCenter.add(hometown);

        jPanelCenter.add(new JLabel(Constants.STUDENT_MARK));
        mark = new JTextField();
        jPanelCenter.add(mark);

        jPanelCenter.add(new JLabel(Constants.STUDENT_EMAIL));
        email = new JTextField();
        jPanelCenter.add(email);

        jPanelCenter.add(new JLabel(Constants.STUDENT_TEL));
        tel = new JTextField();
        tel.addKeyListener(new KeyAdapter(){
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode()==KeyEvent.VK_ENTER){
                    AddView.this.add();
                }
                super.keyPressed(e);
            }
        });
        jPanelCenter.add(tel);

        jPanelCenter.add(new JLabel("-------------------------------------------------"));
        jPanelCenter.add(new JLabel("-------------------------------------------------"));

        /**下侧Panel**/
        jPanelSouth = new JPanel();
        jPanelSouth.setLayout(new GridLayout(1,2));
        addButton = new JButton(Constants.ADDVIEW_ADDBUTTON);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddView.this.add();
            }
        });

        exitButton = new JButton(Constants.EXITBUTTON);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        jPanelSouth.add(addButton);
        jPanelSouth.add(exitButton);

        this.add(jPanelCenter,BorderLayout.CENTER);
        this.add(jPanelSouth,BorderLayout.SOUTH);

        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setBounds(470, 200, 400, 270);
        this.setResizable(false);
        this.setVisible(true);
    }

    /**
     * 添加
     */
    private void add(){
        if (this.check()){
            Student stu = this.buildStudent();
            boolean isSuccess = ((StudentDAO) BaseDAO.getAbilityDAO(DAO.StudentDAO)).add(stu);
            if(isSuccess){
                this.setEmpty();
                //如果记录超过99条，就显示第一页
                if (mv.getCurrPageNum() < 0 || mv.getCurrPageNum() > 99) {
                    mv.setCurrPageNum(1);
                }
                //否则显示当前页不改变
                String[][] result = ((StudentDAO) BaseDAO.getAbilityDAO(DAO.StudentDAO)).query(mv.getCurrPageNum());
                mv.initJTable(mv.getjTable(), result);
            }
        }
    }

    /**
     * 检查输入信息是否合法
     * @return result true合法，false不合法
     */
    private boolean check(){
        boolean result = false;
        if ("".equals(name.getText()) || "".equals(sno.getText()) || "".equals(department.getText())
                || "".equals(sex.getText()) || "".equals(mark.getText()) || "".equals(tel.getText())
                || "".equals(email.getText()) || "".equals(hometown.getText())) {
            return result;
        } else {
            result = true;
        }
        return result;
    }

    /**
     * 获取TextFiled信息，并将其存入stu
     */
    private Student buildStudent() {
        Student stu = new Student();
        stu.setDepartment(department.getText());
        stu.setEmail(email.getText());
        stu.setHomeTown(hometown.getText());
        stu.setMark(mark.getText());
        stu.setName(name.getText());
        stu.setSno(sno.getText());
        stu.setTel(tel.getText());
        stu.setSex(sex.getText());
        return stu;
    }

    /**
     * 更新后将TextFiled设为空
     */
    private void setEmpty(){
        name.setText("");
        sno.setText("");
        department.setText("");
        sex.setText("");
        email.setText("");
        hometown.setText("");
        tel.setText("");
        mark.setText("");
    }

}
