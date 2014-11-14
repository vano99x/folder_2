package ta.lib.Thread;

import android.os.Handler;

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

public class BackgroundService
{
	private ScheduledExecutorService _scheduled;
	private final Lock _lock;

	private MainEngine _engine;
	private IAppService __appService;
	private boolean __isShowErrorMsb;
	protected RunnableWithEvent __targetFunc;

	public BackgroundService(RunnableWithEvent tF)
	{
		this._scheduled = null;
		this._lock = new ReentrantLock();

		this.__isShowErrorMsb = false;
		this._engine = MainEngine.getInstance();
		this.__appService = Bootstrapper.Resolve( IAppService.class );
		this.__appService.get_Closing().Add(get_onClosing());
		this.__appService.get_Running().Add(get_onRunning());
		this.__appService.add_Logout(get_onLogout());

		_scheduled = Executors.newScheduledThreadPool(1);

		this.__targetFunc = tF;
		final BackgroundService service = this;
		Runnable task = new Runnable()
		{
			private BackgroundService _service = service;
			@Override
			public void run() {
				_service.Execute();
			}
		};
		_scheduled.scheduleAtFixedRate(task, 3L, 150L, TimeUnit.SECONDS);
	}

	//protected void SetTargetFuncAndRunService()
	//{
	//}



	//*********************************************************************************************
	//**     Event Handler
	private       onCls get_onClosing() { onCls o = new onCls(); o.arg1 = this; return o; }
	private class onCls extends RunnableWithArgs<Object,Object> { public void run()
	{
		BackgroundService _this = (BackgroundService)this.arg1;
		_this.__isShowErrorMsb = false;
	}}
	private       onRn get_onRunning() { onRn o = new onRn(); o.arg1 = this; return o; }
	private class onRn extends RunnableWithArgs<Object,Object> { public void run()
	{
		BackgroundService _this = (BackgroundService)this.arg1;
		_this.__isShowErrorMsb = true;
	}}
	private       onLg get_onLogout() { onLg o = new onLg(); o.arg1 = this; return o; }
	private class onLg extends RunnableWithArgs<Object,Object> { public void run()
	{
		BackgroundService _this = (BackgroundService)this.arg1;
		_this._scheduled.shutdown();
		_this._scheduled = null;
	}}



	public void Execute()
	{
		int aaa = 9;
		if( _lock.tryLock() )
		{
			try {
				//this.__targetFunc.run();
				this.__targetFunc.call();

				final RunnableWithEvent tF  = this.__targetFunc;

				Handler h = ta.timeattendance.MainActivity.get_RespondHandler();
				h.post(new Runnable(){public void run(){

					tF.BackgroundFuncComplete.RunEvent( tF.arg, tF.result);

				}});
			}
			catch( Exception e)
			{
				if(this.__isShowErrorMsb)
				{
					HttpHelper.ExceptionHandler( e );
				}
			}
			finally
			{
				_lock.unlock();
			}
		}
	}
}
