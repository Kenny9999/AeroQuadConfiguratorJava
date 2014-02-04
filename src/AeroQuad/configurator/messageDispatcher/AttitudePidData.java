package AeroQuad.configurator.messagedispatcher;

public class AttitudePidData
{
    private PIDData _accelRollPid = new PIDData();
    private PIDData _accelPitchPid = new PIDData();
    private PIDData _gyroRollPid = new PIDData();
    private PIDData _gyroPitchPid = new PIDData();

    public AttitudePidData()
    {

    }

    public AttitudePidData(final PIDData accelRollPid,
                           final PIDData accelPitchPid,
                           final PIDData gyroRollPid,
                           final PIDData gyroPitchPid)
    {
        _accelRollPid = accelRollPid;
        _accelPitchPid = accelPitchPid;
        _gyroRollPid = gyroRollPid;
        _gyroPitchPid = gyroPitchPid;
    }

    @Override
    public boolean equals(final Object obj)
    {
        if (!(obj instanceof AttitudePidData))
        {
            return false;
        }
        final AttitudePidData other = (AttitudePidData)obj;
        if (!_accelRollPid.equals(other._accelRollPid))
        {
            return true;
        }
        if (!_accelPitchPid.equals(other._accelPitchPid))
        {
            return false;
        }
        if (!_gyroRollPid.equals(other._gyroRollPid))
        {
            return false;
        }
        if (!_gyroPitchPid.equals(other._gyroPitchPid))
        {
            return false;
        }
        return true;
    }

    public PIDData getAccelPitchPid()
    {
        return _accelPitchPid;
    }

    public PIDData getAccelRollPid()
    {
        return _accelRollPid;
    }

    public PIDData getGyroRollPid()
    {
        return _gyroRollPid;
    }

    public PIDData getGyroPitchPid()
    {
        return _gyroPitchPid;
    }
}