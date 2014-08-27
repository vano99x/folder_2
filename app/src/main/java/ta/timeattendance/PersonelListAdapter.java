package ta.timeattendance;

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
			paramView = this.mInflater.inflate(R.layout.list_item, null);
			paramView.setTag(null);
		}

		try
		{
			TextView     textView = (TextView)    paramView.findViewById(R.id.txtName);
			LinearLayout baseView = (LinearLayout)paramView.findViewById(R.id.item);
			baseView.setOnClickListener(this.listener);

			if (this.personelArray != null)
			{
				Personel p = this.personelArray[paramInt];

				//if(p.CardId == null)
				//{
				//int aaa = 9;
				//int aaa2 = 9-2;
				//}

				textView.setText(p.LastName + " " + p.FirstName + " " + p.ThirdName);
				baseView.setTag(new Object[]{ R.id.PersonelListItem_Id, p});
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

/* Location:           C:\Users\vano99\Desktop\jd-gui-0.3.5.windows\TandAOffline_dex2jar.jar
 * Qualified Name:     com.ifree.timeattendance.PersonelListAdapter
 * JD-Core Version:    0.6.2
 */