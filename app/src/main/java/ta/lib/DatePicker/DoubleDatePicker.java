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
import ta.lib.Common.DateTime;
import ta.timeattendance.R;

public class DoubleDatePicker implements IIntervalDateTimeProvider
{
	private ta.timeattendance.MainActivityProxy _mainActivity;
	private DateTime _start;
	private DateTime _end;

	public DoubleDatePicker(
		ta.timeattendance.MainActivityProxy context
		,DateTime start, DateTime end
	)
	{
		this._mainActivity = context;
		this._start = start;
		this._end   = end;
		this.SelectedDateChanged = new SelectedDateChangedEventClass();
	}



	//*********************************************************************************************
	//**     Event
	class   SelectedDateChangedEventClass extends Event<SelectedDateEventArgs,Object> {}
	private SelectedDateChangedEventClass SelectedDateChanged;
	public void set_SelectedDateChanged(RunnableWithArgs runnable)
	{
		this.SelectedDateChanged.Add(runnable);
	}



	//*********************************************************************************************
	//**     public
	public void ShowLeft()
	{
		try{
			get_MyDatePickerLeft().Show();
		}
		catch(Exception e) {
			Exception ex = e;
		}
	}
	public void ShowRight()
	{
		get_MyDatePickerRight().Show();
	}



	//*********************************************************************************************
	//**     prop public
	public DateTime GetDateTimeLeft()
	{
		return get_MyDatePickerLeft().get_CurrentDateTime();
	}
	public DateTime GetDateTimeRight()
	{
		return get_MyDatePickerRight().get_CurrentDateTime();
	}



	//*********************************************************************************************
	//**     prop
	private MyDatePicker _myDatePickerLeft;
	private MyDatePicker get_MyDatePickerLeft()
	{
		if(_myDatePickerLeft == null)
		{
			FragmentManager fragmentManager = _mainActivity.get_FragmentManager();
			_myDatePickerLeft = new MyDatePicker();
			_myDatePickerLeft.Init("left",fragmentManager,this._start);
		
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
			_myDatePickerRight.Init("right",fragmentManager,this._end);
		
			_myDatePickerRight.SelectedDateChanged.Add(get_onSelectedDateChanged());
		}
		return _myDatePickerRight;
	}



	//*********************************************************************************************
	//**     Event Handler
	private onDateCh get_onSelectedDateChanged() { onDateCh o = new onDateCh(); o.arg1 = this; return o; }
	class onDateCh extends RunnableWithArgs<SelectedDateEventArgs,Object> { public void run()
	{
		DoubleDatePicker _this = (DoubleDatePicker)this.arg1;
		_this.SelectedDateChanged.RunEvent( this.arg );
	}}
}
