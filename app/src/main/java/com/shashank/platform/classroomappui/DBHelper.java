package com.shashank.platform.classroomappui;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "MyDBName.db";
    public static final String DAILY_SCHEDULE_TABLE_NAME = "daily_schedule";
    public static final String DAILY_SCHEDULE_COLUMN_ID = "id";
    public static final String DAILY_SCHEDULE_CONTENTS_COLUMN = "dscontents";
    public static final String DAILY_SCHEDULE_COLUMN_PROF_NAME = "profName";
    public static final String DAILY_SCHEDULE_TABLE_COURSE_TITLE = "courseName";
    public static final String DAILY_SCHEDULE_TABLE_TIMINGS = "timings";
    public static final String DAILY_SCHEDULE_TABLE_DATE = "date";
    public static final String DAILY_SCHEDULE_TABLE_ROOM_NO = "roomNo";


    public static final String ANNOUNCEMENT_TABLE_NAME = "announcements";
    public static final String ANNOUNCEMENT_COLUMN_ID = "id_a";
    public static final String ANNOUNCEMENT_COLUMN_TITLE = "title";
    public static  final String ANNOUNCEMENT_COLUMN_CONTENT = "content";
    public static final String ANNOUNCEMENT_COLUMN_TIME = "time";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 2);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table daily_schedule" +
                        "(id integer primary key, profName String,courseName String,timings String,date String,roomNo String)"
        );
        db.execSQL(
                "create table announcements" +
                  "(id_a integer primary key, title String,content String,time String)");
//        db.execSQL(
//                "create table daily_schedlule" +
//                        "(id integer primary key,complete_info dailySchedule)"
//        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int old_ver, int new_ver) {
        db.execSQL("DROP TABLE IF EXISTS daily_schedule");
        db.execSQL("DROP TABLE IF EXISTS announcements");
        onCreate(db);
    }

    public boolean insertAnnouncement(String title,String content,String time ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("title",title);
        contentValues.put("content",content);
        contentValues.put("time",time);
        db.insert("announcements",null,contentValues);
        return true;
    }

    public boolean insertDailySchedule(String pname, String cname, String tim, String date, String roomNo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("profName", pname);
        contentValues.put("courseName", cname);
        contentValues.put("timings", tim);
        contentValues.put("date", date);
        contentValues.put("roomNo", roomNo);
        db.insert("daily_schedule", null, contentValues);

        return true;
    }

    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("select * from daily_schedule where id=" + id + "", null);
    }

    public int numberOfRows() {
        SQLiteDatabase db = this.getReadableDatabase();
        return (int) DatabaseUtils.queryNumEntries(db, ANNOUNCEMENT_TABLE_NAME);
    }
    

    public boolean updateDailySchedule(Integer id, String pname, String cname, String tim, String date, String roomNo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("profName", pname);
        contentValues.put("courseName", cname);
        contentValues.put("timings", tim);
        contentValues.put("date", date);
        contentValues.put("roomNo", roomNo);
        db.update("daily_schedule", contentValues, "id = ?", new String[]{Integer.toString(id)});
        return true;
    }

    public ArrayList<Announcement> getAllAnnouncements(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Announcement> res = new ArrayList<>();
        Cursor c = db.rawQuery("select * from announcements",null);
        c.moveToFirst();
        while(!c.isAfterLast()){
            Announcement temp = new Announcement();
            temp.setContent(c.getString(c.getColumnIndex(ANNOUNCEMENT_COLUMN_CONTENT)));
            temp.setTitle(c.getString(c.getColumnIndex(ANNOUNCEMENT_COLUMN_TITLE)));
            temp.setTime(c.getString(c.getColumnIndex(ANNOUNCEMENT_COLUMN_TIME)));
            res.add(temp);
            c.moveToNext();
        }
        return res;
    }

    public ArrayList<dailySchedule> getAllDailySchedules() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<dailySchedule> lis = new ArrayList<>();
