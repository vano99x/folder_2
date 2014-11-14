package ta.Tabs.Settings;

import android.content.Context;
import java.net.URL;
import org.json.JSONObject;

import ta.lib.*;
import ta.timeattendance.Models.*;
import ta.timeattendance.MainEngine;
import ta.timeattendance.MainActivity;

public class CurrentVersionServices implements ICurrentVersionServices
{
	private MainEngine _engine;
	
	public CurrentVersionServices()
	{
		this._engine = MainEngine.getInstance();
		//_context = MainActivity.get_FragmentActivity();
		this.CurrentVersionLoaded = new CurrentVersionLoadedEventClass();
	}
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



	private class   CurrentVersionLoadedEventClass extends Event<String,Boolean> {}
	private         CurrentVersionLoadedEventClass CurrentVersionLoaded;
	public void set_CurrentVersionLoaded(RunnableWithArgs runnable){
		this.CurrentVersionLoaded.Add(runnable);
	}



	class LoadCurrentVersionNumberBFClass extends BackgroundFunc<String,Boolean> {}
	public void LoadCurrentVersionNumber()
	{
		BackgroundFunc.Go( new LoadCurrentVersionNumberBFClass(), get_Load(), get_LoadComplete(), "-LoadCurrentVersionNumber-");
	}
	
	private LoadComplete get_LoadComplete() { LoadComplete o = new LoadComplete(); o.arg1 = this; return o; }
	class   LoadComplete extends RunnableWithArgs<String,Boolean> { public void run() // ---> call in ui thread
	{
		boolean result = this.result;
		if(result)
		{
			CurrentVersionLoaded.RunEvent( this.arg );
		}
	}}
	
	private Load get_Load() { Load o = new Load(); o.arg1 = this; return o; }
	class Load extends RunnableWithArgs<String,Boolean> { public void run()
	{
		CurrentVersionServices _this = (CurrentVersionServices)this.arg1;
		String currentVersionNumber = null;
		boolean result = false;

		if( HttpHelper.IsInternetAvailable(_this._engine.mContext))
		{
			try
			{
				//String str = HttpHelper.getCurrentVersionNumberURL();
				/*String str = null;
				URL url = new URL(str);
				String data = HttpHelper.httpGet(url);

				JSONObject json = new JSONObject(data);

				if(json.has("Number"))
				{
					currentVersionNumber = json.getInt("Number");
					result = true;
				}*/
				currentVersionNumber = "1.01";
				result = true;
			}
			catch( Exception e)
			{
				HttpHelper.ExceptionHandler( e );
			}
		}

		this.arg = currentVersionNumber;
		this.result = result;
	}}
}