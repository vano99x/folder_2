package ta.Tabs.CheckinList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.text.SimpleDateFormat;

import ta.Database.*;

import ta.timeattendance.R;

public class CheckinAdapter3 extends BaseAdapter implements Filterable
{
	//private View.OnClickListener listener;
	public TabCheckinList tabCheckinList;
	private LayoutInflater mInflater;
	public Context _context;

	public List<Checkin> items;
	public ArrayList<Checkin> original;

	public CheckinAdapter3(
		Context paramContext, 
		Checkin[] checkinArr, 
		TabCheckinList paramOnClickListener)
	{
		this.mInflater = ((LayoutInflater)paramContext.getSystemService("layout_inflater"));
		this.tabCheckinList = paramOnClickListener;
		this._context = paramContext;
		
		this.original = new ArrayList<Checkin>(Arrays.asList(checkinArr));

		//this.items.addAll(this.original);
		this.items = CheckinAdapter3.GetCheckinListByDatesInterval(this.tabCheckinList, this.original);
	}
	
	//*********************************************************************************************
	//*      public static
	public static List<Checkin> GetCheckinListByDatesInterval( TabCheckinList tabCheckinList, ArrayList<Checkin> original)
	{
		int year1  = tabCheckinList._year1;
		int month1 = tabCheckinList._month1;
		int day1   = tabCheckinList._day1;
		int year2  = tabCheckinList._year2;
		int month2 = tabCheckinList._month2;
		int day2   = tabCheckinList._day2;

		//long currentMS     = System.currentTimeMillis();
		//Date currentDate   = new Date(currentMS);
		Calendar selectedDate1 = Calendar.getInstance();
		Calendar selectedDate2 = Calendar.getInstance();
		selectedDate1.set( year1 , month1, day1,  0,  0,  0);
		selectedDate2.set( year2 , month2, day2, 23, 59, 59);

		List<Checkin> list = new ArrayList<Checkin>();
		for (Checkin ch : original)
		{
			//Date checkinDate = ch.get_DateObj();
			Calendar checkinDate = ch.get_CalendarObj();

			if(checkinDate.after(selectedDate1) && checkinDate.before(selectedDate2))
			{
				list.add(ch);
			}
		}

		return list;
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

	private static String GetTextForItem(Checkin checkin)
	{
		long dt = Long.parseLong(checkin.DateTime);
		Date date = new Date(dt);
		String dateStr = get_DateFormat().format(date);

		Personel personel = checkin.get_Personel();
		String nameStr = personel.FirstName;

		return nameStr + " "+ dateStr;
	}


	
	//*********************************************************************************************
	//*      Override
	@Override
	public int getCount()
	{
		if (this.items == null)
		{
			return 0;
		}
		return this.items.size();
	}

	@Override
	public Object getItem(int paramInt)
	{
		return Integer.valueOf(paramInt);
	}

	@Override
	public long getItemId(int paramInt)
	{
		return paramInt;
	}

	@Override
	public View getView(int paramInt, View paramView, ViewGroup viewGroup)
	{
		if( paramView == null )
		{
			paramView = this.mInflater.inflate(R.layout._4_checkin_list_item, null);
			//paramView.setTag(null);
		}

		TextView     chView  = (TextView)paramView.findViewById(R.id.CheckinListItem_TextId);
		TextView     stateView = (TextView)paramView.findViewById(R.id.CheckinListItem_StateId);
		//LinearLayout baseView = (LinearLayout)paramView.findViewById(R.id.CheckinListItem_RootId);
		//baseView.setOnClickListener(this.tabCheckinList);

		if( this.items != null && this.items.size() != 0 )
		{
			try
			{
				Checkin checkin = this.items.get(paramInt);
				String text = GetTextForItem(checkin);
				String state = Integer.toString(checkin.get_StateCheckinOnServer());

				chView.setText(text);

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
			catch (Exception e)
			{
				Exception ex = e;
			}
		}
		return paramView;
	}

	private Filter _filter;
	@Override
	public Filter getFilter()
	{
		if( this._filter == null )
		{
			this._filter = new CheckinFilter(this);
		}
		return this._filter;
	}

}