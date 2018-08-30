/**
 * 项目名：student
 * 修改历史：
 * 作者： LZ
 * 创建时间： 2018年8月27日-下午1:37:39
 */
package main.view;

//常数引用
import main.util.Constants;

//DAO
import main.dao.BaseDAO;
import main.dao.DAO;
import main.dao.StudentDAO;

//MODEL
import main.model.Student;

//GUI
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * 模块说明： 更新界面
 */

public class DeleteView extends JFrame {
    private MainView mv;

    private JPanel jPanelCenter,jPanelSouth;
    private JTextField name,sno;
    private JButton deleteButton,exitButton;

    public DeleteView(MainView mv) {
        this.mv = mv;
        init();
    }

    private void init() {
        this.setTitle(Constants.DELETEVIEW_TITLE);
        /**中间Panel**/
        jPanelCenter = new JPanel();
        jPanelCenter.setLayout(new GridLayout(3, 2));

        jPanelCenter.add(new JLabel(Constants.STUDENT_NAME));
        name = new JTextField();
        jPanelCenter.add(name);

        jPanelCenter.add(new JLabel(Constants.STUDENT_SNO));
        sno = new JTextField();
        sno.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode()==KeyEvent.VK_ENTER){
                    DeleteView.this.delete();
                }
                super.keyPressed(e);
            }
        });
        jPanelCenter.add(sno);

        jPanelCenter.add(new JLabel("-------------------------------------------------"));
        jPanelCenter.add(new JLabel("-------------------------------------------------"));

        /**下侧Panel**/
        jPanelSouth = new JPanel();
        jPanelSouth.setLayout(new GridLayout(1, 2));
        deleteButton = new JButton(Constants.DELETEVIEW_DELETEBUTTON);
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DeleteView.this.delete();
            }
        });

        exitButton = new JButton(Constants.EXITBUTTON);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        jPanelSouth.add(deleteButton);
        jPanelSouth.add(exitButton);

        this.add(jPanelCenter, BorderLayout.CENTER);
        this.add(jPanelSouth, BorderLayout.SOUTH);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setBounds(470, 250, 400, 130);
        setResizable(false);
        setVisible(true);
    }

    /**
     * 删除
     */
    private void delete(){
        if (this.check()){
            Student stu = this.buildStudent();
            boolean isSuccess = ((StudentDAO) BaseDAO.getAbilityDAO(DAO.StudentDAO)).delete(stu);
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
        if ("".equals(name.getText()) || "".equals(sno.getText())) {
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
        stu.setName(name.getText());
        stu.setSno(sno.getText());
        return stu;
    }

    /**
     * 更新后将TextFiled设为空
     */
    private void setEmpty(){
        name.setText("");
        sno.setText("");
    }
}
