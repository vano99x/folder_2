package ta.Tabs.PointsList;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import ta.lib.*;
import ta.Database.*;
import ta.lib.tabui.*;
import ta.timeattendance.*;
import ta.timeattendance.Models.*;

import ta.timeattendance.R;

public class TabPointsList extends Tab implements View.OnClickListener
{
	//public final String ACTION_START_PROCESS_POINT = "START.PROCESS.POINT";
	MainEngine _engine;
	private ListPointsAdapter listRouteAdapter;
	private ListView _listView;
	private TextView mLableEmptyList;
	private TextView mLableHead;

	private ISupervisorModel __svModel;
	private IPointModel __pointModel;

	public TabPointsList(Context context, ViewGroup paramViewGroup, int int1, int int2)
	{
		super(context, paramViewGroup, int1, int2);
		this._engine = MainEngine.getInstance();

		_listView =        (ListView)this.root.findViewById(R.id.PagePointsList_ListView_Id);
		mLableEmptyList = (TextView)this.root.findViewById(R.id.PagePointsList_EmptyListTextView_Id);
		mLableHead =      (TextView)this.root.findViewById(R.id.PagePointsList_HeadTextView_Id);
		
		this.__svModel = Bootstrapper.Resolve( ISupervisorModel.class );
		this.__pointModel = Bootstrapper.Resolve( IPointModel.class );
	}

	private void createListRouteAdapter(Point[] pointArray)
	{
		if (this.listRouteAdapter != null)
		{
			this.listRouteAdapter = null;
		}
		this.listRouteAdapter = new ListPointsAdapter(this.context, pointArray, this);
		this._listView.setAdapter(this.listRouteAdapter);
	}

	//public void hide()
	//{
	//    super.Hide();
	//}
	//public void onClick(View paramView)
	//{
	//    Object tag = paramView.getTag();
	//    if (tag != null) {
	//        Point point = (Point)tag;
	//        this.listener.doAction(this, "START.PROCESS.POINT", point);
	//    }
	//}

	//*********************************************************************************************
	//**     Control Handler
	public void PointsListItem_Selected(Point point)
	{
		this.__pointModel.set_CurrentPoint(point);
		UIHelper.Instance().switchState(MainActivity.State.MODE_SELECTION);
	}

	@Override
	public void Show()
	{
		super.Show(); //TALog.Log("engine = " + localMainEngine);

		Personel superviser = this.__svModel.get_CurrentSuperviser();
		superviser.get_Points(true, get_onLoadComplete(), this.context);
	}

	private onLC get_onLoadComplete() { onLC o = new onLC(); o.arg1 = this; return o; }
	class onLC extends RunnableWithArgs { public void run()
	{
		TabPointsList _this = (TabPointsList)this.arg1;
		Personel superviser = _this.__svModel.get_CurrentSuperviser();
		Point [] pointArr = superviser.get_Points(false,null,null);
		_this.createListRouteAdapter(pointArr);

		_this.UpdateCtrlData();
	}}

	public void UpdateCtrlData()
	{
		if(this.listRouteAdapter != null && this.listRouteAdapter.getCount() > 0) {
			this.mLableHead.setVisibility(     View.VISIBLE);
			this.mLableEmptyList.setVisibility(View.INVISIBLE);
		} else {
			this.mLableHead.setVisibility(     View.INVISIBLE);
			this.mLableEmptyList.setVisibility(View.VISIBLE);
		}
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
				case R.id.PointsListItem_Id:{
					PointsListItem_Selected((Point)arr[1]);
				break;}
			}
		}
	}
}