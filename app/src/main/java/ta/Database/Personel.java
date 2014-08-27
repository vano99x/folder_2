package ta.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Base64;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;
//import com.ifree.timeattendance.TALog;
import ta.Database.Point;
import ta.timeattendance.MainEngine;
import ta.lib.BackgroundFunc;
import ta.lib.RunnableWithArgs;
import ta.lib.*;

public class Personel //extends EntityBase
{
	//public static final String COLUMN_CARDID = "CardId";
	//public static final String COLUMN_FIST_NAME = "FirstName";
	//public static final String COLUMN_ID = "Id";
	//public static final String COLUMN_IS_SUPERVISOR = "IsSupervisor";
	//public static final String COLUMN_LAST_NAME = "LastName";
	//public static final String COLUMN_PERSONEL_CODE = "PersonelCode";
	//public static final String COLUMN_PHOTO_TIME = "PhotoTimeSpan";
	//public static final String COLUMN_THIRD_NAME = "ThirdName";
	//public static final int NUM_COLUMN_ID = 0;
	//public static final int NUM_COLUMN_FIST_NAME = 1;
	//public static final int NUM_COLUMN_LAST_NAME = 2;
	//public static final int NUM_COLUMN_THIRD_NAME = 3;
	//public static final int NUM_COLUMN_IS_SUPERVISOR = 4;
	//public static final int NUM_COLUMN_PERSONEL_CODE = 5;
	//public static final int NUM_COLUMN_CARDID = 6;
	//public static final int NUM_COLUMN_PHOTO_TIME = 7;
	//public static final String TABLE_NAME = "Personel";

	public int Id;
	public String FirstName;
	public String LastName;
	public String ThirdName;

	public boolean IsSupervisor;
	public String Pin;
	public int PersonelCode;
	public String CardId;
	public String PhotoTimeSpan;

	public boolean IsDeleted;
	public boolean IsDismiss;
	public byte[] Photo;
	public Point [] pointArray;

	public Personel()
	{
		this.Id           = -1;
		this.Pin          = null;
		this.PersonelCode = 0;
		this.pointArray   = null;
	}

	//************************************************************************************************
	// select from sqlite by one field
	public static Personel SelecByPin( String pinStr )
	{
		DbConnector db = DbConnector.getInstance();
		Personel p = null;

		//try{
		//Cursor c = db.Select("SELECT * FROM Personel", null);
		//ArrayList<Personel> al = getPersonelList(c);
		//Personel[] arr = al.toArray(new Personel[0]);

		Cursor cursor = db.GetEntity( "Personel", "Pin", new String[] { pinStr } );

		p = Personel.FromCursor(cursor);

		//int aaa = 9;
		//int aaa2 = aaa - 2;

		//} catch(Exception e) {
		//    Exception ex = e;
		//}

		return p;
	}

	public static Personel SelectById(int id)
	{
		DbConnector db = DbConnector.getInstance();
		Cursor cursor = db.Select("select * from Personel where Id=?", new String[]{ String.valueOf(id) });

		return FromCursor( cursor );
	}

	public static Personel SelectByCard(long paramLong)
	{
		DbConnector db = DbConnector.getInstance();
		String[] arrayOfString = new String[]{ String.valueOf(paramLong) };

		Cursor cursor = db.GetEntity("Personel", "CardId", arrayOfString);

		return FromCursor( cursor );

		//return null;
	}


	//************************************************************************************************
	// prop
	private PersonelPoint [] _personelPoints;
	public PersonelPoint [] get_PersonelPoints()
	{
		if( this._personelPoints == null )
		{
			int PersonelId = this.Id;

			int count = this.pointArray.length;
			this._personelPoints = new PersonelPoint[count];

			for (int i = 0; i < count; i++)
			{
				int PointId = this.pointArray[i].Id;
				this._personelPoints[i] = PersonelPoint.getInstance( PersonelId, PointId);
			}
		}
		return this._personelPoints;
	}

