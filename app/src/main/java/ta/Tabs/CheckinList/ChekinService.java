package ta.Tabs.CheckinList;

import java.util.TimerTask;
import java.util.Timer;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import ta.lib.*;
import ta.lib.UIHelper.Act;
import ta.lib.Thread.BackgroundService;
import ta.timeattendance.Models.*;
import ta.Database.*;
import ta.timeattendance.Models.*;
import ta.timeattendance.Services.*;
import ta.timeattendance.MainEngine;

public class ChekinService implements IChekinService
{
	private MainEngine _engine;
	//private static ScheduledExecutorService _scheduled;
	//private static final Lock _lock;
	//private IAppService __appService;
	//private boolean __isShowErrorMsb;

	static // static ctor
	{
		//_scheduled = null;
		//_lock = new ReentrantLock();
	}

	private BackgroundService _service;

	private static class SaveCheckinCompleteEventClass extends Event<Object,Boolean> {}
	private SaveCheckinCompleteEventClass SaveCheckinCompleteEvent;
	public void add_SaveCheckinComplete(RunnableWithArgs runnable)
	{
		this.SaveCheckinCompleteEvent.Add(runnable);
	}
	
	public ChekinService()
	{
		this._service = new BackgroundService(   get_Send().Add(get_SendComplete())   );
		this.SaveCheckinCompleteEvent = new SaveCheckinCompleteEventClass();

		//this.__isShowErrorMsb = false;
		this._engine = MainEngine.getInstance();
		//this.__appService = Bootstrapper.Resolve( IAppService.class );
		//this.__appService.get_Closing().Add(get_onClosing());
		//this.__appService.get_Running().Add(get_onRunning());
		//this.__appService.get_Logout().Add(get_onLogout());

		//if(_scheduled != null)
		//{
		//	_scheduled.shutdown();
		//	_scheduled = null;
		//	_scheduled = Executors.newScheduledThreadPool(1);
		//}
		//else
		//{
		//	_scheduled = Executors.newScheduledThreadPool(1);
		//}
		
		//final SendChekinService service = this;

		//Runnable task = new Runnable()
		//{
		//	SendChekinService _service = service;
		//	@Override
		//	public void run() {
		//		_service.SendCheckin();
		//	}
		//};

		//_scheduled.scheduleAtFixedRate(task, 5L, 600L, TimeUnit.SECONDS);
	}



	//*********************************************************************************************
	//**     Event Handler
	//private       onCls get_onClosing() { onCls o = new onCls(); o.arg1 = this; return o; }
	//private class onCls extends RunnableWithArgs<Object,Object> { public void run()
	//{
	//	SendChekinService _this = (SendChekinService)this.arg1;
	//	_this.__isShowErrorMsb = false;
	//}}
	//private       onRn get_onRunning() { onRn o = new onRn(); o.arg1 = this; return o; }
	//private class onRn extends RunnableWithArgs<Object,Object> { public void run()
	//{
	//	SendChekinService _this = (SendChekinService)this.arg1;
	//	_this.__isShowErrorMsb = true;
	//}}
	//private       onLg get_onLogout() { onLg o = new onLg(); o.arg1 = this; return o; }
	//private class onLg extends RunnableWithArgs<Object,Object> { public void run()
	//{
	//	//SendChekinService _this = (SendChekinService)this.arg1;
	//	SendChekinService._scheduled.shutdown();
	//	SendChekinService._scheduled = null;
	//}}



	//class SendCheckinBFClass extends BackgroundFunc<Object,Boolean> {}
	//public void SendCheckin()
	//{
	//	int aaa = 9;
	//	if( _lock.tryLock() )
	//	{
	//		try {
	//			BackgroundFunc.Go( new SendCheckinBFClass(), get_Send(), get_SendComplete(), "-SendCheckin-");
	//		}
	//		finally {
	//			_lock.unlock();
	//		}
	//	}
	//}



