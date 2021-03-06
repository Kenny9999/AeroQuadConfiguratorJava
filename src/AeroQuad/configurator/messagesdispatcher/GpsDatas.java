package AeroQuad.configurator.messagesdispatcher;

public class GpsDatas
{
    private final String _state;
    private final String _nbSats;
    private final String _speed;
    private final String _altitude;
    private final String _heading;
    private final String _latitude;
    private final String _longitude;
    private final String _distanceToWayPoint;
    private final String _angleToWatPoint;

    public GpsDatas(final String state,
                    final String nbSats,
                    final String speed,
                    final String altitude,
                    final String heading,
                    final String latitude,
                    final String longitude,
                    final String distanceToWayPoint,
                    final String angleToWayPoint)
    {
        _state = state;
        _nbSats = nbSats;
        _speed = speed;
        _altitude = altitude;
        _heading = heading;
        _latitude = latitude;
        _longitude = longitude;
        _distanceToWayPoint = distanceToWayPoint;
        _angleToWatPoint = angleToWayPoint;
    }

    public String getGpsState()
    {
        return _state;
    }

    public String getNbSats()
    {
        return _nbSats;
    }

    public String getSpeed()
    {
        return _speed;
    }

    public String getAltitude()
    {
        return _altitude;
    }

    public String getHeading()
    {
        return _heading;
    }

    public String getLatitude()
    {
        return _latitude;
    }

    public String getLongitude()
    {
        return _longitude;
    }

    public String getDistanceToWayPoint()
    {
        return _distanceToWayPoint;
    }

    public String getAngleToWayPoint()
    {
        return _angleToWatPoint;
    }
}
