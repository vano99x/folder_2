package ta.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import java.util.ArrayList;
import org.json.JSONObject;
import org.json.JSONArray;

public class Point
{
	//public static final String COLUMN_ID = "Id";
	//public static final String COLUMN_NAME = "Name";
	//private static final int NUM_COLUMN_ID = 0;
	//private static final int NUM_COLUMN_NAME = 1;
	//public static final String TABLE_NAME = "Point";

	public int Id;
    public int ObjectId;
	public String Name;

	public Point()
	{
		this.Id = -1;
		this.Name = null;
	}
	private Point(int paramInt, String paramString)
	{
	this.Id = paramInt;
	this.Name = paramString;
	}

	public static Point[] CreateArray(JSONArray jsonArr)
	{
		Point[] pointArr;
		try
		{
			int count = jsonArr.length();
			pointArr = new Point[count];
			for (int i = 0; i < count; i++)
			{
				pointArr[i] = Point.Create( jsonArr.getJSONObject(i));
			}
		}
		catch (Exception e)
		{
			pointArr = new Point[0];
		}
		return pointArr;
	}

	public static Point Create(JSONObject jo)
	{
		Point point = new Point();
		try
		{
			point.Id = jo.getInt("Id");
            point.ObjectId = jo.getInt("ObjectId");
			point.Name = jo.getString("Name");
			return point;
		}
		catch (Exception ex)
		{
		}
		return null;
	}

	public static Point[] getBySuperviser(int paramInt, Context paramContext)
	{
		DbConnector db = DbConnector.getInstance();
        String[] arrayOfString = {String.valueOf(paramInt)};

		Cursor cursor =
			db.Select(
				"SELECT * FROM Point p INNER JOIN PersonelPoint pp ON p.Id=pp.PointId WHERE pp.PersonelId=?", 
				arrayOfString
			);

		ArrayList list = new ArrayList();
		if (cursor.moveToNext())
		{
			do
			{
				list.add(new Point(cursor.getInt(0), cursor.getString(1)));
			}
			while (cursor.moveToNext());
		}
		cursor.close();
		return (Point[])list.toArray(new Point[0]);

		//return null;
	}

	private static void delete(int paramInt, Context paramContext)
	{
		DbConnector db = DbConnector.getInstance();
		db.delete("Point", "Id", String.valueOf(paramInt));
	}

	private static void save(Point paramPoint, Context paramContext)
	{
		DbConnector db = DbConnector.getInstance();
		ContentValues cv = new ContentValues();
		cv.put("Id", Integer.valueOf(paramPoint.Id));
		cv.put("ObjectId", Integer.valueOf(paramPoint.ObjectId));
		cv.put("Name", paramPoint.Name);
		db.insert("Point", cv);
	}

	public static void sync(Point[] pointArray, Context paramContext)
	{
		for (int i = 0; i < pointArray.length; i++)
		{
			delete(pointArray[i].Id, paramContext);
			save(pointArray[i], paramContext);
		}
	}
}