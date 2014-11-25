package ta.Database;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ta.lib.Common.EntityKeyField;
import ta.lib.Common.EntityField;

public abstract class EntityBase
{
	/*public abstract String get_TableName();    public abstract String get_KeyField();    public abstract String[] get_Fields();*/

	public String TableName;
	public String KeyField;
	public String[] Fields;

	private String _drivedClass;
	public EntityBase()
	{
	}
	public EntityBase(String drivedClass)
	{
		_drivedClass = drivedClass;

		Class type = this.getClass();

		TableName = ta.lib.Common.CommonHelper.ClassFromFullName(type.getName());
		KeyField = EntityBase.GetKeyFieldsArrByEntity(type);
		Fields   = EntityBase.GetFieldsArrByEntity(   type);
	}



	//*********************************************************************************************
	//**     Fields
	protected static Map<Class, String[]> _typeFieldsArr = new HashMap<Class, String[]>();
	protected static String[] GetFieldsArrByEntity(Class type)
	{
		String[] arr = _typeFieldsArr.get(type);
		if(arr == null)
		{
			Field[] fields = type.getFields();

			ArrayList<String> list = new ArrayList<String>();
			int count = fields.length;
			for (int i = 0; i < count; i++)
			{
				Field field = fields[i];
				if(field.isAnnotationPresent(EntityField.class))
				{
					String name = field.getName();
					list.add(name);
				}
				else if(field.isAnnotationPresent(EntityKeyField.class)) {
					String name = field.getName();
					list.add(name);
				}
			}
			arr = list.toArray(new String[0]);
		}
		return arr;
	}

	protected static Map<Class, String> _typeKeyFieldsArr = new HashMap<Class, String>();
	protected static String GetKeyFieldsArrByEntity(Class type)
	{
		String kf = _typeKeyFieldsArr.get(type);
		if(kf == null)
		{
			Field[] fields = type.getFields();

			int count = fields.length;
			for (int i = 0; i < count; i++)
			{
				Field field = fields[i];
				if(field.isAnnotationPresent(EntityKeyField.class))
				{
					String name = field.getName();
					kf = name;
					break;
				}
			}
		}
		return kf;
	}


	//*********************************************************************************************
	//**     static

	protected static DbConnector db()
	{
		return DbConnector.getInstance();
	}

	public static void UpdateArray(EntityBase [] array)
		throws java.lang.NoSuchFieldException,
			   java.lang.IllegalAccessException, 	java.lang.NoSuchMethodException
	{
		int numberOfRows = 0;
		long rowID = -1;
		DbConnector db = DbConnector.getInstance();

		//Class type = array.getClass().getComponentType();
		//Field field = type.getField(keyField);
		//Method method= type.getMethod("Delete");
        int i = 0;
        int count = array.length;
		for(; i < count&& i!=count; i++)
		{ //int id = (Integer)field.get(array[i]); //method.invoke(array[i])

            //EntityBase o = array[i];
			//o.Delete();
            array[i].Delete();
		}

		for (int i2 = 0; i2 < array.length; i2++)
		{
			rowID = array[i2].Insert();
		}
	}

	public static void InsertArray(EntityBase [] array)
		throws java.lang.NoSuchFieldException,
			   java.lang.IllegalAccessException
		//	   ,java.lang.NoSuchMethodException
	{
		int numberOfRows = 0;
		long rowID = -1;
		DbConnector db = DbConnector.getInstance();

		for (int i = 0; i < array.length; i++)
		{
			rowID = array[i].Insert();
		}
	}



	//*********************************************************************************************
	//**     instance

	//public void FromCursor( Class type, Cursor cursor )
	//	throws java.lang.NoSuchFieldException,
	//		   java.lang.IllegalAccessException
	//{
	//}



	//*********************************************************************************************
	//       use in Delete
	//*********************************************************************************************
	//public abstract Object get_KeyValue();
	public Object get_KeyValue() //throws java.lang.NoSuchFieldException, //java.lang.IllegalAccessException
	{
		Class type = this.getClass();
		Field field = null;
		Object arrRes = null;

		try{
		field = type.getField(this.KeyField);	// NoSuchFieldException
		arrRes = field.get(this);// NullPointerException, IllegalArgumentException, IllegalAccessException
		}catch(Exception e){
			Throwable thr = e.getCause();
			throw new RuntimeException( e.getCause() );
		}

		return arrRes;
	}

