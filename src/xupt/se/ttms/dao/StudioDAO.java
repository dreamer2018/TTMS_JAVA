package xupt.se.ttms.dao;

import java.util.LinkedList;
import java.util.List;
import java.sql.ResultSet;


import xupt.se.ttms.idao.iStudioDAO;
import xupt.se.ttms.model.Studio;
import xupt.se.util.DBUtil;


/*
    封装了对数据库的部分操作，插入演出厅信息，更新演出厅信息
 */
public class StudioDAO implements iStudioDAO {

    /*
        往数据库中插入信息演出厅信息
     */
    @Override
    public int insert(Studio stu) {
        try {
            String sql = "insert into studio(studio_name, studio_row_count, studio_col_count, studio_introduction )"
                    + " values('"
                    + stu.getName()
                    + "', "
                    + stu.getRowCount()
                    + ", " + stu.getColCount()
                    + ", '" + stu.getIntroduction()
                    + "' )";

            //DBUtil()类中，封装了对于数据库连接操作
            DBUtil db = new DBUtil();
            //调用DBUtil中的连接方法
            db.openConnection();
            ResultSet rst = db.getInsertObjectIDs(sql);
            if (rst != null && rst.first()) {
                stu.setID(rst.getInt(1));
            }
            db.close(rst);
            db.close();
            return 1;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    /*
        更新演出厅数据
     */
    @Override
    public int update(Studio stu) {
        int rtn = 0;
        try {
            String sql = "update studio set " + " studio_name ='"
                    + stu.getName() + "', " + " studio_row_count = "
                    + stu.getRowCount() + ", " + " studio_col_count = "
                    + stu.getColCount() + ", " + " studio_introduction = '"
                    + stu.getIntroduction() + "' ";

            sql += " where studio_id = " + stu.getID();
            DBUtil db = new DBUtil();
            db.openConnection();
            rtn = db.execCommand(sql);
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rtn;
    }

    /*
    通过ID删除演出厅数据
     */
    @Override
    public int delete(int ID) {
        int rtn = 0;
        try {
            String sql = "delete from  studio ";
            sql += " where studio_id = " + ID;
            DBUtil db = new DBUtil();
            db.openConnection();
            rtn = db.execCommand(sql);
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rtn;
    }

    /*
        检索演出厅数据，condt为检索条件
     */
    @Override
    public List<Studio> select(String condt) {
        List<Studio> stuList = null;
        stuList = new LinkedList<Studio>();
        try {
            String sql = "select studio_id, studio_name, studio_row_count, studio_col_count, studio_introduction from studio ";
            condt.trim();
            if (!condt.isEmpty())
                sql += " where " + condt;
            DBUtil db = new DBUtil();
            if (!db.openConnection()) {
                System.out.print("fail to connect database");
                return null;
            }
            ResultSet rst = db.execQuery(sql);
            if (rst != null) {
                while (rst.next()) {
                    Studio stu = new Studio();
                    stu.setID(rst.getInt("studio_id"));
                    stu.setName(rst.getString("studio_name"));
                    stu.setRowCount(rst.getInt("studio_row_count"));
                    stu.setColCount(rst.getInt("studio_col_count"));
                    stu.setIntroduction(rst.getString("studio_introduction"));
                    stuList.add(stu);
                }
            }
            db.close(rst);
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stuList;
    }
}
