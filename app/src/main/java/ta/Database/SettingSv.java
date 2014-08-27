package ta.Database;

import android.content.ContentValues;
import android.database.Cursor;

import ta.timeattendance.Models.*;

public class SettingSv
{
	public int Id;
	public String Point;

	private SettingSv()
	{
		this.Id    = -1;
		this.Point = null;
	}


	//*********************************************************************************************
	//**     public static
	public static long Insert(int id, int PointId)
	{
		DbConnector db = DbConnector.getInstance();
		ContentValues cv = new ContentValues();
		cv.put("Id", Integer.valueOf(id));
		cv.put("PointId", PointId);
		long res = db.insert("SettingSv", cv);
		return res;
	}

	public static Point SelectPoint(int id)
	{
		DbConnector db = DbConnector.getInstance();
		Point p = null;

		//select p.Id,p.Name from Settings as s inner join Point as p on s.PointId = p.Id where s.Id = 268
		//Cursor cursor = db.GetEntity( "SettingSv", "Id", new String[] { String.valueOf(id) } );
		Cursor cursor = null;
		try{
			cursor = db.Select(
				//"SELECT *           FROM Point     p INNER JOIN PersonelPoint pp ON p.Id      = pp.PointId WHERE pp.PersonelId=?",
				  "select p.Id,p.Name from SettingSv s inner join Point         p  on s.PointId = p.Id       where s.Id=?",
				new String[] { String.valueOf(id) }
			);
		} catch(Exception e){
			Exception ex = e;
		}
		p = SettingSv.PointFromCursor(cursor);
		return p;
	}

	public static void UpdatePoint(int id, int PointId)
	{
		Object result = SettingSv.Delete( id );
		long res = SettingSv.Insert( id, PointId);
	}

	public static Object Delete(int id)
	{
		DbConnector db = DbConnector.getInstance();
		return db.delete("SettingSv", "Id", String.valueOf(id));
	}



	//*********************************************************************************************
	//**     private static
	private static SettingSv FromCursor(Cursor cursor)
	{
		if(cursor.isAfterLast())
			return null;

		SettingSv s = null;
		int index = -1;

		index = cursor.getColumnIndex("Id");
		if(index != -1)
		{
			s = new SettingSv();
			s.Id    = cursor.getInt(index);

			index   = cursor.getColumnIndex("Point");
			if(index != -1)
			s.Point = cursor.getString(index);
		}
		return s;
	}

	private static Point PointFromCursor(Cursor cursor)
	{
		if(cursor.isAfterLast())
			return null;

		Point p = null;
		int index = -1;

		index = cursor.getColumnIndex("Id");
		if(index != -1)
		{
			p = new Point();
			p.Id    = cursor.getInt(index);

			index   = cursor.getColumnIndex("Name");
			if(index != -1)
			p.Name = cursor.getString(index);
		}
		return p;
	}


}