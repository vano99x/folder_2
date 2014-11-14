package ta.timeattendance.Services;

import ta.lib.RunnableWithArgs;
import ta.timeattendance.Models.*;

public interface IAppService extends IBaseModel
{
	ta.timeattendance.Services.AppService.CreateEventClass  get_Create();     void CreateRunEvent();
	ta.timeattendance.Services.AppService.ClosingEventClass get_Closing();    void ClosingRunEvent();
	ta.timeattendance.Services.AppService.RunningEventClass get_Running();    void RunningRunEvent();

	ta.timeattendance.Services.AppService.GotFocusEventClass  get_GotFocus();     void GotFocusRunEvent();

	//ta.timeattendance.Services.AppService.LogoutEventClass  get_Logout();     void LogoutRunEvent();
	void add_Logout(        RunnableWithArgs r);    void         LogoutRunEvent();
	void add_Clearing(      RunnableWithArgs r);    void       ClearingRunEvent();
	void add_LogoutClearing(RunnableWithArgs r);    void LogoutClearingRunEvent();
	void Login();
}