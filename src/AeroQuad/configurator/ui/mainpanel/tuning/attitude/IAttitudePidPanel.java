package AeroQuad.configurator.ui.mainpanel.tuning.attitude;

import AeroQuad.configurator.messagesdispatcher.PIDData;
import AeroQuad.configurator.ui.mainpanel.tuning.UserLevel;

public interface IAttitudePidPanel
{
    void setUserLevel(UserLevel userLevel);

    void setAccelRollPid(PIDData pid);

    void setAccelPitchPid(PIDData pid);

    void setSinced(boolean synced);

    void setAccelCutOff(String accelCutOff);
}
