package ta.Database;

import android.content.ContentValues;
import android.database.Cursor;
import java.util.ArrayList;

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

	public static Category SelectCategory(int id)
	{
		DbConnector db = DbConnector.getInstance();
		Category p = null;
		Cursor cursor = null;
		try{

String str = 
//"select p.Id,p.Name from SettingSv s inner join Category         p  on s.CategoryId = p.Id       where s.Id=?",
"select c.Id as Id, c.ObjectId as ObjectId,c.Name as Name "+
"from SettingSv s "+
"inner join Category c "+
"on s.CategoryId = c.Id "+
"where s.Id=?";

String str1 =  "select * from SettingSv s where s.Id=?";
//String str2 =  "select * from Category s ";

		cursor = db.Select(str
            ,
            //null
			new String[] { String.valueOf(id) }
		);

		while (cursor.moveToNext())
		{
           // int svid = cursor.getInt(0);
           // int pointId = cursor.getInt(1);
            //int cId = cursor.getInt(2);
            //int tId = cursor.getInt(3);
			p = new Category();
			p.FromCursor( Category.class, cursor);
            int aaa = 9;
			break;
		}
		cursor.close();

		} catch(Exception e){
			Exception ex = e;
		}
		return p;
	}

	public static Template SelectTemplate(int id)
	{
		DbConnector db = DbConnector.getInstance();
		Template p = null;
		Cursor cursor = null;
		try{

		cursor = db.Select(
"select t.Id  as Id, "+
"t.CategoryId as CategoryId, "+
"t.StartTime  as StartTime, "+
"t.BreakTime  as BreakTime, "+
"t.EndTime    as EndTime "+
"from SettingSv s "+
"inner join Template t "+
"on s.TemplateId = t.Id "+
"where s.Id=?",
			new String[] { String.valueOf(id) }
		);

		while (cursor.moveToNext())
		{
			p = new Template();
			p.FromCursor( Template.class, cursor);
			break;
		}
		cursor.close();

		} catch(Exception e){
			Exception ex = e;
		}
		return p;
	}

	public static void UpdatePoint(int id, int PointId, int cmId, int tmId)
	{
		Object result = SettingSv.Delete( id );
		long res = SettingSv.Insert( id, PointId, cmId, tmId);
	}

	public static Object Delete(int id)
	{
		DbConnector db = DbConnector.getInstance();
		return db.delete("SettingSv", "Id", String.valueOf(id));
	}

	public static long Insert(int id, int PointId, int cmId, int tmId)
	{
		DbConnector db = DbConnector.getInstance();
		ContentValues cv = new ContentValues();
		cv.put("Id", Integer.valueOf(id));
		cv.put("PointId", PointId);
		cv.put("CategoryId", cmId);
		cv.put("TemplateId", tmId);
		long res = db.insert("SettingSv", cv);
		return res;
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
		Point p = null;
		int index = -1;

		ArrayList<Point> list = new ArrayList<Point>();
		while (cursor.moveToNext())
		{
			p = new Point();
			index = cursor.getColumnIndex("Id");
			if(index != -1)
			{
				p.Id   = cursor.getInt(index);

				index  = cursor.getColumnIndex("Name");
				if(index != -1)
				p.Name = cursor.getString(index);
			}
			list.add(p);
		}
		cursor.close();

		int count = list.size();
		if(count > 0)
		{
			Point[] array = (Point[])list.toArray(new Point[0]);
			p = array[0];
		}
		/*index = cursor.getColumnIndex("Id");
		if(index != -1)
		{
			p = new Point();
			p.Id    = cursor.getInt(index);

			index   = cursor.getColumnIndex("Name");
			if(index != -1)
			p.Name = cursor.getString(index);
		}*/
		return p;
	}


}