	class GetPointsBFClass extends BackgroundFunc<JSONArray,Boolean> {}
	public Point [] get_Points(boolean isTryLoad, RunnableWithArgs loadComplete, Context context)
	{
		if(isTryLoad)
		{
			if(this.pointArray == null)
			{
				// 1 - load from local db
				Point [] pointArr   = Point.getBySuperviser( this.Id, context);
				if(pointArr == null || pointArr.length == 0)
				{
					// 2 - request from web services
					BackgroundFunc.Go( new GetPointsBFClass(), get_onLP(context), get_onLPComplete(loadComplete), "-onLP-");
				} else {
					this.pointArray = pointArr;
					loadComplete.run();
				}
			}else{
				loadComplete.run();
			}
			return null;
		}
		else
		{
			return this.pointArray;
		}
	}

	private onLPComplete get_onLPComplete(RunnableWithArgs loadComplete) { onLPComplete o = new onLPComplete(); o.arg1 = this; o.arg2 = loadComplete; return o; }
	class   onLPComplete extends RunnableWithArgs<JSONArray,Boolean> { public void run()
	{
		Personel _this = (Personel)this.arg1;
		boolean result = this.result;
		if(result) {
			_this.pointArray = Point.CreateArray((JSONArray)this.arg);
			RunnableWithArgs loadComplete = (RunnableWithArgs)this.arg2;
			loadComplete.run();
		}
	}}
	private onLP get_onLP(Context context) { onLP o = new onLP(); o.arg1 = this; o.arg2 = context; return o; }
	class onLP extends RunnableWithArgs<JSONArray,Boolean> { public void run()
	{
		try{

		Context context = (Context)this.arg2;
		if(HttpHelper.IsInternetAvailable(context))
		{
			String str = HttpHelper.getAuthRequestURL(((Personel)this.arg1).Pin);
			URL url = new URL(str);
			String data = HttpHelper.httpGet(url);
			JSONObject json = new JSONObject(data);

			if( json.has("Points") ) //JSONArray jsArr = jo.getJSONArray("Points"); //p.pointArray[i] = Point.CreateArray(jsArr);
			{
				JSONArray jsArr = json.getJSONArray("Points");
				this.arg = jsArr; this.result = true;
			}
			else {
				this.arg = null; this.result = false;
			}
		}
		else {
			this.arg = null; this.result = false;
		}
		
		} catch( Exception e) {
			//java.net.ConnectException: failed to connect to /93.153.172.26 (port 80): connect failed: ENETUNREACH (Network is unreachable)
			HttpHelper.ExceptionHandler( e );
		}
	}}



	//************************************************************************************************
	// static
	private static ArrayList<Personel> ArrayFromCursor(Cursor cursor)
	{
		ArrayList<Personel> list = new ArrayList<Personel>();
		if (!cursor.isAfterLast())
		{
			do
			{
				list.add(   FromCursor(cursor)   );
			}
			while( cursor.moveToNext() );
		}
		return list;
	}
	public static Personel FromCursor(Cursor cursor)
	{
		if(cursor.isAfterLast())
			return null;

		Personel p = null;
		int index = -1;
		//String[] columnNames = cursor.getColumnNames();
		//String strFirstName = cursor.getString(1);
		//String columnName_1 = cursor.getColumnName(1);

		index = cursor.getColumnIndex("Id");
		if(index != -1)
		{
			p = new Personel();

			p.Id            = cursor.getInt(index);

			index = cursor.getColumnIndex("FirstName");
			if(index != -1)
			p.FirstName     = cursor.getString(index);

			index = cursor.getColumnIndex("LastName");
			if(index != -1)
			p.LastName      = cursor.getString(index);

			index = cursor.getColumnIndex("ThirdName");
			if(index != -1)
			p.ThirdName     = cursor.getString(index);

			index = cursor.getColumnIndex("IsSupervisor");
			if(index != -1){
				int i = cursor.getInt(index);
				boolean isSv = i > 0? true: false;
				p.IsSupervisor  = isSv;
			}
			index = cursor.getColumnIndex("IsDismiss");
			if(index != -1){
				int i = cursor.getInt(index);
				if(i == 1){
					p.IsDismiss = true;
				}else{
					p.IsDismiss = false;
				}
			}

			index = cursor.getColumnIndex("Pin");
			if(index != -1)
			p.Pin           = cursor.getString(index);

			index = cursor.getColumnIndex("PersonelCode");
			if(index != -1)
			p.PersonelCode  = cursor.getInt(index);

			index = cursor.getColumnIndex("CardId");
			if(index != -1)
			p.CardId        = cursor.getString(index);

			index = cursor.getColumnIndex("PhotoTimeSpan");
			if(index != -1)
			p.PhotoTimeSpan = cursor.getString(index);
		}
		return p;
	}

