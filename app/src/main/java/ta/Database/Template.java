package ta.Database;

import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;

import org.json.JSONArray;
import org.json.JSONObject;
import ta.lib.operator;
import ta.lib.operator.*;
import java.util.ArrayList;

public class Template extends EntityBase
{
	public int Id;
	public int CategoryId;
	public int StartTime;
	public int BreakTime;
	public int EndTime;

	private void Init()
	{
		TableName = "Template";
		KeyField = "Id";
		Fields = new String[]{						 "Id","CategoryId","StartTime","BreakTime","EndTime"};
	}
	// for save in EntityBase
	public Object[] get_Values(){return new Object[]{ Id,  CategoryId,  StartTime,  BreakTime,  EndTime };}
	// for delete in EntityBase
	public Object   get_KeyValue(){return			  Id;}

	public Template()
	{
		Init();
	}

	public static Template[] getAllTemplate()
	{
        ArrayList list = new ArrayList();
		try{

		DbConnector db = DbConnector.getInstance();

		String sql = "select * from Template";

		Cursor cursor = db.Select( sql, null );
		while (cursor.moveToNext())
		{
			Template t = new Template();
			t.FromCursor( Template.class, cursor);
			list.add(t);
		}
		cursor.close();
		
		} catch(Exception e) {
			String str = e.getMessage();
			Exception ex = e;
		}
		return (Template[])list.toArray(new Template[0]);
	}



	//*********************************************************************************************
	//**     Server
	/*public static Template[] TemplateArrayFromJson(JSONObject jo, Context context)
		throws org.json.JSONException
	{
		Template[] templatesArray = null;

		if( jo.has("Templates") )
		{
			JSONArray jsArr = jo.getJSONArray("Templates");

			int count = jsArr.length();
			templatesArray = new Template[count];

			for( int i = 0; i < count; i++)
			{
				templatesArray[i] = Template.FromJson(jsArr.getJSONObject(i), context);
			}
		}

		return templatesArray;
	}

	public static Template FromJson(JSONObject jo, Context context)
	{
		Template t = null;
		try
		{
			if(jo.has("Id"))
			{
				t = new Template();
				t.Id = jo.getInt("Id");

				if(jo.has("PointId"))
				t.PointId = jo.getInt("PointId");

				if(jo.has("CategoryId"))
				t.CategoryId = jo.getInt("CategoryId");

				if(jo.has("Name"))
				t.Name = jo.getString("Name");
			}
		}
		catch (Exception e)
		{
		}
		return t;
	}*/



	//*********************************************************************************************
	//**     Local DB
	/*public static void UpdateLocalDB(Point [] array, Category [] catArr, Template [] tplArr)
	{
		for (int i = 0; i < array.length; i++)
		{
			int count = deleteByPoint( array[i].Id );
		}

		for (int i = 0; i < tplArr.length; i++)
		{
			final Template t = tplArr[i];

			ArrayList<Integer> pointsIds =
				operator.Where(
					catArr,
					new WhereRunnable<Category,Integer>() { public Integer run(Category item)
					{   return item.Id == t.CategoryId? item.PointId: null;   }}
				);
			Integer pointId = pointsIds.get(0);

			save(pointId, t);
		}
	}

	private static int deleteByPoint(int id)
	{
		DbConnector db = DbConnector.getInstance();
		return db.delete("Template", "PointId", String.valueOf(id));
	}

	private static void save(Integer pointId,Template t)
	{
		DbConnector db = DbConnector.getInstance();
		ContentValues cv = new ContentValues();
		cv.put("Id",         Integer.valueOf(t.Id));
		cv.put("PointId",    pointId);
		cv.put("CategoryId", Integer.valueOf(t.CategoryId));
		cv.put("Name",       t.Name);
		db.insert("Template", cv);
	}*/
}
