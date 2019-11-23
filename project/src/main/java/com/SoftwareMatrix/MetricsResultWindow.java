package com.SoftwareMatrix;

import com.intellij.openapi.wm.ToolWindow;

import javax.swing.*;

public class MetricsResultWindow {
    /* Declare private fields here */
    JPanel myToolWindowContent;
    CCPageFactory CCpageFactory;
    DefaultPageFactory defaultpageFactory;

    /**
     * Constructor of tool window
     */
    public MetricsResultWindow(ToolWindow toolWindow) {
        myToolWindowContent = new JPanel();
        defaultpageFactory = new DefaultPageFactory(this, myToolWindowContent);
        CCpageFactory = new CCPageFactory(this, myToolWindowContent);
        defaultpageFactory.createDefaultPage();
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
        if (label == "CC") {
            CCpageFactory.createCCPage();
        }
        if (label == "Default") {
            defaultpageFactory.createDefaultPage();
        }
    }
}