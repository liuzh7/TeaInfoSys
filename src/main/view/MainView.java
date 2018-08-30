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
import main.dao.DAO;
import main.dao.BaseDAO;
import main.dao.StudentDAO;

//MODEL
import main.model.Student;

//GUI
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.*;

/**
 * 模块名：主界面
 */

public class MainView extends JFrame {

    private JPanel jPanelNorth,jPanelSourth,jPanelCenter;
    private JTextField condition;
    private JButton jButtonFind,jButtonAdd,jButtonDelete,jButtonUpdate,jButtonFirst,jButtonPre,jButtonNext,jButtonLast;
    private JLabel currPageNumJLabel;
    private DefaultTableModel myTableModel;

    private JTable jTable;
    private JScrollPane jScrollPane;

    private AddView av;
    private DeleteView dv;
    private UpdateView uv;

    /**当前显示页码*/
    private int currPageNum = 1;
    private int maxPageNum = 99;

	private static String[] column = {"id", Constants.STUDENT_NAME, Constants.STUDENT_SNO, Constants.STUDENT_SEX, Constants.STUDENT_DEPARTMETN,
            Constants.STUDENT_HOMETOWN, Constants.STUDENT_MARK, Constants.STUDENT_EMAIL, Constants.STUDENT_TEL };

    public MainView(){
        this.init();
     }

