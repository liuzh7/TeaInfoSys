/**
 * 项目名：student
 * 修改历史：
 * 作者： LZ
 * 创建时间： 2018年8月27日-下午1:37:39
 */
package main.model;

/**
 * 模块说明： 学生
 * 用于传递Student数据
 */
public class Student {

    private int id;
    private String sno;
    private String name;
    private String sex;
    private String department;// 院系
    private String homeTown;// 籍贯
    private String mark;// 学分
    private String email;
    private String tel;// 联系方式

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getHomeTown() {
        return homeTown;
    }

    public void setHomeTown(String homeTown) {
        this.homeTown = homeTown;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

}
