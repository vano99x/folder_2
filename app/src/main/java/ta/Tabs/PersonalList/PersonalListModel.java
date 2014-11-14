package ta.Tabs.PersonalList;

import ta.timeattendance.MainActivity;

public class PersonalListModel implements IPersonalListModel
{
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

	private MainActivity.State __CallerView;
	public MainActivity.State get_CallerView()
	{
		return this.__CallerView;
	}
	public void set_CallerView(MainActivity.State s)
	{
		this.__CallerView = s;
	}
}
