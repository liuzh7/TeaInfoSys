package main.dao;

import main.model.Admin;

import java.sql.SQLException;

public class AdminDAO extends BaseDAO {
    private static AdminDAO ad;

    public AdminDAO(){

    }

    /**
     * 单例类
     */
    public static synchronized AdminDAO getInstance(){
        if (ad == null){
            ad = new AdminDAO();
        }
        return ad;
    }

    /**
     * 检查管理员账户是否存在
     */
    public boolean queryForLogin(Admin ad){
        String username = ad.getUsername();
        String password = ad.getPassword();
        boolean result = false;
        if (username.length() == 0 || password.length() == 0){
            return result;
        }
        String[] para = {username,password};
        String sql = "SELECT * FROM admin WHERE username=? and password=?";
        rs = db.executeQuery(sql,para);
        try {
            if (rs.next()){
                result = true;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            this.destroy();//rs只要有东西就证明账户存在，就不需要rs了，直接断开就好
        }
        return result;
    }

}
