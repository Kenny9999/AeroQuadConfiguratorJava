package AeroQuad.configurator.messagesdispatcher;

public class AccroPidData
{
    private PIDData _rollPidData = new PIDData();
    private PIDData _pichPidData = new PIDData();
    private String _stickScaling = "";
    private String _throttlePIDAdjustment = "";

    public AccroPidData()
    {

    }

    public AccroPidData(final PIDData rollPidData, final PIDData pichPidData, final String stickScaling, final String throttlePIDAdjustment)
    {
        _rollPidData = rollPidData;
        _pichPidData = pichPidData;
        _stickScaling = stickScaling;
        _throttlePIDAdjustment = throttlePIDAdjustment;
    }



    public PIDData getRollPid()
    {
        return _rollPidData;
    }

    public PIDData getPitchPid()
    {
        return _pichPidData;
    }

    public String getStickScaling()
    {
        return _stickScaling;
    }

    public void setRollPid(final PIDData rollPid)
    {
        _rollPidData = rollPid;
    }

    public AccroPidData getCopy()
    {
        return new AccroPidData(_rollPidData.getCopy(), _pichPidData.getCopy(), _stickScaling, _throttlePIDAdjustment);
    }

    public void setPitchPid(final PIDData pitchPid)
    {
        _pichPidData = pitchPid;
    }

    public void setStickScaling(final String stickScaling)
    {
        _stickScaling = stickScaling;
    }

    @Override
    public boolean equals(final Object obj)
    {
        if (!(obj instanceof AccroPidData))
        {
            return false;
        }
        final AccroPidData other = (AccroPidData)obj;
        if (!_rollPidData.equals(other._rollPidData))
        {
            return false;
        }
        if (!_pichPidData.equals(other._pichPidData))
        {
            return false;
        }
        if (!_stickScaling.equals(other._stickScaling))
        {
            return false;
        }
        if (!_throttlePIDAdjustment.equals(other._throttlePIDAdjustment))
        {
            return false;
        }
        return true;
    }

    public void setThrottlePIDAdjustment(final String text)
    {
        _throttlePIDAdjustment = text;
    }

    public String getThrottlePIDAdjustment()
    {
        return _throttlePIDAdjustment;
    }
}
