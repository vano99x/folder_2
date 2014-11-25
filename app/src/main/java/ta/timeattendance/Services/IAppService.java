package ta.timeattendance.Services;

import ta.lib.RunnableWithArgs;
import ta.timeattendance.Models.*;

public interface IAppService extends IBaseModel
{
	// creat new activity
	//ta.timeattendance.Services.AppService.CreateEventClass  get_Create();     void CreateRunEvent();
	void add_Creating(      RunnableWithArgs r);    void       CreatingRunEvent();

	// open window
	ta.timeattendance.Services.AppService.RunningEventClass get_Running();    void RunningRunEvent();

	// exit sv - save data
	void add_Logout(        RunnableWithArgs r);    void         LogoutRunEvent();

	// delete sv that use in Logout
	void add_LogoutClearing(RunnableWithArgs r);    void LogoutClearingRunEvent();

	// clase win
	ta.timeattendance.Services.AppService.ClosingEventClass get_Closing();    void ClosingRunEvent();

	// clear old activity
	void add_Clearing(      RunnableWithArgs r);    void       ClearingRunEvent();



	ta.timeattendance.Services.AppService.GotFocusEventClass  get_GotFocus();     void GotFocusRunEvent();
	void Login();
}