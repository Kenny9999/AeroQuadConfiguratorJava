package AeroQuad.configurator.communication.messaging.messageanalyzer;

import AeroQuad.configurator.messagesdispatcher.AttitudePidData;
import AeroQuad.configurator.messagesdispatcher.IMessageDispatcher;
import AeroQuad.configurator.messagesdispatcher.PIDData;

public class AttitudePidMessageAnalyser implements IMessageAnalyser
{

    private final IMessageDispatcher _messageDispatcher;

    public AttitudePidMessageAnalyser(final IMessageDispatcher messageDispatcher)
    {
        _messageDispatcher = messageDispatcher;
    }

    @Override
    public boolean analyzeRawData(final String rawData)
    {
        try
        {
            final String splittedData[] = rawData.split(",");

            final PIDData accelRollPid = new PIDData(splittedData[0], splittedData[1], splittedData[2]);
            final PIDData accelPitchPid = new PIDData(splittedData[3], splittedData[4], splittedData[5]);
            final String accelCutOff =  splittedData[6];

            final AttitudePidData attitudePidData = new AttitudePidData(accelRollPid, accelPitchPid, accelCutOff);
            _messageDispatcher.dispatchMessage(IMessageDispatcher.ATTITUDE_PID_DATA_KEY, attitudePidData);

        }
        catch (final Exception e)
        {
            return false;
        }

        return true;
    }
}
