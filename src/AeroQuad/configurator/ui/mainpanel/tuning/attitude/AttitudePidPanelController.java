package AeroQuad.configurator.ui.mainpanel.tuning.attitude;

import AeroQuad.configurator.communication.messaging.IMessageDefinition;
import AeroQuad.configurator.communication.messaging.request.AttitudePidRequest;
import AeroQuad.configurator.communication.messaging.request.IRequest;
import AeroQuad.configurator.messagesdispatcher.AttitudePidData;
import AeroQuad.configurator.messagesdispatcher.IMessageDispatcher;
import AeroQuad.configurator.messagesdispatcher.PIDData;
import AeroQuad.configurator.ui.mainpanel.tuning.UserLevel;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class AttitudePidPanelController implements IAttitudePidPanelController
{
    private final IMessageDispatcher _messageDispatcher;
    private IAttitudePidPanel _panel;

    private boolean _haveBeenSincedOnce = false;

    private AttitudePidData _attitudePidData = new AttitudePidData();
    private AttitudePidData _userAttitudePidData = new AttitudePidData();
    private UserLevel _userLevel = UserLevel.Beginner;

    public AttitudePidPanelController(final IMessageDispatcher messageDispatcher)
    {
        _messageDispatcher = messageDispatcher;

        _messageDispatcher.addListener(IMessageDispatcher.ATTITUDE_PID_DATA_KEY, new PropertyChangeListener()
        {
            @Override
            public void propertyChange(final PropertyChangeEvent evt)
            {
                _attitudePidData = (AttitudePidData) evt.getNewValue();
                if (!_haveBeenSincedOnce)
                {
                    updatePanelFromPidData(_attitudePidData);
                    _userAttitudePidData = _attitudePidData.getCopy();
                    _haveBeenSincedOnce = true;
                    _panel.setSinced(true);
                }
            }
        });
    }

    private void updatePanelFromPidData(final AttitudePidData attitudePidData)
    {
        _panel.setAccelPitchPid(attitudePidData.getAccelPitchPid());
        _panel.setAccelRollPid(attitudePidData.getAccelRollPid());
        _panel.setAccelCutOff(attitudePidData.getAccelCutOff());
    }

    @Override
    public void setUserLevel(final UserLevel userLevel)
    {
        _userLevel = userLevel;
        _panel.setUserLevel(userLevel);
    }

    @Override
    public IRequest getRequest()
    {
        return new AttitudePidRequest(_messageDispatcher);
    }

    @Override
    public boolean haveBeenSincedOnce()
    {
        return _haveBeenSincedOnce;
    }

    @Override
    public String getSetPidCommand()
    {
        final StringBuffer buffer = new StringBuffer();
        buffer.append(IMessageDefinition.ATTITUDE_PID_SET_COMMAND);
        buffer.append(_userAttitudePidData.getAccelRollPid().getP() + ";");
        buffer.append("0.00;");
        buffer.append("0.00;");
        if (_userLevel == UserLevel.Beginner)
        {
            buffer.append(_userAttitudePidData.getAccelRollPid().getP() + ";");
            buffer.append("0.00;");
            buffer.append("0.00;");
        }
        else
        {
            buffer.append(_userAttitudePidData.getAccelPitchPid().getP() + ";");
            buffer.append("0.00;");
            buffer.append("0.00;");
        }
        buffer.append(_userAttitudePidData.getAccelCutOff());
        return buffer.toString();
    }

    @Override
    public void userDefaultButtonPressed()
    {
        final String accelP = System.getProperty(DEFAULT_PID_ACCEL_P);
        final String accelI = System.getProperty(DEFAULT_PID_ACCEL_I);
        final String accelD = System.getProperty(DEFAULT_PID_ACCEL_D);
        final PIDData accelPid = new PIDData(accelP,accelI,accelD);

        _userAttitudePidData = new AttitudePidData(accelPid, accelPid.getCopy(), "0.0");
        updatePanelFromPidData(_userAttitudePidData);
    }

    @Override
    public void setHaveNotBeenSincedOnce()
    {
        _haveBeenSincedOnce = false;
        _panel.setSinced(_haveBeenSincedOnce);
        _attitudePidData = new AttitudePidData();
        updatePanelFromPidData(_attitudePidData);
    }

    @Override
    public void setPanel(final IAttitudePidPanel panel)
    {
        _panel = panel;
    }

    @Override
    public void userAccelRollPidChanged(final PIDData pid)
    {
        _userAttitudePidData.setAccelRollPid(pid);
        if(_userLevel == UserLevel.Beginner)
        {
            _userAttitudePidData.setAccelPitchPid(pid);
            _panel.setAccelPitchPid(pid);
        }
    }

    @Override
    public void userAccelPitchPidChanged(final PIDData pid)
    {
        _userAttitudePidData.setAccelPitchPid(pid);
    }

    @Override
    public void userAccelCutOffChanged(final String accelCutOff) {
        _userAttitudePidData.setAccelCutOff(accelCutOff);
    }

    @Override
    public boolean isUserDataInSinced()
    {
        boolean ret = true;
        if (!_attitudePidData.equals(_userAttitudePidData))
        {
            ret = false;
        }
        _panel.setSinced(ret);
        return ret;
    }
}
