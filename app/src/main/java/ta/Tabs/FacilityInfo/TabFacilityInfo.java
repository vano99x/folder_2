package ta.Tabs.FacilityInfo;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Filter;
import android.widget.DatePicker;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.support.v4.app.FragmentManager;


//import java.util.Date;
import java.util.Calendar;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;

import ta.lib.*;
import ta.lib.Common.*;
import ta.timeattendance.*;
import ta.lib.DatePicker.MyDatePicker;
import ta.lib.DatePicker.SelectedDateEventArgs;
import ta.timeattendance.Models.*;
import ta.Database.*;
import ta.lib.tabui.Tab;
import ta.timeattendance.R;

public class TabFacilityInfo extends Tab implements View.OnClickListener
{
	private MainActivityProxy _mainActivity;
	private MainEngine _engine;
	private ListView _listView;
	private TextView _clientNameTv;

	public  FacilityInfoEntity _facilityInfoEntity;
	private ISupervisorModel __svModel;

	public TabFacilityInfo(
		MainActivityProxy context, ViewGroup paramViewGroup, int paramInt1, int paramInt2)
	{
		super(context, paramViewGroup, paramInt1, paramInt2);
		this._mainActivity = context;
		this._engine = MainEngine.getInstance();

		this._clientNameTv = (TextView)this.root.findViewById(R.id.FacilityInfo_ClientName_Id);
		this._listView =     (ListView)this.root.findViewById(R.id.FacilityInfo_ListView_Id);
		this._listView.setScrollbarFadingEnabled(false);
		
		Button filterLeftBtn = (Button)this.root.findViewById(R.id.FacilityInfo_FilterLeftBtn_Id);
		filterLeftBtn.setOnClickListener(this);
		filterLeftBtn.setTag(R.id.FacilityInfo_FilterLeftBtn_Id);
		
		Button filterRightBtn = (Button)this.root.findViewById(R.id.FacilityInfo_FilterRightBtn_Id);
		filterRightBtn.setOnClickListener(this);
		filterRightBtn.setTag(R.id.FacilityInfo_FilterRightBtn_Id);

		this.__svModel = Bootstrapper.Resolve( ISupervisorModel.class );
	}
	
	//*********************************************************************************************
	//       MyDatePicker
	private MyDatePicker _myDatePickerLeft;
	private MyDatePicker get_MyDatePickerLeft()
	{
		if(_myDatePickerLeft == null)
		{
			FragmentManager fragmentManager = _mainActivity.get_FragmentManager();
			_myDatePickerLeft = new MyDatePicker();
			_myDatePickerLeft.Init("left",fragmentManager);
		
			_myDatePickerLeft.SelectedDateChanged.Add(get_onSelectedDateChanged());
		}
		return _myDatePickerLeft;
	}

	private MyDatePicker _myDatePickerRight;
	private MyDatePicker get_MyDatePickerRight()
	{
		if(_myDatePickerRight == null)
		{
			FragmentManager fragmentManager = _mainActivity.get_FragmentManager();
			_myDatePickerRight = new MyDatePicker();
			_myDatePickerRight.Init("right",fragmentManager);
		
			_myDatePickerRight.SelectedDateChanged.Add(get_onSelectedDateChanged());
		}
		return _myDatePickerRight;
	}

	//*********************************************************************************************
	//*      Event Handler
	private onSelectedDateChanged get_onSelectedDateChanged() { onSelectedDateChanged o = new onSelectedDateChanged(); o.arg1 = this; return o; }
	class onSelectedDateChanged extends RunnableWithArgs<SelectedDateEventArgs,Boolean> { public void run()
	{
		TabFacilityInfo _this = (TabFacilityInfo)this.arg1;

		//Object[] resultArr = (Object[])this.result;

		//String pickerName = (String)resultArr[0];
		//DateTime dt       = (DateTime)resultArr[1];

		//String str = dt.ToDateString();

		DateTime LeftDt = get_MyDatePickerLeft().get_CurrentDateTime();
		DateTime RightDt = get_MyDatePickerRight().get_CurrentDateTime();

		_this.Load( LeftDt, RightDt);
	}}

	public void SetFacilityInfo(FacilityInfoEntity fie)
	{
		this._facilityInfoEntity = fie;
		this._clientNameTv.setText( this._facilityInfoEntity.ClientName );

		//ListAdapter adp = this._listView.getAdapter();
		//Filter f = this._adapter.getFilter();
		//f.filter("1");
		//List<FacilityEntity> founded = new ArrayList<FacilityEntity>(Arrays.asList(this._tabFacilityInfo._facilityInfoEntity.FacilityArray));
		this._adapter.set_Items( this._facilityInfoEntity.FacilityArray );
		this._adapter.notifyDataSetChanged();
	}

	private void Load(DateTime leftDt, DateTime rightDt)
	{
		String pin = this.__svModel.get_CurrentSuperviser().Pin;

		String leftTimeStr  = leftDt.ToDateString();
		String rightTimeStr = rightDt.ToDateString();

		this._engine.GetFacilityInfo( pin, leftTimeStr, rightTimeStr);
	}

	private FacilityInfoAdapter _adapter;
	private DateTime _showDateTime;
	public void Show()
	{
		super.Show();

		if (this._adapter != null) {
			this._adapter = null;
		}
		this._adapter = new FacilityInfoAdapter(this.context, this);
		this._listView.setAdapter(this._adapter);

		_showDateTime = DateTime.GetCurrentDateTime();
		get_MyDatePickerLeft().set_CurrentDateTime(  _showDateTime );
		get_MyDatePickerRight().set_CurrentDateTime( _showDateTime );
		
		DateTime LeftDt = get_MyDatePickerLeft().get_CurrentDateTime();
		DateTime RightDt = get_MyDatePickerRight().get_CurrentDateTime();

		this.Load( LeftDt, RightDt);
	}

	private void FilterLeft()
	{
		get_MyDatePickerLeft().Show();
	}

	private void FilterRight()
	{
		get_MyDatePickerRight().Show();
	}

	public void onClick(View paramView)
	{
		Object tag = paramView.getTag();
		Integer integer = operator.as(Integer.class, tag);

		if( integer != null)
		{
			switch(integer)
			{
				case R.id.FacilityInfo_FilterLeftBtn_Id:{
					FilterLeft();
				break;}
				case R.id.FacilityInfo_FilterRightBtn_Id:{
					FilterRight();
				break;}
			}
		}
	}
}