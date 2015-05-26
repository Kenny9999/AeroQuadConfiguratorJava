package AeroQuad.configurator.ui.mainpanel.setup.vehiclesetup;

public interface IVehicleSetupController
{
    void setPanel(IVehicleSetupPanel panel);

    void pwmReceiverSelected();
    void ppmReceiverSelected();
    void sbusReceiverSelected();

    void triConfigSelected();
    void quadXConfigSelected();
    void quadPlusConfigSelected();
    void quadY4ConfigSelected();
    void hexY6ConfigSelected();
    void hexXConfigSelected();
    void hexPlusConfigSelected();
    void octoX8ConfigSelected();
    void octoXConfigSelected();
    void octoPlusConfigSelected();

    void reverseYawSelected(boolean reversed);

    void batterieMonitorSelected(boolean selected);

    void useGpsSelected(boolean selected);

    void setLoopTime(String escUpdateSpeed);
}
