package AeroQuad.configurator.communication;

import AeroQuad.configurator.communication.messaging.messageanalyzer.IMessageAnalyser;
import AeroQuad.configurator.communication.messaging.request.IRequest;
import AeroQuad.configurator.communication.messaging.request.VehicleInfoRequest;
import AeroQuad.configurator.messageDispatcher.IMessageDispatcher;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

import javax.swing.SwingUtilities;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;


public class SerialCommunicator implements ISerialCommunicator
{
    @SuppressWarnings("unchecked")
    private CommPortIdentifier _portId = null;
    private String _connectedPortName;
    private SerialPort _connectedPort = null;

    private InputStream _imputStreamReader = null;
    private OutputStream _outputStream = null;
    private boolean _isConnected = false;
    private BufferedReader _bufferedReader = null;

    private final IMessageDispatcher _messageDispatcher;
    private IMessageAnalyser _messageAnalyser;
    private IMessageAnalyser _vehicleStateAnalyser;

    private final PropertyChangeSupport _propertyChangeSupport = new PropertyChangeSupport(this);

    public SerialCommunicator(final IMessageDispatcher messageDispatcher)
    {
        _messageDispatcher = messageDispatcher;
    }

    @Override
    public void addListener(final String propertyName, final PropertyChangeListener listener)
    {
        _propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
    }

    @Override
    public List<String> getComPortAvailable()
    {
        final ArrayList<String> portListName = new ArrayList<String>(1);
        final Enumeration portList = CommPortIdentifier.getPortIdentifiers();

        while (portList.hasMoreElements())
        {
            _portId = (CommPortIdentifier) portList.nextElement();
            portListName.add(_portId.getName().toString());
        }

        return portListName;      // Return the ports found on the system
    }

    @Override
    public void connect(final String commPort)
    {
        connect(ISerialCommunicator.DEFAULT_BOAD_RATE, commPort);
    }

    @Override
    public void connect(final int baudRate, final String defaultPort)
    {
        _connectedPortName = defaultPort;

        boolean portFound = false;
        if (_portId != null)
        {               // If _portId contains at least one element!!!
            if (_portId.getName().equals(_connectedPortName))
            {
                portFound = true;
                System.out.println("Port found on: " + _connectedPortName);
            }
        }

        if (portFound)
        {
            try
            {
                _connectedPort = (SerialPort) _portId.open("Aeroquad Serial Communicator", 2000);
                _imputStreamReader = _connectedPort.getInputStream();
                _outputStream = _connectedPort.getOutputStream();
                _bufferedReader = new BufferedReader(new InputStreamReader(_imputStreamReader), 5);
                _connectedPort.setSerialPortParams(baudRate, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
                _connectedPort.setFlowControlMode(SerialPort.FLOWCONTROL_NONE);
                _connectedPort.addEventListener(new SerialPortEventListener()
                {

                    @Override
                    public void serialEvent(SerialPortEvent serialPortEvent)
                    {
                        processSerialEvent(serialPortEvent);
                    }
                });
            }
            catch (final Exception e)
            {
                System.err.println("Can't connect to comm port because of " + e);
                _imputStreamReader = null;
            }
            // Advise if data available to be read on the port
            _connectedPort.notifyOnDataAvailable(true);

            System.out.println("Port: " + _connectedPortName + " opened");
            _isConnected = true;
            _propertyChangeSupport.firePropertyChange(CONNECTION_STATE_CHANGE, null, _isConnected);
            sendCommand("x");
            final VehicleInfoRequest request = new VehicleInfoRequest(_messageDispatcher);
            _vehicleStateAnalyser = request.getMessageAnalyser();
            sendRequest(request);
        }
    }

    @Override
    public void disconnect()
    {
        if (_isConnected)
        {
            try
            {
                if (_imputStreamReader != null)
                {
                    _imputStreamReader.close();
                    _imputStreamReader = null;
                }
                if (_bufferedReader != null)
                {
                    _bufferedReader.close();
                    _bufferedReader = null;
                }
            }
            catch (Exception e)
            {
                System.err.println("Close connection problem");
            }

            _connectedPort.close();

            System.out.println("Serial Port closed!!");
            _isConnected = false;
            _propertyChangeSupport.firePropertyChange(CONNECTION_STATE_CHANGE, null, _isConnected);
        }
    }


    @Override
    public void sendRequest(final IRequest request)
    {
        _messageAnalyser = request.getMessageAnalyser();
        sendCommand(request.getStringMessage());
    }

    @Override
    public void sendCommand(final String command)
    {
        try
        {
            _propertyChangeSupport.firePropertyChange(RAW_DATA_MESSAGE_SENT, null, command);
            _outputStream.write(command.getBytes());
            _outputStream.close();
        }
        catch (IOException e)
        {
            System.err.println("Send command error");
            disconnect();
        }
    }

    @Override
    public boolean isConnected()
    {
        return _isConnected;
    }


    private void processSerialEvent(final SerialPortEvent event)
    {

        switch (event.getEventType())
        {
            case SerialPortEvent.BI:
            case SerialPortEvent.CD:
            case SerialPortEvent.CTS:
            case SerialPortEvent.DSR:
            case SerialPortEvent.FE:
            case SerialPortEvent.OE:
            case SerialPortEvent.PE:
            case SerialPortEvent.RI:
            case SerialPortEvent.OUTPUT_BUFFER_EMPTY:
                break;

            case SerialPortEvent.DATA_AVAILABLE:
                SwingUtilities.invokeLater(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        analyzeIncomingData();
                    }
                });
                break;

            default:
                break;
        }
    }


    private void analyzeIncomingData()
    {
        String rawData = null;
        try
        {
            while ((rawData = _bufferedReader.readLine()) != null)
            {
                handleReceivedString(rawData);
            }
        }
        catch (IOException e)
        {
            //System.err.println("Decoding message error = " + rawData);
        }
    }

    private void handleReceivedString(final String rawData)
    {
//        System.out.println(rawData);
        _propertyChangeSupport.firePropertyChange(RAW_DATA_MESSAGE_RECEIVED, null, rawData);
        if (!_vehicleStateAnalyser.analyzeRawData(rawData))
        {
            _messageAnalyser.analyzeRawData(rawData);
        }
    }
}
