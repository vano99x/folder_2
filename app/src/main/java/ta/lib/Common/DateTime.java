package ta.lib.Common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import ta.Entity.IDateSortable2;
import ta.lib.DatePicker.IIntervalDateTimeProvider;

public class DateTime
{

	public int Year;
	public int Month;
	public int Day;
	public int Hour;
	public int Minute;
	public int Second;

	private DateTime()
	{}

	public DateTime( int y, int m, int d, int h, int min, int sec)
	{
		DateTime.Init(this, y, m, d, h, min, sec);
	}

	public DateTime(long milliseconds)
	{
		DateTime.Init(this, milliseconds);
	}



	//*********************************************************************************************
	//*      format
	private static SimpleDateFormat _dateFormat;
	private static SimpleDateFormat get_DateFormat()
	{
		if( DateTime._dateFormat == null )
		{
			DateTime._dateFormat = new SimpleDateFormat("dd.MM.yyyy");
		}
		return DateTime._dateFormat;
	}

	private static SimpleDateFormat _monthDayFormat;
	private static SimpleDateFormat get_MonthDayFormat()
	{
		if( DateTime._monthDayFormat == null )
		{
			DateTime._monthDayFormat = new SimpleDateFormat("dd.MM");
		}
		return DateTime._monthDayFormat;
	}

	private static SimpleDateFormat _timeFormat;
	private static SimpleDateFormat get_TimeFormat()
	{
		if( DateTime._timeFormat == null )
		{
			DateTime._timeFormat = new SimpleDateFormat("HH:mm");
		}
		return DateTime._timeFormat;
	}



	//*********************************************************************************************
	//*      ToString
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
	public String ToMonthDayString()
	{
		Calendar c = Calendar.getInstance();
		c.set(  Year,  Month,  Day,  Hour,  Minute,  Second);

		Date d = c.getTime();
		String str = get_MonthDayFormat().format(d);

		return str;
	}
	public String ToTimeString()
	{
		Calendar c = Calendar.getInstance();
		c.set(  Year,  Month,  Day,  Hour,  Minute,  Second);

		Date d = c.getTime();
		String str = get_TimeFormat().format(d);

		return str;
	}



	//*********************************************************************************************
	//*      public
	public DateTime AddDays(int day)
	{
		Calendar c = Calendar.getInstance();
		c.set(  Year,  Month,  Day,  Hour,  Minute,  Second);
		c.add(Calendar.DAY_OF_MONTH,day);
		Init( this, c);
		return this;
	}
	public DateTime AddMinutes(int min)
	{
		Calendar c = Calendar.getInstance();
		c.set(  Year,  Month,  Day,  Hour,  Minute,  Second);
		c.add(Calendar.MINUTE,min);
		Init( this, c);
		return this;
	}
	public DateTime MinuteBeforeMidnight()
	{
		Hour = 23;
		Minute = 59;
		Second = 59;
		return this;
	}

	public long ToUnixMilliseconds()
	{
		Calendar c = Calendar.getInstance();
		c.set(  Year,  Month,  Day,  Hour,  Minute,  Second);
		long millisecond = c.getTimeInMillis();
		return millisecond;
	}

	public int ToUnixSeconds()
	{
		return (int) (this.ToUnixMilliseconds() / 1000L);
	}

	public Calendar ToCalendar()
	{
		Calendar c = Calendar.getInstance();
		c.set(  Year,  Month,  Day,  Hour,  Minute,  Second);
		return c;
	}



	//*********************************************************************************************
	//*      static

	private static void Init(DateTime dt, int y, int m, int d, int h, int min, int sec)
	{
		dt.Year   = y;
		dt.Month  = m;
		dt.Day    = d;
		dt.Hour   = h;
		dt.Minute = min;
		dt.Second = sec;
	}

	private static void Init(DateTime dt,Calendar calendar)
	{
		int year  = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day   = calendar.get(Calendar.DAY_OF_MONTH);
		int hour  = calendar.get(Calendar.HOUR_OF_DAY);
		int min   = calendar.get(Calendar.MINUTE);
		int sec   = calendar.get(Calendar.SECOND);
		
		DateTime.Init(dt, year, month, day, hour, min, sec);
	}

