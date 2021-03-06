package AeroQuad.configurator.ui.mainpanel.tuning.altitudehold;

import AeroQuad.configurator.communication.messaging.IMessageDefinition;
import AeroQuad.configurator.communication.messaging.request.AltitudeHoldPidRequest;
import AeroQuad.configurator.communication.messaging.request.IRequest;
import AeroQuad.configurator.messagesdispatcher.AltitudeHoldPidData;
import AeroQuad.configurator.messagesdispatcher.IMessageDispatcher;
import AeroQuad.configurator.messagesdispatcher.PIDData;
import AeroQuad.configurator.ui.mainpanel.tuning.UserLevel;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class AltitudeHoldPidPanelController implements IAltitudeHoldPidPanelController
{
    private final IMessageDispatcher _messageDispatcher;
    private IAltitudeHoldPidPanel _panel;

    private boolean _haveBeenSincedOnce = false;
    private AltitudeHoldPidData _altitudeHoldPidData = new AltitudeHoldPidData();
    private AltitudeHoldPidData _userAltitudeHoldPidData = new AltitudeHoldPidData();


    public AltitudeHoldPidPanelController(final IMessageDispatcher messageDispatcher)
    {
        _messageDispatcher = messageDispatcher;

        _messageDispatcher.addListener(IMessageDispatcher.ALTITUDE_HOLD_PID_KEY, new PropertyChangeListener()
        {
            @Override
            public void propertyChange(final PropertyChangeEvent evt)
            {
                _altitudeHoldPidData = (AltitudeHoldPidData)evt.getNewValue();
                if (!_haveBeenSincedOnce)
                {
                    updatePanelFromPidData(_altitudeHoldPidData);
                    _userAltitudeHoldPidData = _altitudeHoldPidData.getCopy();
                    _haveBeenSincedOnce = true;
                    _panel.setSinced(true);
                }
            }
        });

    }

    private void updatePanelFromPidData(final AltitudeHoldPidData altitudeHoldPidData)
    {
        _panel.setAltitudePid(altitudeHoldPidData.getAltitudePid());
        _panel.setThrottleBump(altitudeHoldPidData.getThrottleBump());
        final double maxVelocitySpeed = Double.parseDouble(altitudeHoldPidData.getMaxVelocitySpeed());
        _panel.setMaxVelocitySpeed(Double.toString(maxVelocitySpeed / 100.0));
        _panel.setSmoothFactor(altitudeHoldPidData.getSmoothFactor());
        _panel.setZDampening(altitudeHoldPidData.getZDampeningPid());
    }

    @Override
    public void setUserLevel(final UserLevel userLevel)
    {
        _panel.setUserLevel(userLevel);
    }

    @Override
    public IRequest getRequest()
    {
        return new AltitudeHoldPidRequest(_messageDispatcher);
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
        buffer.append(IMessageDefinition.ALTITUDE_HOLD_PID_SET_COMMAND);

        buffer.append(_userAltitudeHoldPidData.getAltitudePid().getP() + ";");
        buffer.append(_userAltitudeHoldPidData.getAltitudePid().getI() + ";");
        buffer.append(_userAltitudeHoldPidData.getAltitudePid().getD() + ";");
        buffer.append(_userAltitudeHoldPidData.getThrottleBump() + ";");
        buffer.append(_userAltitudeHoldPidData.getMaxVelocitySpeed() + ";");
        buffer.append(_userAltitudeHoldPidData.getSmoothFactor() + ";");
        buffer.append(_userAltitudeHoldPidData.getZDampeningPid().getP() + ";");
        buffer.append(_userAltitudeHoldPidData.getZDampeningPid().getI() + ";");
        buffer.append(_userAltitudeHoldPidData.getZDampeningPid().getD());

        return buffer.toString();
    }

    @Override
    public void userDefaultButtonPressed()
    {
        final String altitudeP = System.getProperty(DEFAULT_ALTITUDE_HOLD_P);
        final String altitudeI = System.getProperty(DEFAULT_ALTITUDE_HOLD_I);
        final String altitudeD = System.getProperty(DEFAULT_ALTITUDE_HOLD_D);
        final PIDData altitudePid = new PIDData(altitudeP,altitudeI,altitudeD);

        final String altitudeBump = System.getProperty(DEFAULT_ALTITUDE_BUMP);
        final String altitudePanic = System.getProperty(DEFAULT_MAX_VELOCITY_SPEED);
        final String smoothFactor = System.getProperty(DEFAULT_ALTITUDE_SMOOTH_FACTOR);

        final String zDampeningP = System.getProperty(DEFAULT_ALTITUDE_ZDAMPENING_P);
        final String zDampeningI = System.getProperty(DEFAULT_ALTITUDE_ZDAMPENING_I);
        final String zDampeningD = System.getProperty(DEFAULT_ALTITUDE_ZDAMPENING_D);
        final PIDData zDampeningPid = new PIDData(zDampeningP,zDampeningI,zDampeningD);

        _userAltitudeHoldPidData = new AltitudeHoldPidData(altitudePid,
                                                           altitudeBump,
                                                           altitudePanic,
                                                           smoothFactor,
                                                           zDampeningPid);
        updatePanelFromPidData(_userAltitudeHoldPidData);
    }

    @Override
    public void setHaveNotBeenSincedOnce()
    {
        _haveBeenSincedOnce = false;
        _panel.setSinced(_haveBeenSincedOnce);
        _altitudeHoldPidData = new AltitudeHoldPidData();
        updatePanelFromPidData(_altitudeHoldPidData);
    }

    @Override
    public void setPanel(final IAltitudeHoldPidPanel panel)
    {
        _panel = panel;
    }

    @Override
    public void userAltitudeHoldPidChanged(final PIDData pid)
    {
        _userAltitudeHoldPidData.setAltitudeHoldPid(pid);
    }

    @Override
    public void userThrottleBumpValueChanged(final String throttleBump)
    {
        _userAltitudeHoldPidData.setThrottleBump(throttleBump);
    }

    @Override
    public void userMaxVelocitySpeedChanged(final String maxVelocitySpeed)
    {
        final double maxSpeed = Double.parseDouble(maxVelocitySpeed) * 100.0;
        _userAltitudeHoldPidData.setMaxVelocitySpeed(Double.toString(maxSpeed));
    }

    @Override
    public void userSmoothFactorValueChanged(final String text)
    {
        _userAltitudeHoldPidData.setSmoothFactor(text);
    }

    @Override
    public void userZDampeningPidChanged(final PIDData pid)
    {
        _userAltitudeHoldPidData.setZDampeningPid(pid);
    }

    @Override
    public boolean isUserDataInSinced()
    {
        boolean ret = true;
        if (!_altitudeHoldPidData.equals(_userAltitudeHoldPidData))
        {
            ret = false;
        }
        _panel.setSinced(ret);
        return ret;
    }
}
