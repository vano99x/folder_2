package ta.ui.NotificationMessage;

import android.os.Handler;
import android.database.Cursor;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import ta.Entity.IDateSortable2;
import ta.lib.*;
import ta.lib.Common.DateTime;
import ta.lib.Common.EntityField;
import ta.lib.Common.SimpleIntervalDateTimeProvider;
import ta.timeattendance.MainActivity;
import ta.timeattendance.MainActivityProxy;
import ta.Database.*;
import ta.lib.Thread.BackgroundService;

public class NotificationMessageService implements INotificationMessageService
{
	private BackgroundService _service;
	private Handler handler;
	private android.media.Ringtone ringtone;
	private HashMap<MessageTimeLevel,SimpleIntervalDateTimeProvider> _periodCollection;

	public NotificationMessageService()
	{
		this._service = new BackgroundService(   get_Task().Add(get_Task_Completed())   );
		handler = new Handler();
		ringtone = 
			android.media.RingtoneManager.getRingtone(
				MainActivity.get_FragmentActivityStatic(),
				//android.provider.Settings.System.DEFAULT_RINGTONE_URI
				//RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
				android.provider.Settings.System.DEFAULT_ALARM_ALERT_URI
			);

		_periodCollection = new HashMap<MessageTimeLevel,SimpleIntervalDateTimeProvider>();
		_periodCollection.put( MessageTimeLevel.Before,  new SimpleIntervalDateTimeProvider());
		_periodCollection.put( MessageTimeLevel.InMoment,new SimpleIntervalDateTimeProvider());
		_periodCollection.put( MessageTimeLevel.After,   new SimpleIntervalDateTimeProvider());
	}



	//*********************************************************************************************
	//*      ringtone & message
	private boolean isBusy;
	private Runnable runnable1 = new Runnable() { public void run() {
		if( isBusy ){
			ringtone.play();
			handler.postDelayed(runnable2, 3000L);
		}
	}};

	private Runnable runnable2 = new Runnable() { public void run() {
		if( isBusy ){
			ringtone.stop();
			handler.postDelayed(runnable1, 3000L);
		}
	}};

	private okBtnClick get_okBtnClick() { okBtnClick o = new okBtnClick(); o.arg1 = this; return o; }
	private static class okBtnClick extends RunnableWithArgs { public void run()
	{
		NotificationMessageService _this = (NotificationMessageService)    this.arg1;
		TplDateEntity tde                = (TplDateEntity)                 this.arg2;
		HashMap<Integer,TplDateEntity> t = (HashMap<Integer,TplDateEntity>)this.arg3;
		t.put(tde.TemplateId, tde);

		_this.ringtone.stop();
		_this.handler.removeCallbacks(_this.runnable1);
		_this.handler.removeCallbacks(_this.runnable2);
		_this.isBusy = false;
	}}

	private boolean ShowMessageForLevel(
		HashMap<String,HashMap<Integer,TplDateEntity>> markedMessage,    List<TplDateEntity> list)
	{
		if(this.isBusy) return true;

		for(TplDateEntity tde : list)
		{
			HashMap<Integer,TplDateEntity> tplArr = markedMessage.get(tde.Date);
			if(tplArr == null) {                    markedMessage.put(tde.Date, new HashMap<Integer,TplDateEntity>());
				                           tplArr = markedMessage.get(tde.Date);
			}
			TplDateEntity tpl = tplArr.get(tde.TemplateId);
			if(tpl == null)
			{
				this.isBusy = true;
				//_this.ShowMessageForLevel(tde);

		MainActivityProxy ma = MainActivity.get_FragmentActivityStatic();
		FragmentManager fm = ma.get_FragmentManager();
		NotificationMessage message = new NotificationMessage();

		String text = "через час заканчивается смена с "+ DateTime.FromDaySecond(tde.StartTime).ToTimeString()+" до "+
			DateTime.FromDaySecond(tde.EndTime).ToTimeString()+" часа, необходимо отследить окончание смены";
		RunnableWithArgs o = get_okBtnClick();    o.arg2 = tde;    o.arg3 = tplArr;
		message.Init(fm,text,o);    message.Show();
		runnable1.run();

				return true;
			}
		}
		return false;
	}



