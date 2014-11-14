package ta.Database;

import android.content.Context;
import android.content.ContentValues;
import org.json.JSONArray;
import org.json.JSONObject;
import ta.lib.operator;
import ta.lib.operator.*;
import java.util.ArrayList;

public class CustomerObject extends EntityBase
{
	public int Id;
	public String Name;

	public CustomerObject()
	{
		TableName = "CustomerObject";
		KeyField = "Id";
		Fields = new String[]{						 "Id","Name"};
	}
	public Object[] get_Values(){return new Object[]{ Id,  Name };}
	public Object   get_KeyValue(){return			  Id;}



	//*********************************************************************************************
	//**     Local DB

	/*public static void UpdateLocalDB(EntityBase [] array)
		throws java.lang.NoSuchFieldException,
			   java.lang.IllegalAccessException, 	java.lang.NoSuchMethodException
	{
		int numberOfRows = 0;

	}*/




	//*********************************************************************************************
	//**     Server

}