	private static void Init(DateTime dt,long milliseconds)
	{
		java.util.Calendar calendarObj = java.util.Calendar.getInstance();
		calendarObj.setTimeInMillis(milliseconds);

		DateTime.Init(dt, calendarObj);
	}



	//*********************************************************************************************
	//*      Create From

	public static DateTime FromDaySecond(int sec)
	{
		int min = sec/60;
		int s = sec - (min*60);
		int h = min/60;
		int m = min - (h  *60);

		return new DateTime(0, 0, 0, h, m, s);
	}
	public static DateTime FromDaySecond(String str)
	{
		int sec = Integer.parseInt(str);
		return FromDaySecond(sec);
	}

	public static DateTime FromUnixSecond(int second)
	{
		long l = second * 1000L;
		Calendar calendarObj = Calendar.getInstance();
		calendarObj.setTimeInMillis( l );

		DateTime dt = new DateTime();
		DateTime.Init(dt, calendarObj);

		return dt;
	}

	public static Calendar GetCalendarFromUnixSecond(int second)
	{
		long l = second * 1000L;
		Calendar calendarObj = Calendar.getInstance();
		calendarObj.setTimeInMillis( l );

		return calendarObj;
	}

	public static long GetMillisecondsFromUnixSecond(int second)
	{
		long l = second * 1000L;
		return l;
	}



	//*********************************************************************************************
	//*      Get Current
	public static DateTime GetCurrentDateTime()
	{
		java.util.Calendar calendarObj = java.util.Calendar.getInstance();

		DateTime dt = new DateTime();
		DateTime.Init(dt, calendarObj);

		return dt;
	}

	public static int GetCurrentUnixSeconds()
	{
		return (int) (System.currentTimeMillis() / 1000L);
	}



	//*********************************************************************************************
	//*      Where Filter
	public static <T> T[] WhereInPeriod(
		IIntervalDateTimeProvider provider, T[] origItems, T[] emptyArr)
	{
		DateTime dt1 = provider.GetDateTimeLeft();
		DateTime dt2 = provider.GetDateTimeRight();
		Iterable<T> itr = Arrays.asList(origItems);
		List<T> list = DateTime.WhereInPeriod( dt1, dt2, itr);
		return list.toArray(emptyArr);
	}
	/*public static <T> List<T> WhereInPeriod(
		IIntervalDateTimeProvider provider, T[] origItems)
	{
		DateTime dt1 = provider.GetDateTimeLeft();
		DateTime dt2 = provider.GetDateTimeRight();
		Iterable<T> itr = Arrays.asList(origItems);
		List<T> list = DateTime.WhereInPeriod( dt1, dt2, itr);
		return list;
	}*/
	public static <T> List<T> WhereInPeriod(
		IIntervalDateTimeProvider provider, ArrayList<T> origItems)
	{
		DateTime dt1 = provider.GetDateTimeLeft();
		DateTime dt2 = provider.GetDateTimeRight();
		List<T> list = DateTime.WhereInPeriod( dt1, dt2, origItems);
		return list;
	}

	public static <T> List<T> WhereInPeriod(
		DateTime dt1, DateTime dt2, Iterable<T> origItems)
	{
		int year1  = dt1.Year;
		int month1 = dt1.Month;
		int day1   = dt1.Day;
		int year2  = dt2.Year;
		int month2 = dt2.Month;
		int day2   = dt2.Day;

		Calendar selectedDate1 = Calendar.getInstance();
		Calendar selectedDate2 = Calendar.getInstance();
		selectedDate1.set( year1 , month1, day1,  0,  0,  0);
		selectedDate2.set( year2 , month2, day2, 23, 59, 59);

		List<T> list = new ArrayList<T>();
		for (T item : origItems)
		{
			IDateSortable2 temp = (IDateSortable2)item;
			Calendar checkinDate = temp.get_CalendarObj();

			if(checkinDate.after(selectedDate1) && checkinDate.before(selectedDate2))
			{
				list.add(item);
			}
		}

		return list;
	}
}