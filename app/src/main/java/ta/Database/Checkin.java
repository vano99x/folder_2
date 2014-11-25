package ta.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import org.json.JSONObject;
import org.json.JSONException;

import java.util.Date;
import java.util.Calendar;
import java.util.ArrayList;
import java.lang.CharSequence;

import ta.Entity.IDateSortable2;
import ta.lib.Common.DateTime;
//import ta.lib.DB.IDateSortable;

public class Checkin extends EntityBase implements IDateSortable2
{
	public int CheckinId; // AUTOINCREMENT

	public int		SupervicerId;			//sv
	public int		WorkerId;				//w
	public String	CardId;					//w
	public int		Mode;					//mode
	public int		PointId;				//p
	public int		DateTime;				//currentTime(Millis) - second
	public int		CategoryId;				//c
	public int		TemplateId;				//t
	public boolean IsCheckinExistOnServer;

	public Checkin()
	{
		Init();
	}

	public Checkin(
		int    svId, 
		int    workerId, 
		String cardIdStr, 
		//boolean isSupervisor,
		int    mode, 
		int    pointId, 
		int dt,
		int    cat,
		int    tplId
		//,boolean isCheckinExistOnServer
		)
	{
		this.SupervicerId = svId;
		this.WorkerId     = workerId;
		this.CardId       = cardIdStr;
		this.Mode         = mode;
		this.PointId      = pointId;
		this.DateTime     = dt;
		this.CategoryId   = cat;
		this.TemplateId   = tplId;
		//this.IsCheckinExistOnServer     = isCheckinExistOnServer;
		this.__stateCheckinOnServer = -1;

		Init();
	}

	public Object[] get_Values(){return new Object[]{
 CheckinId,  SupervicerId,  WorkerId,  CardId,  Mode,  PointId,  DateTime,  CategoryId,  TemplateId,  IsCheckinExistOnServer };} private void f(){ Fields = new String[]{
"CheckinId","SupervicerId","WorkerId","CardId","Mode","PointId","DateTime","CategoryId","TemplateId","IsCheckinExistOnServer"};
	}

	public Object   get_KeyValue(){return CheckinId;}
	private void Init() {     KeyField = "CheckinId";
		f();
		TableName = "Checkin";
	}



	//*********************************************************************************************
	//**     Local DB
	public static Checkin [] GetAllCheckin( Context context)
	{
		DbConnector db = DbConnector.getInstance();
		Checkin [] ch = null;
		try{

		//String[] arrayOfString = new String[]{ String.valueOf(pcode) };
		//Cursor cursor = db.select("select * from Checkin WHERE PersonelCode=?", arrayOfString);
		Cursor cursor = db.Select("select * from Checkin", null);
		ArrayList<Checkin> al = getCheckinList(cursor);
		ch = al.toArray(new Checkin[0]);

		} catch(Exception e) {
			Exception ex = e;
		}

		return ch;
	}

	public static ArrayList<Checkin> GetLocalCheckins(Context context)
	{
		DbConnector db = DbConnector.getInstance();
		String[] arg = new String[]{ String.valueOf(0) };
		Cursor cursor = db.Select("select * from Checkin WHERE IsCheckinExistOnServer=?", arg);
		//Cursor cursor = db.Select("select * from Checkin", null);
		ArrayList<Checkin> al = getCheckinList(cursor);
		return al;
	}
	public static int GetCountSendCheckins(Context context)
	{
		DbConnector db = DbConnector.getInstance();
		String[] arg = new String[]{ String.valueOf(1) };
		Cursor cursor = db.Select("select * from Checkin WHERE IsCheckinExistOnServer=?", arg);
		int count = cursor.getCount();
		cursor.close();
		return count;
	}
	public static int GetCountNotSendCheckins(Context context)
	{
		return GetCountCheckinsByState(context, -1);
	}
	public static int GetCountErrorSendCheckins(Context context)
	{
		return GetCountCheckinsByState(context, -2);
	}
	private static int GetCountCheckinsByState(Context context, int state)
	{
		DbConnector db = DbConnector.getInstance();
		String[] arg = new String[]{ String.valueOf(state) };
		Cursor cursor = db.Select("select * from Checkin WHERE StateCheckinOnServer=?", arg);
		int count = cursor.getCount();
		cursor.close();
		return count;
	}

	//public static void Update(Checkin ch, Context context)
	//{
	//	Object result = null;
	//	long resSave  = -2;

	//	result  = Checkin.delete( ch.CheckinId, context);
	//	resSave = ch.save(  );

	//	int aaa = 9;
	//}



