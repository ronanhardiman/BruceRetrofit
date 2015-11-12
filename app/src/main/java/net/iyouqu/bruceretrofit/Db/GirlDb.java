package net.iyouqu.bruceretrofit.Db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import net.iyouqu.bruceretrofit.Bean.Girl;
import net.iyouqu.bruceretrofit.Db.table.GirlTable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by q on 2015/9/28.
 */
public class GirlDb {
	private MySqliteHelper mySqliteHelper = null;
	private Context mContext;
	private static GirlDb mGirlDb;
	private static final String SQL_CREATE_GIRL = "CREATE TABLE "+ GirlTable.GIRL_TABLE +"("
			+ GirlTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT , "
			+ GirlTable.WHO + " VARCHAR(25) , "
			+ GirlTable.DATE_PUBLISHEDAT + " TEXT , "
			+ GirlTable.DATE_CREATEDAT + " TEXT , "
			+ GirlTable.DATE_UPDATEDAT + " TEXT , "
			+ GirlTable.DESC + " VARCHAR(250) , "
			+ GirlTable.URL + " VARCHAR(250) , "
			+ GirlTable.TYPE + " VARCHAR(50) , "
			+ GirlTable.USED + " INTEGER DEFAULT 0 , "
			+ GirlTable.OBJECT_ID + " VARCHAR(25) UNIQUE ON CONFLICT REPLACE , "
			+ GirlTable.INSERT_TIME + " long "
			+ ");";
	// UNIQUE ON CONFLICT REPLACE
	// 用 UNIQUE object_id 重复 会异常
	private static final String DATABASE_NAME = "girl.db";
	private static final int version = 1;
	private Object date;

	private GirlDb(Context mContext) {
		this.mContext = mContext;
		this.mySqliteHelper = MySqliteHelper.getMySqliteHelper(mContext);
	}

	public synchronized static GirlDb getInstance(Context mContext) {
		if (mGirlDb == null) {
			mGirlDb = new GirlDb(mContext);
		}
		return mGirlDb;
	}

	//
	public boolean addGirl(Girl girl){
		long result = -1;
		SQLiteDatabase db = null;
		try {
			db = mySqliteHelper.getWritableDatabase();
			ContentValues values = new ContentValues();
			values.put(GirlTable.DESC, girl.desc);
			values.put(GirlTable.OBJECT_ID, girl.objectId);
			values.put(GirlTable.TYPE, girl.type);
			values.put(GirlTable.URL, girl.url);
			values.put(GirlTable.USED, girl.used ? 1 : 0);
			values.put(GirlTable.WHO, girl.who);
			values.put(GirlTable.DATE_CREATEDAT, girl.createdAt);
			values.put(GirlTable.DATE_PUBLISHEDAT, girl.publishedAt);
			values.put(GirlTable.DATE_UPDATEDAT, girl.updatedAt);
			values.put(GirlTable.INSERT_TIME, System.currentTimeMillis());
			result = db.insert(GirlTable.GIRL_TABLE, null ,values);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (db != null) {
				try {
					db.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}
		return result != -1;
	}

	public boolean addGirlList(List<Girl> girls){
		long result = -1;
		SQLiteDatabase db = null;
		int count = 0;
		final int size = girls.size();
		try {
			db = mySqliteHelper.getWritableDatabase();
			db.beginTransaction();
			for (int i = 0; i < size; i++) {
				Girl girl = girls.get(i);
				ContentValues values = new ContentValues();
				values.put(GirlTable.DESC, girl.desc);
				values.put(GirlTable.OBJECT_ID, girl.objectId);
				values.put(GirlTable.TYPE, girl.type);
				values.put(GirlTable.URL, girl.url);
				values.put(GirlTable.USED, girl.used ? 1 : 0);
				values.put(GirlTable.WHO, girl.who);
				values.put(GirlTable.DATE_CREATEDAT, girl.createdAt);
				values.put(GirlTable.DATE_PUBLISHEDAT, girl.publishedAt);
				values.put(GirlTable.DATE_UPDATEDAT, girl.updatedAt);
				values.put(GirlTable.INSERT_TIME, System.currentTimeMillis());
				result = db.insert(GirlTable.GIRL_TABLE, null, values);
				if (result != -1) {
					count++;
				}
			}
			db.setTransactionSuccessful();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if (db != null) {
				try {
					db.endTransaction();
					db.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}
		return count > 0;
	}

	public ArrayList<Girl> getGirlList() {
		SQLiteDatabase db = null;
		Cursor c = null;
		ArrayList<Girl> list = new ArrayList<>();
		try {
			db = mySqliteHelper.getReadableDatabase();
			c = db.query(GirlTable.GIRL_TABLE, null, null, null, null, null, null);
			Girl girl;
			for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
				girl = new Girl();
				getGirlFromDB(girl, c);
				list.add(girl);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (c != null) {
					c.close();
				}
				if (db != null) {
					db.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

		return list;
	}

	private final void getGirlFromDB(Girl girl, Cursor c) {
		girl.objectId = c.getString(GirlTable.OBJECT_ID_INDEX);
		girl.who = c.getString(GirlTable.WHO_INDEX);
		girl.url = c.getString(GirlTable.URL_INDEX);
		girl.desc = c.getString(GirlTable.DESC_INDEX);
		girl.type = c.getString(GirlTable.TYPE_INDEX);
		girl.createdAt = c.getString(GirlTable.DATE_CREATEDAT_INDEX);
		girl.publishedAt = c.getString(GirlTable.DATE_PUBLISHEDAT_INDEX);
		girl.updatedAt = c.getString(GirlTable.DATE_UPDATEDAT_INDEX);
		girl.used = c.getInt(GirlTable.USED_INDEX) != 0 ? true : false;
		girl.insert_time = c.getLong(GirlTable.INSERT_TIME_INDEX);
	}

	public String getDate() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		return dateFormat.format(date);
	}

	private static class MySqliteHelper extends SQLiteOpenHelper{
		private static MySqliteHelper mySqliteHelper;
		private final static Object mdb_lock = new Object();

		public static MySqliteHelper getMySqliteHelper(Context mContext) {
			if (mySqliteHelper == null) {
				synchronized (mdb_lock) {
					if (mySqliteHelper == null) {
						mySqliteHelper = new MySqliteHelper(mContext);
					}
				}
			}
			return mySqliteHelper;
		}

		private MySqliteHelper(Context mContext){
			super(mContext,DATABASE_NAME,null,version);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(SQL_CREATE_GIRL);
		}

		@Override
		public void onConfigure(SQLiteDatabase db) {
			super.onConfigure(db);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		}
	}
}
