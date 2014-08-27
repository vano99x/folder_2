package ta.lib.DatePicker;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.DatePicker;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import ta.lib.*;

public class CustomDatePickerControl extends DatePicker
{
	private static String [] MonthNames;

	static
	{
		CustomDatePickerControl.MonthNames = new String[]{"01","02","03","04","05","06","07","08","09","10","11","12"};
	}

	public CustomDatePickerControl(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		SetMonthAsInteger(this);
	}

	private static Field __MonthField;
	private static Field get_MonthField()
	{
		if(CustomDatePickerControl.__MonthField == null)
		{
			Field[] fields = DatePicker.class.getDeclaredFields();
			try
			{
				for(Field field : fields)
				{
					String name = field.getName();
					if(name.indexOf("Month") != -1) //mShortMonths //mMonthPicker
					{
						Class type = field.getType();
						String typeName = type.getName();
						boolean isArray = type.isArray();

						if(typeName.indexOf("String") != -1 && isArray)
						{
							field.setAccessible(true);
							CustomDatePickerControl.__MonthField = field;
							//Method m = field.getType().getDeclaredMethod("setRange", int.class, int.class, String[].class);
							//m.setAccessible(true);
							//m.invoke(field.get(this), 1, 12, s);
							//field.set(dpObj, CustomDatePickerControl.MonthNames);
							break;
						}
					}
				}
			}
			catch (Exception e)
			{
				//System.out.println(e.getMessage());
				//e.printStackTrace();
			}
		}
		return CustomDatePickerControl.__MonthField;
	}

	private static void SetMonthAsInteger(DatePicker dpObj)
	{
		Field field = get_MonthField();
		if(field != null)
		{
			try
			{
				Object obj = field.get(dpObj);
				String [] arr = operator.as(String[].class, obj);
				if(arr != null && arr.length == 12)
				{
					field.set(dpObj, CustomDatePickerControl.MonthNames);
				}
			}
			catch(java.lang.IllegalAccessException e)
			{
				Exception ex = e;
			}
		}
	}
}