package com.SoftwareMatrix;

import com.intellij.openapi.wm.ToolWindow;

import javax.swing.*;

public class MetricsResultWindow {
    /* Declare private fields here */
    private JPanel myToolWindowContent;
    private CCPage CCpage;
    private DefaultPage defaultpage;

    /**
     * Constructor of tool window
     */
    public MetricsResultWindow(ToolWindow toolWindow) {
        myToolWindowContent = new JPanel();
        defaultpage = new DefaultPage(this, myToolWindowContent);
        CCpage = new CCPage(this, myToolWindowContent);
        myToolWindowContent = defaultpage.getPage();
    }

    /**
     * Returns content of this tool window
     * 
     * @return whole content of tool window
     */
    public JPanel getContent() {
        return myToolWindowContent;
    }

    public void changeView(String label) {
        switch(label){
            case "CC":
                myToolWindowContent.removeAll();
                myToolWindowContent = CCpage.getPage();
                myToolWindowContent.updateUI();
                break;
            default:
                myToolWindowContent.removeAll();
                myToolWindowContent = defaultpage.getPage();
                myToolWindowContent.updateUI();
        }
    }
}