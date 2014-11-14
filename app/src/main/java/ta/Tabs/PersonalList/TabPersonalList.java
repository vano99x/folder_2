package ta.Tabs.PersonalList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

import ta.Database.Personel;
import ta.lib.tabui.Tab;
import ta.Tabs.PersonalInfo.TabPersonalInfo;
import ta.lib.*;
import ta.timeattendance.MainActivity.State;
import ta.timeattendance.*;
import ta.timeattendance.Models.*;
import ta.timeattendance.Services.*;
import ta.timeattendance.R;

public class TabPersonalList extends Tab implements View.OnClickListener
{
	private ListView listView;
	//private TextView mLableEmptyList;
	private EditText txtSearch;

	private IPersonalService __personalService;

	public TabPersonalList(Context context, ViewGroup viewGroup, int i1, int i2)
	{
		super(context, viewGroup, i1, i2);
		//this._engine = MainEngine.getInstance();

		listView        = (ListView)this.root.findViewById(R.id.PersonelList_ListView_Id);
		//mLableEmptyList = (TextView)this.root.findViewById(R.id.lable_empty_list);
		txtSearch       = (EditText)this.root.findViewById(R.id.PersonelList_EditText_Id);

		View searchBtn = this.root.findViewById(R.id.SearchButton_Id);
		searchBtn.setOnClickListener(this);
		searchBtn.setTag(new Object[]{R.id.SearchButton_Id});
		
		// subscribe on event
		//this._engine.WorkerFound.Add(get_onWorkerFound());
		this.__personalService = Bootstrapper.Resolve(IPersonalService.class);
	}



	private void createAdapter(Personel[] personalArr)
	{
		try
		{
			if( personalArr.length != 0 ) {
				//Tab.Hide(this.mLableEmptyList);
			}
			this.listView.setAdapter(new PersonelListAdapter(this.context, personalArr, this));
		}
		catch (Exception e) {
			Exception ex = e;
		}
	}



	//*********************************************************************************************
	//       Load Workers
	private void SearchButton_Click()
	{
		//String str = getSearchString();
		//this._engine.searchPersonel(str);
		String str = this.txtSearch.getText().toString();
		this.__personalService.LoadWorkerFromLocalDb(str, get_onWorkerFound());
	}

	private static class IdSorter implements Comparator<Personel>
	{
		public int compare(Personel anEmployee, Personel anotherEmployee)
		{
			return anEmployee.LastName.compareTo(anotherEmployee.LastName);
		}
	}

	private onWorkerFound get_onWorkerFound() { onWorkerFound a = new onWorkerFound(); a.arg1 = this; return a; }
	class onWorkerFound extends RunnableWithArgs<Object/*Personel[]*/,Object> { public void run()
	{
		TabPersonalList _this = (TabPersonalList)this.arg1;

		//Personel[] arrPersonel = this.arg;
		Personel[] arrPersonel = _this.__personalService.get_LoadedWorkerFromLocalDb();
		ArrayList<Personel> list = new ArrayList<Personel>(Arrays.asList(arrPersonel));
		Collections.sort(list, new IdSorter());
		arrPersonel = list.toArray(new Personel[0]);

		_this.createAdapter(arrPersonel);
	}}



	//*********************************************************************************************
	//       Control Handler
	private void PersonelListItem_Selected(Personel personel)
	{
		//MainEngine.getInstance().set_CurrentWorker( personel );
		//TabPersonelInfo pi = ((TabPersonelInfo)UIHelper.Instance().get_TabConteiner().GetByEnum(State.PERSONEL_INFO).Tab);
		//pi.IsShowCheckiedWorker = false;
		//UIHelper.Instance().switchState(ta.timeattendance.MainActivity.State.PERSONEL_INFO);

		Bootstrapper.Resolve( ISupervisorModel.class ).set_SelectedWorker(personel);

		IPersonalListModel plm = Bootstrapper.Resolve(IPersonalListModel.class);
		MainActivity.State state = plm.get_CallerView();
		if(state.equals(State.PERSONEL_INFO))
		{
			TabPersonalInfo pi = ((TabPersonalInfo)UIHelper.Instance().get_TabConteiner().GetByEnum(State.PERSONEL_INFO).Tab);
			pi.IsShowCheckiedWorker = false;
		}

		UIHelper.Instance().switchState(state);
	}



	public void onClick(View ctrl)
	{
		Object tag = ctrl.getTag();
		Object [] arr = (Object[])tag;
		Integer integer = (Integer)arr[0];

		if( integer != null)
		{
			switch(integer) {
			case R.id.SearchButton_Id:{
				SearchButton_Click();
			break;}
			case R.id.PersonelList_Item_TagId:{
				PersonelListItem_Selected((Personel)arr[1]);
			break;}
			}
		}
	}
}