	//*********************************************************************************************
	//*      period
	private void UpdatePeriodCollection()
	{
		SimpleIntervalDateTimeProvider timeProvider = null;

		timeProvider = this._periodCollection.get(MessageTimeLevel.Before);
		timeProvider.set_DateTimeLeft( DateTime.GetCurrentDateTime().AddMinutes(-40));
		timeProvider.set_DateTimeRight(DateTime.GetCurrentDateTime().AddMinutes(-10));

		timeProvider = this._periodCollection.get(MessageTimeLevel.InMoment);
		timeProvider.set_DateTimeLeft( DateTime.GetCurrentDateTime().AddMinutes(-7));
		timeProvider.set_DateTimeRight(DateTime.GetCurrentDateTime().AddMinutes(3));

		timeProvider = this._periodCollection.get(MessageTimeLevel.After);
		timeProvider.set_DateTimeLeft( DateTime.GetCurrentDateTime().AddMinutes(30));
		timeProvider.set_DateTimeRight(DateTime.GetCurrentDateTime().AddMinutes(60));
	}

	private HashMap<String,HashMap<Integer,TplDateEntity>> _markedMessage1 = new HashMap<String,HashMap<Integer,TplDateEntity>>();
	private HashMap<String,HashMap<Integer,TplDateEntity>> _markedMessage2 = new HashMap<String,HashMap<Integer,TplDateEntity>>();
	private HashMap<String,HashMap<Integer,TplDateEntity>> _markedMessage3 = new HashMap<String,HashMap<Integer,TplDateEntity>>();

	private taskCpl get_Task_Completed() { taskCpl o = new taskCpl(); o.arg1 = this; return o; }
	private static class taskCpl extends RunnableWithArgs<ArrayList<TplDateEntity>,Boolean> { public void run()
	{
		NotificationMessageService _this = (NotificationMessageService)this.arg1;
		Boolean result = this.result;
		try{

		if(result)
		{
			_this.UpdatePeriodCollection();
			ArrayList<TplDateEntity> list = this.arg;
			//for(TplDateEntity tde : list)
			//{
			//	if(_this._markedMessage1 == null)
			//		_this._markedMessage1 = new HashMap<String,HashMap<Integer,TplDateEntity>>();

			//	HashMap<Integer,TplDateEntity> tplArr = _this._markedMessage1.get(tde.Date);
			//	if(tplArr == null) {                    _this._markedMessage1.put(tde.Date, new HashMap<Integer,TplDateEntity>());
			//		                           tplArr = _this._markedMessage1.get(tde.Date);
			//	}
			//	TplDateEntity tpl = tplArr.get(tde.TemplateId);
			//	if(tpl == null)
			//	{
			//		_this.ShowMessageForLevel(tde);
			//		break;
			//	}
			//}

			MessageTimeLevel level;
			List<TplDateEntity> listPeriod;
			//Before
			level = MessageTimeLevel.Before;
			listPeriod = DateTime.WhereInPeriod(_this._periodCollection.get(level), list);
			if(listPeriod.size() != 0)
			if(_this.ShowMessageForLevel( _this._markedMessage1, listPeriod)) return;

			//InMoment
			level = MessageTimeLevel.Before;
			listPeriod = DateTime.WhereInPeriod(_this._periodCollection.get(level), list);
			if(listPeriod.size() != 0)
			if(_this.ShowMessageForLevel( _this._markedMessage2, listPeriod)) return;

			//After
			level = MessageTimeLevel.Before;
			listPeriod = DateTime.WhereInPeriod(_this._periodCollection.get(level), list);
			if(listPeriod.size() != 0)
			if(_this.ShowMessageForLevel( _this._markedMessage3, listPeriod)) return;
		}

		} catch(Exception e) {
			String str = e.getMessage();
			Exception ex = e;
		}
	}}


