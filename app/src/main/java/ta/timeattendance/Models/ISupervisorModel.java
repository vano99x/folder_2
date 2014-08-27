package ta.timeattendance.Models;

import ta.lib.*;
import ta.Database.*;

public interface ISupervisorModel extends IBaseModel
{
	Personel get_CurrentSuperviser();
	void set_CurrentSuperviser(Personel p);

	void SvChanged_EventAdd(RunnableWithArgs runnable);
}