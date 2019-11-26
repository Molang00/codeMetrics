package com.SoftwareMatrix.PageFactory;

import javax.swing.JPanel;

public interface PageFactoryInterface{
    public JPanel createPage();
    public void generateTopView();
    public void generateCenterView();
    public void generateBottomView();
    public void generateButtons();
    public void generateTable();
}