	//*********************************************************************************************
	//**     Send Checkin
	public void SendCheckin()
	{
		new Thread(new Runnable(){public void run()
		{
			_service.Execute();

		}},"-SendCheckin-").start();
	}

	private SendComplete get_SendComplete() { SendComplete o = new SendComplete(); o.arg1 = this; return o; }
	private static class SendComplete extends RunnableWithArgs<Object,Boolean> { public void run()
	{
		boolean result = this.result;
		if(result)
		{
			int aaa = 9;
		}
	}}
	
	private Send get_Send() { Send o = new Send(); o.arg1 = this; return o; }
	private static class Send extends RunnableWithEvent<Object,Boolean> { @Override public void call() throws Exception
	{
		ChekinService _this = (ChekinService)this.arg1;

		if( HttpHelper.IsInternetAvailable(_this._engine.mContext))
		{
			CheckinSender.SendCheckinArray(_this._engine.mContext);
		}

		this.result = true;
	}public void run(){}}



	//*********************************************************************************************
	//**     Save Checkin to local db


	//************************************************************************************************
	// 3 save checkin from personel info
	//class SaveCheckinBFClass extends BackgroundFunc<Checkin,Boolean> {}
	public void SaveCheckin(Checkin ch) // ---> UI thread and BG thread
	{
		//if((this.get_WorkerAppliedNFC() != null) && (this.get_WorkerAppliedNFC().Id != -1) )
		//{
		//	BackgroundFunc.Go( new SaveCheckinBFClass(), get_onSaveCheckin(), get_onBfComplete(), "-SaveCheckin-");
		//}
		//BackgroundFunc.Go(    get_onSaveCheckin(ch).Add(get_onBfComplete()),    "-SaveCheckin-"    );
		RunnableWithEvent<Checkin,Boolean> temp = get_onSaveCheckin(ch).Add(get_onBfComplete());
        temp.run();
        temp.BackgroundFuncComplete.RunEvent(temp.arg,temp.result);
	}
	
	private temp1 get_onSaveCheckin(Checkin ch) { temp1 o = new temp1(); o.arg1 = this; o.arg2 = ch; return o; }
	private static class temp1 extends RunnableWithEvent<Checkin,Boolean> { public void run()
	{
		ChekinService engine = (ChekinService)this.arg1;
		Checkin ch = (Checkin)this.arg2;
		boolean result = false;
		//Checkin ch = null;

		//MsgFromBackground(Act.StartOperation);

		//Point p = engine.__pointModel.get_CurrentPoint();
		//Personel w = engine.get_WorkerAppliedNFC();
		//if(p == null)
		//{
		//	result = false;
		//	MsgFromBackground( Act.CheckpointNotSelect );
		//}
		//else //if(!w.IsDismiss)
		{
			//ICategoryModel cm = Bootstrapper.Resolve( ICategoryModel.class );
			//int categoryId = cm.get_CurrentCategory().Id;
			//ITemplateModel tm = Bootstrapper.Resolve( ITemplateModel.class );
			//int templateId = tm.get_CurrentTemplate().Id;

			/*ch = new Checkin(
				engine.__svModel.get_CurrentSupervisor().Id,	// SupervicerId
				w.Id,											// WorkerId
				w.CardId,										// CardId
				engine.getCurrentMode(),						// Mode
				p.Id,											// PointId
				ta.lib.Common.DateTime.GetCurrentUnixSeconds(),		// DateTime
				categoryId,
				templateId
			);*/

			ch.IsCheckinExistOnServer = false;
			ch.save(  );
			//MsgFromBackground( Act.CheckinSaveLocal );
			result = true;

		}

		this.arg = ch;
		this.result = result;
	}}

	private temp2 get_onBfComplete() { temp2 o = new temp2(); o.arg1 = this; return o; }
	private static class temp2 extends RunnableWithArgs<Checkin,Boolean> { public void run()
	{
		ChekinService _this = (ChekinService)this.arg1;
		Boolean res = this.result;
		_this.SaveCheckinCompleteEvent.RunEvent(null,res);
	}}



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
}