	public static Personel[] ArrayFromJson(String serverRespond, Context context)
	{
		Personel[] personelArr;
		try
		{
			JSONArray JsonArr = new JSONObject(serverRespond).getJSONArray("personels");
			int count = JsonArr.length();
			personelArr = new Personel[count];
			for (int i = 0; i < count; i++)
			{
				personelArr[i] = Personel.FromJson( JsonArr.getJSONObject(i), context);
			}
		}
		catch (Exception e)
		{
			personelArr = new Personel[0];
		}
		return personelArr;
	}
	public static Personel FromJson(JSONObject jo, Context context)
	{
		Personel p = null;
		try
		{
			if(jo.has("Id"))
			{
				p = new Personel();
				p.Id = jo.getInt("Id");


				if(jo.has("FirstName"))
				p.FirstName = jo.getString("FirstName");

				if(jo.has("LastName"))
				p.LastName = jo.getString("LastName");

				if(jo.has("ThirdName"))
				p.ThirdName = jo.getString("ThirdName");

				if (jo.has("IsSupervisor"))
				{
					p.IsSupervisor = jo.getBoolean("IsSupervisor");
				}
				if (jo.has("PersonelCode"))
				{
					p.PersonelCode = jo.getInt("PersonelCode");
				}
				if (jo.has("Card"))
				{
					p.CardId = jo.getString("Card");
				}
				if (jo.has("IsDeleted"))
				{
					p.IsDeleted = jo.getBoolean("IsDeleted");
				}

				p.IsDismiss = false;
				if (jo.has("IsDismiss")) {
					int isDismiss = jo.getInt("IsDismiss");
					if(isDismiss == 1){
						p.IsDismiss = true;
					}
				}

				if(jo.has("Photo"))
				{
					p.PhotoTimeSpan = jo.getString("Photo");
					if( !p.loadCachedPhoto(context) )
					{
						URL fotoUrl = new URL( HttpHelper.getPhotoAddress(p.Id) );
						p.Photo = Base64.decode( HttpHelper.httpGet(fotoUrl), 0);
						p.SavePhoto(context);
					}
				}

				//add
				if( jo.has("Points") )
				{
					JSONArray jsArr = jo.getJSONArray("Points");

					int count = jsArr.length();
					p.pointArray = new Point[count];

					for( int i = 0; i < count; i++)
					{
						p.pointArray[i] = Point.Create(jsArr.getJSONObject(i));
					}
				}
			}
		}
		catch (Exception e)
		{
		}
		return p;
	}



	private static long save(Personel p, Context context)
	{
		DbConnector db = DbConnector.getInstance();
		ContentValues cv = new ContentValues();

		cv.put("Id", Integer.valueOf(p.Id));
		cv.put("FirstName",           p.FirstName);
		cv.put("LastName",           p.LastName);
		cv.put("ThirdName",          p.ThirdName);

		if(p.pointArray != null) {
			cv.put("IsSupervisor",  Boolean.valueOf(true));
		} else {
			cv.put("IsSupervisor",  Boolean.valueOf(p.IsSupervisor));
		}

		if(p.Pin != null) {
			//cv.put("Pin", Integer.valueOf(p.Pin));
			cv.put("Pin", p.Pin);
		}

		if(p.PersonelCode != 0) {
			cv.put("PersonelCode",  Integer.valueOf(p.PersonelCode));
		}
		if(p.CardId != null) {
			cv.put("CardId",        String.valueOf( p.CardId));
		}
		if(p.PhotoTimeSpan != null) {
			cv.put("PhotoTimeSpan",                 p.PhotoTimeSpan);
		}

		cv.put("IsDismiss",  Boolean.valueOf(p.IsDismiss));

		return db.insert("Personel", cv);
	}


