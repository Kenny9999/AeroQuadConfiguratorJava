package AeroQuad.configurator.ui.mainpanel.setup.vehiclesetup;

import AeroQuad.configurator.communication.ISerialCommunicator;
import AeroQuad.configurator.messagesdispatcher.FlightConfigType;
import AeroQuad.configurator.messagesdispatcher.IMessageDispatcher;
import AeroQuad.configurator.messagesdispatcher.ReceiverType;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class VehicleSetupController implements IVehicleSetupController
{
    private final ISerialCommunicator _communicator;
    private final IMessageDispatcher _messageDispatcher;
    private IVehicleSetupPanel _panel;
    private int _nbChannels = 0;

    private ReceiverType _receiverType = ReceiverType.PWM;
    private FlightConfigType _flightConfigType = FlightConfigType.QUAD_X;
    private int _reversedYawDirection = 1;
    private int _useBatteryMonitor = 0;
    private int _useGps = 0;
    private String _loopTime = "2500";


    public VehicleSetupController(final IMessageDispatcher messageDispatcher, final ISerialCommunicator communicator)
    {
        _communicator = communicator;
        _messageDispatcher = messageDispatcher;

        messageDispatcher.addListener(IMessageDispatcher.FLIGHT_CONFIG_PROPERTY_KEY, new PropertyChangeListener()
        {
            public void propertyChange(final PropertyChangeEvent evt)
            {
                _flightConfigType = FlightConfigType.fromOrdinal(Integer.parseInt((String)evt.getNewValue()));
                _panel.selectFlightConfigType(_flightConfigType);
            }
        });

        messageDispatcher.addListener(IMessageDispatcher.RECEIVER_TYPE_KEY, new PropertyChangeListener()
        {
            @Override
            public void propertyChange(final PropertyChangeEvent evt)
            {
                _receiverType = ReceiverType.fromOrdinal(Integer.parseInt((String)evt.getNewValue()));
                _panel.setReceiverType(_receiverType);
            }
        });

        messageDispatcher.addListener(IMessageDispatcher.YAW_DIRECTION_KET, new PropertyChangeListener()
        {
            @Override
            public void propertyChange(final PropertyChangeEvent evt)
            {
                final int reversed = Integer.parseInt((String)evt.getNewValue());
                _reversedYawDirection = reversed;
                _panel.setYawIsReversed(reversed == -1);
            }
        });

        messageDispatcher.addListener(IMessageDispatcher.NB_RECEIVER_CHANNEL_PROPERTY_KEY, new PropertyChangeListener()
        {
            @Override
            public void propertyChange(final PropertyChangeEvent evt)
            {
                _nbChannels = (int)evt.getNewValue();
                updateOptionVisibilityFromChannelCount(_nbChannels);
            }
        });

        messageDispatcher.addListener(IMessageDispatcher.BATTERY_MONITOR_PROPERTY_KEY, new PropertyChangeListener()
        {
            @Override
            public void propertyChange(final PropertyChangeEvent evt)
            {
                final boolean batterieMonitorEnabled = (boolean)evt.getNewValue();
                _useBatteryMonitor = batterieMonitorEnabled ? 1 : 0;
                _panel.setBatterieMonitorSelected(batterieMonitorEnabled);
            }
        });

        messageDispatcher.addListener(IMessageDispatcher.GPS_PROPERTY_KEY, new PropertyChangeListener()
        {
            @Override
            public void propertyChange(final PropertyChangeEvent evt)
            {
                final boolean useGps = (boolean)evt.getNewValue();
                _useGps = useGps ? 1 : 0;
                _panel.setUseGps(useGps);
            }
        });

        messageDispatcher.addListener(IMessageDispatcher.LOOP_TIME_KEY, new PropertyChangeListener()
        {
            @Override
            public void propertyChange(final PropertyChangeEvent evt)
            {
                _loopTime = (String)evt.getNewValue();
                _panel.setLoopTime(_loopTime);
            }
        });

    }

    @Override
    public void setPanel(final IVehicleSetupPanel panel)
    {
        _panel = panel;
    }

    @Override
    public void pwmReceiverSelected()
    {
        _receiverType = ReceiverType.PWM;
        if (_nbChannels == 5)
        {
            _panel.selectQuadX();
        }
        sendUpdateToFlightController();
    }

    @Override
    public void ppmReceiverSelected()
    {
        _receiverType = ReceiverType.PPM;
        sendUpdateToFlightController();
    }


    @Override
    public void sbusReceiverSelected()
    {
        _receiverType = ReceiverType.SBUS;
        sendUpdateToFlightController();
    }

    @Override
    public void triConfigSelected()
    {
        _flightConfigType = FlightConfigType.TRI;
        sendUpdateToFlightController();
    }

    @Override
    public void quadXConfigSelected()
    {
        _flightConfigType = FlightConfigType.QUAD_X;
        sendUpdateToFlightController();
    }

    @Override
    public void quadPlusConfigSelected()
    {
        _flightConfigType = FlightConfigType.QUAD_PLUS;
        sendUpdateToFlightController();
    }

    @Override
    public void quadY4ConfigSelected()
    {
        _flightConfigType = FlightConfigType.QUAD_Y4;
        sendUpdateToFlightController();
    }

    @Override
    public void hexY6ConfigSelected()
    {
        _flightConfigType = FlightConfigType.HEX_Y6;
        if (_nbChannels == 5)
        {
            _panel.selectPpmReceiver();
        }
        sendUpdateToFlightController();
    }

    @Override
    public void hexXConfigSelected()
    {
        _flightConfigType = FlightConfigType.HEX_X;
        if (_nbChannels == 5)
        {
            _panel.selectPpmReceiver();
        }
        sendUpdateToFlightController();
    }

    @Override
    public void hexPlusConfigSelected()
    {
        _flightConfigType = FlightConfigType.HEX_PLUS;
        if (_nbChannels == 5)
        {
            _panel.selectPpmReceiver();
        }
        sendUpdateToFlightController();
    }

    @Override
    public void octoX8ConfigSelected()
    {
        _flightConfigType = FlightConfigType.OCTO_X8;
        sendUpdateToFlightController();
    }

    @Override
    public void octoXConfigSelected()
    {
        _flightConfigType = FlightConfigType.OCTO_X;
        sendUpdateToFlightController();
    }

    @Override
    public void octoPlusConfigSelected()
    {
        _flightConfigType = FlightConfigType.OCTO_PLUS;
        sendUpdateToFlightController();
    }

    @Override
    public void reverseYawSelected(final boolean reversed)
    {
        _reversedYawDirection = reversed ? -1 : 1;
        sendUpdateToFlightController();
    }

    @Override
    public void batterieMonitorSelected(final boolean selected)
    {
        _useBatteryMonitor = selected ? 1 : 0;
        sendUpdateToFlightController();
    }

    @Override
    public void useGpsSelected(final boolean selected)
    {
        _useGps = selected ? 1 : 0;
        sendUpdateToFlightController();
    }

    @Override
    public void setLoopTime(final String loopTime)
    {
        _loopTime = loopTime;
        sendUpdateToFlightController();

    }

    private void updateOptionVisibilityFromChannelCount(final int nbChannels)
    {
        _panel.setSbusVisible(nbChannels != 5);
        _panel.setOctoX8Visible(nbChannels != 5);
        _panel.setOctoXVisible(nbChannels != 5);
        _panel.setOctoPlusVisible(nbChannels != 5);
        _panel.setBatterieMonitorEnabled(nbChannels != 5);
    }


    private void sendUpdateToFlightController()
    {
        final StringBuffer buffer = new StringBuffer();
        buffer.append("J " + _receiverType.ordinal() + ";");
        buffer.append(_flightConfigType.ordinal() + ";");
        buffer.append(Integer.toString(_reversedYawDirection) + ";");
        buffer.append(Integer.toString(_useBatteryMonitor) + ";");
        buffer.append(Integer.toString(_useGps) + ";");
        buffer.append(_loopTime + ";");

        _communicator.sendCommand(buffer.toString());
    }

}
