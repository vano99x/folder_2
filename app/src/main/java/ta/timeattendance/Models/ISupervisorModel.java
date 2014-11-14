package ta.timeattendance.Models;

import ta.lib.*;
import ta.Database.*;

public interface ISupervisorModel extends IBaseModel
{
	Personel get_CurrentSupervisor();
	void set_CurrentSuperviser(Personel p);

	Personel get_SelectedWorker();
	void set_SelectedWorker(Personel p);

	Personel get_WorkerAppliedNFC();
	void set_WorkerAppliedNFC(Personel p);

	void SvChanged_EventAdd(RunnableWithArgs runnable);

	void SaveWorkerCheckin(Personel p);
}