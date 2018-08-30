/**
 * 项目名：student
 * 修改历史：
 * 作者： LZ
 * 创建时间： 2018年8月27日-下午1:37:39
 */
package main.view;

import main.util.Constants;
import main.dao.BaseDAO;
import main.dao.AdminDAO;
import main.dao.DAO;
import main.model.Admin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * 模块说明： 登录界面
 */

public class LoginView extends JFrame {

    private JPanel jPanelCenter,jPanelSouth;
    private JTextField username,password;
    private JButton loginButton,resetButton;

    private MainView mv;

    public LoginView(){
        this.init();
    }

    private void init(){
        this.setTitle(Constants.LOGIN_TITLE);
        /**上侧Panel*/
        jPanelCenter = new JPanel();
        jPanelCenter.setLayout(new GridLayout(3,2));
        jPanelCenter.add(new JLabel(Constants.LOGIN_USERNAME));
        username = new JTextField();
        jPanelCenter.add(username);
        jPanelCenter.add(new JLabel(Constants.LOGIN_PASSWORD));
        password = new JPasswordField();
        password.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER){
                    LoginView.this.login();
                }
                super.keyPressed(e);
            }
        });
        jPanelCenter.add(password);

        jPanelCenter.add(new JLabel("----------------------------------------------"));
        jPanelCenter.add(new JLabel("----------------------------------------------"));
        this.add(jPanelCenter,BorderLayout.CENTER);

        /**下侧Panel*/
        jPanelSouth = new JPanel();
        jPanelSouth.setLayout(new GridLayout(1,2));
        //登陆
        loginButton = new JButton(Constants.LOGIN);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoginView.this.login();
            }
        });
        //重置
        resetButton = new JButton(Constants.RESET);
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoginView.this.setEmpty();
            }
        });
        //集成
        jPanelSouth.add(loginButton);
        jPanelSouth.add(resetButton);
        this.add(jPanelSouth,BorderLayout.SOUTH);

        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setBounds(450, 250, 375, 140);
        this.setResizable(false);
        this.setVisible(true);
    }

    private void login(){
        if (this.check()){
            Admin ad = this.buildAdmin();
            boolean isSuccess = ((AdminDAO) BaseDAO.getAbilityDAO(DAO.AdminDAO)).queryForLogin(ad);
            if (isSuccess){
                this.mv = new MainView();
                this.dispose();
            }else{
                this.setEmpty();
            }
        }
    }

    /**
     * 检查输入信息是否合法
     * @return result true合法，false不合法
     */
    private boolean check(){
        boolean result = false;
        if ("".equals(username.getText()) || "".equals(password.getText())) {
            return result;
        } else {
            result = true;
        }
        return result;
    }

    /**
     * 获取TextFiled信息，并将其存入ad
     */
    private Admin buildAdmin() {
        Admin ad = new Admin();
        ad.setUsername(username.getText());
        ad.setPassword(password.getText());
        return ad;
    }

    /**
     * 更新后将TextFiled设为空
     */
    private void setEmpty(){
        username.setText("");
        password.setText("");
    }

}
