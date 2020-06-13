package org.octavia.octaviaGui.search;

import javax.swing.*;

public class SearchPageView {
    private JPanel view;
    private JPanel sidePanel;
    private JPanel listPanel;

    public SearchPageView(JPanel sidePanel, JPanel listPanel) {
        this.sidePanel = sidePanel;
        this.listPanel = listPanel;
    }

    public JPanel getView(){
        return view;
    }
    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
