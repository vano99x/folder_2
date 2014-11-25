package ta.Database;

import android.content.Context;
import android.content.ContentValues;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;

import ta.lib.operator;
import ta.lib.operator.*;
import ta.lib.Common.EntityKeyField;
import ta.lib.Common.EntityField;

public class CustomerObject extends EntityBase
{
	@EntityKeyField public int Id;
	@EntityField public String Name;

	public CustomerObject()
	{
		super("CustomerObject");
		//TableName = "CustomerObject";
		//KeyField = "Id";
		//Fields = new String[]{						 "Id","Name"};
	}
	//public Object[] get_Values(){return new Object[]{ Id, Name };}
	//public Object   get_KeyValue(){return			  Id;}

}
