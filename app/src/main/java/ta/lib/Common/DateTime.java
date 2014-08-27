package ta.lib.Common;

import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;

public class DateTime
{

	public int Year;
	public int Month;
	public int Day;
	public int Hour;
	public int Minute;
	public int Second;

	public DateTime( int y, int m, int d, int h, int min, int sec)
	{
		Year   = y;
		Month  = m;
		Day    = d;
		Hour   = h;
		Minute = min;
		Second = sec;
	}

	private static SimpleDateFormat _dateFormat;
	private static SimpleDateFormat get_DateFormat()
	{
		if( DateTime._dateFormat == null )
		{
			DateTime._dateFormat = new SimpleDateFormat("dd.MM.yyyy");
		}
		return DateTime._dateFormat;
	}



	//*********************************************************************************************
	//*      public
	public String ToDateString()
	{
		Calendar c = Calendar.getInstance();
		c.set(  Year,  Month,  Day,  Hour,  Minute,  Second);
		//long millisecond = c.getTimeInMillis();
		//Date d = new Date(millisecond);
		Date d = c.getTime();

		String dateStr = get_DateFormat().format(d);

		return dateStr;
	}



	//*********************************************************************************************
	//*      static
	public static DateTime GetCurrentDateTime()
	{
		java.util.Calendar calendar = java.util.Calendar.getInstance();

		int year  = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day   = calendar.get(Calendar.DAY_OF_MONTH);
		int hour  = calendar.get(Calendar.HOUR_OF_DAY);
		int min   = calendar.get(Calendar.MINUTE);
		int sec   = calendar.get(Calendar.SECOND);

		DateTime dt = new DateTime( year, month, day, hour, min, sec);

		return dt;
	}
}