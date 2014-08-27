package ta.timeattendance.Models;

import ta.lib.*;
import ta.timeattendance.Models.*;
import ta.Database.*;
import ta.timeattendance.Services.*;

public class SettingSvModel implements ISettingSvModel
{
	private ISupervisorModel __svModel;
	private IPointModel      __pointModel;
	private IAppService __appService;

	public SettingSvModel()
	{
		this.PointSettingChanged = new PointSettingChangedEventClass();

		this.__appService = Bootstrapper.Resolve( IAppService.class );
		this.__appService.get_Closing().Add(get_onClosing());
		this.__appService.get_Create().Add(get_onSupervisorChanged());
		//AppService.CreateEventClass evt = this.__appService.get_Create();
		//h1 handler = get_onSupervisorChanged();
		//evt.Add(handler);

		this.__svModel = Bootstrapper.Resolve( ISupervisorModel.class );
		this.__svModel.SvChanged_EventAdd(get_onSupervisorChanged2());
		this.__pointModel = Bootstrapper.Resolve( IPointModel.class );
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
	//**     Event
	class PointSettingChangedEventClass extends Event<Point,Object> {}
	private PointSettingChangedEventClass PointSettingChanged;
	public void PointSettingChanged_Add(RunnableWithArgs runnable)
	{
		PointSettingChanged.Add(runnable);
	}



	//*********************************************************************************************
	//**     Event Handler
	private       onCls get_onClosing() { onCls o = new onCls(); o.arg1 = this; return o; }
	private class onCls extends RunnableWithArgs<Object,Object> { public void run()
	{
		SettingSvModel _this = (SettingSvModel)this.arg1;

		Point p = _this.__pointModel.get_CurrentPoint();
		if(p != null)
		{
			Personel sv = _this.__svModel.get_CurrentSuperviser();
			if(sv != null)
			{
				int id = _this.__svModel.get_CurrentSuperviser().Id;
				SettingSv.UpdatePoint(id, p.Id);
			}
		}
	}}

	private       h2 get_onSupervisorChanged2() { h2 o = new h2(); o.arg1 = this; return o; }
	private class h2 extends RunnableWithArgs<Personel,Object> { public void run()
	{
		h1 handler = get_onSupervisorChanged();
		handler.run();
	}}

	private       h1 get_onSupervisorChanged() { h1 o = new h1(); o.arg1 = this; return o; }
	private class h1 extends RunnableWithArgs<Object,Object> { public void run()
	{
		//Point p = _this.__pointModel.get_CurrentPoint();
		//_this.PointSettingChanged.RunEvent( point );

		SettingSvModel _this = (SettingSvModel)this.arg1;
		//Personel sv = this.arg;
		Personel sv = _this.__svModel.get_CurrentSuperviser();
		if(sv != null)
		{
			Point p = _this.__pointModel.get_CurrentPoint();
			if(p == null)
			{
				Point point = SettingSv.SelectPoint(sv.Id);
				_this.__pointModel.set_CurrentPoint( point );
			}
		}
	}}
}