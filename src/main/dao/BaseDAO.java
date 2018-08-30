package main.dao;

import main.util.DBUtil;

import java.sql.*;

/**
 * 模块说明： DAO基类
 * 向外提供管理两个实例Dao的方法getAbilityDAO
 * 向外提供注销方法
 */
public abstract class BaseDAO {
    protected static DBUtil db = DBUtil.getDBUtil();
    protected ResultSet rs; //用于AdminDAO中放db处理结果
    private static BaseDAO baseDAO;


    public BaseDAO(){

    }

    public static synchronized BaseDAO getAbilityDAO(DAO dao){
        switch (dao){
            case AdminDAO:
                if (baseDAO == null || baseDAO.getClass() != AdminDAO.class) {
                    baseDAO = AdminDAO.getInstance();
                }
                break;
            case StudentDAO:
                if (baseDAO == null || baseDAO.getClass() != StudentDAO.class) {
                    baseDAO = StudentDAO.getInstance();
                }
                break;
            default:
                break;
        }
        return baseDAO;
    }

    /**
     * 工具类的引用始终都在，用的时候再在工具类里面开里面的SQL即可。
     */
    public void destroy(){
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            db.close();
        }
    }
}
