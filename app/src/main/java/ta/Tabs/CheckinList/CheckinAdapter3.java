package ta.Tabs.CheckinList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Filter;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.text.SimpleDateFormat;


import ta.lib.Common.DateTime;
import ta.lib.Controls.AdapterBase;
import ta.Database.*;
import ta.lib.DatePicker.IIntervalDateTimeProvider;
import ta.timeattendance.R;

public class CheckinAdapter3 extends AdapterBase<Checkin> //implements Filterable
{
	public IIntervalDateTimeProvider _intervalDateTimeProvider;
	public Checkin[] _sourceItems;

	public CheckinAdapter3(
		Context context,
		Checkin[] checkinArr, 
		TabCheckinList onClickListener,
		IIntervalDateTimeProvider intervalDateTimeProvider)
	{
		super(context, null, onClickListener);

		this._intervalDateTimeProvider = intervalDateTimeProvider;
		this._sourceItems = checkinArr;
		this._items = DateTime.WhereInPeriod(this._intervalDateTimeProvider, this._sourceItems, new Checkin[0]);
	}

	private static SimpleDateFormat _dateFormat;
	private static SimpleDateFormat get_DateFormat()
	{
		if( CheckinAdapter3._dateFormat == null )
		{
			CheckinAdapter3._dateFormat = new SimpleDateFormat("dd MMM yyyy HH:mm");
		}
		return CheckinAdapter3._dateFormat;
	}



	//*********************************************************************************************
	//**     filter
	//public List<Checkin> GetFilteredItems()
	//{
	//	List<Checkin> result = 
	//		CheckinHelper.GetCheckinListByDatesInterval( this._intervalDateTimeProvider, this._items );
	//	return result;
	//}

	//public void RefreshFilteredItems(List<Checkin> list)
	//{
	//	this._filteredItems.clear();
	//	this._filteredItems.addAll(list);
	//	this.notifyDataSetChanged();
	//}

	public void RefreshByCurrenDate()
	{
		Checkin[] filteredItems =
			DateTime.WhereInPeriod( this._intervalDateTimeProvider, this._sourceItems, new Checkin[0]);

		this._items = filteredItems;
		this.notifyDataSetChanged();
	}


	
	//*********************************************************************************************
	//*      Override

	@Override
	public String GetTextFromItem(Checkin item)
	{
		long dt = ta.lib.Common.DateTime.GetMillisecondsFromUnixSecond(item.DateTime);
		Date date = new Date(dt);
		String dateStr = get_DateFormat().format(date);

		Personel personel = item.get_Personel();
		String nameStr = personel.FirstName;

		return nameStr + " "+ dateStr;
	}

	@Override
	public Integer GetListItemIdentifier()
	{
		return R.layout._4_checkin_list_item;
	}

	@Override
	public View getView(int indexItem, View view, ViewGroup viewGroup)
	{
		view = super.Inflate(view);
		TextView currentTextView = (TextView)view.findViewById(R.id.CheckinListItem_TextId);
		TextView stateView =       (TextView)view.findViewById(R.id.CheckinListItem_StateId);

		if( this._items != null && this._items.length != 0 )
		{
			Checkin checkin = this._items[indexItem];
			String text = GetTextFromItem(checkin);
			String state = Integer.toString(checkin.get_StateCheckinOnServer());

			currentTextView.setText(text);
			switch(checkin.get_StateCheckinOnServer())
			{
				case -2:{
					stateView.setBackgroundColor(0xffff0000);
					stateView.setText(state);
				break;}
				case -1:{
					stateView.setBackgroundColor(0xfff5d50b);
					stateView.setText(state);
				break;}
				default :{
					stateView.setBackgroundColor(0xff46b525);
					stateView.setText(state);
				}
			}
		}
		return view;
	}

	//private Filter _filter;
	//@Override
	//public Filter getFilter()
	//{
	//	if( this._filter == null )
	//	{
	//		this._filter = new CheckinFilter(this);
	//	}
	//	return this._filter;
	//}

		//if( paramView == null )
		//{
		//	paramView = this._inflater.inflate(R.layout._4_checkin_list_item, null);
		//}
		//TextView     chView  = (TextView)paramView.findViewById(R.id.CheckinListItem_TextId);
		//TextView     stateView = (TextView)paramView.findViewById(R.id.CheckinListItem_StateId);

		//if( this._items != null )
		//{
		//	try
		//	{
		//		Checkin checkin = this._items.get(paramInt);
		//		String text = GetTextForItem(checkin);
		//		String state = Integer.toString(checkin.get_StateCheckinOnServer());

		//		chView.setText(text);
		//		switch(checkin.get_StateCheckinOnServer())
		//		{
		//			case -2:{
		//				stateView.setBackgroundColor(0xffff0000);
		//				stateView.setText(state);
		//			break;}
		//			case -1:{
		//				stateView.setBackgroundColor(0xfff5d50b);
		//				stateView.setText(state);
		//			break;}
		//			default :{
		//				stateView.setBackgroundColor(0xff46b525);
		//				stateView.setText(state);
		//			}
		//		}
		//	}
		//	catch (Exception e)
		//	{
		//		Exception ex = e;
		//	}
		//}

}