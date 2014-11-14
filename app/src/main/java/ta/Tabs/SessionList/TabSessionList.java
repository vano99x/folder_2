package ta.Tabs.SessionList;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ListAdapter;
import java.util.ArrayList;

import ta.lib.*;
import ta.lib.Common.*;
import ta.Database.*;
import ta.Entity.*;
import ta.lib.DatePicker.DoubleDatePicker;
import ta.lib.DatePicker.SelectedDateEventArgs;
import ta.lib.tabui.*;
import ta.timeattendance.*;
import ta.timeattendance.Models.*;

import ta.timeattendance.R;

public class TabSessionList extends Tab implements View.OnClickListener
{
	//private ITemplateModel     __templateModel;
	//private ISupervisorModel   __svModel;
	//private ICategoryModel     __categoryModel;
	//private ListSessionAdapter _listAdapter;

	private ListView _listView;
	//private TextView _headLabel;
	private TextView _emptyLabel;
	private DoubleDatePicker _doubleDatePicker;
	private ISessionModel   __sessionModel;

	public TabSessionList(ta.timeattendance.MainActivityProxy context, ViewGroup viewGroup, int i1, int i2)
	{
		super(context, viewGroup, i1, i2);

		this._doubleDatePicker = 
			new DoubleDatePicker(
				context,
				DateTime.GetCurrentDateTime().AddDays(-15),
				DateTime.GetCurrentDateTime()
			);
		this._doubleDatePicker.set_SelectedDateChanged(get_onSelectedDateChanged());

		View leftFilterBtn = this.root.findViewById(R.id.Session_LeftFilterBtn_Id);
		leftFilterBtn.setOnClickListener(this);
		leftFilterBtn.setTag( new Object[]{R.id.Session_LeftFilterBtn_Id} );

		View rightFilterBtn = this.root.findViewById(R.id.Session_RightFilterBtn_Id);
		rightFilterBtn.setOnClickListener(this);
		rightFilterBtn.setTag(new Object[]{R.id.Session_RightFilterBtn_Id});

		//_headLabel  = (TextView)this.root.findViewById(R.id.Session_Head_Id);
		_emptyLabel = (TextView)this.root.findViewById(R.id.Session_EmptyList_Id);
		_listView   = (ListView)this.root.findViewById(R.id.Session_ListView_Id);

		//this.__svModel = Bootstrapper.Resolve( ISupervisorModel.class );
		//this.__templateModel = Bootstrapper.Resolve( ITemplateModel.class );
		//this.__categoryModel = Bootstrapper.Resolve( ICategoryModel.class );
		//this.__categoryModel.set_CurrentCategoryChanged().Add(get_onCurrentVersionLoaded());
		this.__sessionModel = ta.timeattendance.Models.Bootstrapper.Resolve( ISessionModel.class );
	}



	//*********************************************************************************************
	//**     Event Handler
	private onDateCh get_onSelectedDateChanged() { onDateCh o = new onDateCh(); o.arg1 = this; return o; }
	class onDateCh extends RunnableWithArgs<SelectedDateEventArgs,Object> { public void run()
	{
		TabSessionList _this = (TabSessionList)this.arg1;
		//_this._listAdapter.RefreshByCurrenDate();
		_this.UpdateListView();
	}}



	//*********************************************************************************************
	//**     private
	private ListAdapter CreateNewListAdapter()
	{
		Session [] sessionArr = null;
		ListSessionAdapter la = null;
		SessionMode sessionMode = this.__sessionModel.get_SessionMode();
		try
		{
			sessionArr = Session.GetAllSession(this._doubleDatePicker,sessionMode);
		}
		catch (Exception e)
		{
			Exception ex = e;
		}

		//if(sessionArr != null && sessionArr.length != 0){
			la = new ListSessionAdapter(this.context, sessionArr, this, this._doubleDatePicker);
		//}
		return la;
	}

	private void UpdateListView()
	{
		ListAdapter la = CreateNewListAdapter();

		if(la == null || la.getCount() == 0){
			Tab.Show(this._emptyLabel);
		}
		else {
			Tab.Hide(this._emptyLabel);
		}
		this._listView.setAdapter(la);
	}/**/



	//*********************************************************************************************
	//**     Override
	@Override
	public void Show()
	{
		super.Show();
		UpdateListView();//
		/*Session [] sessionArr = Session.GetAllSession(this._doubleDatePicker);

			if (this._listAdapter != null)
			{
				this._listAdapter = null;
			}
			this._listAdapter = new ListSessionAdapter(this.context, sessionArr, this, this._doubleDatePicker);
			this._listView.setAdapter(this._listAdapter);*/
	}

	@Override
	public void Hide()
	{
		super.Hide();
		//this._listAdapter = null;
	}



	//*********************************************************************************************
	//**     Control Handler
	private void FilterLeft()
	{
		this._doubleDatePicker.ShowLeft();
	}
	private void FilterRight()
	{
		this._doubleDatePicker.ShowRight();
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
				case R.id.Session_LeftFilterBtn_Id:{
					FilterLeft();
				break;}
				case R.id.Session_RightFilterBtn_Id:{
					FilterRight();
				break;}
			}
		}
	}
}