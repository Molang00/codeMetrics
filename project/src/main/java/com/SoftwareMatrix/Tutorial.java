package com.SoftwareMatrix;

import com.intellij.ui.treeStructure.Tree;

import javax.swing.*;
import javax.swing.plaf.metal.MetalIconFactory;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class Tutorial {
    private JTree tree;
    private DefaultMutableTreeNode root;

    private static final Icon folderIcon = MetalIconFactory.getTreeFolderIcon();
    private static final Icon leafIcon = MetalIconFactory.getFileChooserDetailViewIcon();

    public Tutorial() {
        root = new DefaultMutableTreeNode("Tutorial");
    }

    private DefaultMutableTreeNode addNode(String nodeName, DefaultMutableTreeNode parent){
        DefaultMutableTreeNode child = new DefaultMutableTreeNode(nodeName);
        parent.add(child);
        return child;
    }

    public JTree getResult() {
        DefaultMutableTreeNode desc = addNode("Project Description", root);
        DefaultMutableTreeNode metrics = addNode("Metrics", root);
        addNode("Maintainability Index", metrics);
        addNode("Halstead Metrics", metrics);
        addNode("Cyclomatic Complexity", metrics);
        DefaultMutableTreeNode OOM = addNode("Object-Oriented Metrics", metrics);
        addNode("OOM1", OOM);
        DefaultMutableTreeNode functions = addNode("Functions", root);
        addNode("Calculations", functions);
        addNode("Export as file", functions);

        tree = new Tree(root);

        DefaultTreeCellRenderer cr = new DefaultTreeCellRenderer();
        cr.setOpenIcon(folderIcon);
        cr.setClosedIcon(folderIcon);
        cr.setLeafIcon(leafIcon);

        tree.setCellRenderer(cr);

        tree.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    JTree tt = (JTree) (e.getSource());
                    DefaultMutableTreeNode tn = (DefaultMutableTreeNode) tt.getLastSelectedPathComponent();

                    if (tn.isLeaf()) {
                        System.out.println(tn);
                        String url = "https://csed332.postech.ac.kr/team3/team_project/wikis/home";
                        switch(tn.toString()){
                            case "Project Description":
                                url = "https://csed332.postech.ac.kr/team3/team_project/wikis/Tutorial#description";
                                break;
                            case "Maintainability Index":
                                url = "https://csed332.postech.ac.kr/team3/team_project/wikis/Tutorial#Maintainability%20Index";
                                break;
                            case "Halstead Metrics":
                                url = "https://csed332.postech.ac.kr/team3/team_project/wikis/Tutorial#Halstead%20Metircs";
                                break;
                            case "Cyclomatic Complexity":
                                url = "https://csed332.postech.ac.kr/team3/team_project/wikis/Tutorial#Cyclomatic%20Complexity";
                                break;
                            case "OOM1":
                                url = "https://csed332.postech.ac.kr/team3/team_project/wikis/Tutorial#Object-Oriented%20Metrics";
                                break;
                            case "Calculations":
                                url = "https://csed332.postech.ac.kr/team3/team_project/wikis/Tutorial#Calculations";
                                break;
                            case "Export as file":
                                url = "https://csed332.postech.ac.kr/team3/team_project/wikis/Tutorial#Exports";
                                break;
                            default:
                                break;
                        }
                        String finalUrl = url;
                        if (Desktop.isDesktopSupported()) {
                            try {
                                Desktop desktop = Desktop.getDesktop();
                                desktop.browse(new URI(finalUrl));
                            } catch (IOException | URISyntaxException e1) {
                                JOptionPane.showMessageDialog(null,
                                        "An error happen " + e1.getMessage());
                            }
                        }
                    }
                    tree.clearSelection();
                }
            }
        });

        tree.setRowHeight(20);

        return tree;
    }
}
