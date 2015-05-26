package AeroQuad.configurator.ui.mainpanel.tuning.attitude;

import AeroQuad.configurator.messagesdispatcher.PIDData;
import AeroQuad.configurator.ui.mainpanel.tuning.IPidPanelController;

public interface IAttitudePidPanelController extends IPidPanelController
{
    String DEFAULT_PID_ACCEL_P = "pid.attitude.accel.default.p";
    String DEFAULT_PID_ACCEL_I = "pid.attitude.accel.default.i";
    String DEFAULT_PID_ACCEL_D = "pid.attitude.accel.default.d";

    void setPanel(IAttitudePidPanel panel);

    void userAccelRollPidChanged(PIDData pid);

    void userAccelPitchPidChanged(PIDData pid);

    void userAccelCutOffChanged(String text);
}
