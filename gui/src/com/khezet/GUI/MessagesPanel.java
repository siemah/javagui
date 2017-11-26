package com.khezet.GUI;

import javax.rmi.CORBA.Util;
import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;

/**
 * Created by acer on 24/11/2017.
 */
public class MessagesPanel extends JPanel {

    private JTree serverTree;
    private DefaultTreeCellRenderer treeCellRenderer;

    MessagesPanel() {
        this.treeCellRenderer = new DefaultTreeCellRenderer();
        //customize a celllrender
        this.treeCellRenderer.setOpenIcon(Utils.createIcon("/images/147021.gif.png"));
        this.treeCellRenderer.setClosedIcon(Utils.createIcon("/images/147347.gif.png"));
        this.treeCellRenderer.setLeafIcon(Utils.createIcon("/images/148418.gif.png"));
        this.serverTree = new JTree(this.createNode());
        //add a cellrenderer to tree
        this.serverTree.setCellRenderer(treeCellRenderer);
        //select only one branch of tree
        this.serverTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        //listen to a selection listener (onchange event)
        this.serverTree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) serverTree.getLastSelectedPathComponent();
                System.out.println(node.getUserObject());
            }
        });

        this.setLayout(new BorderLayout());
        this.add(new JScrollPane(serverTree), BorderLayout.CENTER);

    }

    private DefaultMutableTreeNode createNode() {
        DefaultMutableTreeNode treeNode = new DefaultMutableTreeNode("Servers");
        DefaultMutableTreeNode servers1 = new DefaultMutableTreeNode("Algeria");
        //servers1 branches
        DefaultMutableTreeNode branche1 = new DefaultMutableTreeNode(new ServerInfo(0, "Setif"));
        DefaultMutableTreeNode branche2 = new DefaultMutableTreeNode(new ServerInfo(1, "Alger"));
        //servers2 branches
        DefaultMutableTreeNode servers2 = new DefaultMutableTreeNode("UK");
        DefaultMutableTreeNode branche3 = new DefaultMutableTreeNode(new ServerInfo(3, "London"));
        DefaultMutableTreeNode branche4 = new DefaultMutableTreeNode(new ServerInfo(4, "Liverpool"));

        servers1.add(branche1);
        servers1.add(branche2);

        servers2.add(branche3);
        servers2.add(branche4);

        treeNode.add(servers1);
        treeNode.add(servers2);
        return treeNode;
    }

}

