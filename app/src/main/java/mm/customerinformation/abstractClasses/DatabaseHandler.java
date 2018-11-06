package mm.customerinformation.abstractClasses;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;




import java.util.ArrayList;


import mm.customerinformation.dataStructure.infoDS;

/**
 * Created by Az on 2017-08-02.
 */

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "CustInfo.db";
    private SQLiteDatabase db = null;
    private MyApplication mApplication;


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mApplication = (MyApplication) context.getApplicationContext();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_Item_table = "";
        try {


            CREATE_Item_table = "CREATE TABLE Info ( Id  INTEGER PRIMARY KEY , Name TEXT , Phone TEXT  ,Descp TEXT, RegNo TEXT,CreateDate TEXT, EntryNo TEXT)";
            db.execSQL(CREATE_Item_table);


        } catch (Exception e) {

            Log.e("db onCreate", e.getMessage());

        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + "Info");


        // Create tables again
        onCreate(db);
    }

    //////////Generic Function


    public ArrayList<infoDS> LoadCustomers(String DocType) {

        String query = "";
        SQLiteDatabase sql = null;

        ArrayList<infoDS> arraylist = new ArrayList<infoDS>();

        try {

            sql = this.getReadableDatabase();

            if (!DocType.equals("")){
                query = "select Id,Name,Phone,Descp,RegNo,CreateDate,EntryNo from Info "+"where Phone = '"+ DocType +"'" +"order by Id ";
            }else {
                query = "select Id,Name,Phone,Descp,RegNo,CreateDate,EntryNo from Info order by Id ";
            }


            Cursor c = sql.rawQuery(query, null);
            while (c.moveToNext()) {
                infoDS infoD=new infoDS();

                infoD.id=c.getInt(0);
                infoD.Name=c.getString(1);
                infoD.Phone=c.getString(2);
                infoD.Descp=c.getString(3);
                infoD.RegNo=c.getString(4);
                infoD.Date=c.getString(5);
                infoD.EntryNo=c.getString(6);

                arraylist.add(infoD);
            }

            c.close();
            return arraylist;
        } catch (Exception e) {
            Log.e("Database LoadWHS", e.getMessage());
            return arraylist;
        } finally {
            if (sql != null) {
                sql.close();
            }
        }
    }



    public boolean AddCustomer(infoDS ds) {
        try {

             String query = "INSERT INTO Info " + " VALUES ( ?,? , ? , ? , ? ,?,?);";

             db = this.getWritableDatabase();

             SQLiteStatement statement = db.compileStatement(query);

             db.beginTransaction();

             statement.clearBindings();

             statement.bindString(2, checkNull(ds.Name));
             statement.bindString(3, checkNull(ds.Phone));
             statement.bindString(4, checkNull(ds.Descp));
             statement.bindString(5, checkNull(ds.RegNo));
             statement.bindString(6, MyApplication.getShortdate() );
            statement.bindString(7, checkNull(ds.EntryNo));

             statement.execute();



            db.setTransactionSuccessful();

            Log.d("TransactionEnd", "2");

            return true;
        } catch (Exception e) {
            Log.e("insertbulk_OWHs", e.getMessage());
            return false;
        } finally {
            try {
                db.endTransaction();
            } catch (Exception e) {
                Log.e("Errr_de1", e.getMessage());
            }
            try {
                db.close();
            } catch (Exception e) {
                Log.e("Errr_de2", e.getMessage());
            }
        }
        }



    public boolean DeleteCustomer(int id) {
        try {

            String query = "DELETE from Info where Id=" + id + ";";

            db = this.getWritableDatabase();

            db.execSQL(query);

            Log.d("TransactionEnd", "2");

            return true;
        } catch (Exception e) {
            Log.e("insertbulk_OWHs", e.getMessage());
            return false;
        } finally {
            try {
            } catch (Exception e) {
                Log.e("Errr_de1", e.getMessage());
            }
            try {
                db.close();
            } catch (Exception e) {
                Log.e("Errr_de2", e.getMessage());
            }
        }
    }

    private String checkNull(String data) {
        try {
            if (data == null) {
                return "";
            }
            return data;
        } catch (Exception e) {
            return "";
        }
    }
    ///
}
