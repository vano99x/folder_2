package ta.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Arrays;

import ta.lib.*;
import ta.lib.operator.*;

public class DbConnector
{
	//private static final String DATABASE_NAME = "ta.db";
	//private static final int DATABASE_VERSION = 1;
	private static DbConnector instance = null;
	private OpenHelper openHelper;
	private SQLiteDatabase mDataBase;

	private DbConnector(Context paramContext)
	{
		this.Clear();

		OpenHelper openHelper = new OpenHelper(paramContext);
		this.mDataBase = openHelper.getWritableDatabase();

		CheckTable(openHelper, "Checkin",  openHelper.CheckinColumnItems());
		CheckTable(openHelper, "Personel", openHelper.PersonelColumnItems());
		CheckTable(openHelper, "SettingSv", openHelper.SettingSvColumnItems());

		int aaa = 9;
	}
	public static DbConnector getInstance()
	{
		return DbConnector.instance;
	}
	public static void CreateInstance(Context paramContext)
	{
		instance = new DbConnector(paramContext);
	}
	public static void DeleteInstance()
	{
		DbConnector.instance = null;
	}
	public void Clear()
	{
		if(this.mDataBase != null)
		this.mDataBase.close();

		if(this.openHelper != null)
		this.openHelper.close();

		this.openHelper = null;
		this.mDataBase = null;
	}





	public Cursor GetEntity(String paramString1, String paramString2, String[] paramArrayOfString)
	{
		Cursor localCursor = this.mDataBase.query(paramString1, null, paramString2 + " =?", paramArrayOfString, null, null, null);
		localCursor.moveToFirst();
		return localCursor;
	}


	// C
	public long insert(String str, ContentValues cv)
	{
		return this.mDataBase.insert(str, null, cv);
	}
	public void exec(String sql)
	{
		this.mDataBase.execSQL(sql);
	}

	//R
	public Cursor Select(String str, String[] strArr)
	{
		Cursor cursor = this.mDataBase.rawQuery(str, strArr);
		cursor.moveToFirst();
		return cursor;
	}

	//U

	//D
	public Object delete(String str1, String str2, String str3)
	{
		return this.mDataBase.delete(str1, str2 + " = ?", new String[] { str3 });
	}
	public Object delete(String str1, String str2, String[] strArr)
	{
		return this.mDataBase.delete(str1, str2, strArr);
	}


	private class OpenHelper 
		extends SQLiteOpenHelper
	{
		public OpenHelper(Context context)
		{
			super(context, "ta.db", null, 1);
		}

		public ColumnItem [] CheckinColumnItems()
		{
			ColumnItem [] arr = new ColumnItem[]
			{
				new ColumnItem("Id",           "INTEGER", "PRIMARY KEY AUTOINCREMENT NOT NULL"),
				new ColumnItem("SupervicerId", "INTEGER", null),
				new ColumnItem("WorkerId",     "INTEGER", null),
				new ColumnItem("CardId",       "INTEGER", null),
			  //new ColumnItem("IsSupervisor", "INTEGER", null),
				new ColumnItem("Mode",         "INTEGER", null),
				new ColumnItem("PointId",      "INTEGER", null),
				new ColumnItem("DateTime",     "TEXT",    null),
				new ColumnItem("IsCheckinExistOnServer",  "INTEGER", null),
				new ColumnItem("StateCheckinOnServer",    "INTEGER", null)
			};
			return arr;
		}
		public ColumnItem [] PersonelColumnItems()
		{
			ColumnItem [] arr = new ColumnItem[]
			{
				new ColumnItem("Id",            "INTEGER", null),
				new ColumnItem("FirstName",     "TEXT",    null),
				new ColumnItem("LastName",      "TEXT",    null),
				new ColumnItem("ThirdName",     "TEXT",    null),

				new ColumnItem("IsSupervisor",  "INTEGER", null),
				new ColumnItem("Pin",           "TEXT",    null),
				new ColumnItem("PersonelCode",  "INTEGER", null),
				new ColumnItem("CardId",        "TEXT",    null),
				new ColumnItem("PhotoTimeSpan", "TEXT",    null),
				new ColumnItem("IsDismiss",     "INTEGER", null)
			};
			return arr;
		}
		public ColumnItem [] SettingSvColumnItems()
		{
			ColumnItem [] arr = new ColumnItem[]
			{
				new ColumnItem("Id",      "INTEGER", null),
				new ColumnItem("PointId", "INTEGER", null)
			};
			return arr;
		}

		public String CreateSql( String tableName, ColumnItem [] arr)
		{
			//ColumnItem [] arr = CheckinColumnItems();
			int count = arr.length;
			int last = count - 1;

			StringBuilder sb = new StringBuilder();
			sb.append("CREATE TABLE " + tableName + " \n");
			sb.append("( \n");
			for( int i = 0; i < count; i++)
			{
				sb.append(arr[i].name);
				sb.append(" ");
				sb.append(arr[i].type);
				if(arr[i].attr != null)
				{
					sb.append(" ");
					sb.append(arr[i].attr);
				}

				if(last == i) {
					sb.append(" \n");
				} else {
					sb.append(", \n");
				}
			}
			sb.append("); \n");

			return sb.toString();
		}

		public void onCreate(SQLiteDatabase paramSQLiteDatabase)
		{
			int aaa = 9;

//			paramSQLiteDatabase.execSQL(
//				"CREATE TABLE Personel      (Id INTEGER, FirstName TEXT, LastName TEXT, ThirdName TEXT, IsSupervisor INT, PersonelCode INT, CardId TEXT, PhotoTimeSpan TEXT); ");
			String strPl = CreateSql("Personel", this.PersonelColumnItems());
			paramSQLiteDatabase.execSQL(strPl);

			paramSQLiteDatabase.execSQL(
				"CREATE TABLE Point         (Id INTEGER, Name TEXT); ");
			paramSQLiteDatabase.execSQL(
				"CREATE TABLE PersonelPoint (PersonelId INTEGER, PointId INTEGER);");
//            paramSQLiteDatabase.execSQL(
//"CREATE TABLE Checkin                              "+
//"(                                                 "+
//"	Id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "+
//"	SupervicerId INTEGER,                          "+
//"	PersonelCode INTEGER,                          "+
//"	CardId       INTEGER,                          "+
//"	Mode         INTEGER,                          "+
//"	PointId      INTEGER,                          "+
//"	DateTime     TEXT                              "+
//");                                                "
//);
			String strCh = CreateSql("Checkin",  this.CheckinColumnItems());
			paramSQLiteDatabase.execSQL(strCh);
		}

		public void onUpgrade(SQLiteDatabase paramSQLiteDatabase, int paramInt1, int paramInt2)
		{
			paramSQLiteDatabase.execSQL("DROP TABLE IF EXISTS Personel");
			paramSQLiteDatabase.execSQL("DROP TABLE IF EXISTS Point");
			paramSQLiteDatabase.execSQL("DROP TABLE IF EXISTS PersonelPoint");
			paramSQLiteDatabase.execSQL("DROP TABLE IF EXISTS Checkin");
			onCreate(paramSQLiteDatabase);
		}
	}


