package ta.Entity;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Iterator;

import ta.Tabs.SessionList.SessionMode;
import ta.lib.Common.DateTime;

import ta.Database.*;
import ta.timeattendance.Mode;

import ta.lib.DatePicker.IIntervalDateTimeProvider;
//import ta.lib.DB.IDateSortable;
//SessionInfo

public class Session 
	//extends EntityBase 
	implements IDateSortable2
{
	//int Id;
	public SessionMode Mode;

	public int Start;			//Checkin
	public int End;				//Checkin

	public String FirstName;		//Personel
	public String LastName;			//Personel
	public String ThirdName;		//Personel

	public String PointName;		//Point
	public String CategoryName;		//Category
	public String TplStart;			//Template
	public String TplBreak;			//Template
	public String TplEnd;			//Template

	//public int Worker;
	//public long DateTime;

	private void Init()
	{
		//TableName = "???";
		//KeyField = "Id";
		//Fields = new String[]{						 "Start","End","FirstName","LastName","ThirdName","PointName","CategoryName","TplStart","TplBreak","TplEnd"};
		//Fields = new String[]{						 "Worker","DateTime"};
	}
	//public Object[] get_Values(){return new Object[]{ Start,  End,  FirstName,  LastName,  ThirdName,  PointName,  CategoryName,  TplStart,  TplBreak,  TplEnd };}
	//public Object[] get_Values(){return new Object[]{ Worker,  DateTime };}
	//public Object   get_KeyValue(){return			  Start;}
	//public Object   get_KeyValue(){return			  Id;}

	public Session()
	{
		Init();
	}



	//*********************************************************************************************
	//**     Local DB

	/*private static Session SessionFromCursor(Cursor cursor)
	{
		Session se = new Session();
		int index = -1;
		
		index = cursor.getColumnIndex("Id");
		if(index != -1)
		se.Start = cursor.getString(index);

		index = cursor.getColumnIndex("SupervicerId");
		if(index != -1)
		se.End = cursor.getString(index);

		return se;
	}*/

	//private static ArrayList<Session> GetList(Cursor cursor)
	//	throws java.lang.NoSuchFieldException,
	//		   java.lang.IllegalAccessException
	//{
	//	ArrayList<Session> list = new ArrayList<Session>();
	//	while (cursor.moveToNext())
	//	{
	//		Session s = new Session();
	//		s.FromCursor( Session.class, cursor);
	//		list.add(s);
	//	}
	//	return list;
	//}

	// IDateSortable
	private Calendar _calendarObj;
	public Calendar get_CalendarObj()
	{
		if( this._calendarObj == null )
		{
			int sec = this.Start;
			this._calendarObj = ta.lib.Common.DateTime.GetCalendarFromUnixSecond(sec);
		}
		return this._calendarObj;
	}



	public static Session [] GetAllSession(IIntervalDateTimeProvider provider, SessionMode sessionMode)
	{
		DbConnector db = DbConnector.getInstance();
		Session [] se = new Session[0];
		try{

		//DateTime current = ta.lib.Common.DateTime.GetCurrentDateTime();
		//current.AddDays(-7);
		//long milliseconds = current.ToUnixMilliseconds();
		//String millisecondsStr = String.valueOf(milliseconds);

		/*String sql =
"select "+
//"       c1.WorkerId as Worker, "+
//"       c1.CardId as Card, "+
"       c1.DateTime as Start, "+
"       c2.DateTime as End "+
//"       p.FirstName as FirstName, "+
//"       p.LastName as LastName, "+
//"       p.ThirdName as ThirdName, "+

//"       pt.Name as PointName, "+
//"       ct.Name as CategoryName, "+
//"       tp.StartTime as TplStart, "+
//"       tp.BreakTime as TplBreak, "+
//"       tp.EndTime as TplEnd "+
"from "+
"    (select * from Checkin where DateTime>"+millisecondsStr+") as c1 "+//
//"    (select * from Checkin as temp where cast(temp.Start as INTEGER)>cast('1413296363277' as INTEGER)) as c1 "+//
"left outer join "+
"    (select * from Checkin) c2 "+
"    on c1.WorkerId=c2.WorkerId "+

//"left outer join Personel p "+
//"    on c1.WorkerId=p.Id " +
//"left outer join Point pt "+
//"    on c1.PointId=pt.Id "+
//"left outer join Category ct "+
//"    on c1.CategoryId=ct.Id "+
//"left outer join Template tp "+
//"    on c1.TemplateId=tp.Id "+
"where c1.DateTime<c2.DateTime "
;

		String sql =
"select "+
"       c1.CheckinId as Checkin1, "+
"       c1.CheckinId as Checkin2 "+
"from "+
"    (select CheckinId from Checkin group by CheckinId order by DateTime limit 1 offset 0) as c1 "+
"left outer join "+
"    (select CheckinId from Checkin group by CheckinId order by DateTime limit 1 offset 1) c2 "+
"    on c1.WorkerId=c2.WorkerId "
;*/

//		String sql =
//"select "+
//"       c1.WorkerId as Worker, "+
//"       c1.CardId as Card, "+
//"       c1.DateTime as Start, "+
//"       c2.DateTime as End, "+
//"       p.FirstName as FirstName, "+
//"       p.LastName as LastName, "+
//"       p.ThirdName as ThirdName, "+
//"       pt.Name as PointName, "+
//"       ct.Name as CategoryName, "+
//"       tp.StartTime as TplStart, "+
//"       tp.BreakTime as TplBreak, "+
//"       tp.EndTime as TplEnd "+
////"       c1.CheckinId as Checkin1, "+
////"       c2.CheckinId as Checkin2, "+
////"       worker.WorkerId as WorkerId "+
//"from "+
//"    (select WorkerId from Checkin group by WorkerId) as worker "+
//"left outer join "+
//"    (select * from Checkin order by DateTime desc limit 1 offset 1) as c1 "+
//"    on worker.WorkerId=c1.WorkerId "+
//"left outer join "+
//"    (select * from Checkin order by DateTime desc limit 1 offset 0) as c2 "+
//"    on worker.WorkerId=c2.WorkerId "+

//"left outer join Personel p "+
//"    on c1.WorkerId=p.Id " +
//"left outer join Point pt "+
//"    on c1.PointId=pt.Id "+
//"left outer join Category ct "+
//"    on c1.CategoryId=ct.Id "+
//"left outer join Template tp "+
//"    on c1.TemplateId=tp.Id "
//;


//String sql1 =
////"select strftime('%s',date(1413487916012))"
//"SELECT COUNT(*) FROM Checkin"
//;
//        Cursor cursor1 = db.Select(sql1, null);
//        cursor1.moveToNext();
//        int str = cursor1.getInt(0);


		DateTime dt1 = provider.GetDateTimeLeft();
		DateTime dt2 = provider.GetDateTimeRight().AddDays(1);
		int start = dt1.ToUnixSeconds();
		int end   = dt2.MinuteBeforeMidnight().ToUnixSeconds();
		String startStr = String.valueOf(start);
		String endStr   = String.valueOf(end);

        //sessionMode

		String sql =
"select "+
"       worker.WorkerId as WorkerId, "+
//"       strftime('%s',strftime('%Y-%m-%d',date.DateTime)) as Date, "+
//"       strftime('%s',date(date.DateTime)) as Date, "+
//"       strftime('%s',date(date.DateTime/1000, 'unixepoch')) as Date, "+
  "       date.Date as Date, "+

"       ch.DateTime as DateTime, "+
"       ch.CheckinId as CheckinId, "+
"       ch.Mode as Mode, "+

"       p.FirstName as FirstName, "+
"       p.LastName as LastName, "+
"       p.ThirdName as ThirdName, "+

"       pt.Name as PointName, "+
"       ct.Name as CategoryName, "+
"       tp.StartTime as TplStart, "+
"       tp.BreakTime as TplBreak, "+
"       tp.EndTime as TplEnd "+

"from "+
"    (select WorkerId from Checkin where "+startStr+"<DateTime and DateTime<"+endStr+" group by WorkerId) as worker "+
"left outer join "+
"    (select distinct WorkerId as Id, strftime('%s',date(DateTime, 'unixepoch')) as Date "+
"    from Checkin where "+startStr+"<DateTime and DateTime<"+endStr+") as date "+
"    on worker.WorkerId=date.Id "+

"left outer join "+
"    (select * from Checkin where "+startStr+"<DateTime and DateTime<"+endStr+") as ch "+
"    on date.Date = strftime('%s',date(ch.DateTime, 'unixepoch')) "+

"left outer join Personel p "+ "    on ch.WorkerId=p.Id " +
"left outer join Point pt "+   "    on ch.PointId=pt.Id "+
"left outer join Category ct "+"    on ch.CategoryId=ct.Id "+
"left outer join Template tp "+"    on ch.TemplateId=tp.Id "
;
//"    on strftime('%s',strftime('%Y-%m-%d',date.DateTime)) = strftime('%s',strftime('%Y-%m-%d',ch.DateTime)) "//+
//"    on strftime('%s',date(date.DateTime)) = strftime('%s',date(ch.DateTime)) "//+

		Cursor cursor = db.Select(sql, null);
		//ArrayList<Session> list = GetList(cursor);
		//se = list.toArray(new Session[0]);

		//	LinkedHashMap<Integer, LinkedHashMap<Long, List<SessionInfo>>> dict =
		//new LinkedHashMap<Integer, LinkedHashMap<Long, List<SessionInfo>>>();

		List<List<List<SessionInfo>>>             list = new ArrayList<List<List<SessionInfo>>>();
		HashMap<Integer,Integer>                  wInd = new HashMap<Integer,Integer>();
		HashMap<Integer,HashMap<Integer,Integer>> dInd = new HashMap<Integer,HashMap<Integer,Integer>>();

		int index = -1;
		while (cursor.moveToNext())
		{
			index = cursor.getColumnIndex("WorkerId"); if(index != -1) { int workerId = cursor.getInt(index);

				//LinkedHashMap<Long, List<SessionInfo>> days = dict.get(workerId);
				//if(days == null) {
				//	dict.put(workerId, new LinkedHashMap<Long, List<SessionInfo>>());
				//	days = dict.get(workerId);
				//}

				Integer wIndex = wInd.get(workerId);
				if(wIndex == null) {
					ArrayList<List<SessionInfo>> item = new ArrayList<List<SessionInfo>>();
					list.add(item);
					wIndex = list.indexOf(item);
					wInd.put(workerId, wIndex);
				}
				List<List<SessionInfo>> days = list.get(wIndex);

				index = cursor.getColumnIndex("Date"); if(index != -1) { int date = cursor.getInt(index);

					//List<SessionInfo> checkins = days.get(date);
					//if(checkins == null) {
					//	days.put(date, new ArrayList<SessionInfo>());
					//	checkins = days.get(date);
					//}
					//SessionInfo s = new SessionInfo();
					//s.FromCursor( SessionInfo.class, cursor);
					//checkins.add(s);

					HashMap<Integer,Integer> dArrIndex = dInd.get(workerId);
					if(dArrIndex == null) {
						dInd.put(workerId, new HashMap<Integer,Integer>());
						dArrIndex = dInd.get(workerId);
					}

					Integer dIndex = dArrIndex.get(date);
					if(dIndex == null) {
						List<SessionInfo> item = new ArrayList<SessionInfo>();
						days.add(item);
						dIndex = days.indexOf(item);
						
						dArrIndex.put(date, dIndex);
					}

					List<SessionInfo> checkins = days.get(dIndex);

					SessionInfo s = new SessionInfo();
					s.FromCursor( SessionInfo.class, cursor);
					checkins.add(s);
				}

			}
		}

		ArrayList<Session> resultList = new ArrayList<Session>();

		//for(LinkedHashMap.Entry<Integer, LinkedHashMap<Long, List<SessionInfo>>> entry : dict.entrySet())
			//LinkedHashMap<Long, List<SessionInfo>> dayArr = entry.getValue();

		for (List<List<SessionInfo>> dayArr : list) {

			// последний день только для проверки закрытия ночьных смен
			int countDay = dayArr.size()-1; for (int iDay = 0; iDay < countDay; iDay++) { List<SessionInfo> chArr = dayArr.get(iDay);

				int count = 1;
				int id_prev_event = -1;
				Map<Integer,SessionInfo> events_prep = new HashMap<Integer,SessionInfo>();

				for (SessionInfo ch : chArr)
				{
					//****************************************************************************
					//   1 закрытые сессии
					//****************************************************************************

					switch(sessionMode) {
					case DepartureOk:{

					// последний чекин - приход
					if(count == chArr.size() && ch.Mode == ta.timeattendance.Mode.StartWork)
					{
						// Считаем что смена ночная если, на следующий день есть уход
						List<SessionInfo> dayNext = null;
						SessionInfo chNext = null;
						if(
							dayArr.size() > (iDay + 1) &&
							(dayNext = dayArr.get(iDay + 1)) != null &&
							dayNext.size() > 0 && 
							(chNext = dayNext.get(0)) != null &&
							chNext.Mode == ta.timeattendance.Mode.EndWork)
						{
							Session s = new Session();
							s.Mode = SessionMode.DepartureOk;
							s.Start = ch.DateTime;
							s.End   = chNext.DateTime;

							s.PointName = ch.PointName; s.CategoryName = ch.CategoryName;
							s.FirstName = ch.FirstName; s.LastName     = ch.LastName; s.ThirdName = ch.ThirdName;
							s.TplStart  = ch.TplStart;  s.TplBreak     = ch.TplBreak; s.TplEnd    = ch.TplEnd;
							resultList.add(s);
						}
					}
					else if(
						events_prep.containsKey(id_prev_event) &&
						events_prep.get(id_prev_event).Mode == ta.timeattendance.Mode.StartWork &&
						ch.Mode == ta.timeattendance.Mode.EndWork
						)
					{
						// смена закрыта
						Session s = new Session();
						s.Mode = SessionMode.DepartureOk;
						s.Start = events_prep.get(id_prev_event).DateTime;
						s.End   = ch.DateTime;

						s.PointName = ch.PointName; s.CategoryName = ch.CategoryName;
						s.FirstName = ch.FirstName; s.LastName     = ch.LastName; s.ThirdName = ch.ThirdName;
						s.TplStart  = ch.TplStart;  s.TplBreak     = ch.TplBreak; s.TplEnd    = ch.TplEnd;
						resultList.add(s);
					}

					break;}
					case DepartureMiss:{

					//****************************************************************************
					//   2 незакрытые сессии
					//****************************************************************************
					if(
						events_prep.containsKey(id_prev_event) &&
						events_prep.get(id_prev_event).Mode == ta.timeattendance.Mode.StartWork &&
						ch.Mode == ta.timeattendance.Mode.StartWork
						)
					{
						// смена закрыта
						Session s = new Session();
						s.Mode = SessionMode.DepartureMiss;
						s.Start = events_prep.get(id_prev_event).DateTime;
						//s.End   = DateTime.get_Null();

						s.PointName = ch.PointName; s.CategoryName = ch.CategoryName;
						s.FirstName = ch.FirstName; s.LastName     = ch.LastName; s.ThirdName = ch.ThirdName;
						s.TplStart  = ch.TplStart;  s.TplBreak     = ch.TplBreak; s.TplEnd    = ch.TplEnd;
						resultList.add(s);
					}

					break;}
					case ArrivalOk:{

					//****************************************************************************
					//   3 открытые сессии
					//****************************************************************************
					if(
						count == chArr.size() && ch.Mode == ta.timeattendance.Mode.StartWork &&
						dayArr.size() == (iDay + 1)
					)
					{
						// смена закрыта
						Session s = new Session();
						s.Mode = SessionMode.ArrivalOk;
						s.Start = events_prep.get(id_prev_event).DateTime;
						//s.End   = DateTime.get_Null();

						s.PointName = ch.PointName; s.CategoryName = ch.CategoryName;
						s.FirstName = ch.FirstName; s.LastName     = ch.LastName; s.ThirdName = ch.ThirdName;
						s.TplStart  = ch.TplStart;  s.TplBreak     = ch.TplBreak; s.TplEnd    = ch.TplEnd;
						resultList.add(s);
					}

					break;}
					}

					events_prep.put(ch.CheckinId, ch);
					id_prev_event = ch.CheckinId;
					count++;
				} // ch
			} // day
		} // worker


		se = resultList.toArray(new Session[0]);

		} catch(Exception e) {
			String str = e.getMessage();
			Exception ex = e;
		}

		return se;
	}
}
