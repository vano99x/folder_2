package ta.lib.Network;

import android.content.Context;
import android.content.ContentValues;
import org.json.JSONArray;
import org.json.JSONObject;
import java.lang.reflect.Field;
import ta.lib.operator.*;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class JsonToEntity
{
	public static <T> T[] JoFieldToEntityArray(
		Class<? extends T> type, T[] emptyArr,
		JSONObject jo, String fieldName, String[] propertyArr, 
		Context context
	)
		throws org.json.JSONException
	{
		T[] array = null;
		if( jo.has(fieldName) )
		{
			JSONArray jsArr = jo.getJSONArray(fieldName);

			int count = jsArr.length();
			ArrayList<T> al = new ArrayList<T>();

			for( int i = 0; i < count; i++)
			{
				T entity =
					JsonToEntity.JoToEntity(
						type,
						jsArr.getJSONObject(i), 
						propertyArr, 
						context
					);
				al.add(entity);
			}
			array = al.toArray(emptyArr);
		}
		return array;
	}

	public static <T> T JoToEntity(
		Class<? extends T> type, 
		JSONObject jo, String[] propertyArr, 
		Context context)
		throws org.json.JSONException
	{
		T entity = null;
		if( !jo.has(propertyArr[0]))
		{
			return null;
		}

		try {

		entity = type.newInstance();					// IllegalAccessException, InstantiationException
		Field field = null;
		int count = propertyArr.length;
		for( int i = 0; i < count; i++)
		{
			if(jo.has(propertyArr[i]))
			{
				field = type.getField(propertyArr[i]);	// NoSuchFieldException
				//field.getType().getCanonicalName();
				String typeName = field.getType().getName();
				//field.getType().getSimpleName();

				if(typeName.equals("int"))
				{
					int val = jo.getInt(propertyArr[i]);// JSONException
					field.set( entity, val );			// IllegalAccessException
				}
				else if(typeName.equals("java.lang.String"))
				{
					String val = jo.getString(propertyArr[i]);//JSONException
					field.set( entity, val );			// IllegalAccessException
				}
				field = null;
			}
		}

		} catch( java.lang.NoSuchFieldException e) {
			Exception ex = e;
		} catch( java.lang.IllegalAccessException e) {
			Exception ex = e;
		} catch( java.lang.InstantiationException e) {
			Exception ex = e;
		}

		return entity;
	}
}
