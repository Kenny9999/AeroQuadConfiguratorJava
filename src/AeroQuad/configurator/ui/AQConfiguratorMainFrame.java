package AeroQuad.configurator.ui;

import AeroQuad.configurator.communication.ISerialCommunicator;
import AeroQuad.configurator.messagesdispatcher.IMessageDispatcher;
import AeroQuad.configurator.ui.connectionstatuspanel.ConnectionStatusPanelController;
import AeroQuad.configurator.ui.connectionstatuspanel.ConnectionStatusPanel;
import AeroQuad.configurator.ui.mainmenue.MainMenuController;
import AeroQuad.configurator.ui.mainmenue.MainMenuPanel;
import AeroQuad.configurator.ui.mainpanel.MainPanel;
import AeroQuad.configurator.ui.mainpanel.MainPanelController;
import AeroQuad.configurator.ui.uiutils.UiUtils;
import AeroQuad.configurator.ui.vehicleinfosfeedback.VehicleInfoFeedbackController;
import AeroQuad.configurator.ui.vehicleinfosfeedback.VehicleInfoFeedbackPanel;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.InsetsUIResource;
import java.awt.*;
import java.util.LinkedList;

public class AQConfiguratorMainFrame extends JFrame
{
    public AQConfiguratorMainFrame(final ISerialCommunicator communicator,
                                   final IMessageDispatcher messageDispatcher)
    {
        super("AeroQuad Configurator v4.0");
        initLookAndFeelUi();
        initUi(communicator, messageDispatcher);
    }


    private static void initLookAndFeelUi()
    {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Panel
        UIManager.put("Panel.background", UiUtils.DEFAULT_PANEL_BACKGROUND_COLOR);

        // Label
        UIManager.put("Label.foreground", Color.white);
        UIManager.put("Label.background", Color.lightGray);
        UIManager.put("Label.font", UiUtils.MEDIUM_FONT);

        // Text Field
        UIManager.put("TextField.font", UiUtils.SMALL_FONT);
        UIManager.put("TextField.border", new LineBorder(new ColorUIResource(50, 50, 50), 1));

        // Text Area
        UIManager.put("TextArea.font", UiUtils.MEDIUM_FONT);
        UIManager.put("TextArea.background", UiUtils.OFF_WHITE_COLOR);
        UIManager.put("TextArea.margin", new InsetsUIResource(8, 10, 10, 0));

        // Buttons
        LinkedList<Object> buttonGradient = new LinkedList<Object>();
        buttonGradient.add(0.3);
        buttonGradient.add(0.0);
        buttonGradient.add(new ColorUIResource(190, 190, 190));
        buttonGradient.add(new ColorUIResource(220, 220, 220));
        buttonGradient.add(new ColorUIResource(190, 190, 190));

        UIManager.put("Button.font", UiUtils.SMALL_FONT);
        UIManager.put("Button.gradient", buttonGradient);
        UIManager.put("Button.select", UiUtils.OFF_WHITE_COLOR);
        UIManager.put("Button.focus", new ColorUIResource(220, 220, 220));
        UIManager.put("Button.border", new LineBorder(UiUtils.BLACK_COLOR, 1));


        UIManager.put("ToggleButton.font", UiUtils.SMALL_FONT);
        UIManager.put("ToggleButton.gradient", buttonGradient);
        UIManager.put("ToggleButton.select", UiUtils.DARK_GREY_COLOR);
        UIManager.put("ToggleButton.focus", UiUtils.DARK_GREY_COLOR);
        UIManager.put("ToggleButton.border", new LineBorder(UiUtils.BLACK_COLOR, 1));

        // Separator
        UIManager.put("Separator.background", UiUtils.BLACK_COLOR);
        UIManager.put("Separator.foreground", UiUtils.DARK_GREY_COLOR);

        // ComboBox
        UIManager.put("ComboBox.font", UiUtils.NORMAL_FONT);


        // ScollPane
        UIManager.put("ScrollPane.border", new LineBorder(UiUtils.DARK_GREY_COLOR, 0));

        // TableHeader
        UIManager.put("TableHeader.font", UiUtils.SMALL_FONT);
        UIManager.put("TableHeader.background", UiUtils.OFF_WHITE_COLOR);
        UIManager.put("TableHeader.foreground", UiUtils.BLACK_COLOR);
        UIManager.put("TableHeader.cellBorder", new LineBorder(UiUtils.DARK_GREY_COLOR, 1));

        // Table
        UIManager.put("Table.background", UiUtils.BLACK_COLOR);
        UIManager.put("Table.foreground", Color.white);
        UIManager.put("Table.font", UiUtils.SMALL_FONT);
        UIManager.put("Table.gridColor", UiUtils.DARK_GREY_COLOR);

        UIManager.put("Panel.background", Color.gray);
    }

    private void initUi(final ISerialCommunicator communicator, final IMessageDispatcher messageDispatcher)
    {
        final JPanel mainContainer = new JPanel(new BorderLayout());

        final MainPanelController mainPanelController = new MainPanelController(messageDispatcher, communicator);
        final MainPanel mainPanel = new MainPanel(mainPanelController);
        mainContainer.add(mainPanel, BorderLayout.CENTER);

        final MainMenuPanel mainMenue = new MainMenuPanel(new MainMenuController(messageDispatcher, mainPanelController));
        mainContainer.add(mainMenue, BorderLayout.WEST);


        final JPanel southPanel = new JPanel(new GridLayout(2,1));
        final JPanel vehicleInfoFeedbackPanel = new VehicleInfoFeedbackPanel(new VehicleInfoFeedbackController(messageDispatcher));
        southPanel.add(vehicleInfoFeedbackPanel);

        final ConnectionStatusPanel connectionPanel = new ConnectionStatusPanel(new ConnectionStatusPanelController(communicator,messageDispatcher));
        southPanel.add(connectionPanel);
        mainContainer.add(southPanel, BorderLayout.SOUTH);

        getContentPane().add(mainContainer);
        pack();
        setVisible(true);

        final Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
    }
}
