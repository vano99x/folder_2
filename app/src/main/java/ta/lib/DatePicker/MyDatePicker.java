package ta.lib.DatePicker;

import android.widget.DatePicker;
import android.app.DatePickerDialog;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Button;
import android.support.v4.app.FragmentManager;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Calendar;

import ta.lib.*;
import ta.lib.Common.DateTime;
import ta.timeattendance.R;

public class MyDatePicker extends DialogFragment 
	implements 
		//DatePickerDialog.OnDateSetListener , 
		View.OnClickListener
{
	private DatePicker _datePicker;
	private FragmentManager _fragmentManager;
	private String _name;
	public class SelectedDateChangedEventClass extends Event<SelectedDateEventArgs,Object> {}
	public       SelectedDateChangedEventClass SelectedDateChanged;

	public MyDatePicker()
	{
		this._datePicker = null;
		this._name = null;
	}
	public void Init(String name, FragmentManager fm, DateTime dateTime)
	{
		this._name = name;
		this._fragmentManager = fm;
		this._currentDateTime = dateTime;
		if(this._currentDateTime == null)
		{
			this._currentDateTime = DateTime.GetCurrentDateTime();
		}
		this.SelectedDateChanged = new SelectedDateChangedEventClass();
	}

	public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState )
	{
		this.getDialog().getWindow().requestFeature(android.view.Window.FEATURE_NO_TITLE); // !!!

		View view = inflater.inflate(R.layout.ctrl_date_picker, container);
		//this.setStyle( STYLE_NORMAL, android.R.style.Theme_Holo_Light_Dialog);
		this._datePicker = (DatePicker) view.findViewById(R.id.DatePicker);
		this._datePicker.updateDate( 
			this._currentDateTime.Year, 
			this._currentDateTime.Month, 
			this._currentDateTime.Day);
		//this._datePicker.setCalendarViewShown(false);

		Button ok = (Button)view.findViewById(R.id.MyDatePickerView_OkId);
		ok.setOnClickListener(this);
		ok.setTag(R.id.MyDatePickerView_OkId);

		Button cancel = (Button)view.findViewById(R.id.MyDatePickerView_CancelId);
		cancel.setOnClickListener(this);
		cancel.setTag(R.id.MyDatePickerView_CancelId);

		return view;
	}


//@Override
//  public Dialog onCreateDialog(Bundle savedInstanceState)
//  {
//    // Use the current date as the default date in the picker
//    final Calendar c = Calendar.getInstance();
//    int year = c.get(Calendar.YEAR);
//    int month = c.get(Calendar.MONTH);
//    int day = c.get(Calendar.DAY_OF_MONTH);

//    // Create a new instance of DatePickerDialog and return it
//    return new DatePickerDialog(getActivity(), this, year, month, day);
//  }



	//*********************************************************************************************
	//*      public
//	public void onDateSet(DatePicker view, int year, int month, int day)
//	{
////    pYear = year; //    pDay = day; //    pMonth = month;
//	}

	public void Show()
	{
		this.show( this._fragmentManager, "fragment_edit_name");
	}



	//*********************************************************************************************
	//*      property
	public DateTime _currentDateTime;
	public DateTime get_CurrentDateTime()
	{
		if(this._datePicker == null)
		{
			return this._currentDateTime;
		}
		else
		{
			int year  = this._datePicker.getYear();
			int month = this._datePicker.getMonth();
			int day   = this._datePicker.getDayOfMonth();

			return new DateTime(year,month,day,0,0,0);
		}
	}
	//public void set_CurrentDateTime(DateTime value)
	//{
	//	this._currentDateTime = value;
	//}



	//*********************************************************************************************
	//*      Ctrl Handler
	private void okBtn_Click()
	{
		DateTime dt = get_CurrentDateTime();
		SelectedDateChanged.RunEvent( new SelectedDateEventArgs( dt, this._name) );
		//SelectedDateChanged.RunEvent( new Object[]{ this._name, year, month, day, dateTime});
		this.dismiss();
	}
	private void cancelBtn_Click()
	{
		this.dismiss();
	}



	public void onClick(View paramView)
	{
		Object tag = paramView.getTag();
		Integer integer = operator.as(Integer.class, tag);

		if( integer != null)
		{
			switch(integer)
			{
				case R.id.MyDatePickerView_OkId:{
					okBtn_Click();
				break;}
				case R.id.MyDatePickerView_CancelId:{
					cancelBtn_Click();
				break;}
			}
		}
	}
}