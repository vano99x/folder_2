package ta.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import java.util.ArrayList;
import org.json.JSONObject;
import org.json.JSONArray;

public class Point extends EntityBase
{
	//public static final String COLUMN_ID = "Id";
	//public static final String COLUMN_NAME = "Name";
	//private static final int NUM_COLUMN_ID = 0;
	//private static final int NUM_COLUMN_NAME = 1;
	//public static final String TABLE_NAME = "Point";

	public int Id;
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
			point.Name = jo.getString("Name");
			return point;
		}
		catch (Exception ex)
		{
		}
		return null;
	}

	private static void delete(int paramInt, Context paramContext)
	{
		DbConnector db = DbConnector.getInstance();
		db.delete("Point", "Id", String.valueOf(paramInt));
	}

	public static Point[] getBySuperviser(int paramInt, Context paramContext)
	{
		DbConnector db = DbConnector.getInstance();
		String[] arrayOfString = new String[1];
		arrayOfString[0] = String.valueOf(paramInt);

		Cursor localCursor = 
			db.Select(
				"SELECT * FROM Point p INNER JOIN PersonelPoint pp ON p.Id=pp.PointId WHERE pp.PersonelId=?", 
				arrayOfString
			);

		ArrayList list = new ArrayList();
		if (!localCursor.isAfterLast())
		{
			do
			{
				list.add(new Point(localCursor.getInt(0), localCursor.getString(1)));
			}
			while (localCursor.moveToNext());
		}
		localCursor.close();
		return (Point[])list.toArray(new Point[0]);

		//return null;
	}

	private static void save(Point paramPoint, Context paramContext)
	{
		DbConnector db = DbConnector.getInstance();
		ContentValues cv = new ContentValues();
		cv.put("Id", Integer.valueOf(paramPoint.Id));
		cv.put("Name", paramPoint.Name);
		db.insert("Point", cv);
	}

	public static void sync(Point[] paramArrayOfPoint, Context paramContext)
	{
		for (int i = 0; i < paramArrayOfPoint.length; i++)
		{
			delete(paramArrayOfPoint[i].Id, paramContext);
			save(paramArrayOfPoint[i], paramContext);
		}
	}
}

/* Location:           C:\Users\vano99\Desktop\jd-gui-0.3.5.windows\TandAOffline_dex2jar.jar
 * Qualified Name:     com.ifree.Database.Point
 * JD-Core Version:    0.6.2
 */