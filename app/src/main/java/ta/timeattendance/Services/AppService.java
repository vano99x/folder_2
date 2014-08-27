package ta.timeattendance.Services;

import ta.lib.*;
import ta.timeattendance.Models.*;

public class AppService implements IAppService
{
	public AppService()
	{
		this.Create = new CreateEventClass();
		this.Closing = new ClosingEventClass();
		this.Running = new RunningEventClass();
		this.Logout = new LogoutEventClass();
		this.GotFocus = new GotFocusEventClass();
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



	public class CreateEventClass extends Event<Object,Object> {}
	private CreateEventClass Create;
	public CreateEventClass get_Create(){ return this.Create; }
	public void CreateRunEvent()
	{
		this.Create.RunEvent( null );
	}

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


	public class LogoutEventClass extends Event<Object,Object> {}
	private LogoutEventClass Logout;
	public LogoutEventClass get_Logout(){ return this.Logout; }
	public void LogoutRunEvent()
	{
		this.Logout.RunEvent( null );
	}


	public class GotFocusEventClass extends Event<Object,Object> {}
	private GotFocusEventClass GotFocus;
	public GotFocusEventClass get_GotFocus(){ return this.GotFocus; }
	public void GotFocusRunEvent()
	{
		this.GotFocus.RunEvent( null );
	}

}