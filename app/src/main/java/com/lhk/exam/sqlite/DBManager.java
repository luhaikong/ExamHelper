package com.lhk.exam.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2019/3/27.
 * 对数据库操作的工具类
 */

public class DBManager {
    private static DBHelper helper;
    public static DBHelper getInstance(Context context){
        if (helper==null){
            synchronized (DBHelper.class){
                if (helper==null){
                    helper = new DBHelper(context);
                }
            }
        }
        return helper;
    }

    /**
     * 获取数据总条目
     * @param db
     * @param tableName
     * @return 数据总条目
     */
    public static int getDataTotal(SQLiteDatabase db, String tableName){
        int total = 0;
        if (db!=null){
            Cursor cursor = db.rawQuery("select * from "+tableName,null);
            total = cursor.getCount();//获取cursor中的数据总数
        }
        return total;
    }

    /**
     * 根据当前页码查询获取该页码对应的数据集合
     * @param db
     * @param tableName
     * @param currentPage
     * @return
     */
    public static List<User> getListByCurrentPage(SQLiteDatabase db, String tableName, int currentPage, int pageSize){
        Cursor cursor = null;
        int index = (currentPage-1)*pageSize;
        if (db!=null){
            String sql = "select * from "+tableName+" limit ?,?";
            cursor = db.rawQuery(sql,new String[]{index+"",pageSize+""});
        }
        return cursorToList(cursor);
    }

    /**
     * 根据sql语句查询获得cursor对象
     * @param db 数据库对象
     * @param sql 查询的sql语句
     * @param selectionArgs 查询条件的占位符
     * @return 查询结果
     */
    public static Cursor selectDataBySql(SQLiteDatabase db, String sql, String[] selectionArgs){
        Cursor cursor = null;
        if (db!=null){
            if (sql!=null&&!"".equals(sql)){
                cursor = db.rawQuery(sql,selectionArgs);
            }
        }
        return cursor;
    }

    /**
     * 将查询的cursor队对象转换成list集合
     * @param cursor
     * @return
     */
    public static List<User> cursorToList(Cursor cursor){
        List<User> list = new ArrayList<>();
        //cursor.moveToNext() 如果返回true表示下一条记录存在，否则表示游标中数据读取完毕
        while (cursor.moveToNext()){
            //getColumnIndex 根据参数中指定的字段名称获取字段下标
            int columnIndex = cursor.getColumnIndex(Constant.TableUser._ID);
            //getInt 根据参数中指定的字段下标获取对应int类型的数据
            int _id = cursor.getInt(columnIndex);

            String name = cursor.getString(cursor.getColumnIndex(Constant.TableUser.NAME));
            int age = cursor.getInt(cursor.getColumnIndex(Constant.TableUser.AGE));

            User user = new User();
            user.set_id(_id);
            user.setName(name);
            user.setAge(age);

            list.add(user);
        }
        return list;
    }


    public static void insert(SQLiteDatabase db,Context context){
        try {
            String sql = "insert into "+ Constant.TableUser.TABLE_NAME+" values(1,'张三',20)";
            DBManager.execSQL(db,sql);

            String sql2 = "insert into "+ Constant.TableUser.TABLE_NAME+" values(2,'李四',30)";
            DBManager.execSQL(db,sql2);
        } catch (Exception e){
            Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
        } finally {
            db.close();
        }
    }

    public static void update(SQLiteDatabase db){
        String updateSql = "update "+Constant.TableUser.TABLE_NAME+" set "+Constant.TableUser.NAME+"='xiaoming' where "+Constant.TableUser._ID+"=1";
        DBManager.execSQL(db,updateSql);
        db.close();
    }

    public static void delete(SQLiteDatabase db){
        String delSql = "delete from "+Constant.TableUser.TABLE_NAME+" where "+Constant.TableUser._ID+"=2";
        DBManager.execSQL(db,delSql);
        db.close();
    }

    public static void query(SQLiteDatabase db){
        String querySql = "select * from "+Constant.TableUser.TABLE_NAME;
        Cursor cursor = DBManager.selectDataBySql(db,querySql,null);
        List<User> list = DBManager.cursorToList(cursor);
        for (User p:list){
            Log.i("tag",p.getName());
        }
        db.close();
    }

    public static void insertDatas(SQLiteDatabase db){
        //1.数据库显示开启事务
        db.beginTransaction();
        for (int i=1;i<100;i++){
            String sq = "insert into "+Constant.TableUser.TABLE_NAME+" values("+i+",'xiaomu"+i+"',18)";
            db.execSQL(sq);
        }
        //2.提交当前事务
        db.setTransactionSuccessful();
        //3.关闭事务
        db.endTransaction();
        db.close();
    }

    /**
     * 根据sql语句在数据库中执行语句
     * @param db
     * @param sql
     */
    public static void execSQL(SQLiteDatabase db, String sql){
        if (db!=null){
            if (sql!=null&&!"".equals(sql)){
                db.execSQL(sql);
            }
        }
    }
}
