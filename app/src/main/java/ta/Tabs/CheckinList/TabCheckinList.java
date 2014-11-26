package ta.Tabs.CheckinList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Filter;
import android.widget.DatePicker;
import android.app.DatePickerDialog;
//import android.app.DatePickerDialog.OnDateSetListener;
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
	private CheckinAdapter3 _listAdapter;

	private TextView _sendTextView;
	private TextView _sendErrorTextView;
	private TextView _notSendTextView;
	private DoubleDatePicker _doubleDatePicker;

	public TabCheckinList(ta.timeattendance.MainActivityProxy context, ViewGroup viewGroup, int i1, int i2)
	{
		super(context, viewGroup, i1, i2);

		this._doubleDatePicker = 
			new DoubleDatePicker(
				context,
				DateTime.GetCurrentDateTime(),
				DateTime.GetCurrentDateTime()
			);
		this._doubleDatePicker.set_SelectedDateChanged(get_onSelectedDateChanged());
		
		Button checkin_btn_filter_left = (Button)this.root.findViewById(R.id.checkin_btn_filter_left);
		checkin_btn_filter_left.setOnClickListener(this);
		checkin_btn_filter_left.setTag(R.id.checkin_btn_filter_left);
		
		Button checkin_btn_filter_right = (Button)this.root.findViewById(R.id.checkin_btn_filter_right);
		checkin_btn_filter_right.setOnClickListener(this);
		checkin_btn_filter_right.setTag(R.id.checkin_btn_filter_right);

		//android.widget.ScrollView.setScrollbarFadingEnabled(false);
		listView = (ListView)this.root.findViewById(R.id.PageCheckinList_ListView_Id);
		listView.setScrollbarFadingEnabled(false);

		_sendTextView      = (TextView)this.root.findViewById(R.id.PageCheckinList_SendTextView_Id);
		_notSendTextView   = (TextView)this.root.findViewById(R.id.PageCheckinList_NotSendTextView_Id);
		_sendErrorTextView = (TextView)this.root.findViewById(R.id.PageCheckinList_SendErrorTextView_Id);
	}



	//*********************************************************************************************
	//**     Event Handler
	private onDateCh get_onSelectedDateChanged() { onDateCh o = new onDateCh(); o.arg1 = this; return o; }
	class onDateCh extends RunnableWithArgs<SelectedDateEventArgs,Object> { public void run()
	{
		TabCheckinList _this = (TabCheckinList)this.arg1;
		//_this._listAdapter.RefreshByCurrenDate();
		Checkin [] checkinArr = _this.GetCheckinArrByPeriod();
		_this._listAdapter.Refresh(checkinArr);
	}}


	private Checkin [] GetCheckinArrByPeriod()
	{
			DateTime dt1 = this._doubleDatePicker.GetDateTimeLeft().ToZeroTime();
			DateTime dt2 = this._doubleDatePicker.GetDateTimeRight().ToMinuteBeforeMidnight();
			String s1 = String.valueOf(dt1.ToUnixSeconds());
			String s2 = String.valueOf(dt2.ToUnixSeconds());
			String where = s1 + " < DateTime and DateTime < " + s2;
			Checkin [] checkinArr = (Checkin[])Checkin.SelectWhere(Checkin.class, where);
		return checkinArr;
	}


	//*********************************************************************************************
	//**     Override
	public void Show()
	{
		super.Show();
		MainEngine engine = MainEngine.getInstance();

		if (engine != null)
		{
			//Checkin [] checkinArr = Checkin.GetAllCheckin( this.context);
			Checkin [] checkinArr = this.GetCheckinArrByPeriod();

			if (this._listAdapter != null)
			{
				this._listAdapter = null;
			}
			this._listAdapter = new CheckinAdapter3(this.context, checkinArr, this, this._doubleDatePicker);
			this.listView.setAdapter(this._listAdapter);

			ShowCheckinStat();
		}
	}



	//*********************************************************************************************
	//**     private
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


	
	//*********************************************************************************************
	//       MyDatePicker
	/*private MyDatePicker _myDatePickerLeft;
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
	}*/