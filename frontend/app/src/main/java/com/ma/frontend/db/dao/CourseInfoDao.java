package com.ma.frontend.db.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.ma.frontend.db.DBHelper;
import com.ma.frontend.domain.CourseInfo;

/**
 * @Auther:kiwi
 * @Date: 2019/5/22 16:12
 */
public class CourseInfoDao {
    private DBHelper globalDBHelper;
    private SQLiteDatabase db;

    public CourseInfoDao(Context context) {
        globalDBHelper = new DBHelper(context);
        db = globalDBHelper.getWritableDatabase();
    }

    public int insert(CourseInfo cInfo) {
        try {
            // 如果找到相同（时间地点相同）课程，更新信息
            String sql1 = "SELECT cid FROM course_info WHERE weekfrom=? AND weekto=? AND weektype=? " +
                    "AND day=? AND lessonfrom=? AND lessonto=? AND place=?";
            Cursor c = db.rawQuery(sql1, new String[]{
                    cInfo.getWeekfrom()+"",
                    cInfo.getWeekto()+"",
                    cInfo.getWeektype()+"",
                    cInfo.getDay()+"",
                    cInfo.getLessonfrom()+"",
                    cInfo.getLessonto()+"",
                    cInfo.getPlace()
            });
            if (c.getCount() != 0) {//??????????????????????????????????????????
                c.moveToFirst();
                int cid = c.getInt(c.getColumnIndex("cid"));
                String sql2 = "UPDATE course_info SET coursename=?,teacher=? WHERE cid=?";
                db.execSQL(sql2, new Object[] {
                        cInfo.getCoursename(),
                        cInfo.getTeacher(),
                        cid
                });
                return cid;
            }
            // 否则插入课程
            String sql = "INSERT INTO course_info (cid,weekfrom,weekto,weektype,day,lessonfrom,lessonto," +
                    "coursename,teacher,place) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            db.execSQL(sql, new Object[] {
                    cInfo.getCid(),
                    cInfo.getWeekfrom(),
                    cInfo.getWeekto(),
                    cInfo.getWeektype(),
                    cInfo.getDay(),
                    cInfo.getLessonfrom(),
                    cInfo.getLessonto(),
                    cInfo.getCoursename(),
                    cInfo.getTeacher(),
                    cInfo.getPlace()
            });
            Cursor c1 = db.rawQuery("SELECT last_insert_rowid()", null);
            c1.moveToFirst();
            return c1.getInt(c1.getColumnIndex("last_insert_rowid()"));
        } catch (Exception e) {
            return 0;
        }

    }

    public boolean delete(int cid) {
        try {
            String sql = "DELETE FROM course_info WHERE cid=?";
            db.execSQL(sql, new Object[] {cid});
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}


