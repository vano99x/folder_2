package ta.Tabs.PersonalList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import ta.Database.Personel;

import ta.timeattendance.R;

public class PersonelListAdapter extends BaseAdapter
{
	private View.OnClickListener listener;
	private LayoutInflater mInflater;
	private Personel[] personelArray;

	public PersonelListAdapter(Context paramContext, Personel[] paramArrayOfPersonel, View.OnClickListener paramOnClickListener)
	{
		this.mInflater = ((LayoutInflater)paramContext.getSystemService("layout_inflater"));
		this.personelArray = paramArrayOfPersonel;
		this.listener = paramOnClickListener;
	}

	public int getCount()
	{
		if (this.personelArray == null)
			return 0;
		return this.personelArray.length;
	}

	public Object getItem(int paramInt)
	{
		if ((this.personelArray != null) && (this.personelArray.length > paramInt))
			return this.personelArray[paramInt];
		return null;
	}

	public long getItemId(int paramInt)
	{
		return paramInt;
	}

	public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
	{
		if (paramView == null)
		{
			paramView = this.mInflater.inflate(R.layout.p_personel_list_item, null);
			paramView.setTag(null);
		}

		try
		{
			LinearLayout baseView  = (LinearLayout)paramView.findViewById(R.id.PersonelList_Item_RootId);
			baseView.setOnClickListener(this.listener);
			TextView     FirstName = (TextView)    paramView.findViewById(R.id.PersonelList_Item_FirstName_Id);
			TextView     LastName  = (TextView)    paramView.findViewById(R.id.PersonelList_Item_LastName_Id);
			TextView     ThirdName = (TextView)    paramView.findViewById(R.id.PersonelList_Item_ThirdName_Id);
			TextView     WorkerId  = (TextView)    paramView.findViewById(R.id.PersonelList_Item_WorkerId_Id);

			if (this.personelArray != null)
			{
				Personel p = this.personelArray[paramInt];

				//if(p.CardId == null)
				//{
				//int aaa = 9;
				//int aaa2 = 9-2;
				//}

				FirstName.setText(p.FirstName);//p.FirstName.
				LastName.setText(p.LastName);
				ThirdName.setText(p.ThirdName);
				WorkerId.setText(String.valueOf(p.Id));

				baseView.setTag(new Object[]{ R.id.PersonelList_Item_TagId, p});
			}
			return paramView;
		}
		catch (Exception e)
		{
			//TALog.Log(e.toString());
		}
		return paramView;
	}
}