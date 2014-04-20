package com.example.kobetikdb;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Adapter;
import android.widget.ArrayAdapter;

public class KobetikSQLite extends SQLiteOpenHelper {

    private final Context myContext;
    static String DB_ADI = "test.db"; //Kaynak veritabaný
    static final int DB_VERSION = 1;
    static String ISIM = "com.example.kobetikdb"; //Uygulamanýzdaki paket adý
    @SuppressLint("SdCardPath")
	private static String DB_YOLU = "/data/data/"+ISIM+"/databases/"; //Veritabanýnýn kopyalanacaðý hedef adres 

    private SQLiteDatabase myDataBase; 
    
 
    public KobetikSQLite(Context context) {
 
        super(context, DB_ADI, null, 1);
        this.myContext = context;
    }    



	public void DBOlustur() throws IOException{
 
        boolean dbExist = DBKontrol();
 
        if(dbExist){

        }else{
 
this.getReadableDatabase();
 
            try {
 
                DBKopyala();
 
            } catch (IOException e) {
 
                throw new Error("Veritabaný Kopyalama Hatasý");
          
 
            }
        }
 
    }
 
    private boolean DBKontrol(){
 
        SQLiteDatabase checkDB = null;
 
        try{
            String myPath = DB_YOLU + DB_ADI;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
 
        }catch(SQLiteException e){
 
        }
 
        if(checkDB != null){
 
            checkDB.close();
 
        }
 
        return checkDB != null ? true : false;
    }
 
    private void DBKopyala() throws IOException{
 
InputStream myGirdi = myContext.getAssets().open(DB_ADI);
 
        String ciktiIsmi = DB_YOLU + DB_ADI;
 
        OutputStream myCikti = new FileOutputStream(ciktiIsmi);
 
byte[] buffer = new byte[1024];
        int uzunluk;
        while ((uzunluk = myGirdi.read(buffer))>0){
            myCikti.write(buffer, 0, uzunluk);
            Log.d("DBHELPER","1024 byte Kopyalandi");
        }

        myCikti.flush();
        myCikti.close();
        myGirdi.close();
 
    }
 
    public void DBAc() throws SQLException{

        String myYol = DB_YOLU + DB_ADI;
        myDataBase = SQLiteDatabase.openDatabase(myYol, null, SQLiteDatabase.OPEN_READONLY);
 
    }
    public void baglan() {
		try {	                 
			DBOlustur();
		     } catch (IOException ioe) {	     
		    throw new Error("DB Oluþturulamadý");
		         }		     
		         try {
		            DBAc();  
		         }catch(SQLException sqle){   
		             throw new Error("DB Açýlamadý"); 
		            
		 }  
	}
 
    @Override
    public synchronized void close() {
 
            if(myDataBase != null)
                myDataBase.close();
 
            super.close();
 
    }
 
    @Override
    public void onCreate(SQLiteDatabase db) {
 
    }
 
    @Override
    public void onUpgrade(SQLiteDatabase db, int eskiversiyon, int yeniversiyon) {
 
    }

	public Cursor query(String query) {
		String selectAll = null;

		if(query.length() > 1){

	    selectAll = query;

	   
	}
		 return myDataBase.rawQuery(selectAll, null);
	}
	public Cursor sorgu(String sorgu) {
		SQLiteDatabase db = this.getWritableDatabase();
	    Cursor cursor = db.rawQuery(sorgu, null);
	   
	    return cursor;
	}
	public void sorgu2(String sorgu) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL(sorgu);
	}
	public void ekle(String[] dizi) {
		
	}
	public String sutun(Cursor c, int index) {
		return c.getString(index).toString();
	}
	
}