	//*********************************************************************************************
	//       use in Save
	//*********************************************************************************************
	//public abstract Object[] get_Values();
	public Object[] get_Values() //throws java.lang.NoSuchFieldException, //   java.lang.IllegalAccessException
	{
		String[] propertyArr = this.Fields;
		int count = propertyArr.length;

		if(count == 0)
			return null;

		Class type = this.getClass();
		Field field = null;
		Object [] arrRes = new Object[count];

		try{
		for( int i = 0; i < count; i++)
		{
			field = type.getField(propertyArr[i]);	// NoSuchFieldException
			Object o = field.get(this);// NullPointerException, IllegalArgumentException, IllegalAccessException
			arrRes[i] = o;
		}
		}catch(Exception e){
			Throwable thr = e.getCause();
			throw new RuntimeException( e.getCause() );
		}
		return arrRes;
	}

	//Create
	public long Insert()    //public long Save() //throws java.lang.NoSuchFieldException, //java.lang.IllegalAccessException
	{
		DbConnector db = DbConnector.getInstance();
		ContentValues cv = new ContentValues();
		String TableName = this.TableName;

		Class type = this.getClass();
		Field field = null;
		try{
		for (int i = 0; i < this.Fields.length; i++)
		{
			String name = this.Fields[i];
			field = type.getField(this.Fields[i]);	// NoSuchFieldException
			String typeName = field.getType().getName();
			Object o        = field.get(this);// NullPointerException, IllegalArgumentException, IllegalAccessException
			if(o != null)
			{
				if(typeName.equals("int")) {
					cv.put(name,  (Integer)o);
				}
				else if(typeName.equals("long")) {
					cv.put(name,  (Long)o);
				}
				else if(typeName.equals("java.lang.String")) {
					cv.put(name,  (String)o);
				}
				else if(typeName.equals("boolean") || typeName.equals("java.lang.Boolean")) {
					cv.put(name,  (Boolean)o);
				}
			}
			field = null;
		}//for
		}catch(Exception e){
			Throwable thr = e.getCause();
			throw new RuntimeException( e.getCause() );
		}

		return db.insert(TableName, cv);
	}

	// Read
	public void FromCursor( Class type, Cursor cursor ) //throws java.lang.NoSuchFieldException, //java.lang.IllegalAccessException
	{
		String[] propertyArr = this.Fields;
		int index = -2;
		Field field = null;

		int count = propertyArr.length;
		try{
		for( int i = 0; i < count; i++)
		{
			index = cursor.getColumnIndex(propertyArr[i]);
			if(index != -1)
			{
				//Field[] fArr = type.getFields();
				field = type.getField(propertyArr[i]);	// NoSuchFieldException
				String typeName = field.getType().getName();

				if(typeName.equals("int"))
				{
					int val = cursor.getInt(index);
					field.set( this, val );			// IllegalAccessException
				}
				else if(typeName.equals("long"))
				{
					long val = cursor.getLong(index);
					field.set( this, val );			// IllegalAccessException
				}
				else if(typeName.equals("java.lang.String"))
				{
					String val = cursor.getString(index);
					field.set( this, val );			// IllegalAccessException
				}
				else if(typeName.equals("boolean") || typeName.equals("java.lang.Boolean")) {
					int b = cursor.getInt(index);
					if(b == 0)
						field.set( this, Boolean.FALSE );
					else
						field.set( this, Boolean.TRUE );
				}
				field = null;
			}
		}//for
		}catch(Exception e){
			Throwable thr = e.getCause();
			throw new RuntimeException( e.getCause() );
		}
	}

	//Update

	public long Update()
		//throws java.lang.NoSuchFieldException,
			   //java.lang.IllegalAccessException,
			   //java.lang.NoSuchMethodException
	{
		int numberOfRows = 0;
		long rowID = -1;
		DbConnector db = DbConnector.getInstance();

		numberOfRows = this.Delete();

		rowID = this.Insert();
		return rowID;
	}

	//Delete
	public int Delete()
		//throws java.lang.NoSuchFieldException,
			   //java.lang.IllegalAccessException
	{
		int id = (Integer)this.get_KeyValue();
		String tableName = this.TableName;
		String keyField  = this.KeyField;
		return db().delete(tableName, keyField, String.valueOf(id));
	}
}