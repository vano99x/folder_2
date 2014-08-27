package ta.timeattendance.Models;

import ta.lib.*;
import ta.Database.*;
import ta.timeattendance.Services.*;

public class SupervisorModel implements ISupervisorModel
{
	private IAppService __appService;
	//private static int _counter;
	private boolean __isClearDependencies;
	private boolean __isKeepAlive;
	public SupervisorModel()
	{
		//_counter++;
		this.__isClearDependencies = false;
		this.__isKeepAlive = true;
		this.CurrentSuperviserApplied = new CurrentSuperviserAppliedEventClass();

		UpdateDependencies();
	}



	public void ClearDependencies()
	{
		this.__appService = null;
		this.__isClearDependencies = true;
	}
	public boolean get_IsClearDependencies() {
		return this.__isClearDependencies;
	}
	public void UpdateDependencies()
	{
		this.__appService = Bootstrapper.Resolve( IAppService.class );
		this.__appService.get_Logout().Add(get_onLogout());
		this.__isClearDependencies = false;
	}
	public boolean get_IsKeepAlive() {
		return this.__isKeepAlive;
	}


	//*********************************************************************************************
	//**     Event Handler
	private       onCls get_onLogout() { onCls o = new onCls(); o.arg1 = this; return o; }
	private class onCls extends RunnableWithArgs<Object,Object> { public void run()
	{
		SupervisorModel _this = (SupervisorModel)this.arg1;
		_this.__currentSuperviser = null;
		_this.__isKeepAlive = false;
	}}



	//*********************************************************************************************
	//**     Event
	class   CurrentSuperviserAppliedEventClass extends Event<Personel,Object> {}
	private CurrentSuperviserAppliedEventClass CurrentSuperviserApplied;
	@Override
	public void SvChanged_EventAdd(RunnableWithArgs runnable)
	{
		int aaa = 9;
		CurrentSuperviserApplied.Add(runnable);
	}



	//*********************************************************************************************
	//**     Property
	private Personel __currentSuperviser;
	@Override
	public Personel get_CurrentSuperviser()
	{
		return this.__currentSuperviser;
	}
	@Override
	public void set_CurrentSuperviser(Personel p)
	{
		if(p != null)
		{
			this.__currentSuperviser = p;
			this.CurrentSuperviserApplied.RunEvent( this.__currentSuperviser );
		}
	}
	
	static
	{
		//_counter = 0;
	}
}
