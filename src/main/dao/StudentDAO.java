package main.dao;

import main.model.Student;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import java.util.ArrayList;

public class StudentDAO extends BaseDAO {
    private static StudentDAO sd;

    private final int fieldNum = 9;
	private final int showNum = 15;

    public StudentDAO(){

    }

    /**
     * 单例类
     */
    public static synchronized StudentDAO getInstance(){
        if (sd == null){
            sd = new StudentDAO();
        }
        return sd;
    }

    /**
     * 增
     */
    public boolean add(Student stu){
        boolean result = false;
        if (this.queryBySno(stu)==1 || stu == null){
            return result;
        }
        String sql = "INSERT INTO student(sno,name,sex,department,homeTown,mark,email,tel) values(?,?,?,?,?,?,?,?)";
        String[] info = {stu.getSno(),stu.getName(),stu.getSex(),stu.getDepartment(),
                stu.getHomeTown(),stu.getMark(),stu.getEmail(),stu.getTel()};
        if(db.executeUpdate(sql,info) == 1) {
            result = true;
        }
        this.destroy();
        return result;
    }

    /**
     * 删
     */
    public boolean delete(Student stu){
        boolean result = false;
        if (stu == null){
            return result;
        }
        String sql = "DELETE FROM student WHERE sno=?";
        String[] info = {stu.getSno()};
        if(db.executeUpdate(sql,info) == 1) {
            result = true;
        }
        this.destroy();
        return result;
    }

    /**
     * 改
     */
    public boolean update(Student stu){
        boolean result = false;
        if (this.queryBySno(stu)==0 || stu == null){
            return result;
        }
        String sql = "UPDATE student SET sex=?,department=?,email=?,tel=?,hometown=?,mark=? where name=? and sno=?";
        String[] info = {stu.getSex(), stu.getDepartment(), stu.getEmail(), stu.getTel(), stu.getHomeTown(),
					stu.getMark(), stu.getName(), stu.getSno()};
        if (db.executeUpdate(sql,info)==1){
            result = true;
        }
        this.destroy();
        return result;
    }

    /**
     * 按照姓名查找
     * @param stu 学生
     * @return
     */
    public String[][] queryByName(Student stu){
        String[][] result = null;
        String name = stu.getName();
		if (name.length() < 0) {
			return result;
		}
        String sql = "SELECT * FROM student WHERE name like ?";
        String[] info = {"%" + name + "%" };
        rs = db.executeQuery(sql,info);
        int i = 0;
        List<Student> stus = new ArrayList<Student>();
        try{
            while (rs.next()){
                this.buildList(rs,stus,i);
                i++;
            }
            if(stus.size() > 0) {
                result = new String[stus.size()][fieldNum];
                for (int j = 0; j < stus.size(); j++) {
                    this.buildResult(result, stus, j);
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            this.destroy();
        }
        return result;
    }

    /**
     * 按页码查询，为MainView提供显示
     * @param pageNum 展示页码数
     * @return 页面内容
     */
    public String[][] query(int pageNum){
        String[][] result = null;
		if (pageNum < 1) {
			return result;
		}
		List<Student> stus = new ArrayList<Student>();
		int beginNum = (pageNum - 1) * showNum;
		String sql = "select * from student limit ?,?";
		Integer[] param = { beginNum, showNum };
		rs = db.executeQuery(sql, param);
		int i = 0;
		try {
			while (rs.next()) {
				buildList(rs, stus, i);
				i++;
			}
			if (stus.size() > 0) {
				result = new String[stus.size()][fieldNum];
				for (int j = 0; j < stus.size(); j++) {
					buildResult(result, stus, j);
				}
			}
		} catch (SQLException se) {
			se.printStackTrace();
		} finally {
			destroy();
		}
		return result;
    }

    /**
     * 按学生编号查找，用于查重
     * @param stu 学生信息
     * @return 学生编号
     */
    private int queryBySno(Student stu){
        int result = 0;
        if (stu == null){
            return result;
        }
        String sql = "SELECT sno FROM student WHERE sno=?";
        String[] info = {stu.getSno()};
        rs = db.executeQuery(sql,info);
        try{
            if (rs.next()){
                result = 1;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 处理方法，将获得的信息放进list
     * @param rs ResultSet
     * @param list 用于存放Student的List
     * @param i 位置
     */
    private void buildList(ResultSet rs, List<Student> list, int i){
        Student stu = new Student();
        try{
            stu.setId(i+1);
            stu.setSno(rs.getString("sno"));
            stu.setName(rs.getString("name"));
            stu.setDepartment(rs.getString("department"));
            stu.setEmail(rs.getString("email"));
            stu.setHomeTown(rs.getString("hometown"));
            stu.setMark(rs.getString("mark"));
            stu.setSex(rs.getString("sex"));
            stu.setTel(rs.getString("tel"));
            list.add(stu);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * 将存放stu的list直接转化成一个二维数组result
     * @param result 二位数组
     * @param stus list
     * @param j 位置
     */
    private void buildResult(String[][] result, List<Student> stus, int j){
        Student stu = stus.get(j);
        result[j][0] = String.valueOf(stu.getId());
		result[j][1] = stu.getName();
		result[j][2] = stu.getSno();
		result[j][3] = stu.getSex();
		result[j][4] = stu.getDepartment();
		result[j][5] = stu.getHomeTown();
		result[j][6] = stu.getMark();
		result[j][7] = stu.getEmail();
		result[j][8] = stu.getTel();
    }

}