	private Task get_Task() { Task o = new Task(); o.arg1 = this; return o; }
	private static class Task extends RunnableWithEvent<ArrayList<TplDateEntity>,Boolean> { public void run()
	{
		NotificationMessageService _this = (NotificationMessageService)this.arg1;
		try{
		//group by WorkerId limit 1 offset 0 order by DateTime

		//order by DateTime limit 1 offset 0
/*"select "+
"    ch.WorkerId "+
"from "+
"    ( "+
"        select distinct TemplateId "+
"        from Checkin where Mode=1 "+startStr+"<DateTime and DateTime<"+endStr+" "+
"        order by DateTime limit 1 offset 0)"+
"    ) as ch "+
"left outer join "+
"    (select * from Template) as tpl "+
"    on ch.TemplateId = tpl.Id "*/

//"    (select "+
//"    * "+
//"    from "+
//"        (select wId from Checkin where "+startStr+"<DateTime and DateTime<"+endStr+" group by WorkerId) as worker "+
//"    left outer join "+
//"        (select * from Checkin where "+startStr+"<DateTime and DateTime<"+endStr+" ) as ch "+
//"        on worker.wId=ch.WorkerId "+
//"left outer join "+
//"    (select * from Template) as tpl "+
//"    on ch.TemplateId = tpl.Id "

//"select"+
//"    tpl.EndTime"+
//"from"+
//"    ("+
//"    select WorkerId as wId, MAX(DateTime) as MaxTime "+
//"    from Checkin where "+startStr+"<DateTime and DateTime<"+endStr+" group by WorkerId "+
//"    ) as worker "+
//"left outer join "+
//"    (select WorkerId as WorkerId, DateTime as DateTime, TemplateId as TemplateId "+
//"    from Checkin where "+startStr+"<DateTime and DateTime<"+endStr+" ) as ch "+
//"    on worker.wId=ch.WorkerId and worker.MaxTime=ch.DateTime "+
//"left outer join "+
//"    Template as tpl "+
//"    on ch.TemplateId = tpl.Id "
		String startStr = String.valueOf(DateTime.GetCurrentDateTime().AddDays(-1).ToUnixSeconds());
		String endStr =   String.valueOf(DateTime.GetCurrentDateTime().ToUnixSeconds());

		String hours24 = startStr+"<DateTime and DateTime<"+endStr;
		String sql=
"select distinct "+
"lastArr.TemplateId as TemplateId, lastArr.DateTime as DateTime, tp.StartTime as StartTime, tp.EndTime as EndTime "+
"from "+
"	( "+
"	select "+
"		ch.TemplateId, ch.Mode, ch.DateTime "+
"	from "+
"		( "+
"		select WorkerId as wId, MAX(DateTime) as MaxTime "+
"		from Checkin where "+hours24+" group by WorkerId "+
"		) as worker "+
"	left outer join "+
"		(select WorkerId as WorkerId, DateTime as DateTime, TemplateId as TemplateId, Mode as Mode "+
"		from Checkin where "+hours24+" ) as ch "+
"		on worker.wId=ch.WorkerId and worker.MaxTime=ch.DateTime "+
"	) as lastArr "+
"left outer join Template tp "+"    on lastArr.TemplateId=tp.Id "+
"where lastArr.Mode=1 "
;

	DbConnector db = DbConnector.getInstance();
	Cursor cursor = db.Select(sql, null);

	int index = -1;
	ArrayList<TplDateEntity> list = new ArrayList<TplDateEntity>();
	while (cursor.moveToNext())
	{
		//index = cursor.getColumnIndex("EndTime");if(index != -1) {
			//int endTime = cursor.getInt(index);

			TplDateEntity td = new TplDateEntity();
			td.FromCursor( TplDateEntity.class, cursor);
			list.add(td);
			td.Date = DateTime.FromUnixSecond(td.DateTime).ToDateString();
		//}
	}
	cursor.close();

	//TplDateEntity endTime = list.get(0);
	//DateTime dS = DateTime.FromUnixSecond(endTime.EndTime);

	//List<TplDateEntity> tdeList = new ArrayList<TplDateEntity>();
	Comparator<TplDateEntity> comparator = new Comparator<TplDateEntity>() {
		public int compare(TplDateEntity o1, TplDateEntity o2) {
			return o2.EndTime - o1.EndTime;
		}
	};
	Collections.sort(list, comparator);

	this.arg = list;
	this.result = true;

	} catch(Exception e) {
		String str = e.getMessage();
		Exception ex = e;
	}

	}} // Task

	public void Run()
	{
		this._service.Execute();
	}



	//*********************************************************************************************
	//*      IBaseModel
	public void ClearDependencies() {
	}
	public boolean get_IsClearDependencies() {
		return false;
	}
	public void UpdateDependencies() {
	}
	public boolean get_IsKeepAlive() {
		return false;
	}



	private static class TplDateEntity extends EntityBase implements IDateSortable2
	{
					 public String Date;
		@EntityField public int TemplateId;
		@EntityField public int DateTime;
		@EntityField public int StartTime;
		@EntityField public int EndTime;

		private void Init() {
			Fields = EntityBase.GetFieldsArrByEntity(TplDateEntity.class);
		}
		public TplDateEntity() {
			Init();
		}

		// IDateSortable
		private Calendar __calendarObj;
		public Calendar get_CalendarObj()
		{
			if( this.__calendarObj == null )
			{
				this.__calendarObj = ta.lib.Common.DateTime.FromDaySecond(this.EndTime).ToCalendar();
			}
			return this.__calendarObj;
		}

		// for save in EntityBase
		public Object[] get_Values(){
			throw new RuntimeException( ta.lib.Common.CommonHelper.CreateMessageForException("TplDateEntity.get_Values()") );
		}

		// for delete in EntityBase
		public Object get_KeyValue(){
			throw new RuntimeException( ta.lib.Common.CommonHelper.CreateMessageForException("TplDateEntity.get_KeyValue()") );
		}
	}
}
