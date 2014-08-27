package ta.Tabs.CheckinList;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Filter;
import android.widget.DatePicker;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.support.v4.app.FragmentManager;

//import java.util.Date;
import java.util.Calendar;
import java.util.Arrays;
import java.util.ArrayList;

import ta.lib.*;
import ta.lib.Common.*;
import ta.timeattendance.*;
import ta.lib.DatePicker.*;
import ta.Database.Checkin;
import ta.lib.tabui.Tab;
import ta.timeattendance.R;

public class TabCheckinList extends Tab implements View.OnClickListener
{
	private ListView listView;
	private MainActivityProxy _mainActivity;

	public int _year1;
	public int _month1;
	public int _day1;
	public int _year2;
	public int _month2;
	public int _day2;

	private TextView _sendTextView;
	private TextView _sendErrorTextView;
	private TextView _notSendTextView;

	public TabCheckinList(ta.timeattendance.MainActivityProxy paramContext, ViewGroup paramViewGroup, int paramInt1, int paramInt2)
	{
		super(paramContext, paramViewGroup, paramInt1, paramInt2);
		this._myDatePickerLeft = null;
		this._mainActivity = paramContext;

		Calendar currentDate = Calendar.getInstance();
		this._year1  = this._year2  = currentDate.get(Calendar.YEAR);
		this._month1 = this._month2 = currentDate.get(Calendar.MONTH);
		this._day1   = this._day2   = currentDate.get(Calendar.DAY_OF_MONTH);

		//android.widget.ScrollView.setScrollbarFadingEnabled(false);
		listView = (ListView)this.root.findViewById(R.id.PageCheckinList_ListView_Id);
		listView.setScrollbarFadingEnabled(false);
		
		Button checkin_btn_filter_left = (Button)this.root.findViewById(R.id.checkin_btn_filter_left);
		checkin_btn_filter_left.setOnClickListener(this);
		checkin_btn_filter_left.setTag(R.id.checkin_btn_filter_left);
		
		Button checkin_btn_filter_right = (Button)this.root.findViewById(R.id.checkin_btn_filter_right);
		checkin_btn_filter_right.setOnClickListener(this);
		checkin_btn_filter_right.setTag(R.id.checkin_btn_filter_right);

		_sendTextView      = (TextView)this.root.findViewById(R.id.PageCheckinList_SendTextView_Id);
		_notSendTextView   = (TextView)this.root.findViewById(R.id.PageCheckinList_NotSendTextView_Id);
		_sendErrorTextView = (TextView)this.root.findViewById(R.id.PageCheckinList_SendErrorTextView_Id);
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
	//**     Event Handler
	private onSelectedDateChanged get_onSelectedDateChanged() { onSelectedDateChanged o = new onSelectedDateChanged(); o.arg1 = this; return o; }
	class onSelectedDateChanged extends RunnableWithArgs<SelectedDateEventArgs,Boolean> { public void run()
	{
		TabCheckinList _this = (TabCheckinList)this.arg1;

		String pickerName = this.arg.name;
		DateTime dt          = this.arg.dt;
		int year          = dt.Year;
		int monthOfYear   = dt.Month;
		int dayOfMonth    = dt.Day;

		if(pickerName.equals("left"))
		{
			_this._year1  = year;
			_this._month1 = monthOfYear;
			_this._day1   = dayOfMonth;
		}else if(pickerName.equals("right")){
			_this._year2  = year;
			_this._month2 = monthOfYear;
			_this._day2   = dayOfMonth;
		}
		Filter f = _this._checkinAdapter.getFilter();
		f.filter("1");
	}}

	private CheckinAdapter3 _checkinAdapter;
	private void CreateCheckinAdapter(Checkin[] paramArrayOfPoint)
	{
		if (this._checkinAdapter != null)
		{
			this._checkinAdapter = null;
		}
		//this._checkinAdapter = new CheckinAdapter(this.context, paramArrayOfPoint, this);
		//ArrayList al = new ArrayList<Checkin>(Arrays.asList(paramArrayOfPoint));
		//this._checkinAdapter = new CheckinAdapter2(this.context, R.id.checkin_input_filter, al);
		//this._checkinAdapter = new CheckinAdapter2(this.context, R.layout.list_item, al);

		this._checkinAdapter = new CheckinAdapter3(this.context, paramArrayOfPoint, this);
		this.listView.setAdapter(this._checkinAdapter);
	}

	//private void FilterAll()
	//{
	//    Filter f = this._checkinAdapter.getFilter();
	//    f.filter("2");
	//    int aaa = 9;
	//}

	public void Show()
	{
		super.Show();
		MainEngine engine = MainEngine.getInstance();

		if (engine != null)
		{
			Checkin [] checkinArr = Checkin.GetAllCheckin( this.context);
			CreateCheckinAdapter(checkinArr);
			ShowCheckinStat();
		}
	}

	private void FilterLeft()
	{
		try
		{
			get_MyDatePickerLeft().Show();
		}
		catch(Exception e)
		{
			Exception ex = e;
		}
		int aaa = 9;
	}

	private void FilterRight()
	{
		get_MyDatePickerRight().Show();
	}

	//*********************************************************************************************
	//**     static
	private void ShowCheckinStat()
	{
		int CountSend =      Checkin.GetCountSendCheckins( this.context);
		int CountNotSend =   Checkin.GetCountNotSendCheckins( this.context);
		int CountErrorSend = Checkin.GetCountErrorSendCheckins( this.context);
		String str1 = Integer.toString(CountSend);
		String str2 = Integer.toString(CountNotSend);
		String str3 = Integer.toString(CountErrorSend);
		_sendTextView.setText(      str1);
		_notSendTextView.setText(   str2);
		_sendErrorTextView.setText( str3);
	}

	public void onClick(View paramView)
	{
		Object tag = paramView.getTag();

		MainEngine engine = MainEngine.getInstance();
		Integer integer = operator.as(Integer.class, tag);

		if( engine != null && integer != null)
		{
		    switch(integer)
		    {
		        case R.id.checkin_btn_filter_left:{
		            FilterLeft();
		        break;}
				case R.id.checkin_btn_filter_right:{
				    FilterRight();
				break;}
		    }
		}
	}
}