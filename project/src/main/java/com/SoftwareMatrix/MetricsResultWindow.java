package com.SoftwareMatrix;

import com.SoftwareMatrix.PageFactory.DefaultPageFactory;
import com.SoftwareMatrix.PageFactory.HaslstedVolumPageFacotry;
import com.SoftwareMatrix.PageFactory.SLOCPageFactory;
import com.SoftwareMatrix.PageFactory.MIPageFactory;
import com.SoftwareMatrix.PageFactory.OOPageFactory;
import com.SoftwareMatrix.PageFactory.CCPageFactory;
import com.intellij.openapi.wm.ToolWindow;

import javax.swing.*;

public class MetricsResultWindow {
    /* Declare private fields here */
    JPanel myToolWindowContent;
    DefaultPageFactory defaultpageFactory;
    MIPageFactory mipageFactory;
    OOPageFactory oopageFactory;
    CCPageFactory ccpageFactory;
    HaslstedVolumPageFacotry haslstedVolumpageFactory;
    SLOCPageFactory SLOCpageFactory;
    String label;

    /**
     * Constructor of tool window
     */
    public MetricsResultWindow(ToolWindow toolWindow) {
        myToolWindowContent = new JPanel();
        defaultpageFactory = new DefaultPageFactory(this, myToolWindowContent);
        mipageFactory = new MIPageFactory(this, myToolWindowContent);
        oopageFactory = new OOPageFactory(this, myToolWindowContent);
        ccpageFactory = new CCPageFactory(this, myToolWindowContent);
        haslstedVolumpageFactory = new HaslstedVolumPageFacotry(this, myToolWindowContent);
        SLOCpageFactory = new SLOCPageFactory(this, myToolWindowContent);
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
        switch (label) {
        case "Default":
            defaultpageFactory.createPage();
            break;
        case "MI":
            mipageFactory.createPage();
            break;
        case "OO":
            oopageFactory.createPage();
            break;
        case "V":
            haslstedVolumpageFactory.createPage();
            break;
        case "G":
            ccpageFactory.createPage();
            break;
        case "SLOC":
            SLOCpageFactory.createPage();
            break;
        }
        myToolWindowContent.revalidate();
    }
}