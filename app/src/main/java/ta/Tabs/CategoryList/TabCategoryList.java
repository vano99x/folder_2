package ta.Tabs.CategoryList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import ta.lib.*;
import ta.Database.*;
import ta.lib.tabui.*;
import ta.timeattendance.*;
import ta.timeattendance.Models.*;

import ta.timeattendance.R;

public class TabCategoryList extends Tab implements View.OnClickListener
{
	private ICategoryModel __categoryModel;
	private ISupervisorModel __svModel;
	private ListCategoryAdapter listAdapter;

	private ListView _listView;
	private TextView _headLabel;
	private TextView _emptyLabel;

	public TabCategoryList(Context context, ViewGroup paramViewGroup, int int1, int int2)
	{
		super(context, paramViewGroup, int1, int2);
		this.listAdapter = null;

		_listView   = (ListView)this.root.findViewById(R.id.Category_ListView_Id);
		_headLabel  = (TextView)this.root.findViewById(R.id.Category_HeadTextView_Id);
		_emptyLabel = (TextView)this.root.findViewById(R.id.Category_EmptyListTextView_Id);
		
		this.__svModel = Bootstrapper.Resolve( ISupervisorModel.class );
		this.__categoryModel = Bootstrapper.Resolve( ICategoryModel.class );
	}



	//*********************************************************************************************
	//**     ui
	private void UpdateCtrlData()
	{
		if(this.listAdapter != null && this.listAdapter.getCount() > 0) {
			//this.mLableHead.setVisibility(     View.VISIBLE);
			//this.mLableEmptyList.setVisibility(View.INVISIBLE);
			Tab.Show(this._headLabel);
			Tab.Hide(this._emptyLabel);
		} else {
			//this.mLableHead.setVisibility(     View.INVISIBLE);
			//this.mLableEmptyList.setVisibility(View.VISIBLE);
			Tab.Hide(this._headLabel);
			Tab.Show(this._emptyLabel);
		}
	}



	//*********************************************************************************************
	//**     Override
	@Override
	public void Show()
	{
		super.Show();

		Personel supervisor = this.__svModel.get_CurrentSupervisor();
		Category [] categoryArr = supervisor.get_Categories();

		if (this.listAdapter == null)
		{
			this.listAdapter = new ListCategoryAdapter(this.context, categoryArr, this);
		}
		this._listView.setAdapter(this.listAdapter);

		this.UpdateCtrlData();
	}

	@Override
	public void Hide()
	{
		super.Hide();
		this.listAdapter = null;
	}



	//*********************************************************************************************
	//**     Control Handler
	public void ListItem_Selected(Category category)
	{
		this.__categoryModel.set_CurrentCategory(category);
		UIHelper.Instance().switchState(MainActivity.State.FLAG_TEMPLATE);
	}



	public void onClick(View ctrl)
	{
		Object tag = ctrl.getTag();
		Object [] arr = (Object[])tag;
		Integer integer = (Integer)arr[0];

		if( integer != null)
		{
			switch(integer)
			{
				case R.id.Category_Item_TagId:{
					ListItem_Selected((Category)arr[1]);
				break;}
			}
		}
	}
}
