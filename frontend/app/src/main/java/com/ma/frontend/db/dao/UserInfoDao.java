package com.ma.frontend.db.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.ma.frontend.db.DBHelper;
import com.ma.frontend.domain.UserInfo;

/**
 * @Auther:kiwi
 * @Date: 2019/5/22 16:16
 */

public class UserInfoDao {
    private DBHelper dbHelper;
    private SQLiteDatabase db;

    public UserInfoDao(Context context) {
        dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    /**
     * 插入一条用户信息
     * @param uInfo 用户信息
     * @return 本次插入的用户的UID
     */
    public int insert(UserInfo uInfo) {
        try {
            UserInfo uInfoNew = query(uInfo.getUid());
            if(uInfoNew!=null)
            {
                uInfo.setUid(uInfoNew.getUid());
                if(update(uInfo)) {
                    return uInfo.getUid();
                }
                else {
                    return 0;
                }
            }
            String sql = "INSERT INTO user_msg (uid, username, gender, phone, headshot, institute, major, year) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            db.execSQL(sql, new Object[] {
                    uInfo.getUid(),
                    uInfo.getUsername(),
                    uInfo.getGender(),
                    uInfo.getPhone(),
                    uInfo.getHeadshot(),
                    uInfo.getInstitute(),
                    uInfo.getMajor(),
                    uInfo.getYear()
            });
            Cursor c1 = db.rawQuery("SELECT last_insert_rowid()", null);
            c1.moveToFirst();
            int id = c1.getInt(c1.getColumnIndex("last_insert_rowid()"));
            c1.close();
            return id;
        } catch (Exception e) {
            return 0;
        }

    }

    /**
     * 按照UID删除用户记录
     * @param uid
     * @return 删除成功返回true
     */
    public boolean delete(int uid) {
        try {
            String sql = "DELETE FROM user_msg WHERE uid = ?";
            db.execSQL(sql, new Object[] {uid});
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 按照用户名删除用户记录
     * @param username
     * @return 删除成功返回true
     */
    public boolean delete(String username) {
        try {
            String sql = "DELETE FROM user_msg WHERE username = ?";
            db.execSQL(sql, new Object[] {username});
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 修改用户信息
     * @param uInfo
     * @return 如果参数为空或者uid为空都返回false
     */

    public boolean update(UserInfo uInfo) {
        if (uInfo == null) {
            return false;
        }
        if (uInfo.getUid() == 0) {
            return false;
        }
        try {
            String sql = "UPDATE user_msg SET username=?, gender=?, phone=?, headshot=?, institute=?, major=?, year=?" +
                    "WHERE uid=?";
            db.execSQL(sql, new Object[] {
                    uInfo.getUsername(),
                    uInfo.getGender(),
                    uInfo.getPhone(),
                    uInfo.getHeadshot(),
                    uInfo.getInstitute(),
                    uInfo.getMajor(),
                    uInfo.getYear(),
                    uInfo.getUid()
            });
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 通过UID查找用户信息
     * @param uid
     * @return 用户信息
     */
    public UserInfo query(int uid) {
        try {
            UserInfo uInfo = new UserInfo();
            String sql = "SELECT * FROM user_msg WHERE uid="+uid;
            Cursor c = db.rawQuery(sql, null);
            if (c.getCount() == 0) {
                return null;
            }
            else {
                c.moveToFirst();
                uInfo.setUid(uid);
                uInfo.setUsername(c.getString(c.getColumnIndex("username")));
                uInfo.setGender(c.getString(c.getColumnIndex("gender")));
                uInfo.setPhone(c.getString(c.getColumnIndex("phone")));
                uInfo.setHeadshot(c.getString(c.getColumnIndex("headshot")));
                uInfo.setInstitute(c.getString(c.getColumnIndex("institute")));
                uInfo.setMajor(c.getString(c.getColumnIndex("major")));
                uInfo.setYear(c.getString(c.getColumnIndex("year")));
                return uInfo;
            }
        } catch (Exception e) {
            return null;
        }
    }

}
