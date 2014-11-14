package ta.Database;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ta.lib.Common.EntityField;

public abstract class EntityBase
{
	/*public abstract String get_TableName();
	public abstract String get_KeyField();
	public abstract String[] get_Fields();*/

	public String TableName;
	public String KeyField;
	public String[] Fields;
	public abstract Object get_KeyValue();
	public abstract Object[] get_Values();



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
			}
			arr = list.toArray(new String[0]);
		}
		return arr;
	}


	//*********************************************************************************************
	//**     static

	protected static DbConnector db()
	{
		return DbConnector.getInstance();
	}

	public static void UpdateLocalDB(EntityBase [] array)
		throws java.lang.NoSuchFieldException,
			   java.lang.IllegalAccessException, 	java.lang.NoSuchMethodException
	{
		int numberOfRows = 0;
		long rowID = -1;
		DbConnector db = DbConnector.getInstance();

		//Class type = array.getClass().getComponentType();
		//Field field = type.getField(keyField);
		//Method method= type.getMethod("Delete");
		for (int i = 0; i < array.length; i++)
		{
			//int id = (Integer)field.get(array[i]);
			//numberOfRows = db.delete("Category", "PointId", String.valueOf(id));
			//method.invoke(array[i])
			array[i].Delete();
		}

		for (int i = 0; i < array.length; i++)
		{
			rowID = array[i].Save();
		}
	}

	public static void InsertArray(EntityBase [] array)
		//throws java.lang.NoSuchFieldException,
		//	   java.lang.IllegalAccessException,
		//	   java.lang.NoSuchMethodException
	{
		int numberOfRows = 0;
		long rowID = -1;
		DbConnector db = DbConnector.getInstance();

		for (int i = 0; i < array.length; i++)
		{
			rowID = array[i].Save();
		}
	}



	//*********************************************************************************************
	//**     instance

	//public void FromCursor( Class type, Cursor cursor )
	//	throws java.lang.NoSuchFieldException,
	//		   java.lang.IllegalAccessException
	//{
	//}

	public void FromCursor( Class type, Cursor cursor )
		throws java.lang.NoSuchFieldException,
			   java.lang.IllegalAccessException
	{
		String[] propertyArr = this.Fields;
		int index = -2;
		Field field = null;

		int count = propertyArr.length;
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
				field = null;
			}
		}
	}

	public long Save()
	{
		DbConnector db = DbConnector.getInstance();
		ContentValues cv = new ContentValues();
		String TableName = this.TableName;

		Object[] arr = get_Values();
		for (int i = 0; i < this.Fields.length; i++)
		{
			String name = this.Fields[i];
			String val  = arr[i].toString();
			cv.put( name, val);
		}

		//cv.put("Id",      Integer.valueOf(c.Id));
		//cv.put("PointId", Integer.valueOf(c.PointId));
		//cv.put("Name",    c.Name);

		return db.insert(TableName, cv);
	}

	public int Delete()
	{
		int id = (Integer)this.get_KeyValue();
		String tableName = this.TableName;
		String keyField  = this.KeyField;
		return db().delete(tableName, keyField, String.valueOf(id));
	}
}