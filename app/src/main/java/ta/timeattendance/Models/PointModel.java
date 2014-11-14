package ta.timeattendance.Models;

import ta.lib.*;
import ta.lib.DatePicker.SelectedDateEventArgs;
import ta.Database.*;

public class PointModel implements IPointModel
{
	//private ISupervisorModel __svModel;

	public PointModel()
	{
		this.CurrentPointChanged = new CurrentPointChangedEventClass();
		//this.__svModel = Bootstrapper.Resolve( ISupervisorModel.class );
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
	class   CurrentPointChangedEventClass extends Event<Point,Boolean> {}
	private CurrentPointChangedEventClass CurrentPointChanged;
	@Override
	public void set_CurrentPointChanged(RunnableWithArgs runnable)
	{
		this.CurrentPointChanged.Add(runnable);
	}



	//*********************************************************************************************
	//**     Event Handler



	//*********************************************************************************************
	//**     Property
	private Point __currentPoint;
	@Override
	public Point get_CurrentPoint()
	{
		return this.__currentPoint;
	}
	@Override
	public void set_CurrentPoint(Point p)
	{
		this.__currentPoint = p;

		if(this.__currentPoint == null)
		{
		}
		else
		{
			this.CurrentPointChanged.RunEvent( this.__currentPoint );
		}
	}
}