package ta.Database;

import android.content.ContentValues;
import android.content.Context;
import org.json.JSONObject;

public class PersonelPoint
{
	//public static final String COLUMN_PERSONEL_ID = "PersonelId";
	//public static final String COLUMN_POINT_ID = "PointId";
	//public static final String TABLE_NAME = "PersonelPoint";
	public int PersonelId;
	public int PointId;

	public static PersonelPoint getInstance(int PersonelId, int PointId)
	{
		PersonelPoint pp = new PersonelPoint();
		try
		{
			pp.PersonelId = PersonelId;
			pp.PointId    = PointId;
			return pp;
		}
		catch (Exception localException)
		{
		}
		return null;
	}

	public static PersonelPoint getInstance(JSONObject paramJSONObject)
	{
		PersonelPoint pp = new PersonelPoint();
		try
		{
			pp.PersonelId = paramJSONObject.getInt("PersonelId");
			pp.PointId    = paramJSONObject.getInt("PointId");
			return pp;
		}
		catch (Exception localException)
		{
		}
		return null;
	}

	private static void delete(int int1, int int2, Context paramContext)
	{
		DbConnector db = DbConnector.getInstance();
		String[] arrayOfString = new String[]{   String.valueOf(int1),   String.valueOf(int2)   };
		db.delete("PersonelPoint", "PersonelId = ? and PointId = ?", arrayOfString);
	}

	private static void save(PersonelPoint pp, Context paramContext)
	{
		DbConnector db = DbConnector.getInstance();
		ContentValues cv = new ContentValues();
		cv.put("PersonelId", Integer.valueOf(pp.PersonelId));
		cv.put("PointId",    Integer.valueOf(pp.PointId));
		db.insert("PersonelPoint", cv);
	}

	public static void sync(PersonelPoint[] ppArray, Context paramContext)
	{
		for (int i = 0; i < ppArray.length; i++)
		{
			delete(
				ppArray[i].PersonelId, 
				ppArray[i].PointId, 
				paramContext
				);
			save(ppArray[i], paramContext);
		}
	}
}

/* Location:           C:\Users\vano99\Desktop\jd-gui-0.3.5.windows\TandAOffline_dex2jar.jar
 * Qualified Name:     com.ifree.Database.PersonelPoint
 * JD-Core Version:    0.6.2
 */