package ta.Tabs.FacilityInfo;

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

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.text.SimpleDateFormat;

import ta.Database.*;
import ta.Database.FacilityInfoEntity.*;

import ta.timeattendance.R;

public class FacilityInfoAdapter extends BaseAdapter //implements Filterable
{
	private LayoutInflater mInflater;
	private TabFacilityInfo _tabFacilityInfo;
	private FacilityEntity [] _facilityEntityArray;

	public FacilityInfoAdapter(Context context, TabFacilityInfo tabFacilityInfo)
	{
		this._tabFacilityInfo = tabFacilityInfo;
		this.mInflater = ((LayoutInflater)context.getSystemService("layout_inflater"));
		this._facilityEntityArray = null;
	}



	//*********************************************************************************************
	//*      implements
	public Object getItem(int paramInt)
	{
		return Integer.valueOf(paramInt);
	}
	public long getItemId(int paramInt)
	{
		return paramInt;
	}
	public int getCount()
	{
		if( this._facilityEntityArray == null )
		{
			return 0;
		}
		return this._facilityEntityArray.length;
	}



	//*********************************************************************************************
	//*      property
	public FacilityEntity [] get_Items()
	{
		return null;
	}
	public void set_Items(FacilityEntity [] value)
	{
		this._facilityEntityArray = value;
	}



	public View getView(int index, View paramView, ViewGroup paramViewGroup)
	{
		if( paramView == null ) {
			paramView = this.mInflater.inflate(R.layout.p_facility_info_item, null); //paramView.setTag(null);
		}

		FacilityEntity fe = this._facilityEntityArray[index];

		TextView facilityNameCtrl = (TextView) paramView.findViewById(R.id.FacilityInfoItem_FacilityName_Id);
		TextView requestCountCtrl = (TextView) paramView.findViewById(R.id.FacilityInfoItem_RequestCount_Id);

		TextView GrantedCountCtrl = (TextView) paramView.findViewById(R.id.FacilityInfoItem_GrantedCount_Id);
		TextView HoursWorkedCtrl = (TextView) paramView.findViewById(R.id.FacilityInfoItem_HoursWorked_Id);
		TextView FinesSumCtrl = (TextView) paramView.findViewById(R.id.FacilityInfoItem_FinesSum_Id);
		TextView BonusesSumCtrl = (TextView) paramView.findViewById(R.id.FacilityInfoItem_BonusesSum_Id);

		facilityNameCtrl.setText(fe.FacilityName);
		requestCountCtrl.setText(fe.RequestCount);
		GrantedCountCtrl.setText(fe.GrantedCount);
		HoursWorkedCtrl.setText( fe.HoursWorked);
		FinesSumCtrl.setText(    fe.FinesSum);
		BonusesSumCtrl.setText(  fe.BonusesSum);
		
		return paramView;
	}

	//private Filter _filter;
	//@Override
	//public Filter getFilter()
	//{
	//if( this._filter == null )
	//{
	//this._filter = new FacilityInfoFilter(this._tabFacilityInfo);
	//}
	//return this._filter;
	//}
}