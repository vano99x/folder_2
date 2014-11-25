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

	private String _ownerName;


	public BackgroundService(RunnableWithEvent tF)
	{
		this(tF,null);
	}

	public BackgroundService(RunnableWithEvent tF, String ownerName)
	{
		this._ownerName = ownerName;
		this._scheduled = null;
		this._lock = new ReentrantLock();

		this.__isShowErrorMsb = false;
		this._engine = MainEngine.getInstance();
		this.__appService = Bootstrapper.Resolve( IAppService.class );
		this.__appService.get_Closing().Add(get_onClosing());
		this.__appService.get_Running().Add(get_onRunning());

		this.__appService.add_Logout(get_onClearBgService());
		this.__appService.add_Clearing(get_onClearBgService());

		this.__targetFunc = tF;

		this.RunInternalService();
	}

	private void RunInternalService()
	{
		CloseInternalService();

		_scheduled = Executors.newScheduledThreadPool(1);
		//final BackgroundService service = this;
		Runnable task = new RunnableWithArgs<Object,Object>(this)
		{
			//private BackgroundService _service = service;
			@Override
			public void run() {
				//_service.Execute();
				((BackgroundService)this.arg1).Execute();
			}
		};
		_scheduled.scheduleAtFixedRate(task, 3L, 10L, TimeUnit.SECONDS);
			String m = "Message";
			if(m.equals(this._ownerName))
			{
				int aaa = 9;
				int aaa2 = aaa-2;
			}
	}

	private void CloseInternalService()
	{
		if(this._scheduled != null)
		{
			this._scheduled.shutdown();
			String m = "Message";
			if(m.equals(this._ownerName))
			{
				int aaa = 9;
				int aaa2 = aaa-2;
			}
		}
		this._scheduled = null;
	}



	//*********************************************************************************************
	//**     Event Handler
	private              temp1 get_onCreateBgService() { temp1 o = new temp1(); o.arg1 = this; return o; }
	private static class temp1 extends RunnableWithArgs<Object,Object> { public void run()
	{
		BackgroundService _this = (BackgroundService)this.arg1;
		_this.RunInternalService();
	}}
	private              temp2 get_onClearBgService() { temp2 o = new temp2(); o.arg1 = this; return o; }
	private static class temp2 extends RunnableWithArgs<Object,Object> { public void run()
	{
		BackgroundService _this = (BackgroundService)this.arg1;
		_this.CloseInternalService();
	}}

	private              onCls get_onClosing() { onCls o = new onCls(); o.arg1 = this; return o; }
	private static class onCls extends RunnableWithArgs<Object,Object> { public void run()
	{
		BackgroundService _this = (BackgroundService)this.arg1;
		_this.__isShowErrorMsb = false;
	}}
	private              onRn get_onRunning() { onRn o = new onRn(); o.arg1 = this; return o; }
	private static class onRn extends RunnableWithArgs<Object,Object> { public void run()
	{
		BackgroundService _this = (BackgroundService)this.arg1;
		_this.__isShowErrorMsb = true;
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
