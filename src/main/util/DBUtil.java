/**
 * 项目名：student
 * 修改历史：
 * 作者： LZ
 * 创建时间： 2018年8月27日-下午1:37:39
 */
package main.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 模块说明：jdbc封装类
 * 工具类向外提供Update和Query方法，并使用单例类提供一个自己的引用，向外提供注销方法
 * 连接不向外提供
 */
public class DBUtil {
    private static DBUtil db;
    /*写成静态变量不用通过构造方法拿类引用，
     * 直接通过静态方法就可以拿到引用，
     * 一般工具类都写一个对自己的静态引用*、
     */

    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;

    public DBUtil() {

    }

    /**
     * 创建单例类
     */
    public static DBUtil getDBUtil() {
        if(db==null){
            db = new DBUtil();
        }
        return db;
    }

    /**
     * 拿到jdbc连接，自己用
     */
    private Connection getConn(){
        try {
            if (conn == null || conn.isClosed()){
                Class.forName(Constants.JDBC_DRIVER);
                conn = DriverManager.getConnection(Constants.JDBC_URL, Constants.JDBC_USERNAME, Constants.JDBC_PASSWORD);
                return conn;
            }
        } catch (ClassNotFoundException e){
            System.out.println("jdbc driver is not found.");
            e.printStackTrace();
        } catch (SQLException e){
            e.printStackTrace();
        }
        return conn;
    }

    /**
     * 关闭连接
     */
    public void close(){
        try {
            if (rs != null){
                rs.close();
                rs = null;
            }
            if (ps != null){
                ps.close();
                ps = null;
            }
            if (conn != null){
                conn.close();
                conn = null;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * DML操作:INSERT SELECT DELETE UPDATE
     * 更新完关闭Connection，PreparedStatement，ResultSet，用的时候重启
     * 既能实现添加，又能实现更新
     */
    public int executeUpdate(String sql, Object[] obj){
        int result = -1;
        if (getConn() == null) {
            return result;
        }
        try {
            ps = conn.prepareStatement(sql);
            for (int i = 0; i < obj.length; i++){
                ps.setObject(i+1,obj[i]);
            }
            result = ps.executeUpdate(); //成功1不成功0
            return result;
        } catch (SQLException e){
            System.out.println("Update failed");
            e.printStackTrace();
        } finally {
            this.close(); // 关闭conn,ps,rs
        }
        return result;
    }

    /**
     * 多条件查询操作
     * 更新完不能关闭Connection，ResultSet ResultSet滚动的时候要用
     * SELECT A FROM B WHERE C=? AND D=?
     */
    public ResultSet executeQuery(String sql, Object[] obj){
        if (getConn() == null) {
            return null;
        }
        try {
            ps = conn.prepareStatement(sql);
            for (int i = 0; i < obj.length; i++){
                ps.setObject(i+1,obj[i]);
            }
            rs = ps.executeQuery(); //返回查询结果放进rs
        } catch (SQLException e){
            System.out.println("Update wrong");
            e.printStackTrace();
        }
        return rs;
    }

}



