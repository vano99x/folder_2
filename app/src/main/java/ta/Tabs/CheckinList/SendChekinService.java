package ta.Tabs.CheckinList;

import java.util.TimerTask;
import java.util.Timer;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import ta.lib.*;
import ta.timeattendance.Models.*;
import ta.Database.*;
import ta.timeattendance.Models.*;
import ta.timeattendance.Services.*;
import ta.timeattendance.MainEngine;

public class SendChekinService implements ISendChekinService
{
	private MainEngine _engine;
	private static ScheduledExecutorService _scheduled;
	private static final Lock _lock;
	private IAppService __appService;
	private boolean __isShowErrorMsb;

	static // static ctor
	{
		_scheduled = null;
		_lock = new ReentrantLock();
	}
	
	public SendChekinService()
	{
		this.__isShowErrorMsb = false;
		this._engine = MainEngine.getInstance();
		this.__appService = Bootstrapper.Resolve( IAppService.class );
		this.__appService.get_Closing().Add(get_onClosing());
		this.__appService.get_Running().Add(get_onRunning());
		this.__appService.get_Logout().Add(get_onLogout());

		if(_scheduled != null)
		{
			_scheduled.shutdown();
			_scheduled = null;
			_scheduled = Executors.newScheduledThreadPool(1);
		}
		else
		{
			_scheduled = Executors.newScheduledThreadPool(1);
		}
		
		final SendChekinService service = this;

		Runnable task = new Runnable()
		{
			SendChekinService _service = service;
			@Override
			public void run() {
				_service.SendCheckin();
			}
		};

		_scheduled.scheduleAtFixedRate(task, 5L, 600L, TimeUnit.SECONDS);
	}
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



	//*********************************************************************************************
	//**     Event Handler
	private       onCls get_onClosing() { onCls o = new onCls(); o.arg1 = this; return o; }
	private class onCls extends RunnableWithArgs<Object,Object> { public void run()
	{
		SendChekinService _this = (SendChekinService)this.arg1;
		_this.__isShowErrorMsb = false;
	}}
	private       onRn get_onRunning() { onRn o = new onRn(); o.arg1 = this; return o; }
	private class onRn extends RunnableWithArgs<Object,Object> { public void run()
	{
		SendChekinService _this = (SendChekinService)this.arg1;
		_this.__isShowErrorMsb = true;
	}}
	private       onLg get_onLogout() { onLg o = new onLg(); o.arg1 = this; return o; }
	private class onLg extends RunnableWithArgs<Object,Object> { public void run()
	{
		//SendChekinService _this = (SendChekinService)this.arg1;
		SendChekinService._scheduled.shutdown();
		SendChekinService._scheduled = null;
	}}



	class SendCheckinBFClass extends BackgroundFunc<Object,Boolean> {}
	public void SendCheckin()
	{
		int aaa = 9;
		if( _lock.tryLock() )
		{
			try {
				BackgroundFunc.Go( new SendCheckinBFClass(), get_Send(), get_SendComplete(), "-SendCheckin-");
			}
			finally {
				_lock.unlock();
			}
		}
	}
	
	private SendComplete get_SendComplete() { SendComplete o = new SendComplete(); o.arg1 = this; return o; }
	class   SendComplete extends RunnableWithArgs<Object,Boolean> { public void run()
	{
		boolean result = this.result;
		if(result)
		{
			int aaa = 9;
		}
	}}
	
	private Send get_Send() { Send o = new Send(); o.arg1 = this; return o; }
	class Send extends RunnableWithArgs<Object,Boolean> { public void run()
	{
		SendChekinService _this = (SendChekinService)this.arg1;
		boolean result = false;

		if( HttpHelper.IsInternetAvailable(_this._engine.mContext))
		{
			try {
				CheckinSender.SendCheckinArray(_this._engine.mContext);
			}
			catch( Exception e)
			{
				if(_this.__isShowErrorMsb)
				{
					HttpHelper.ExceptionHandler( e );
				}
				else
				{
					int aaa = 9;
					int aaa2 = aaa-2;
				}

				result = false;
			}
		}
		this.result = result;
	}}
}