	class RowInfo
	{
		//public String type;
		String name;
		int valueInt;
		String valueStr;
	}


	class ColumnItem
	{
		public String name;
		public String type;
		public String attr;

		public ColumnItem( String name, String type, String attr)
		{
			this.name = name;
			this.type = type;
			this.attr = attr;
		}
	}









	public void CheckTable(OpenHelper openHelper, String tableName, ColumnItem [] entityPropArr)
	{
		if(IsTableExists(tableName) == false)
		{
			String sql = openHelper.CreateSql( tableName, entityPropArr);
			this.mDataBase.execSQL(sql);
		}
		else
		{
			Cursor dbCursor = this.mDataBase.query(tableName, null, null, null, null, null, null);
			String[] columnNames = dbCursor.getColumnNames();
			ArrayList<String> colums = new ArrayList<String>(Arrays.asList(columnNames));
			boolean Remove = false;
			boolean isConteins = false;

			// check existing in db declared coluns
			int count = entityPropArr.length;
			for( int i = 0; i < count; i++)
			{
				String nameProp = entityPropArr[i].name;

				isConteins = colums.contains(nameProp);
				if(isConteins == false)
				{
					Remove = true;
					break;
				}
			}

			int aaa = 9;
			int aaa2 = aaa - 2;

			//ArrayList<ColumnItem> entityPropList = new ArrayList<ColumnItem>(Arrays.asList(entityPropArr));
			ArrayList<String> entityPropNames = 
				operator.Where(
					entityPropArr,
					new WhereRunnable<ColumnItem,String>() { public String run(ColumnItem item)
					{   return item.name;   }}
				);
			count = colums.size();
			for( int i = 0; i < count; i++)
			{
			    String nameColum = colums.get(i);

			    isConteins = entityPropNames.contains(nameColum);
			    if(isConteins == false)
			    {
			        Remove = true;
					break;
			    }
			}

			if(Remove == true)
			{
				this.mDataBase.execSQL("DROP TABLE IF EXISTS " + tableName);
				String sql = openHelper.CreateSql( tableName, entityPropArr);
				this.mDataBase.execSQL(sql);
			}
		}
	}

	public boolean IsTableExists(String tableName)
	{
		Cursor cursor = Select("SELECT name FROM sqlite_master WHERE type='table' AND name='" + tableName + "'", null);

		boolean result = false;
		if(cursor != null)
		{
			if(cursor.getCount()>0)
			{
				result = true;
			}
			cursor.close();
		}

		return result;
	}

	public RowInfo [] GetColumnInfoArray(String sql)
	{
		RowInfo [] ciArr = null;
		Cursor cursor = Select(sql, null);//"SELECT * FROM sqlite_master WHERE type='table' AND name='Checkin'"
		if(!cursor.isAfterLast())
		{
			int count = cursor.getColumnCount();
			ciArr = new RowInfo[count];

			for(int i = 0; i < count; i++)
			{
				ciArr[i] = new RowInfo();
				RowInfo ci = ciArr[i];

				String name = cursor.getColumnName(i);
				ci.name = name;

				ci.valueInt = cursor.getInt(i);
				ci.valueStr = cursor.getString(i);

				//int type = cursor.getType(i);
				//switch(type)
				//{
				//    case Cursor.FIELD_TYPE_INTEGER:{
				//        ci.type = "int";
				//        ci.value = cursor.getInt(i);
				//    break;}
				//    case Cursor.FIELD_TYPE_STRING:{
				//        ci.type = "tring";
				//        ci.value = cursor.getString(i);
				//    break;}
				//    case Cursor.FIELD_TYPE_NULL:{
				//        ci.type = "null";
				//    break;}
				//}
			}
			
		}
		return ciArr;
	}

}

/* Location:           C:\Users\vano99\Desktop\jd-gui-0.3.5.windows\TandAOffline_dex2jar.jar
 * Qualified Name:     com.ifree.Database.DbConnector
 * JD-Core Version:    0.6.2
 */