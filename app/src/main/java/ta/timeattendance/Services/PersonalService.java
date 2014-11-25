package ta.timeattendance.Services;

import android.database.Cursor;

import java.util.ArrayList;
import java.net.URL;

import ta.Database.DbConnector;
import ta.Database.Personel;
import ta.lib.*;
import ta.lib.BackgroundFunc;
import ta.lib.RunnableWithArgs;
import ta.lib.RunnableWithEvent;

public class PersonalService implements IPersonalService
{

	public PersonalService()
	{
		this.UpdWorkerCard_CmplEvent = new UpdWorkerCard_CmplEventClass();
	}

	//*********************************************************************************************
	//**     event

	private static class UpdWorkerCard_CmplEventClass extends Event<String,Boolean> {}
	private              UpdWorkerCard_CmplEventClass
	                     UpdWorkerCard_CmplEvent;
	public void add_UpdateWorkerCardIdOnServerComplete(RunnableWithArgs runnable)
	{
		this.UpdWorkerCard_CmplEvent.Add(runnable);
	}



	//*********************************************************************************************
	//**     Finde worker by lastname
	public void LoadWorkerFromLocalDb(String str, Runnable completed)
	{
		BackgroundFunc.Go(    get_loadW(str).Add(get_LW_Completed(completed)),    "-LoadWorkerFromLocalDb-"    );
	}

	private loadW get_loadW(String str) { loadW o = new loadW(); o.arg1 = this; o.arg2 = str; return o; }
	private static class loadW extends RunnableWithEvent<Personel[],Boolean> { public void run()
	{
		DbConnector db = DbConnector.getInstance();
		String str = (String)this.arg2;
		//String upperStr = str.toUpperCase();

		//String str = "SELECT * FROM Personel WHERE UPPER(LastName) LIKE '%" + upperStr + "%'";
		//String str = "SELECT * FROM Personel";
		String sql = "SELECT * FROM Personel WHERE LastName LIKE '%" + str + "%'";

		Cursor c = db.Select(sql, null);

		ArrayList<Personel> al = Personel.ArrayFromCursor(c);

		Personel[] arr = al.toArray(new Personel[0]);

		this.arg = arr;
		this.result = true;
	}}

	private LW_Completed get_LW_Completed(Runnable completed) { LW_Completed o = new LW_Completed(); o.arg1 = this; o.arg2 = completed; return o; }
	private static class LW_Completed extends RunnableWithArgs<Personel[],Boolean> { public void run()
	{
		boolean result = this.result;   PersonalService _this = (PersonalService)this.arg1;
		if(result) {
			_this.set_LoadedWorkerFromLocalDb(this.arg);
			((Runnable)this.arg2).run();
		}
	}}

	private Personel[] __loadedWorkerFromLocalDb;
	public Personel[] get_LoadedWorkerFromLocalDb()
	{
		return this.__loadedWorkerFromLocalDb;
	}
	private void set_LoadedWorkerFromLocalDb(Personel[] value)
	{
		this.__loadedWorkerFromLocalDb = value;
	}



	//*********************************************************************************************
	//**     update worker cardId on server
	public void UpdateWorkerCardIdOnServer(Personel tag)
	{
		BackgroundFunc.Go(    get_updCard(tag).Add(get_updCard_Completed()),    "-UpdateWorkerCardIdOnServer-"    );
	}

	private temp1 get_updCard(Personel tag) { temp1 o = new temp1(); o.arg1 = this; o.arg2 = tag; return o; }
	private static class temp1 extends RunnableWithEvent<String,Boolean> {
	//@Override public void call() throws Exception {
	public void run(){
		boolean res = false;
		try {
			Personel tag = (Personel)this.arg2;
			String respond = null;
			String strUrl = "http://93.153.172.26/update_worker?worker="+String.valueOf(tag.Id)+"&card="+tag.CardId;
			respond = HttpHelper.httpGet(new URL(strUrl));
			this.arg = respond;
			res = true;
		}
		catch( Exception e)
		{
			result = false;
			HttpHelper.ExceptionHandler( e );
		}
		this.result = res;
	}
	//public void run(){}
	}

	private temp2 get_updCard_Completed() { temp2 o = new temp2(); o.arg1 = this; return o; }
	private static class temp2 extends RunnableWithArgs<String,Boolean> { public void run()
	{
		PersonalService _this = (PersonalService)this.arg1;
		String  arg = this.arg;
		Boolean res = this.result;
		_this.UpdWorkerCard_CmplEvent.RunEvent(arg,res);
	}}



	public void ClearDependencies() {
	}
	public boolean get_IsClearDependencies() {
		return false;
	}
	public void UpdateDependencies() {
	}
	public boolean get_IsKeepAlive() {
		return false;
	}
}
