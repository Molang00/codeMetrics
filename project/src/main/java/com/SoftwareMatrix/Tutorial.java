package com.SoftwareMatrix;

import com.intellij.ui.treeStructure.Tree;
import org.apache.commons.io.IOUtils;
import org.w3c.dom.Document;
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
        addNode("Project Description", root);
        DefaultMutableTreeNode metrics = addNode("Metrics", root);
        addNode("Maintainability_Index", metrics);
        addNode("Halstead_Metrics", metrics);
        addNode("Cyclomatic_Complexity", metrics);
        DefaultMutableTreeNode OOM = addNode("Object-Oriented Metrics", metrics);
        addNode("Object-Oriented_Metrics", OOM);
        DefaultMutableTreeNode functions = addNode("Functions", root);
        addNode("Calculations", functions);
        addNode("Exports", functions);

        tree = new Tree(root);


        DefaultTreeCellRenderer cr = new DefaultTreeCellRenderer();
        cr.setOpenIcon(folderIcon);
        cr.setClosedIcon(folderIcon);
        cr.setLeafIcon(leafIcon);

        tree.setCellRenderer(cr);

        InputStream is = Tutorial.class.getResourceAsStream("string.xml");
        System.out.println(is);
        assert is != null;

        InputStream iss = this.getClass().getResourceAsStream("string.xml");
        System.out.println(iss);
        assert iss != null;

//        try {
//            String result = IOUtils.toString(is, StandardCharsets.UTF_8);
//            System.out.println(result);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        try {
//            String result = IOUtils.toString(iss, StandardCharsets.UTF_8);
//            System.out.println(result);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


//        System.out.println(Tutorial.class.getResource("string.xml").getPath());

//        this.getClass()

//        System.out.println(Tutorial.class);
//        System.out.println(Tutorial.class.getResource("string.xml"));
//        System.out.println(Tutorial.class.getResourceAsStream("string.xml"));
////        System.out.println(Tutorial.class.getResource("main/resources/string.xml").getFile());
//        System.out.println(Thread.currentThread().getContextClassLoader().getResourceAsStream("string.xml"));
//
//        System.out.println(getClass().getClassLoader());
//        System.out.println(getClass().getClassLoader().getResource("string.xml"));
//        System.out.println(getClass().getClassLoader().getResource("string.xml").getFile());

        tree.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    JTree tt = (JTree) (e.getSource());
                    DefaultMutableTreeNode tn = (DefaultMutableTreeNode) tt.getLastSelectedPathComponent();
                    if (tn.isLeaf()) {
                        System.out.println(tn.getUserObject());
                        String url = "";
                        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                        DocumentBuilder builder = null;
                        Document document = null;
                        try {
                            builder = factory.newDocumentBuilder();
                            InputStream is = Tutorial.class.getResourceAsStream("string.xml");
                            document = builder.parse(new File(Tutorial.class.getResource("string.xml").getFile()));
                            if (document == null)
                                document = builder.parse(new File("main/resources/string.xml"));
                        } catch (ParserConfigurationException | SAXException | IOException ex) {
                            ex.printStackTrace();
                        }

                        assert document != null;

                        NodeList nodeList = document.getElementsByTagName("url");
                        Node node = nodeList.item(1);
                        url = node.getNodeValue();
//                        System.out.println(url);

                        String finalUrl = url + "#" + tn.getUserObject();
//                        System.out.println(finalUrl);
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
