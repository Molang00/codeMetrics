package com.SoftwareMatrix;

import com.intellij.ui.treeStructure.Tree;
import org.apache.commons.io.IOUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.swing.plaf.metal.MetalIconFactory;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;

/**
 * Class for showing tutorial of our project on window
 */
public class Tutorial {
    private JTree tree;
    private DefaultMutableTreeNode root;
    private String url;

    private static final Icon folderIcon = MetalIconFactory.getTreeFolderIcon();
    private static final Icon leafIcon = MetalIconFactory.getFileChooserDetailViewIcon();

    /**
     * Constructor of Tutorial class.
     */
    public Tutorial() {
        root = new DefaultMutableTreeNode("Tutorial");
    }

    /**
     * Add node to Tutorial tree
     * @param nodeName The name of new node
     * @param parent the node that new node will be attached
     * @return DefaultMutableTreeNode which is newly added
     */
    private DefaultMutableTreeNode addNode(String nodeName, DefaultMutableTreeNode parent){
        DefaultMutableTreeNode child = new DefaultMutableTreeNode(nodeName);
        parent.add(child);
        return child;
    }

    /**
     * Returns the Tutorial tree
     *
     * @return JTree of Tutorial
     */
    public JTree getResult() {
        addNode("Project_Description", root);
        DefaultMutableTreeNode metrics = addNode("Metrics", root);
        addNode("Maintainability_Index", metrics);
        addNode("Halstead_Metrics", metrics);
        addNode("Cyclomatic_Complexity", metrics);
        addNode("Object-Oriented_Metrics", metrics);

        tree = new Tree(root);


        DefaultTreeCellRenderer cr = new DefaultTreeCellRenderer();
        cr.setOpenIcon(folderIcon);
        cr.setClosedIcon(folderIcon);
        cr.setLeafIcon(leafIcon);

        tree.setCellRenderer(cr);

        InputStream is = this.getClass().getResourceAsStream("string.xml");
        assert is != null;

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(is);
            NodeList nl = doc.getElementsByTagName("Tutorial");
            Node node = nl.item(0);

            url = node.getTextContent();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        tree.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    JTree tt = (JTree) (e.getSource());
                    DefaultMutableTreeNode tn = (DefaultMutableTreeNode) tt.getLastSelectedPathComponent();
                    if (tn.isLeaf()) {
                        String finalUrl = url + "#" + tn.getUserObject();
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
        tree.setCellRenderer(new DefaultTreeCellRenderer(){
            @Override public Component getTreeCellRendererComponent(
                    JTree tree, Object value, boolean isSelected, boolean expanded,
                    boolean leaf, int row, boolean hasFocus) {
                JComponent c = (JComponent) super.getTreeCellRendererComponent(
                        tree, value, isSelected, expanded, leaf, row, hasFocus);
                c.setOpaque(false);
                return c;
            }
            private final Color ALPHA_OF_ZERO = new Color(0, true);
            @Override public Color getBackgroundNonSelectionColor() {
                return ALPHA_OF_ZERO;
            }
            private final Color backgroundSelectionColor = new Color(100, 100, 255, 100);
            @Override public Color getBackgroundSelectionColor() {
                return backgroundSelectionColor;
            }
        });

        return tree;
    }
}
