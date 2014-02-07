package AeroQuad.configurator.ui.mainpanel.tuning.altitudehold;

import AeroQuad.configurator.messagedispatcher.PIDData;
import AeroQuad.configurator.ui.mainpanel.tuning.UserLevel;
import AeroQuad.configurator.ui.mainpanel.tuning.pidpanel.PidPanel;
import AeroQuad.configurator.ui.mainpanel.tuning.singleparamconfigpanel.SingleParamConfigPanel;
import AeroQuad.configurator.ui.mainpanel.tuning.syncedstate.SyncedStatePanel;
import AeroQuad.configurator.ui.uiutils.UiUtils;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AltitudeHoldPidPanel extends JPanel implements IAltitudeHoldPidPanel
{
    private final IAltitudeHoldPidPanelController _controller;

    private final PidPanel _altitudePidPanel = new PidPanel("Altitude");
    private final SingleParamConfigPanel _throttleBumpPanel = new SingleParamConfigPanel("<HTML><CENTER>Throttle<BR>Bump</CENTER></HTML>");
    private final SingleParamConfigPanel _throttlePanicPanel = new SingleParamConfigPanel("<HTML><CENTER>Throttle<BR>Panic</CENTER></HTML>");
    private final SingleParamConfigPanel _minThrottleAdjustPanel = new SingleParamConfigPanel("<HTML><CENTER>Min Throttle<BR>Adjust</CENTER></HTML>");
    private final SingleParamConfigPanel _maxThrottleAdjustPanel = new SingleParamConfigPanel("<HTML><CENTER>Max Throttle<BR>Adjust</CENTER></HTML>");
    private final SingleParamConfigPanel _smoothFactorPanel = new SingleParamConfigPanel("<HTML><CENTER>Smooth<BR>Factor</CENTER></HTML>");
    private final PidPanel _zDampening = new PidPanel("Z Dampening");
    private final JButton _resetDefaultButton = new JButton("<HTML><CENTER>Reset<BR>Default</CENTER></HTML>");
    private final SyncedStatePanel _syncStatePanel = new SyncedStatePanel();
    private UserLevel _userLevel;
    private JPanel _centerPanel;

    public AltitudeHoldPidPanel(final IAltitudeHoldPidPanelController altitudeHoldPidPanelController)
    {
        _controller = altitudeHoldPidPanelController;

        _controller.setPanel(this);

        initPanel();
    }

    private void initPanel()
    {
        setLayout(new BorderLayout());
        setBorder(new LineBorder(Color.black,4));

        final JPanel mainPanel = new JPanel(new BorderLayout());
        final JLabel headerLabel = new JLabel("Altitude Hold");
        headerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headerLabel.setPreferredSize(new Dimension(0, UiUtils.HEATHER_PREFERED_HEIGHT));
        mainPanel.add(headerLabel, BorderLayout.NORTH);
        headerLabel.setBorder(new LineBorder(Color.black, 1));

        _centerPanel = new JPanel(new GridLayout(1,12));
        _centerPanel.add(_altitudePidPanel);
        _centerPanel.add(_throttleBumpPanel);
        _centerPanel.add(_throttlePanicPanel);
        _centerPanel.add(_minThrottleAdjustPanel);
        _centerPanel.add(_maxThrottleAdjustPanel);
        _centerPanel.add(_smoothFactorPanel);
        _centerPanel.add(_smoothFactorPanel);
        _centerPanel.add(_zDampening);
        _centerPanel.add(_resetDefaultButton);
        _centerPanel.add(_syncStatePanel);

        mainPanel.add(_centerPanel, BorderLayout.CENTER);
        add(mainPanel, BorderLayout.WEST);

        _altitudePidPanel.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(final ActionEvent e)
            {
                _controller.userAltitudeHoldPidChanged(_altitudePidPanel.getPid());
            }
        });

        _throttleBumpPanel.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(final ActionEvent e)
            {
                _controller.userThrottleBumpValueChanged(_throttleBumpPanel.getText());
            }
        });

        _throttlePanicPanel.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(final ActionEvent e)
            {
                _controller.userThrottlePanicValueChanged(_throttlePanicPanel.getText());
            }
        });

        _minThrottleAdjustPanel.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(final ActionEvent e)
            {
                _controller.userMinThrottleAdjustValueChanged(_minThrottleAdjustPanel.getText());
            }
        });

        _maxThrottleAdjustPanel.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(final ActionEvent e)
            {
                _controller.userMaxThrottleAdjustValueChanged(_maxThrottleAdjustPanel.getText());
            }
        });

        _smoothFactorPanel.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(final ActionEvent e)
            {
                _controller.userSmoothFactorValueChanged(_smoothFactorPanel.getText());
            }
        });

        _zDampening.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(final ActionEvent e)
            {
                _controller.userZDampeningPidChanged(_zDampening.getPid());
            }
        });

        _resetDefaultButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(final ActionEvent e)
            {
                _controller.userDefaultButtonPressed();
            }
        });
    }

    @Override
    public void setUserLevel(final UserLevel userLevel)
    {
        _userLevel = userLevel;
        updateCenterPanelFromUserLevel();
    }

    @Override
    public void setSinced(final boolean synced)
    {
        _syncStatePanel.setSynced(synced);
    }

    @Override
    public void setAltitudePid(final PIDData pid)
    {
        _altitudePidPanel.setPid(pid);
    }

    @Override
    public void setThrottleBump(final String altitudeBump)
    {
        _throttleBumpPanel.setText(altitudeBump);
    }

    @Override
    public void setThrottlePanic(final String throttlePanic)
    {
        _throttlePanicPanel.setText(throttlePanic);
    }

    @Override
    public void setMinThrottleAdjust(final String minThrottleAdjust)
    {
        _minThrottleAdjustPanel.setText(minThrottleAdjust);
    }

    @Override
    public void setMaxThrottleAdjust(final String maxThrottleAdjust)
    {
        _maxThrottleAdjustPanel.setText(maxThrottleAdjust);
    }

    @Override
    public void setSmoothFactor(final String smoothFactor)
    {
        _smoothFactorPanel.setText(smoothFactor);
    }

    @Override
    public void setZDampening(final PIDData zDampeningPid)
    {
        _zDampening.setPid(zDampeningPid);
    }

    private void updateCenterPanelFromUserLevel()
    {
        _centerPanel.removeAll();

        _centerPanel.add(_altitudePidPanel);
        if (_userLevel == UserLevel.Beginner)
        {
            _altitudePidPanel.setDVisible(false);
            _centerPanel.add(_minThrottleAdjustPanel);
            _centerPanel.add(_maxThrottleAdjustPanel);
        }
        else if (_userLevel == UserLevel.Intermediate)
        {
            _altitudePidPanel.setDVisible(false);
            _centerPanel.add(_throttleBumpPanel);
            _centerPanel.add(_minThrottleAdjustPanel);
            _centerPanel.add(_maxThrottleAdjustPanel);
        }
        else if (_userLevel == UserLevel.Advanced)
        {
            _altitudePidPanel.setDVisible(true);
            _centerPanel.add(_throttleBumpPanel);
            _centerPanel.add(_throttlePanicPanel);
            _centerPanel.add(_minThrottleAdjustPanel);
            _centerPanel.add(_maxThrottleAdjustPanel);
            _centerPanel.add(_smoothFactorPanel);
            _centerPanel.add(_zDampening);
        }

        _centerPanel.add(_resetDefaultButton);
        _centerPanel.add(_syncStatePanel);
    }
}
