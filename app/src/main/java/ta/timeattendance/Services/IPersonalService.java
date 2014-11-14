package ta.timeattendance.Services;

import ta.Database.Personel;
import ta.lib.RunnableWithArgs;
import ta.timeattendance.Models.*;

public interface IPersonalService extends IBaseModel
{
	void LoadWorkerFromLocalDb(String str, Runnable completed);
	Personel[] get_LoadedWorkerFromLocalDb();

	void UpdateWorkerCardIdOnServer(Personel tag);
	void add_UpdateWorkerCardIdOnServerComplete(RunnableWithArgs runnable);
}
