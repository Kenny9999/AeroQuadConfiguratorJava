package AeroQuad.configurator.ui.mainpanel.setup.vehiclesetup;

import AeroQuad.configurator.messagesdispatcher.FlightConfigType;
import AeroQuad.configurator.messagesdispatcher.ReceiverType;
import AeroQuad.configurator.ui.uiutils.IntegerFilterKeyAdapter;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class VehicleSetupPanel extends JPanel implements IVehicleSetupPanel
{
    private final IVehicleSetupController _controller;

    private final JRadioButton _receiverPwmRadioButton = new JRadioButton("Normal (PWM)");
    private final JRadioButton _receiverPpmRadioButton = new JRadioButton("PPM");
    private final JRadioButton _receiverSbusRadioButton = new JRadioButton("SBUS");

    private final JRadioButton _quadXRadioButton = new JRadioButton("Quad X");
    private final JRadioButton _quadPlusRadioButton = new JRadioButton("Quad +");
    private final JRadioButton _triRadioButton = new JRadioButton("Tri");
    private final JRadioButton _hexY6RadioButton = new JRadioButton("Hex Y6");
    private final JRadioButton _hexXRadioButton = new JRadioButton("Hex X");
    private final JRadioButton _hexPlusRadioButton = new JRadioButton("Hex +");
    private final JRadioButton _quadY4RadioButton = new JRadioButton("Quad Y4");
    private final JRadioButton _octoX8RadioButton = new JRadioButton("Octo X8");
    private final JRadioButton _octoXRadioButton = new JRadioButton("Octo X");
    private final JRadioButton _octoPlusRadioButton = new JRadioButton("Octo +");

    private final JTextField _loopTimeTextField = new JTextField("2500");

    private final JCheckBox _reverseYawCheckBox = new JCheckBox("Reverse Yaw");
    private final JCheckBox _batteriMonitorCheckBox = new JCheckBox("Batterie monitor");
    private final JCheckBox _gpsCheckBox = new JCheckBox("Use GPS");


    public VehicleSetupPanel(final IVehicleSetupController controller)
    {
        _controller = controller;
        _controller.setPanel(this);

        init();
    }

    private void init()
    {
        setLayout(new BorderLayout());

        createHeaderPanel();

        createMiddlePanel();
    }


    private void createHeaderPanel()
    {
        final JLabel label = new JLabel("<HTML><CENTER>All change here will be automatically synced.<BR>Reboot of the board will be required for change to be applied </CENTER></HTML>");
        label.setBorder(new LineBorder(Color.black, 1));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        add(label, BorderLayout.NORTH);
    }

    private void createMiddlePanel()
    {
        final JPanel middlePanel = new JPanel();
        middlePanel.setLayout(new BoxLayout(middlePanel, BoxLayout.Y_AXIS));
        add(middlePanel, BorderLayout.CENTER);

        final JPanel receiverPanel = createReceiverPanel();
        middlePanel.add(receiverPanel);

        final JPanel motorConfigPanel = createMotorConfigPanel();
        middlePanel.add(motorConfigPanel);

        final JPanel escUpdateSpeedPanel = createLoopTimePanel();
        middlePanel.add(escUpdateSpeedPanel);

        final JPanel otherOptionPanel = createOtherOptionPanel();
        middlePanel.add(otherOptionPanel);

        bindActions();
    }

    private JPanel createReceiverPanel()
    {
        final JPanel receiverPanel = new JPanel(new BorderLayout());
        final TitledBorder receiverPanelBorder = new TitledBorder("Receiver type selection");
        receiverPanelBorder.setTitleColor(Color.WHITE);
        receiverPanel.setBorder(receiverPanelBorder);
        final JPanel receiverDetailPanel = new JPanel(new GridLayout(1,3));
        receiverDetailPanel.add(_receiverPwmRadioButton);
        receiverDetailPanel.add(_receiverPpmRadioButton);
        receiverDetailPanel.add(_receiverSbusRadioButton);
        final ButtonGroup group = new ButtonGroup();
        group.add(_receiverPwmRadioButton);
        group.add(_receiverPpmRadioButton);
        group.add(_receiverSbusRadioButton);
        receiverPanel.add(receiverDetailPanel, BorderLayout.CENTER);
        return receiverPanel;
    }

    private JPanel createMotorConfigPanel()
    {
        final JPanel motorConfigPanel = new JPanel(new BorderLayout());
        final TitledBorder motorConfigBorder = new TitledBorder("Choose motor config");
        motorConfigBorder.setTitleColor(Color.WHITE);
        motorConfigPanel.setBorder(motorConfigBorder);

        final JPanel motorConfigPanelDetail = new JPanel(new GridLayout(5,2));
        motorConfigPanelDetail.add(_triRadioButton);
        motorConfigPanelDetail.add(_quadXRadioButton);
        motorConfigPanelDetail.add(_quadPlusRadioButton);
        motorConfigPanelDetail.add(_quadY4RadioButton);
        motorConfigPanelDetail.add(_hexY6RadioButton);
        motorConfigPanelDetail.add(_hexXRadioButton);
        motorConfigPanelDetail.add(_hexPlusRadioButton);
        motorConfigPanelDetail.add(_octoX8RadioButton);
        motorConfigPanelDetail.add(_octoXRadioButton);
        motorConfigPanelDetail.add(_octoPlusRadioButton);

        final ButtonGroup group = new ButtonGroup();
        group.add(_triRadioButton);
        group.add(_quadXRadioButton);
        group.add(_quadPlusRadioButton);
        group.add(_quadY4RadioButton);
        group.add(_hexY6RadioButton);
        group.add(_hexXRadioButton);
        group.add(_hexPlusRadioButton);
        group.add(_octoX8RadioButton);
        group.add(_octoXRadioButton);
        group.add(_octoPlusRadioButton);
        motorConfigPanel.add(motorConfigPanelDetail, BorderLayout.CENTER);

        return motorConfigPanel;
    }

    private JPanel createOtherOptionPanel()
    {
        final JPanel otherOptions = new JPanel(new GridLayout(2,2));
        final TitledBorder otherOptionBorder = new TitledBorder("Others options");
        otherOptionBorder.setTitleColor(Color.WHITE);
        otherOptions.setBorder(otherOptionBorder);
        otherOptions.add(_reverseYawCheckBox);
        otherOptions.add(_batteriMonitorCheckBox);
        otherOptions.add(_gpsCheckBox);
        final JLabel tempLabel = new JLabel();
        tempLabel.setOpaque(true);
        tempLabel.setBackground(_reverseYawCheckBox.getBackground());
        otherOptions.add(tempLabel);

        return otherOptions;
    }

    private JPanel createLoopTimePanel()
    {
        final JPanel loopTimePanel = new JPanel(new GridLayout(1,5));
        final TitledBorder LoopTimeBorder = new TitledBorder("Loop time");
        LoopTimeBorder.setTitleColor(Color.WHITE);
        loopTimePanel.setBorder(LoopTimeBorder);

        _loopTimeTextField.addKeyListener(new IntegerFilterKeyAdapter());
        loopTimePanel.add(_loopTimeTextField);
        final JLabel tempLabel = new JLabel();
        tempLabel.setOpaque(true);
        loopTimePanel.add(tempLabel);

        return loopTimePanel;
    }

    private void bindActions()
    {
        _receiverPwmRadioButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(final ActionEvent e)
            {
                _controller.pwmReceiverSelected();
            }
        });
        _receiverPpmRadioButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(final ActionEvent e)
            {
                _controller.ppmReceiverSelected();
            }
        });
        _receiverSbusRadioButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(final ActionEvent e)
            {
                _controller.sbusReceiverSelected();
            }
        });

        _triRadioButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(final ActionEvent e)
            {
                _controller.triConfigSelected();
            }
        });
        _quadXRadioButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(final ActionEvent e)
            {
                _controller.quadXConfigSelected();
            }
        });
        _quadPlusRadioButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(final ActionEvent e)
            {
                _controller.quadPlusConfigSelected();
            }
        });
        _quadY4RadioButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(final ActionEvent e)
            {
                _controller.quadY4ConfigSelected();
            }
        });
        _hexY6RadioButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(final ActionEvent e)
            {
                _controller.hexY6ConfigSelected();
            }
        });
        _hexXRadioButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(final ActionEvent e)
            {
                _controller.hexXConfigSelected();
            }
        });
        _hexPlusRadioButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(final ActionEvent e)
            {
                _controller.hexPlusConfigSelected();
            }
        });
        _octoX8RadioButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(final ActionEvent e)
            {
                _controller.octoX8ConfigSelected();
            }
        });
        _octoXRadioButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(final ActionEvent e)
            {
                _controller.octoXConfigSelected();
            }
        });
        _octoPlusRadioButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(final ActionEvent e)
            {
                _controller.octoPlusConfigSelected();
            }
        });

        _loopTimeTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                _controller.setLoopTime(_loopTimeTextField.getText());
            }
        });

        _reverseYawCheckBox.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(final ActionEvent e)
            {
                _controller.reverseYawSelected(_reverseYawCheckBox.isSelected());
            }
        });

        _batteriMonitorCheckBox.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(final ActionEvent e)
            {
                _controller.batterieMonitorSelected(_batteriMonitorCheckBox.isSelected());
            }
        });

        _gpsCheckBox.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(final ActionEvent e)
            {
                _controller.useGpsSelected(_gpsCheckBox.isSelected());
            }
        });
    }

    @Override
    public void selectFlightConfigType(final FlightConfigType flightConfigType)
    {
        switch (flightConfigType)
        {
            case QUAD_X:
                _quadXRadioButton.setSelected(true);
                break;
            case QUAD_PLUS:
                _quadPlusRadioButton.setSelected(true);
                break;
            case HEX_PLUS:
                _hexPlusRadioButton.setSelected(true);
                break;
            case HEX_X:
                _hexXRadioButton.setSelected(true);
                break;
            case TRI:
                _triRadioButton.setSelected(true);
                break;
            case QUAD_Y4:
                _quadY4RadioButton.setSelected(true);
                break;
            case HEX_Y6:
                _hexY6RadioButton.setSelected(true);
                break;
            case OCTO_X8:
                _octoX8RadioButton.setSelected(true);
                break;
            case OCTO_PLUS:
                _octoPlusRadioButton.setSelected(true);
                break;
            case OCTO_X:
                _octoXRadioButton.setSelected(true);
                break;
        }
    }

    @Override
    public void setReceiverType(final ReceiverType receiverType)
    {
        switch (receiverType)
        {
            case PWM:
                _receiverPwmRadioButton.setSelected(true);
                break;
            case PPM:
                _receiverPpmRadioButton.setSelected(true);
                break;
            case SBUS:
                _receiverSbusRadioButton.setSelected(true);
                break;

        }
    }

    @Override
    public void setYawIsReversed(final boolean reversed)
    {
        _reverseYawCheckBox.setSelected(reversed);
    }

    @Override
    public void setSbusVisible(final boolean visible)
    {
        _receiverSbusRadioButton.setVisible(visible);
    }

    @Override
    public void setOctoX8Visible(final boolean visible)
    {
        _octoX8RadioButton.setVisible(visible);
    }

    @Override
    public void setOctoXVisible(final boolean visible)
    {
        _octoXRadioButton.setVisible(visible);
    }

    @Override
    public void setOctoPlusVisible(final boolean visible)
    {
        _octoPlusRadioButton.setVisible(visible);
    }

    @Override
    public void setBatterieMonitorEnabled(final boolean visible)
    {
        _batteriMonitorCheckBox.setVisible(visible);
    }

    @Override
    public void setBatterieMonitorSelected(final boolean selected)
    {
        _batteriMonitorCheckBox.setSelected(selected);
    }

    @Override
    public void setUseGps(final boolean useGps)
    {
        _gpsCheckBox.setSelected(useGps);
    }

    @Override
    public void setLoopTime(final String loopTime) {
        _loopTimeTextField.setText(loopTime);
    }


    @Override
    public void selectPpmReceiver()
    {
        _receiverPpmRadioButton.doClick();
    }

    @Override
    public void selectQuadX()
    {
        _quadXRadioButton.doClick();
    }

}