    private void init(){
        this.setTitle(Constants.MAINVIEW_TITLE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (av != null){
                    av.dispose();
                    av = null;
                }
                if (dv != null){
                    dv.dispose();
                    dv = null;
                }
                if (uv != null){
                    uv.dispose();
                    uv = null;
                }
                super.windowClosing(e);
            }
        });
        /**上侧Panel*/
        jPanelNorth = new JPanel();
        jPanelNorth.setLayout(new GridLayout(1,5));
        //输入框
        condition = new JTextField(Constants.PARAM_FIND_CONDITION);
        condition.addKeyListener(new FindListener());
         //查找
        jButtonFind = new JButton(Constants.PARAM_FIND);
        jButtonFind.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainView.this.find();
            }
        });
        //添加
        jButtonAdd = new JButton(Constants.PARAM_ADD);
        jButtonAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainView.this.av = new AddView(MainView.this);
            }
        });
         //删除
        jButtonDelete = new JButton(Constants.PARAM_DELETE);
        jButtonDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainView.this.dv = new DeleteView(MainView.this);
            }
        });
        //更新
        jButtonUpdate = new JButton(Constants.PARAM_UPDATE);
        jButtonUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainView.this.uv = new UpdateView(MainView.this);
            }
        });
         //集成
        jPanelNorth.add(condition);
        jPanelNorth.add(jButtonFind);
        jPanelNorth.add(jButtonAdd);
        jPanelNorth.add(jButtonDelete);
        jPanelNorth.add(jButtonUpdate);
        this.add(jPanelNorth,BorderLayout.NORTH);


        /**中间Pannel*/
        jPanelCenter = new JPanel();
        jPanelCenter.setLayout(new GridLayout(1,1));
        //获取数据库信息query第一页信息用于建表
        String[][] result = ((StudentDAO) BaseDAO.getAbilityDAO(DAO.StudentDAO)).query(currPageNum);
        //利用TableModel建表
        myTableModel = new DefaultTableModel(result, column);
        jTable = new JTable(myTableModel);
        //利用TableCellRenderer调整表格，居中
		DefaultTableCellRenderer cr = new DefaultTableCellRenderer();
		cr.setHorizontalAlignment(JLabel.CENTER);
        jTable.setDefaultRenderer(Object.class, cr);
        //更新表格内容和格式，此处为了保证在表内容发生变化时格式统一
        initJTable(jTable, result);
         //集成
        jScrollPane = new JScrollPane(jTable);
        jPanelCenter.add(jScrollPane);
        this.add(jPanelCenter,BorderLayout.CENTER);


         /**下侧Pannel*/
        jPanelSourth = new JPanel();
        jPanelSourth.setLayout(new GridLayout(1,5));
        //首页
        jButtonFirst = new JButton(Constants.MAINVIEW_FIRST);
        jButtonFirst.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currPageNum = 1;
                String[][] result = ((StudentDAO) BaseDAO.getAbilityDAO(DAO.StudentDAO)).query(currPageNum);
                initJTable(jTable, result);
                currPageNumJLabel.setText(Constants.MAINVIEW_PAGENUM_JLABEL_DI + currPageNum
                        + Constants.MAINVIEW_PAGENUM_JLABEL_YE);
            }
        });
        //上一页
        jButtonPre = new JButton(Constants.MAINVIEW_PRE);
        jButtonPre.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currPageNum--;
                if (currPageNum <= 0) {
                    currPageNum = 1;
                }
                String[][] result = ((StudentDAO) BaseDAO.getAbilityDAO(DAO.StudentDAO)).query(currPageNum);
                initJTable(jTable, result);
                currPageNumJLabel.setText(Constants.MAINVIEW_PAGENUM_JLABEL_DI + currPageNum
                        + Constants.MAINVIEW_PAGENUM_JLABEL_YE);
            }
        });
        //页标
        currPageNumJLabel = new JLabel(Constants.MAINVIEW_PAGENUM_JLABEL_DI + currPageNum + Constants.MAINVIEW_PAGENUM_JLABEL_YE);
        currPageNumJLabel.setHorizontalAlignment(JLabel.CENTER);
        //下一页
        jButtonNext = new JButton(Constants.MAINVIEW_NEXT);
        jButtonNext.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currPageNum++;
                if (currPageNum > maxPageNum) {
                    currPageNum = maxPageNum;
                }
                String[][] result = ((StudentDAO) BaseDAO.getAbilityDAO(DAO.StudentDAO)).query(currPageNum);
                initJTable(jTable, result);
                currPageNumJLabel.setText(Constants.MAINVIEW_PAGENUM_JLABEL_DI + currPageNum
                        + Constants.MAINVIEW_PAGENUM_JLABEL_YE);
            }
        });
        //最后一页
        jButtonLast = new JButton(Constants.MAINVIEW_LAST);
        jButtonLast.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currPageNum = maxPageNum;
                String[][] result = ((StudentDAO) BaseDAO.getAbilityDAO(DAO.StudentDAO)).query(currPageNum);
                initJTable(jTable, result);
                currPageNumJLabel.setText(Constants.MAINVIEW_PAGENUM_JLABEL_DI + currPageNum
                        + Constants.MAINVIEW_PAGENUM_JLABEL_YE);
            }
        });

        jPanelSourth.add(jButtonFirst);
        jPanelSourth.add(jButtonPre);
        jPanelSourth.add(currPageNumJLabel);
        jPanelSourth.add(jButtonNext);
        jPanelSourth.add(jButtonLast);
        this.add(jPanelSourth,BorderLayout.SOUTH);
        // 后处理
        this.setBounds(400, 200, 750, 340);

        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setVisible(true);
     }

    private class FindListener extends KeyAdapter{
         @Override
         public void keyPressed(KeyEvent e) {
             if (e.getKeyCode() == KeyEvent.VK_ENTER){
                MainView.this.find();
             }
             super.keyPressed(e);
         }
     }

    private void find(){
        currPageNum = 0;
        String param = condition.getText();
        if (param == null || "".equals(param.trim()) ) {
            //如果没结果，把表格置空
			initJTable(this.jTable, null);
            //如果没结果，把表初始化
//            String[][] result = ((StudentDAO) BaseDAO.getAbilityDAO(DAO.StudentDAO)).query(1);
//            initJTable(this.jTable, result);
            //如果没结果，表不变
        }else{
            Student stu = new Student();
            stu.setName(param);
            String[][] result = ((StudentDAO) BaseDAO.getAbilityDAO(DAO.StudentDAO)).queryByName(stu);
            condition.setText("");
            initJTable(this.jTable, result);
        }
         //页码位置换成查询结果
        currPageNumJLabel.setText(Constants.MAINVIEW_FIND_JLABEL);
     }

    /**
    * 用于更新数据
    * @param jTable 表格
    * @param result 数据
    */
    public void initJTable(JTable jTable, String[][] result){
        //换数据
        ((DefaultTableModel) jTable.getModel()).setDataVector(result, column);
        jTable.setRowHeight(20);
        TableColumn firsetColumn = jTable.getColumnModel().getColumn(0);
        firsetColumn.setPreferredWidth(30);
        firsetColumn.setMaxWidth(30);
        firsetColumn.setMinWidth(30);
        TableColumn secondColumn = jTable.getColumnModel().getColumn(1);
        secondColumn.setPreferredWidth(60);
        secondColumn.setMaxWidth(60);
        secondColumn.setMinWidth(60);
        TableColumn thirdColumn = jTable.getColumnModel().getColumn(2);
        thirdColumn.setPreferredWidth(90);
        thirdColumn.setMaxWidth(90);
        thirdColumn.setMinWidth(90);
        TableColumn fourthColumn = jTable.getColumnModel().getColumn(3);
        fourthColumn.setPreferredWidth(30);
        fourthColumn.setMaxWidth(30);
        fourthColumn.setMinWidth(30);
        TableColumn seventhColumn = jTable.getColumnModel().getColumn(6);
        seventhColumn.setPreferredWidth(30);
        seventhColumn.setMaxWidth(30);
        seventhColumn.setMinWidth(30);
        TableColumn ninthColumn = jTable.getColumnModel().getColumn(8);
        ninthColumn.setPreferredWidth(90);
        ninthColumn.setMaxWidth(90);
        ninthColumn.setMinWidth(90);
    }

    public JTable getjTable() {
        return jTable;
    }

    public int getCurrPageNum() {
        return currPageNum;
    }

    public void setCurrPageNum(int currPageNum) {
        this.currPageNum = currPageNum;
    }

}

