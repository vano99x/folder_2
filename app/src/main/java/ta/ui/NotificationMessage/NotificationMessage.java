package ta.ui.NotificationMessage;

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
import android.widget.TextView;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Calendar;

import ta.lib.*;
import ta.lib.Common.DateTime;
import ta.timeattendance.R;

public class NotificationMessage extends DialogFragment implements View.OnClickListener
{
	private FragmentManager _fragmentManager;
	private String _text;
	private Runnable _onClick;

	public NotificationMessage()
	{
	}
	public void Init(FragmentManager fm, String text, Runnable onClick)
	{
		this._fragmentManager = fm;
		this._text = text;
		this._onClick = onClick;
	}

	public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState )
	{
		this.getDialog().getWindow().requestFeature(android.view.Window.FEATURE_NO_TITLE); // !!!

		View view = inflater.inflate(R.layout.ctrl_notification_message, container);
		//this.setStyle( STYLE_NORMAL, android.R.style.Theme_Holo_Light_Dialog);

		TextView textView = (TextView)view.findViewById(R.id.NotificationMessage_TextId);
		textView.setText(this._text);

		Button ok = (Button)view.findViewById(R.id.NotificationMessage_OkId);
		ok.setOnClickListener(this);
		ok.setTag(R.id.NotificationMessage_OkId);

		return view;
	}



	//*********************************************************************************************
	//*      public
	public void Show()
	{
		this.show( this._fragmentManager, "NotificationMessage");
	}



	//*********************************************************************************************
	//*      Ctrl Handler
	private void okBtn_Click()
	{
		this._onClick.run();
		this.dismiss();
	}



	@Override
	public void onClick(View paramView)
	{
		Object tag = paramView.getTag();
		Integer integer = operator.as(Integer.class, tag);
		if( integer != null)
		{
			switch(integer)
			{
				case R.id.NotificationMessage_OkId:{
					okBtn_Click();
				break;}
			}
		}
	}
}
