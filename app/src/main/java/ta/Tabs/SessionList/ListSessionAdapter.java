package ta.Tabs.SessionList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.text.SimpleDateFormat;

import ta.Database.*;
import ta.Entity.Session;
import ta.Tabs.SessionList.SessionMode;
import ta.lib.Common.DateTime;
import ta.lib.Controls.AdapterBase;
import ta.lib.DatePicker.IIntervalDateTimeProvider;
//import ta.Tabs.CheckinList.CheckinHelper;
import ta.timeattendance.R;

public class ListSessionAdapter extends AdapterBase<Session>
{
	public IIntervalDateTimeProvider _intervalDateTimeProvider;
	public Session[] _sourceItems;

	public ListSessionAdapter(
		Context context,
		Session[] checkinArr,
		TabSessionList onClickListener,
		IIntervalDateTimeProvider intervalDateTimeProvider)
	{
		super(context, null, onClickListener);

		this._intervalDateTimeProvider = intervalDateTimeProvider;
		this._sourceItems = checkinArr;
		this._items = DateTime.WhereInPeriod(this._intervalDateTimeProvider, this._sourceItems, new Session[0]);

		//this._filteredItems = CheckinHelper.GetCheckinListByDatesInterval(this._intervalDateTimeProvider, this._items);
	}



	//*********************************************************************************************
	//**     filter
	/*public void RefreshByCurrenDate()
	{
		Session[] filteredItems =
			CheckinHelper.GetCheckinListByDatesInterval(
				this._intervalDateTimeProvider, this._sourceItems, new Session[0] );

		this._items = filteredItems;
		this.notifyDataSetChanged();
	}*/


	
	//*********************************************************************************************
	//*      Override

	@Override
	public String GetTextFromItem(Session item)
	{
		return " ";
	}

	@Override
	public Integer GetListItemIdentifier()
	{
		return R.layout.p_session_list_item;
	}

	@Override
	public View getView(int indexItem, View view, ViewGroup viewGroup)
	{
		view = super.Inflate(view);
		//TextView date =      (TextView)view.findViewById(R.id.Session_ListItem_Date_Id);

		TextView firstName = (TextView)view.findViewById(R.id.Session_ListItem_FirstName_Id);
		TextView lastName =  (TextView)view.findViewById(R.id.Session_ListItem_LastName_Id);
		TextView thirdName = (TextView)view.findViewById(R.id.Session_ListItem_ThirdName_Id);

		TextView pointName = (TextView)view.findViewById(R.id.Session_ListItem_PointName_Id);
		TextView catName =   (TextView)view.findViewById(R.id.Session_ListItem_CategoryName_Id);

		TextView start =     (TextView)view.findViewById(R.id.Session_ListItem_Start_Id);
		TextView end =       (TextView)view.findViewById(R.id.Session_ListItem_End_Id);
		TextView tpl =       (TextView)view.findViewById(R.id.Session_ListItem_Tpl_Id);

		//String str;
		if( this._items != null && this._items.length != 0 )
		{
			Session item = this._items[indexItem];

			DateTime dS = DateTime.FromUnixSecond(item.Start);
			String str1 = dS.ToMonthDayString();
			String str2 = dS.ToTimeString();
			start.setText(str1+"-"+str2);
			
			switch(item.Mode) {
			case DepartureOk:{

			String strE;
			DateTime dE = DateTime.FromUnixSecond(item.End);
			strE = dE.ToTimeString();
			if(dS.Day != dE.Day)
			{
				String temp = dE.ToMonthDayString();
				strE = temp+"-"+strE;
			}
			end.setText(strE);

			break;}
			//case DepartureMiss:
			//case ArrivalOk:{

			//break;}
			}

			//DateTime d = DateTime.FromUnixSecond(item.Start);
			//String str1 = d.ToMonthDayString();
			//date.setText(str1);

			//String str2 = d.ToTimeString();
			//start.setText(    "от "+str2);

			//DateTime d3 = DateTime.FromUnixSecond(item.End);
			//String str3 = d3.ToTimeString();
			//end.setText(      "до "+str3);

			firstName.setText(item.FirstName);
			lastName.setText( item.LastName);
			pointName.setText(item.PointName);
			catName.setText(  item.CategoryName);

			DateTime tplStart = DateTime.FromDaySecond(item.TplStart);
			String str4 = tplStart.ToTimeString();

			DateTime tplEnd   = DateTime.FromDaySecond(item.TplEnd);
			String str5 = tplEnd.ToTimeString();

			str4 = str4 +"-"+ str5;
			tpl.setText(str4);/**/
		}

		return view;
	}
}
