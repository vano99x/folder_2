package ta.Database;

import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;
import android.util.Base64;
import java.util.ArrayList;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.util.SortedMap;
import java.util.Set;
import java.util.Iterator;
import java.nio.charset.Charset;
import org.json.JSONArray;
import org.json.JSONObject;

public class Category extends EntityBase
{
	public int Id;
	public int ObjectId;
	public String Name;

	private void Init()
	{
		TableName = "Category";
		KeyField = "Id";
		Fields = new String[]{						 "Id","ObjectId","Name"};
	}
	// for save in EntityBase
	public Object[] get_Values(){return new Object[]{ Id,  ObjectId,  Name };}
	// for delete in EntityBase
	public Object   get_KeyValue(){return			  Id;}

	public Category()
	{
		Init();
	}

	public Category(int id, int objectId, String name)
	{
		Init();

		Id = id;
		ObjectId = objectId;
		Name = name;
	}/**/

	//*********************************************************************************************
	//**     Server
	/*public static Category[] CategoryArrayFromJson(JSONObject jo, Context context)
		throws org.json.JSONException
	{
		Category[] categoryArray = null;

		if( jo.has("Category") )
		{
			JSONArray jsArr = jo.getJSONArray("Category");

			int count = jsArr.length();
			categoryArray = new Category[count];

			for( int i = 0; i < count; i++)
			{
				categoryArray[i] = Category.FromJson(jsArr.getJSONObject(i), context);
			}
		}

		return categoryArray;
	}

	public static Category FromJson(JSONObject jo, Context context)
	{
		Category c = null;
		try
		{
			if(jo.has("Id"))
			{
				c = new Category();
				c.Id = jo.getInt("Id");

				if(jo.has("PointId"))
				c.PointId = jo.getInt("PointId");

				if(jo.has("Name"))
				{
					byte[] bytes = jo.optString("Name").getBytes();
					//String result = Base64.encodeToString(bytes, Base64.DEFAULT);
					//String result = new String(bytes, );
					//InputStream is = new InputStream();
					//InputStream is = new InputStreamReader();

					Charset windows1251Charset = null;
					ArrayList<Charset> list = new ArrayList<Charset>();
					SortedMap<String, Charset> charsets = Charset.availableCharsets();
					int count = charsets.size();
					Set names = charsets.keySet();
					for(Iterator e = names.iterator(); e.hasNext();)
					{
						String name = (String) e.next();
						Charset chs = (Charset) charsets.get(name);
						String str = chs.name();
						if(str.indexOf("1251") > -1)
						{
							//windows1251Charset = chs;
							//list.add(str);
							list.add(chs);
						}
					}
					Charset[] array = (Charset[])list.toArray(new Charset[0]);
					windows1251Charset = array[0];

					InputStream is = new ByteArrayInputStream(bytes);

					//BufferedReader r = new BufferedReader( new InputStreamReader(is, "win-1251"));
					//String s_1 = r.toString();

					BufferedReader r2 = new BufferedReader( new InputStreamReader(is, windows1251Charset));
					String s_2 = r2.toString();

					String s_3 = r2.readLine();

					c.Name = jo.getString("Name");
				}
			}
		}
		catch (Exception e)
		{
			Exception ex = e;
		}
		return c;
	}*/



	//*********************************************************************************************
	//**     Local DB
	//public static void UpdateByPointsLocalDB(Point [] array, Category [] catArr, Context context)
	//{
	//	int numberOfRows = 0;
	//	long rowID = -1;

	//	for (int i = 0; i < array.length; i++)
	//	{
	//		numberOfRows = deleteByPoint( array[i].Id );
	//	}

	//	for (int i = 0; i < catArr.length; i++)
	//	{
	//		Category c = catArr[i];
	//		rowID = save(c);
	//	}
	//}

	//private static int deleteByPoint(int id)
	//{
	//	DbConnector db = DbConnector.getInstance();
	//	return db.delete("Category", "PointId", String.valueOf(id));
	//}

	//private static long save(Category c)
	//{
	//	DbConnector db = DbConnector.getInstance();
	//	ContentValues cv = new ContentValues();
	//	cv.put("Id",      Integer.valueOf(c.Id));
	//	cv.put("PointId", Integer.valueOf(c.PointId));
	//	cv.put("Name",    c.Name);
	//	return db.insert("Category", cv);
	//}

	// for local Auth  
	public static Category[] getCategoryBySupervisor(int personelId)
	{
		DbConnector db = DbConnector.getInstance();
		String[] paramArr = {String.valueOf(personelId)};

String sql_1 =
"select * from Category c ";

String sql_2 =
"    inner join CustomerObject co "+
"        on c.ObjectId=co.Id ";

String sql_3 =
"    inner join Point p "+
"        on co.Id=p.ObjectId ";

String sql_4 =
"    inner join PersonelPoint pp "+
"        on p.Id=pp.PointId ";

String sql_where =
"    where pp.PersonelId=?";

String sql = sql_1 + sql_2 + sql_3 + sql_4 + sql_where//
        ;//

		ArrayList list = new ArrayList();

        Cursor cursor = db.Select( sql, paramArr );
        //Cursor cursor = db.Select( sql, null );
		//Cursor cursor = db.Select( "select * from CustomerObject c", null );
		while (cursor.moveToNext())
		{
			int id = cursor.getInt(0);
			int objectId = cursor.getInt(1);
			String name = cursor.getString(2);

			list.add(new Category(id, objectId, name));
		}
		cursor.close();

		return (Category[])list.toArray(new Category[0]);

		//return null;
	}
}
