package AeroQuad.configurator.ui.mainpanel.setup.vehiclesetup;

import AeroQuad.configurator.messagesdispatcher.FlightConfigType;
import AeroQuad.configurator.messagesdispatcher.ReceiverType;

public interface IVehicleSetupPanel
{
    void selectFlightConfigType(FlightConfigType flightConfigType);

    void setReceiverType(ReceiverType receiverType);

    void setYawIsReversed(boolean reversed);

    void setSbusVisible(boolean visible);

    void setOctoX8Visible(boolean visible);

    void setOctoXVisible(boolean visible);

    void setOctoPlusVisible(boolean visible);

    void selectPpmReceiver();

    void selectQuadX();

    void setBatterieMonitorEnabled(boolean visible);

    void setBatterieMonitorSelected(boolean selected);

    void setUseGps(boolean useGps);

    void setLoopTime(String escSpeed);
}
