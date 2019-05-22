package com.ma.frontend.db.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.ma.frontend.db.DBHelper;
import com.ma.frontend.domain.GlobalInfo;

/**
 * @Auther:kiwi
 * @Date: 2019/5/22 16:12
 */
public class GlobalInfoDao {
        private DBHelper dbHelper;
        private SQLiteDatabase db;

        public GlobalInfoDao(Context context) {
            dbHelper = new DBHelper(context);
            db = dbHelper.getWritableDatabase();
        }

        public boolean insert(GlobalInfo globalInfo) {
            try {
                String sqlDel = "DELETE FROM global_info";
                db.execSQL(sqlDel);
                String sql = "INSERT INTO global_info (key, value) VALUES (?, ?)";
                db.execSQL(sql, new Object[] {"version", globalInfo.getVersion()});
                db.execSQL(sql, new Object[] {"versionstr", globalInfo.getVersionStr()});
                db.execSQL(sql, new Object[] {"termbegin", globalInfo.getTermBegin()});
                db.execSQL(sql, new Object[] {"yearfrom", globalInfo.getYearFrom()});
                db.execSQL(sql, new Object[] {"yearto", globalInfo.getYearTo()});
                db.execSQL(sql, new Object[] {"term", globalInfo.getTerm()});
                db.execSQL(sql, new Object[] {"isfirstuse", globalInfo.isFirstUse()});
                db.execSQL(sql, new Object[] {"activeuseruid", globalInfo.getActiveUserUid()});
                return true;
            } catch (Exception e) {
                return false;
            }
        }

        public GlobalInfo query() {
            try {
                GlobalInfo gInfo = new GlobalInfo();
                Cursor c = db.rawQuery("SELECT * FROM global_info", null);
                if (c.getCount() == 0) {
                    return null;
                }
                else {
                    c.moveToFirst();
                    gInfo.setVersion(c.getInt(c.getColumnIndex("value")));
                    c.moveToNext();
                    gInfo.setVersionStr(c.getString(c.getColumnIndex("value")));
                    c.moveToNext();
                    gInfo.setTermBegin(c.getString(c.getColumnIndex("value")));
                    c.moveToNext();
                    gInfo.setYearFrom(c.getInt(c.getColumnIndex("value")));
                    c.moveToNext();
                    gInfo.setYearTo(c.getInt(c.getColumnIndex("value")));
                    c.moveToNext();
                    gInfo.setTerm(c.getInt(c.getColumnIndex("value")));
                    c.moveToNext();
                    gInfo.setFirstUse(c.getInt(c.getColumnIndex("value")));
                    c.moveToNext();
                    gInfo.setActiveUserUid(c.getInt(c.getColumnIndex("value")));
                }
                return gInfo;
            } catch (Exception e) {
                return null;
            }

        }
}


