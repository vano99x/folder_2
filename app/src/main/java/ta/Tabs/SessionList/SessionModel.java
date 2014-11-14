package ta.Tabs.SessionList;

public class SessionModel implements ISessionModel
{
	public SessionModel()
	{

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
	//**     Property
	private SessionMode __sessionMode;
	public SessionMode get_SessionMode()
	{
		return this.__sessionMode;
	}
	public void set_SessionMode(SessionMode m)
	{
		this.__sessionMode = m;

		//if(this.__currentTemplate == null)
		//{
		//}
		//else
		//{
		//	this.CurrentTemplateChanged.RunEvent( this.__currentTemplate );
		//}
	}
}
