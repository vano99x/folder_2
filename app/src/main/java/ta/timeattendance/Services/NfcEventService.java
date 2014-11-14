package ta.timeattendance.Services;

import android.os.Handler;

import ta.lib.*;
import ta.lib.DatePicker.SelectedDateEventArgs;
import ta.Database.*;
import ta.timeattendance.MainActivity;
import ta.timeattendance.MainActivityProxy;
import ta.timeattendance.Models.Bootstrapper;

public class NfcEventService implements INfcEventService
{
	private NfcThread _nfcThread;
	private IAppService __appService;

	public NfcEventService()
	{
		this.NewTagReceivedEvent = new NewTagReceivedEventClass();

		this.__appService = Bootstrapper.Resolve(IAppService.class);
		this.__appService.add_Clearing(get_onClearing());
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
	private       clrn get_onClearing() { clrn o = new clrn(); o.arg1 = this; return o; }
	private class clrn extends RunnableWithArgs<Object,Object> { public void run()
	{
		NfcEventService _this = (NfcEventService)this.arg1;
		_this._nfcThread.Stop();
	}}



	//private MainActivity __mainActivity;
	public void Start(MainActivity ma)
	{
		//__mainActivity = ma;
		this._nfcThread = new NfcThread(ma, this);
	}



	//*********************************************************************************************
	//**     Event
	private static class NewTagReceivedEventClass extends Event<Long,Object> {}
	private NewTagReceivedEventClass NewTagReceivedEvent;
	public void add_NewTagReceived(RunnableWithArgs runnable)
	{
		this.NewTagReceivedEvent.Add(runnable);
	}

	// not exist in INfcEventService (use in nfcThread)
	public void RunEvent(long l)
	{
		//BackgroundFunc.RunEventInUiThread(this.NewTagReceivedEvent, l, null);
		Handler h = ta.timeattendance.MainActivity.get_RespondHandler();
		h.post(new RunnableWithArgs(this.NewTagReceivedEvent, l, null){public void run()
		{
			((Event<Long,Object>)this.arg1).RunEvent( (Long)this.arg2, (Object)this.arg3);
			MainActivity.get_FragmentActivityStatic().NfcNotBusy();
		}});
	}
}
