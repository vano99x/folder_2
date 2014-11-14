package ta.Tabs.TemplateList;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;

import ta.lib.*;
import ta.Database.*;
import ta.lib.tabui.*;
import ta.timeattendance.*;
import ta.timeattendance.Models.*;

import ta.timeattendance.R;

public class TabTemplateList extends Tab implements View.OnClickListener
{
	private ITemplateModel   __templateModel;
	private ISupervisorModel __svModel;
	private ICategoryModel   __categoryModel;
	private ListTemplateAdapter listAdapter;

	private ListView _listView;
	private TextView _headLabel;
	private TextView _emptyLabel;

	public TabTemplateList(Context context, ViewGroup paramViewGroup, int int1, int int2)
	{
		super(context, paramViewGroup, int1, int2);

		_headLabel = (TextView)this.root.findViewById(R.id.PagePointsList_HeadTextView_Id);

		_listView   = (ListView)this.root.findViewById(R.id.Template_ListView_Id);
		_headLabel  = (TextView)this.root.findViewById(R.id.Template_HeadTextView_Id);
		_emptyLabel = (TextView)this.root.findViewById(R.id.Template_EmptyListTextView_Id);

		this.__svModel = Bootstrapper.Resolve( ISupervisorModel.class );
		this.__templateModel = Bootstrapper.Resolve( ITemplateModel.class );
		this.__categoryModel = Bootstrapper.Resolve( ICategoryModel.class );
		//this.__categoryModel.set_CurrentCategoryChanged().Add(get_onCurrentVersionLoaded());
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
		Template [] templateAll = supervisor.get_Templates();
		Category    currentCategory = this.__categoryModel.get_CurrentCategory();

		ArrayList<Template> templateList = new ArrayList<Template>();
		int count = templateAll.length;
		for (int i=0;i<count;i++)
		{
			if(templateAll[i].CategoryId == currentCategory.Id)
			{
				templateList.add(templateAll[i]);
			}
		}

		Template [] templateArr = templateList.toArray(new Template[0]);

		if (this.listAdapter == null)
		{
			this.listAdapter = new ListTemplateAdapter(this.context, templateArr, this);
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
	public void ListItem_Selected(Template template)
	{
		this.__templateModel.set_CurrentTemplate(template);
		UIHelper.Instance().switchState(MainActivity.State.MODE_SELECTION);
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
				case R.id.Template_Item_TagId:{
					ListItem_Selected((Template)arr[1]);
				break;}
			}
		}
	}
}