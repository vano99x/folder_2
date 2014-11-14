package ta.timeattendance.Models;

import ta.lib.*;
import ta.lib.DatePicker.SelectedDateEventArgs;
import ta.Database.*;

public class CategoryModel implements ICategoryModel
{
	public CategoryModel()
	{
		this.CurrentCategoryChanged = new CurrentCategoryChangedEventClass();
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
	class   CurrentCategoryChangedEventClass extends Event<Category,Boolean> {}
	private CurrentCategoryChangedEventClass CurrentCategoryChanged;
	public void set_CurrentCategoryChanged(RunnableWithArgs runnable)
	{
		this.CurrentCategoryChanged.Add(runnable);
	}



	//*********************************************************************************************
	//**     Property
	private Category __currentCategory;
	//@Override
	public Category get_CurrentCategory()
	{
		return this.__currentCategory;
	}
	//@Override
	public void set_CurrentCategory(Category c)
	{
		this.__currentCategory = c;

		if(this.__currentCategory == null)
		{
		}
		else
		{
			this.CurrentCategoryChanged.RunEvent( this.__currentCategory );
		}
	}
}
