package ta.timeattendance.Models;

import ta.lib.*;
import ta.lib.DatePicker.SelectedDateEventArgs;
import ta.Database.*;

public class TemplateModel implements ITemplateModel
{
	public TemplateModel()
	{
		this.CurrentTemplateChanged = new CurrentTemplateChangedEventClass();
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
	class   CurrentTemplateChangedEventClass extends Event<Template,Boolean> {}
	private CurrentTemplateChangedEventClass CurrentTemplateChanged;
	public void add_CurrentTemplateChanged(RunnableWithArgs runnable)
	{
		this.CurrentTemplateChanged.Add(runnable);
	}



	//*********************************************************************************************
	//**     Property
	private Template __currentTemplate;
	public Template get_CurrentTemplate()
	{
		return this.__currentTemplate;
	}
	public void set_CurrentTemplate(Template c)
	{
		this.__currentTemplate = c;

		if(this.__currentTemplate == null)
		{
		}
		else
		{
			this.CurrentTemplateChanged.RunEvent( this.__currentTemplate );
		}
	}
}
