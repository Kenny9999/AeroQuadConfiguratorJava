package AeroQuad.configurator.messagesdispatcher;


import AeroQuad.configurator.ui.uiutils.UiUtils;

public class AltitudeHoldPidData
{
    private PIDData _altitudeHoldPid = new PIDData();
    private String _throttleBump = "0";
    private String _throttlePanic = "0";
    private String _smoothFactor = "0";
    private PIDData _zDampeningPid = new PIDData();

    public AltitudeHoldPidData()
    {

    }

    public AltitudeHoldPidData(final PIDData altitudeHoldPid,
                               final String throttleBump,
                               final String throttlePanic,
                               final String smoothFactor,
                               final PIDData zDampeningPid)
    {
        _altitudeHoldPid = altitudeHoldPid;
        _throttleBump = throttleBump;
        _throttlePanic = throttlePanic;
        _smoothFactor = smoothFactor;
        _zDampeningPid = zDampeningPid;
    }

    public PIDData getAltitudePid()
    {
        return _altitudeHoldPid;
    }

    public String getThrottleBump()
    {
        return _throttleBump;
    }

    public String getThrottlePanic()
    {
        return _throttlePanic;
    }

    public String getSmoothFactor()
    {
        return _smoothFactor;
    }

    public PIDData getZDampeningPid()
    {
        return _zDampeningPid;
    }

    @Override
    public boolean equals(final Object obj)
    {
        if (!(obj instanceof AltitudeHoldPidData))
        {
            return false;
        }
        final AltitudeHoldPidData other = (AltitudeHoldPidData)obj;
        if (!_altitudeHoldPid.equals(other._altitudeHoldPid))
        {
            return false;
        }
        if (!UiUtils.areFloatEquals(_throttleBump, other._throttleBump))
        {
            return false;
        }
        if (!UiUtils.areFloatEquals(_throttlePanic, other._throttlePanic))
        {
            return false;
        }
        if (!UiUtils.areFloatEquals(_smoothFactor, other._smoothFactor))
        {
            return false;
        }
        if (!_zDampeningPid.equals(other._zDampeningPid))
        {
            return false;
        }

        return true;
    }

    public void setAltitudeHoldPid(final PIDData altitudeHoldPid)
    {
        _altitudeHoldPid = altitudeHoldPid;
    }

    public void setThrottleBump(final String throttleBump)
    {
        _throttleBump = throttleBump;
    }

    public void setThrottlePanic(final String throttlePanic)
    {
        _throttlePanic = throttlePanic;
    }

    public void setSmoothFactor(final String smoothFactor)
    {
        _smoothFactor = smoothFactor;
    }

    public void setZDampeningPid(final PIDData zDampeningPid)
    {
        _zDampeningPid = zDampeningPid;
    }

    public AltitudeHoldPidData getCopy()
    {
        return new AltitudeHoldPidData(_altitudeHoldPid.getCopy(),
                                       _throttleBump,
                                       _throttlePanic,
                                       _smoothFactor,
                                       _zDampeningPid.getCopy());
    }
}
