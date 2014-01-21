package AeroQuad.configurator.ui.mainpanel.monitoring.vehiclestatus.otherssensorsstatuspanel;

import AeroQuad.configurator.messageDispatcher.FlightMode;

public interface IOtherSensorsStatusPanel
{
    void setBaroEnabled(boolean enabled);

    void setBatteryMonitorEnabled(boolean enabled);

    void setMotorArmedState(boolean armed);

    void setVehicleAltitude(Float value);

    void setAltitudeHoldState(boolean enabled);

    void setFlightMode(FlightMode flightMode);

    void setVoltage(Float value);
}
