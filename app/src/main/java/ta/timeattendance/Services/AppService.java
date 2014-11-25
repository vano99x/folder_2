package ta.timeattendance.Services;

import ta.lib.*;
import ta.timeattendance.Models.*;

public class AppService implements IAppService
{
	boolean isLogin;

	public AppService()
	{
		this.isLogin = false;
		this.CreateEvent = new CreateEventClass();
		this.Closing = new ClosingEventClass();
		this.Running = new RunningEventClass();
		this.Logout = new LogoutEventClass();
		this.GotFocus = new GotFocusEventClass();

		this.ClearEvent       = new       ClearEventClass();
		this.LogoutClearEvent = new LogoutClearEventClass();
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



	//public class CreateEventClass extends Event<Object,Object> {}
	//private CreateEventClass Create;
	//public CreateEventClass get_Create(){ return this.Create; }
	//public void CreateRunEvent()
	//{
	//	this.Create.RunEvent( null );
	//}
	private static class CreateEventClass extends Event<Object,Object> {}
	private CreateEventClass CreateEvent;
	public void add_Creating(RunnableWithArgs r) { this.CreateEvent.Add(r); }
	public void     CreatingRunEvent()           { this.CreateEvent.RunEvent();    }



	public class ClosingEventClass extends Event<Object,Object> {}
	private ClosingEventClass Closing;
	public ClosingEventClass get_Closing(){ return this.Closing; }
	public void ClosingRunEvent()
	{
		this.Closing.RunEvent( null );
	}


	public class RunningEventClass extends Event<Object,Object> {}
	private RunningEventClass Running;
	public RunningEventClass get_Running(){ return this.Running; }
	public void RunningRunEvent()
	{
		this.Running.RunEvent( null );
	}


	public class GotFocusEventClass extends Event<Object,Object> {}
	private GotFocusEventClass GotFocus;
	public GotFocusEventClass get_GotFocus(){ return this.GotFocus; }
	public void GotFocusRunEvent()
	{
		this.GotFocus.RunEvent( null );
	}

	public void Login()
	{
		this.isLogin = true;
	}
	private static class LogoutEventClass extends Event<Object,Object> {}
	private LogoutEventClass Logout;
	public void add_Logout(RunnableWithArgs r){ this.Logout.Add(r); }
	public void LogoutRunEvent()
	{
		this.isLogin = false;
		this.Logout.RunEvent( null );
	}


	private static class ClearEventClass extends Event<Object,Object> {}
	private ClearEventClass ClearEvent;
	public void add_Clearing(RunnableWithArgs runnable) { this.ClearEvent.Add(runnable); }
	public void     ClearingRunEvent()                  { this.ClearEvent.RunEvent();    }


	private static class LogoutClearEventClass extends Event<Object,Object> {}
	private LogoutClearEventClass LogoutClearEvent;
	public void add_LogoutClearing(RunnableWithArgs r) { this.LogoutClearEvent.Add(r); }
	public void LogoutClearingRunEvent()
	{
		if(this.isLogin == false)
		{
			this.LogoutClearEvent.RunEvent();
		}
	}

}