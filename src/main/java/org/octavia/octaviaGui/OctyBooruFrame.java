package org.octavia.octaviaGui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * Singleton class that contains the main JFrame of the application
 */
public class OctyBooruFrame {

    //The Frame containing the main program
    private static JFrame frame;
    //The singleton instance of the class
    private static OctyBooruFrame oFrame;
    //default height of the application
    private static int height = 1080;
    //default width of the application
    private static int width = 1920;
    //Main panel containing the program
    private JPanel mainPanel;
    private JTabbedPane tabPane;

    /**
     * Constructor of the class.
     * Initializes the Frame of the application.
     */
    private OctyBooruFrame() {
        //create the title bar
        String[] title = {
                "Hello there",
                "Finding everything you need"
        };
        frame = new JFrame("OctyBooru - " + title[(int)(Math.random() * title.length)]);

        //set default values for the frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.setContentPane(mainPanel);
        frame.pack();
        frame.setSize(width, height);
        mainPanel.setSize(width, height);
        frame.setContentPane(mainPanel);
        frame.setVisible(true);
    }
/**
    public void setMainPanel(JPanel panel){
        frame.setContentPane(panel);
        frame.pack();
    }
    **/

    public void addTab(String title, JPanel panel){
        tabPane.addTab(title, panel);
        int index = tabPane.indexOfTab(title);
        JPanel pnlTab = new JPanel(new GridBagLayout());
        pnlTab.setOpaque(false);
        JLabel lblTitle = new JLabel(title);
        JButton btnClose = new JButton("x");

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;

        pnlTab.add(lblTitle, gbc);

        gbc.gridx++;
        gbc.weightx = 0;
        gbc.insets = new Insets(5,5,5,0);
        pnlTab.add(btnClose, gbc);

        tabPane.setTabComponentAt(index, pnlTab);

        MyCloseActionHandler myCloseActionHandler = new MyCloseActionHandler(title);

        btnClose.addActionListener(myCloseActionHandler);
    }

    //@TODO work this out to better integrate into the current program
    public class MyCloseActionHandler implements ActionListener {

        private String tabName;

        public MyCloseActionHandler(String tabName) {
            this.tabName = tabName;
        }

        public String getTabName() {
            return tabName;
        }

        public void actionPerformed(ActionEvent evt) {

            int index = tabPane.indexOfTab(getTabName());
            if (index >= 0) {

                tabPane.removeTabAt(index);
                // It would probably be worthwhile getting the source
                // casting it back to a JButton and removing
                // the action handler reference ;)

            }

        }

    }
    /**
     * Either initialize the instance of the main application frame or get the instance that is already in use.
     * @return The single instance of the class
     */
    public static OctyBooruFrame getInstance() {
        if (oFrame == null)
            oFrame = new OctyBooruFrame();
        return oFrame;
    }

    private void createUIComponents() {
    }




}
