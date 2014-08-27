package ta.lib.DatePicker;

//import android.app.*;
import android.app.DatePickerDialog;
import android.widget.DatePicker;
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
import ta.lib.Common.*;
import ta.timeattendance.R;

public class MyDatePicker extends DialogFragment implements DatePickerDialog.OnDateSetListener , View.OnClickListener
{
	private DatePicker _datePicker;
	private FragmentManager _fragmentManager;
	private String _name;
	public class SelectedDateChangedEventClass extends Event<SelectedDateEventArgs,Boolean> {}
	public       SelectedDateChangedEventClass SelectedDateChanged;

	public MyDatePicker()
	{
		this._datePicker = null;
		this._name = null;
	}
	public void Init(String name, FragmentManager fragmentManager)
	{
		this._name = name;
		this._fragmentManager = fragmentManager;
		this.SelectedDateChanged = new SelectedDateChangedEventClass();
	}
	public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState )
	{
		this.getDialog().getWindow().requestFeature(android.view.Window.FEATURE_NO_TITLE); // !!!

		View view = inflater.inflate(R.layout.ctrl_date_picker, container);
		//this.setStyle( STYLE_NORMAL, android.R.style.Theme_Holo_Light_Dialog);
		this._datePicker = (DatePicker) view.findViewById(R.id.DatePicker);
		this._datePicker.updateDate( get_CurrentDateTime().Year, get_CurrentDateTime().Month, get_CurrentDateTime().Day);
		//this._datePicker.setCalendarViewShown(false);

		Button ok = (Button)view.findViewById(R.id.MyDatePickerView_OkId);
		ok.setOnClickListener(this);
		ok.setTag(R.id.MyDatePickerView_OkId);

		Button cancel = (Button)view.findViewById(R.id.MyDatePickerView_CancelId);
		cancel.setOnClickListener(this);
		cancel.setTag(R.id.MyDatePickerView_CancelId);

        return view;
    }


/*@Override
  public Dialog onCreateDialog(Bundle savedInstanceState)
  {
    // Use the current date as the default date in the picker
    final Calendar c = Calendar.getInstance();
    int year = c.get(Calendar.YEAR);
    int month = c.get(Calendar.MONTH);
    int day = c.get(Calendar.DAY_OF_MONTH);

    // Create a new instance of DatePickerDialog and return it
    return new DatePickerDialog(getActivity(), this, year, month, day);
  }*/



	//*********************************************************************************************
	//*      public
	public void onDateSet(DatePicker view, int year, int month, int day)
	{
//    pYear = year; //    pDay = day; //    pMonth = month;
	}

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
	public void set_CurrentDateTime(DateTime value)
	{
		this._currentDateTime = value;
	}



	//*********************************************************************************************
	//*      Ctrl Handler
	public void onClick_okBtn()
	{

		//Calendar dateTime = Calendar.getInstance();
		//dateTime.set( year, month, day,  0,  0,  0);

		DateTime dt = get_CurrentDateTime();
		SelectedDateChanged.RunEvent( new SelectedDateEventArgs( dt, this._name) );

		//SelectedDateChanged.RunEvent( new Object[]{ this._name, year, month, day, dateTime});


		this.dismiss();
		//this.
	}
	public void onClick_cancelBtn()
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
		            onClick_okBtn();
		        break;}
		        case R.id.MyDatePickerView_CancelId:{
		            onClick_cancelBtn();
		        break;}
		    }
		}
	}
}