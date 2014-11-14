package ta.Entity;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;

import ta.lib.Common.DateTime;

import ta.Database.*;
import ta.timeattendance.Mode;
import ta.lib.Common.EntityField;

public class SessionInfo extends EntityBase
{
	//@EntityField public long WorkerId;
	//@EntityField public long Date;

	@EntityField public int DateTime;				//Checkin
	@EntityField public int  CheckinId;			//Checkin
	@EntityField public int  Mode;				//Checkin

	@EntityField public String FirstName;		//Personel
	@EntityField public String LastName;			//Personel
	@EntityField public String ThirdName;		//Personel

	@EntityField public String PointName;		//Point
	@EntityField public String CategoryName;		//Category
	@EntityField public String TplStart;			//Template
	@EntityField public String TplBreak;			//Template
	@EntityField public String TplEnd;			//Template

	private void Init()
	{
		Fields = EntityBase.GetFieldsArrByEntity(SessionInfo.class);
	}
	public SessionInfo()
	{
		Init();
	}

	// for save in EntityBase
	public Object[] get_Values(){
		throw new RuntimeException( ta.lib.Common.CommonHelper.CreateMessageForException("SessionInfo.get_Values()") );
	}

	// for delete in EntityBase
	public Object get_KeyValue(){
		throw new RuntimeException( ta.lib.Common.CommonHelper.CreateMessageForException("SessionInfo.get_KeyValue()") );
	}
}
