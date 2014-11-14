package ta.Tabs.CheckinList;

import android.content.Context;
import java.util.Calendar;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.text.SimpleDateFormat;

import ta.lib.Common.DateTime;
import ta.lib.DatePicker.IIntervalDateTimeProvider;
import ta.Database.*;
import ta.Entity.IDateSortable2;

//public class CheckinHelper
//{
	
	//*********************************************************************************************
	//*      public static   IDateSortable
	/*public static <T> T[] GetCheckinListByDatesInterval(
		IIntervalDateTimeProvider provider, T[] origItems, T[] emptyArr)
	{
		DateTime dt1 = provider.GetDateTimeLeft();
		DateTime dt2 = provider.GetDateTimeRight();
		int year1  = dt1.Year;
		int month1 = dt1.Month;
		int day1   = dt1.Day;
		int year2  = dt2.Year;
		int month2 = dt2.Month;
		int day2   = dt2.Day;

		//long currentMS     = System.currentTimeMillis();
		//Date currentDate   = new Date(currentMS);
		Calendar selectedDate1 = Calendar.getInstance();
		Calendar selectedDate2 = Calendar.getInstance();
		selectedDate1.set( year1 , month1, day1,  0,  0,  0);
		selectedDate2.set( year2 , month2, day2, 23, 59, 59);

		List<T> list = new ArrayList<T>();
		for (T item : origItems)
		{
			//Date checkinDate = ch.get_DateObj();
			IDateSortable2 temp = (IDateSortable2)item;
			Calendar checkinDate = temp.get_CalendarObj();

			if(checkinDate.after(selectedDate1) && checkinDate.before(selectedDate2))
			{
				list.add(item);
			}
		}

		return list.toArray(emptyArr);
	}
}*/
