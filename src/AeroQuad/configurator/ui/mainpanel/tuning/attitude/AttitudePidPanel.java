package AeroQuad.configurator.ui.mainpanel.tuning.attitude;

import AeroQuad.configurator.messagesdispatcher.PIDData;
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


public class AttitudePidPanel extends JPanel implements IAttitudePidPanel
{
    private final IAttitudePidPanelController _controller;

    private final PidPanel _accelRollPidPanel = new PidPanel("Accel Roll");
    private final PidPanel _accelPitchPidPanel = new PidPanel("Accel Pitch");
    private final SingleParamConfigPanel _accelCutOffPanel = new SingleParamConfigPanel("<HTML><CENTER>Accel<BR>Cut Off</CENTER></HTML>");
    private final JButton _resetDefaultButton = new JButton("<HTML><CENTER>Reset<BR>Default</CENTER></HTML>");
    private final SyncedStatePanel _syncStatePanel = new SyncedStatePanel();
    private UserLevel _userLevel = UserLevel.Beginner;
    private JPanel _centerPanel;


    public AttitudePidPanel(final IAttitudePidPanelController attitudePanelController)
    {
        _controller = attitudePanelController;
        _controller.setPanel(this);
        initPanel();
    }

    private void initPanel()
    {
        setLayout(new BorderLayout());
        setBorder(new LineBorder(Color.black,3));

        final JPanel mainPanel = new JPanel(new BorderLayout());
        final JLabel headerLabel = new JLabel("Attitude");
        headerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headerLabel.setPreferredSize(new Dimension(0, UiUtils.HEATHER_PREFERED_HEIGHT));
        mainPanel.add(headerLabel, BorderLayout.NORTH);
        headerLabel.setBorder(new LineBorder(Color.black, 1));

        _centerPanel = new JPanel(new GridLayout(1,6));


        mainPanel.add(_centerPanel, BorderLayout.CENTER);
        add(mainPanel, BorderLayout.WEST);

        _accelRollPidPanel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                _controller.userAccelRollPidChanged(_accelRollPidPanel.getPid());
            }
        });

        _accelPitchPidPanel.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(final ActionEvent e)
            {
                _controller.userAccelPitchPidChanged(_accelPitchPidPanel.getPid());
            }
        });
        _accelCutOffPanel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                _controller.userAccelCutOffChanged(_accelCutOffPanel.getText());
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
    public void setAccelRollPid(final PIDData pid)
    {
        _accelRollPidPanel.setPid(pid);
    }

    @Override
    public void setAccelPitchPid(final PIDData pid)
    {
        _accelPitchPidPanel.setPid(pid);
    }

    @Override
    public void setSinced(final boolean synced)
    {
        _syncStatePanel.setSynced(synced);
    }

    @Override
    public void setAccelCutOff(final String accelCutOff) {
        _accelCutOffPanel.setText(accelCutOff);
    }

    private void updateCenterPanelFromUserLevel()
    {
        _centerPanel.removeAll();
        _centerPanel.add(_accelRollPidPanel);

        if (_userLevel == UserLevel.Beginner)
        {
            _accelRollPidPanel.setHeader("Accel");
            _accelRollPidPanel.setIVisible(false);
            _accelRollPidPanel.setDVisible(false);
        }
        else if (_userLevel == UserLevel.Intermediate)
        {
            _accelRollPidPanel.setHeader("Accel Roll");
            _accelRollPidPanel.setIVisible(false);
            _accelRollPidPanel.setDVisible(false);

            _centerPanel.add(_accelPitchPidPanel);

            _accelPitchPidPanel.setIVisible(false);
            _accelPitchPidPanel.setDVisible(false);
        }
        else if (_userLevel == UserLevel.Advanced)
        {
            _centerPanel.add(_accelPitchPidPanel);

            _accelRollPidPanel.setHeader("Accel Roll");
            _accelRollPidPanel.setIVisible(false);
            _accelRollPidPanel.setDVisible(false);

            _accelPitchPidPanel.setIVisible(false);
            _accelPitchPidPanel.setDVisible(false);
            _centerPanel.add(_accelCutOffPanel);
        }

        _centerPanel.add(_resetDefaultButton);
        _centerPanel.add(_syncStatePanel);
    }
}
