package net.wash8.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by ncb-user on 2014/12/23.
 */
public class MyCarDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "mycardata.db";

    private static final String USER_ID = "UserID";
    private static final String NUMBER = "Number";
    private static final String BRAND = "Brand";
    private static final String MODEL = "Model";
    private static final String IMAGE = "Image";
    private static final String COLOR = "Color";
    private static final String ID = "_id";
    private static final String CAR_ID = "CAR_ID";

    private static final String MOBILE="Mobile";
    private static final String AUTHCODE="AuthCode";
    private static final String LASTNAME="LastName";
    private static final String GENDER="Gender";
    private static final String JOB="Job";
    private static final String WEIXIN="Weixin";
    private static final String BALANCE="Balance";
    private static final String APPVERSION="AppVersion";
    private static final String CREATEDDATETIME="CreatedDateTime";
    private static final String USERINFOID="ID";
    private static final String NOTORDERED="NotOrdered";

    private static final int DATABASE_VERSION = 1;
    private Context con;

    public MyCarDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.con = context;
    }

    @Override
    public void onCreate(SQLiteDatabase dp) {
        String sql = "CREATE TABLE IF NOT EXISTS mycar (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + CAR_ID + " INTEGER," +
                USER_ID + " INTEGER," + NUMBER + " VARCHAR," +
                BRAND + " VARCHAR," + MODEL + " VARCHAR," + IMAGE + " VARCHAR," + COLOR + " VARCHAR)";
        dp.execSQL(sql);
        String sql1 = "CREATE TABLE IF NOT EXISTS userinfo (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + USERINFOID +
                " INTEGER," + MOBILE + " VARCHAR," + AUTHCODE + " VARCHAR," +
                LASTNAME + " VARCHAR," + GENDER + " VARCHAR," + NOTORDERED+" VARCHAR,"+ JOB + " VARCHAR," + WEIXIN +
                " VARCHAR," +BALANCE +
                " VARCHAR,"+APPVERSION+" VARCHAR,"+CREATEDDATETIME+" VARCHAR)";
        dp.execSQL(sql1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean onInsert(SQLiteDatabase db, String UserID, String CAR_ID, String Number, String Brand, String Model,
                            String Image, String Color) {
        ContentValues values = new ContentValues();
        values.put("UserID", UserID);
        values.put("CAR_ID", CAR_ID);
        values.put("Number", Number);
        values.put("Brand", Brand);
        values.put("Model", Model);
        values.put("Image", Image);
        values.put("Color", Color);
        if (db.insert("mycar", null, values) > 0) {
            return true;
        } else {
            Toast.makeText(con, "添加失败", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
    public boolean onInsertUserInfo(SQLiteDatabase db, String Mobile, String AuthCode, String LastName, String Gender, String
            Job, String Weixin, String Balance,String AppVersion,String CreatedDateTime,String ID,String NotOrdered){
        ContentValues values = new ContentValues();
        values.put("Mobile", Mobile);
        values.put("AuthCode", AuthCode);
        values.put("LastName", LastName);
        values.put("Gender", Gender);
        values.put("Job", Job);
        values.put("Weixin", Weixin);
        values.put("Balance", Balance);
        values.put("AppVersion", AppVersion);
        values.put("CreatedDateTime", CreatedDateTime);
        values.put("ID", ID);
        values.put("NotOrdered",NotOrdered);
        if (db.insert("userinfo", null, values) > 0) {
            return true;
        } else {
            Toast.makeText(con, "添加失败", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public void onDelete(SQLiteDatabase db, int CarID) {
        String sql = "delete from mycar " + "where CAR_ID=" + CarID;
        db.execSQL(sql);
    }

    public void onDeleteAll(SQLiteDatabase db) {
        String sql = "delete from mycar";
        db.execSQL(sql);
    }
    public void onDeleteAll_UserInfo(SQLiteDatabase db) {
        String sql = "delete from userinfo";
        db.execSQL(sql);
    }

    public Cursor onQuery(SQLiteDatabase db, String USER_ID, String CAR_ID, String Number, String Brand, String Model, String
            Image, String Color) {
        Cursor cursor = db.query("mycar", new String[]{USER_ID, CAR_ID, Number, Brand, Model, Image, Color
        }, null, null, null, null, null);
        return cursor;
    }
    public Cursor onQuery_UserInfo(SQLiteDatabase db, String Mobile, String AuthCode, String LastName, String Gender,
                                   String Job, String Weixin, String Balance,String AppVersion,String
            CreatedDateTime,String ID,String NotOrdered){
        Cursor cursor = db.query("userinfo", new String[]{Mobile, AuthCode, LastName, Gender, Job, Weixin, Balance,
                 AppVersion, CreatedDateTime, ID, NotOrdered}, null, null, null, null, null);
        return cursor;
    }

    public boolean hasRegister(){
        SQLiteDatabase database=this.getReadableDatabase();
        Cursor cursor=database.rawQuery("select ID from userinfo", null);

        return cursor.getCount() > 0;
    }
}
