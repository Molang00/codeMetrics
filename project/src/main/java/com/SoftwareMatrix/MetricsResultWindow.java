package com.SoftwareMatrix;

import com.SoftwareMatrix.PageFactory.DefaultPageFactory;
import com.SoftwareMatrix.PageFactory.MIPageFactory;
import com.SoftwareMatrix.PageFactory.OOPageFactory;
import com.intellij.openapi.wm.ToolWindow;

import javax.swing.*;

public class MetricsResultWindow {
    /* Declare private fields here */
    JPanel myToolWindowContent;
    MIPageFactory mipageFactory;
    OOPageFactory oopageFactory;
    DefaultPageFactory defaultpageFactory;
    String label;

    /**
     * Constructor of tool window
     */
    public MetricsResultWindow(ToolWindow toolWindow) {
        myToolWindowContent = new JPanel();
        defaultpageFactory = new DefaultPageFactory(this, myToolWindowContent);
        mipageFactory = new MIPageFactory(this, myToolWindowContent);
        oopageFactory = new OOPageFactory(this, myToolWindowContent);
        label = "Default";
        defaultpageFactory.createPage();
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
        this.label = label;
        if (label == "MI") {
            mipageFactory.createPage();
        }
        if (label == "OO") {
            oopageFactory.createPage();
        }
        if (label == "Default") {
            defaultpageFactory.createPage();
        }
        myToolWindowContent.revalidate();
    }
}