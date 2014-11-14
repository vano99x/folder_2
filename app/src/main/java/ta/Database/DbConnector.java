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
	private SQLiteDatabase _db;

	private DbConnector(Context paramContext)
	{
		this.Clear();

		OpenHelper openHelper = new OpenHelper(paramContext);
		this._db = openHelper.getWritableDatabase();

		CheckTable(openHelper, "Point",  openHelper.PointColumnItems());
		CheckTable(openHelper, "Checkin",  openHelper.CheckinColumnItems());
		CheckTable(openHelper, "Personel", openHelper.PersonelColumnItems());
		CheckTable(openHelper, "SettingSv", openHelper.SettingSvColumnItems());

		CheckTable(openHelper, "CustomerObject", openHelper.CustomerObjectColumnItems());
		CheckTable(openHelper, "Category", openHelper.CategoryColumnItems());
		CheckTable(openHelper, "Template", openHelper.TemplateColumnItems());

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
		if(this._db != null)
		this._db.close();

		if(this.openHelper != null)
		this.openHelper.close();

		this.openHelper = null;
		this._db = null;
	}





	public Cursor GetEntity(String paramString1, String paramString2, String[] paramArrayOfString)
	{
    /**
     * Query the given table, returning a {@link Cursor} over the result set.
     *
     * @param table The table name to compile the query against.
     * @param columns A list of which columns to return. Passing null will
     *            return all columns, which is discouraged to prevent reading
     *            data from storage that isn't going to be used.
     * @param selection A filter declaring which rows to return, formatted as an
     *            SQL WHERE clause (excluding the WHERE itself). Passing null
     *            will return all rows for the given table.
     * @param selectionArgs You may include ?s in selection, which will be
     *         replaced by the values from selectionArgs, in order that they
     *         appear in the selection. The values will be bound as Strings.
     * @param groupBy A filter declaring how to group rows, formatted as an SQL
     *            GROUP BY clause (excluding the GROUP BY itself). Passing null
     *            will cause the rows to not be grouped.
     * @param having A filter declare which row groups to include in the cursor,
     *            if row grouping is being used, formatted as an SQL HAVING
     *            clause (excluding the HAVING itself). Passing null will cause
     *            all row groups to be included, and is required when row
     *            grouping is not being used.
     * @param orderBy How to order the rows, formatted as an SQL ORDER BY clause
     *            (excluding the ORDER BY itself). Passing null will use the
     *            default sort order, which may be unordered.
     * @return A {@link Cursor} object, which is positioned before the first entry. Note that
     * {@link Cursor}s are not synchronized, see the documentation for more details.
     * @see Cursor
     */
		Cursor localCursor = this._db.query(paramString1, null, paramString2 + " =?", paramArrayOfString, null, null, null);
		localCursor.moveToFirst();
		return localCursor;
	}


	// C
	public long insert(String str, ContentValues cv)
	{
		return this._db.insert(str, null, cv);
	}
	public void exec(String sql)
	{
		this._db.execSQL(sql);
	}

	//R
	public Cursor Select(String str, String[] strArr)
	{
		Cursor cursor = this._db.rawQuery(str, strArr);
		//if(cursor.moveToFirst())
		    return cursor;
        //else
        //    return null;
	}

	//U

	//D
	public int delete(String str1, String str2, String str3)
	{
		return this._db.delete(str1, str2 + " = ?", new String[] { str3 });
	}
	public int delete(String str1, String str2, String[] strArr)
	{
/**
     * Convenience method for deleting rows in the database.
     *
     * @param table the table to delete from
     * @param whereClause the optional WHERE clause to apply when deleting.
     *            Passing null will delete all rows.
     * @param whereArgs You may include ?s in the where clause, which
     *            will be replaced by the values from whereArgs. The values
     *            will be bound as Strings.
     * @return the number of rows affected if a whereClause is passed in, 0
     *         otherwise. To remove all rows and get a count pass "1" as the
     *         whereClause.
     */
		return this._db.delete(str1, str2, strArr);
	}


	private class OpenHelper 
		extends SQLiteOpenHelper
	{
		public OpenHelper(Context context)
		{
			super(context, "ta.db", null, 1);
		}

		public ColumnItem [] PointColumnItems() { return new ColumnItem[] {
				new ColumnItem("Id",       "INTEGER", null),
				new ColumnItem("ObjectId", "INTEGER", null),
				new ColumnItem("Name",     "TEXT",    null)
		};}

		public ColumnItem [] CheckinColumnItems()
		{
			ColumnItem [] arr = new ColumnItem[]
			{
				new ColumnItem("CheckinId",    "INTEGER", "PRIMARY KEY AUTOINCREMENT NOT NULL"),
				new ColumnItem("SupervicerId", "INTEGER", null),									//sv
				new ColumnItem("WorkerId",     "INTEGER", null),									//w
				new ColumnItem("CardId",       "INTEGER", null),									//w
			  //new ColumnItem("IsSupervisor", "INTEGER", null),
				new ColumnItem("Mode",         "INTEGER", null),									//mode
				new ColumnItem("PointId",      "INTEGER", null),									//p
				new ColumnItem("DateTime",     "INTEGER",    null),									//time
				//new ColumnItem("DateTime",     "TEXT",    null),									//time
				new ColumnItem("CategoryId",   "INTEGER", null),
				new ColumnItem("TemplateId",   "INTEGER", null),

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
				new ColumnItem("PointId", "INTEGER", null),
				new ColumnItem("CategoryId", "INTEGER", null),
				new ColumnItem("TemplateId", "INTEGER", null)
			};
			return arr;
		}

		public ColumnItem [] CustomerObjectColumnItems()
		{
			ColumnItem [] arr = new ColumnItem[]
			{
				new ColumnItem("Id",      "INTEGER", null),
				new ColumnItem("Name",    "TEXT", null)
			};
			return arr;
		}

		public ColumnItem [] CategoryColumnItems()
		{
			ColumnItem [] arr = new ColumnItem[]
			{
				new ColumnItem("Id",      "INTEGER", null),//"PRIMARY KEY AUTOINCREMENT NOT NULL"
				new ColumnItem("ObjectId", "INTEGER", null),
				new ColumnItem("Name",    "TEXT", null)
			};
			return arr;
		}

		public ColumnItem [] TemplateColumnItems()
		{
			ColumnItem [] arr = new ColumnItem[]
			{
				new ColumnItem("Id",         "INTEGER", null),//"PRIMARY KEY AUTOINCREMENT NOT NULL"
				//new ColumnItem("PointId",    "INTEGER", null),
				new ColumnItem("CategoryId", "INTEGER", null),
				//new ColumnItem("Name",       "TEXT", null)
				new ColumnItem("StartTime", "INTEGER", null),
				new ColumnItem("BreakTime", "INTEGER", null),
				new ColumnItem("EndTime", "INTEGER", null),
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

		@Override
		public void onCreate(SQLiteDatabase paramSQLiteDatabase)
		{
			int aaa = 9;

//paramSQLiteDatabase.execSQL(
//	"CREATE TABLE Personel      
//(Id INTEGER, FirstName TEXT, LastName TEXT, ThirdName TEXT, IsSupervisor INT, PersonelCode INT, CardId TEXT, PhotoTimeSpan TEXT); ");
			String strPl = CreateSql("Personel", this.PersonelColumnItems());
			paramSQLiteDatabase.execSQL(strPl);

			//paramSQLiteDatabase.execSQL( "CREATE TABLE Point         (Id INTEGER, Name TEXT); ");
			paramSQLiteDatabase.execSQL(
				"CREATE TABLE PersonelPoint (PersonelId INTEGER, PointId INTEGER);");

			String strCh = CreateSql("Checkin",  this.CheckinColumnItems());
			paramSQLiteDatabase.execSQL(strCh);/**/
		}

		@Override
		public void onUpgrade(SQLiteDatabase paramSQLiteDatabase, int paramInt1, int paramInt2)
		{
			paramSQLiteDatabase.execSQL("DROP TABLE IF EXISTS Personel");
			paramSQLiteDatabase.execSQL("DROP TABLE IF EXISTS Point");
			paramSQLiteDatabase.execSQL("DROP TABLE IF EXISTS PersonelPoint");
			paramSQLiteDatabase.execSQL("DROP TABLE IF EXISTS Checkin");
			onCreate(paramSQLiteDatabase);/**/
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
			this._db.execSQL(sql);
		}
		else
		{
			Cursor dbCursor = this._db.query(tableName, null, null, null, null, null, null);
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
				this._db.execSQL("DROP TABLE IF EXISTS " + tableName);
				String sql = openHelper.CreateSql( tableName, entityPropArr);
				this._db.execSQL(sql);
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