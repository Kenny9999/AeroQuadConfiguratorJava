package AeroQuad.configurator.ui.connectionpanel;

import AeroQuad.configurator.communication.ISerialCommunicator;
import AeroQuad.configurator.messagedispatcher.IMessageDispatcher;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

public class ConnectionPanelController implements IConnectionStatusPanelController
{
    private final ISerialCommunicator _communicator;
    private IConnectionStatusPanel _panel;

    public ConnectionPanelController(final ISerialCommunicator communicator, final IMessageDispatcher messageDispatcher)
    {
        _communicator = communicator;

        _communicator.addListener(ISerialCommunicator.CONNECTION_STATE_CHANGE,new PropertyChangeListener()
        {
            @Override
            public void propertyChange(final PropertyChangeEvent event)
            {
                final boolean connected = ((Boolean)event.getNewValue()).booleanValue();
                updateConnectionState(connected);
            }
        });

        messageDispatcher.addListener(IMessageDispatcher.COMMUNICATION_USAGE_VALUE_CAHNGED, new PropertyChangeListener()
        {
            @Override
            public void propertyChange(PropertyChangeEvent evt)
            {
                final double usage = (double)evt.getNewValue();
                _panel.setUsage(usage);
            }
        });

    }

    private void updateConnectionState(final boolean connected)
    {
        _panel.setConnected(connected);
    }


    public void setPanel(IConnectionStatusPanel panel)
    {
        _panel = panel;
//        _panel.setConnectionButtonEnabled(true);
//        _panel.setDeconnectionButtonEnabled(false);
    }
}