	public static Personel[] search(String paramString, Context context)
	{
		DbConnector db = DbConnector.getInstance();
		String upperStr = paramString.toUpperCase();

		//String str = "SELECT * FROM Personel WHERE UPPER(LastName) LIKE '%" + upperStr + "%'";
		//String str = "SELECT * FROM Personel";
		String str = "SELECT * FROM Personel WHERE LastName LIKE '%" + paramString + "%'";

		Cursor c = db.Select(str, null);

		ArrayList<Personel> al = ArrayFromCursor(c);

		Personel[] arr = al.toArray(new Personel[0]);

		return arr;
	}


	public static void sync(Personel[] arr, Context paramContext)
	{
		Object result = null;
		long resSave  = -2;
		for (int i = 0; i < arr.length; i++)
		{
			result = Personel.delete( arr[i].Id, paramContext);

			if (!arr[i].IsDeleted)
			{
				resSave = save( arr[i], paramContext);
			}
		}
	}

	public static Object delete(long paramLong, Context paramContext)
	{
		DbConnector db = DbConnector.getInstance();
		return db.delete("Personel", "Id", String.valueOf(paramLong));
	}


	//************************************************************************************************
	// public func
	public Personel [] LoadWorkers(Context context)
		throws Exception
	{
		Personel [] personelArr = null;

		if( this.Pin != null )
		{
			String strUrl = HttpHelper.getSearchURL( this.Pin );
			URL urlPersonel = new URL(strUrl);
			String respond = HttpHelper.httpGet(urlPersonel);
			personelArr = Personel.ArrayFromJson( respond, context);
		}

		return personelArr;
	}

	//************************************************************************************************
	// foto
	private String getFileName()
	{
		return GetPhotoNameStart() + this.PhotoTimeSpan;
	}

	public void SavePhoto(Context paramContext)
	{
		File localFile = new File(paramContext.getFilesDir(), getFileName());
		try
		{
			if (localFile.exists())
				localFile.delete();
			localFile.createNewFile();
			BufferedOutputStream localBufferedOutputStream = new BufferedOutputStream(new FileOutputStream(localFile));
			localBufferedOutputStream.write(this.Photo);
			localBufferedOutputStream.flush();
			localBufferedOutputStream.close();
			return;
		}
		catch (IOException localIOException)
		{
			localIOException.printStackTrace();
		}
	}

	public boolean loadCachedPhoto(Context paramContext)
	{
		String str = getFileName();
		File localFile = new File(paramContext.getFilesDir(), str);
		if (localFile.exists())
		{
			try
			{
				FileInputStream stream = new FileInputStream(localFile);
				this.Photo = new byte[(int)localFile.length()];
				stream.read(this.Photo);
				return true;
			}
			catch (Exception localException)
			{
				//TALog.Log("Failed to read photo file " + str);
				return false;
			}
		}
		findAndDeletePreviousVersion(paramContext.getFilesDir());
		return false;
	}
	private void findAndDeletePreviousVersion(File paramFile)
	{
		for( File localFile : paramFile.listFiles() )
		{
			if (localFile.getName().startsWith(GetPhotoNameStart()))
			{
				localFile.delete();
			}
		}
	}
	

	private String GetPhotoNameStart()
	{
		return Integer.toString(this.Id) + "_";
	}
}

/* Location:           C:\Decompile\dex2jar-0.0.9.13\TandAOffline_dex2jar.jar
 * Qualified Name:     com.ifree.Database.Personel
 * JD-Core Version:    0.6.2
 */