	//*********************************************************************************************
	//*      private static
	//private static Checkin getCheckin(Cursor cursor)
	//{
	//	if(!cursor.isAfterLast())
	//	{
	//		return FromCursor(cursor);
	//	}
	//	return null;
	//}
	private static Checkin FromCursor(Cursor cursor)
	{
		Checkin ch = new Checkin();
		int index = -2;
		
		index = cursor.getColumnIndex("CheckinId");
		if(index != -1)
		ch.CheckinId = cursor.getInt(index);

		index = cursor.getColumnIndex("SupervicerId");
		if(index != -1)
		ch.SupervicerId = cursor.getInt(index);

		index = cursor.getColumnIndex("WorkerId");
		if(index != -1)
		ch.WorkerId = cursor.getInt(index);

		index = cursor.getColumnIndex("CardId");
		if(index != -1)
		ch.CardId       = cursor.getString(index);

		index = cursor.getColumnIndex("Mode");
		if(index != -1)
		ch.Mode         = cursor.getInt(index);

		index = cursor.getColumnIndex("PointId");
		if(index != -1)
		ch.PointId      = cursor.getInt(index);

		index = cursor.getColumnIndex("DateTime");
		if(index != -1)
		//ch.DateTime     = cursor.getString(index);
		ch.DateTime     = cursor.getInt(index);

		index = cursor.getColumnIndex("IsCheckinExistOnServer");
		if(index != -1){
			int i = cursor.getInt(index);
			if(i == 1){
				ch.IsCheckinExistOnServer = true;
			}
		}

		index = cursor.getColumnIndex("StateCheckinOnServer");
		if(index != -1)
		ch.__stateCheckinOnServer = cursor.getInt(index);
		

		return ch;
	}
	private static ArrayList<Checkin> getCheckinList(Cursor cursor)
	{
		ArrayList list = new ArrayList();
		//if (!cursor.isAfterLast())
        int i = 0;
		while (cursor.moveToNext())
		{
			list.add(FromCursor(cursor));
            if(i == 11500)
            {
                int aaa = 9;
                int aaa2 = aaa-2;
            }
            i++;
		}
		cursor.close();
		return list;
	}

	private static Object delete(int paramInt, Context paramContext)
	{
		DbConnector db = DbConnector.getInstance();
		return db.delete("Checkin", "Id", String.valueOf(paramInt));
	}

	/*public static ArrayList<Checkin> getForSync(Context paramContext)
	{
		DbConnector db = DbConnector.getInstance();
		ArrayList localArrayList = new ArrayList();
		Cursor localCursor = db.Select("select * from Checkin", null);

		if (!localCursor.isAfterLast())
		{
			do
			{
				Checkin localCheckin = new Checkin();

				localCheckin.CheckinId           = localCursor.getInt(0);
				localCheckin.SupervicerId = localCursor.getInt(1);
				localCheckin.CardId       = localCursor.getString(2);
				localCheckin.Mode         = localCursor.getInt(3);
				localCheckin.PointId      = localCursor.getInt(4);
				localCheckin.DateTime     = localCursor.getString(5);

				localArrayList.add(localCheckin);
			}
			while (localCursor.moveToNext());
		}

		return localArrayList;
	}*/


	//*********************************************************************************************
	//*      public func

	private Date _dateObj;
	public Date get_DateObj()
	{
		if( this._dateObj == null )
		{
			//long l = Long.parseLong(this.DateTime);
			long l = this.DateTime;
			this._dateObj = new Date( l );
		}
		return this._dateObj;
	}
	// IDateSortable
	private Calendar __calendarObj;
	public Calendar get_CalendarObj()
	{
		if( this.__calendarObj == null )
		{
			int sec = this.DateTime;
			this.__calendarObj = ta.lib.Common.DateTime.GetCalendarFromUnixSecond(sec);
		}
		return this.__calendarObj;
	}

	//public long save(Context paramContext, boolean isCheckinExistOnServer)
	public long save()
	{
		DbConnector db = DbConnector.getInstance();
		ContentValues cv = new ContentValues();

		cv.put("SupervicerId", Integer.valueOf(this.SupervicerId));
		cv.put("WorkerId",     Integer.valueOf(this.WorkerId));
		cv.put("CardId",       this.CardId);
		cv.put("Mode",         Integer.valueOf(this.Mode));
		cv.put("PointId",      Integer.valueOf(this.PointId));
		cv.put("DateTime",     this.DateTime);

		//cv.put("IsCheckinExistOnServer", Boolean.valueOf(isCheckinExistOnServer));
		cv.put("IsCheckinExistOnServer", Boolean.valueOf(this.IsCheckinExistOnServer));
		cv.put("StateCheckinOnServer",   Integer.valueOf(this.get_StateCheckinOnServer()));
		
		long id = db.insert("Checkin", cv);
		
		this.CheckinId = (int)id;

		return id;
	}

	public Personel _personel;
	public Personel get_Personel()
	{
		if( this._personel == null )
		{
			//if(this.IsSupervisor)
			//{
				this._personel = Personel.SelectById( this.WorkerId );
			//}
			//else
			//{
			//    this._personel = Personel.SelectByCard( Long.parseLong(this.CardId) );
			//}
		}
		return this._personel;
	}

	private int __stateCheckinOnServer;
	public int get_StateCheckinOnServer()
	{
		return this.__stateCheckinOnServer;
	}
	public void set_StateCheckinOnServer(String serverRespond)
	{
					int stateCheckinOnServer = -1;
					try {
						JSONObject jo = new JSONObject(serverRespond);
						if(jo.has("status")) {
							String status = jo.getString("status");
							stateCheckinOnServer = Integer.parseInt(status);
						}
					} catch(JSONException jse){ } catch(NumberFormatException nfe){ }
					this.__stateCheckinOnServer = stateCheckinOnServer;
	}
	public void set_StateCheckinOnServer(int state)
	{
		this.__stateCheckinOnServer = state;
	}
}