//        String[] columns = new String[]{DAILY_SCHEDULE_COLUMN_ID,DAILY_SCHEDULE_CONTENTS_COLUMN,DAILY_SCHEDULE_COLUMN_PROF_NAME,DAILY_SCHEDULE_TABLE_COURSE_TITLE,
//                DAILY_SCHEDULE_TABLE_TIMINGS,DAILYCHEDULE_TABLE_DATE,DAILY_SCHEDULE_TABLE_ROOM_NO};
////        Cursor res = db.query(DAILY_SCHEDULE_TABLE_NAME,columns,null,null,null,null, null);
        Cursor res = db.rawQuery("select * from daily_schedule", null);
        res.moveToFirst();
        int count = 0;
        while (!res.isAfterLast()) {
            res.moveToNext();
            count++;
        }


        res.moveToFirst();
        dailySchedule[] ret = new dailySchedule[count];
        for (int i = 0; i < count; i++) {
            ret[i] = new dailySchedule();
            ret[i].setProfessor_name(res.getString(res.getColumnIndex(DAILY_SCHEDULE_COLUMN_PROF_NAME)));

            res.moveToNext();
        }

        res.moveToFirst();
        for (int i = 0; i < count; i++) {
            ret[i].setCourseName(res.getString(res.getColumnIndex(DAILY_SCHEDULE_TABLE_COURSE_TITLE)));
            res.moveToNext();
        }

        res.moveToFirst();
        for (int i = 0; i < count; i++) {
            ret[i].setFrom_to(res.getString(res.getColumnIndex(DAILY_SCHEDULE_TABLE_TIMINGS)));
            res.moveToNext();
        }

        res.moveToFirst();
        for (int i = 0; i < count; i++) {
            ret[i].setDate(res.getString(res.getColumnIndex(DAILY_SCHEDULE_TABLE_DATE)));
            res.moveToNext();
        }

        res.moveToFirst();
        for (int i = 0; i < count; i++) {
            ret[i].setFrom_to(res.getString(res.getColumnIndex(DAILY_SCHEDULE_TABLE_TIMINGS)));
            res.moveToNext();
        }
        res.moveToFirst();
        for (int i = 0; i < count; i++) {
            ret[i].setClassN0(res.getString(res.getColumnIndex(DAILY_SCHEDULE_TABLE_ROOM_NO)));
            res.moveToNext();
        }
//        while (!res.isAfterLast()) {
//            dailySchedule temp = new dailySchedule();
//            temp.setCourseName(res.getString(res.getColumnIndex(DAILY_SCHEDULE_TABLE_COURSE_TITLE)));
//            temp.setProfessor_name(res.getString(res.getColumnIndex(DAILY_SCHEDULE_COLUMN_PROF_NAME)));
//            temp.setClassN0(res.getString(res.getColumnIndex(DAILY_SCHEDULE_TABLE_ROOM_NO)));
//            temp.setDate(res.getString(res.getColumnIndex(DAILY_SCHEDULE_TABLE_DATE)));
//            temp.setFrom_to(res.getString(res.getColumnIndex(DAILY_SCHEDULE_TABLE_TIMINGS)));
//            lis.add(temp);
//
//        }
        for (int i = 0; i < count; i++) lis.add(ret[i]);
        return lis;
    }

    public int deleteDailySchedule(Integer id,Context context) {

        SQLiteDatabase db = this.getWritableDatabase();
        Toast.makeText(context, "Id is "+id, Toast.LENGTH_SHORT).show();
        return db.delete("daily_schedule", "id = ? ", new String[]{Integer.toString(id)});
    }
    public int deleteAnnouncement(String id_,Context context)
    {
        SQLiteDatabase db =this.getWritableDatabase();
        return db.delete("announcements","id_a = ? ",new String[]{id_});
    }


    public String getIdOfAnnouncement(Announcement a,Context context){
        SQLiteDatabase db  = this.getReadableDatabase();
        Cursor c= db.rawQuery("select * from announcements",null);
        c.moveToFirst();
        while(!c.isAfterLast()){
            if(c.getString(c.getColumnIndex(ANNOUNCEMENT_COLUMN_TITLE)).equals(a.getTitle()) && c.getString(c.getColumnIndex(ANNOUNCEMENT_COLUMN_TIME)).equals(a.getTime())){
                return c.getString(c.getColumnIndex(ANNOUNCEMENT_COLUMN_ID));
            }
        }
        return "-1";
    }

    public Integer getIdOfSchedule(dailySchedule ds,Context context) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from daily_schedule", null);
        res.moveToFirst();
        while (!res.isAfterLast()) {
            if (res.getString(res.getColumnIndex(DAILY_SCHEDULE_TABLE_COURSE_TITLE)).equals(ds.getCourseName()) &&
     res.getString(res.getColumnIndex(DAILY_SCHEDULE_COLUMN_PROF_NAME)).equals(ds.getProfessor_name()) &&
            res.getString(res.getColumnIndex(DAILY_SCHEDULE_TABLE_ROOM_NO)).equals(ds.getClassN0())
            ){
                return Integer.parseInt(res.getString(res.getColumnIndex(DAILY_SCHEDULE_COLUMN_ID)));
            }
            res.moveToNext();
        }

        Toast.makeText(context, "Failed to get the id", Toast.LENGTH_SHORT).show();
        return -1;
    }
}
