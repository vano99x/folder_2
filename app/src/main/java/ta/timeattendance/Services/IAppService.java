package ta.timeattendance.Services;

import ta.timeattendance.Models.*;

public interface IAppService extends IBaseModel
{
	ta.timeattendance.Services.AppService.CreateEventClass  get_Create();     void CreateRunEvent();
	ta.timeattendance.Services.AppService.ClosingEventClass get_Closing();    void ClosingRunEvent();
	ta.timeattendance.Services.AppService.RunningEventClass get_Running();    void RunningRunEvent();
	ta.timeattendance.Services.AppService.LogoutEventClass  get_Logout();     void LogoutRunEvent();

	ta.timeattendance.Services.AppService.GotFocusEventClass  get_GotFocus();     void GotFocusRunEvent();
}