/*
  mostly taken from https://docs.oracle.com/javase/tutorial/uiswing/examples/components/index.html
 */

package ui;

import model.Shoe;
import model.ShoeInventory;

import java.awt.GridLayout;
import java.awt.Toolkit;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.*;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;

public class InventoryTree extends JPanel {
    protected DefaultMutableTreeNode rootNode;
    protected DefaultTreeModel treeModel;
    protected JTree tree;
    private Toolkit toolkit = Toolkit.getDefaultToolkit();

    public InventoryTree() {
        super(new GridLayout(1, 0));

        rootNode = new DefaultMutableTreeNode("Inventory");
        treeModel = new DefaultTreeModel(rootNode);
        treeModel.addTreeModelListener(new MyTreeModelListener());
        tree = new JTree(treeModel);
        tree.setEditable(true);
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        tree.setShowsRootHandles(true);

        JScrollPane scrollPane = new JScrollPane(tree);
        add(scrollPane);
    }

    // REQUIRES: Select a shoe
    // MODIFIES: this, si
    // EFFECTS : removes the selected node from the tree and the inventory
    public void removeCurrentNode(ShoeInventory si) {
        TreePath currentSelection = tree.getSelectionPath();
        if (currentSelection != null) {
            DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode) (currentSelection.getLastPathComponent());
            MutableTreeNode parent = (MutableTreeNode) (currentNode.getParent());
            si.removeShoe(Integer.parseInt(currentNode.toString()));
            if (parent != null) {
                treeModel.removeNodeFromParent(currentNode);
                return;
            }
        }
        toolkit.beep();
    }

    // MODIFIES: this
    // EFFECTS : adds a node to the tree
    public DefaultMutableTreeNode addObject(Object child) {
        DefaultMutableTreeNode parentNode = null;
        TreePath parentPath = tree.getSelectionPath();

        if (parentPath == null) {
            parentNode = rootNode;
        } else {
            parentNode = (DefaultMutableTreeNode) (parentPath.getLastPathComponent());
        }

        return addObject(parentNode, child, true);
    }

    // MODIFIES: this
    // EFFECTS : adds a node to a parent node
    public DefaultMutableTreeNode addObject(DefaultMutableTreeNode parent, Object child) {
        return addObject(parent, child, false);
    }

    // MODIFIES: this
    // EFFECTS : adds a node to a parent node with the option of being visible or not
    public DefaultMutableTreeNode addObject(DefaultMutableTreeNode parent, Object child, boolean shouldBeVisible) {
        DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(child);

        if (parent == null) {
            parent = rootNode;
        }

        treeModel.insertNodeInto(childNode, parent, parent.getChildCount());

        if (shouldBeVisible) {
            tree.scrollPathToVisible(new TreePath(childNode.getPath()));
        }

        return childNode;
    }

    // internal class to make a tree model
    private static class MyTreeModelListener implements TreeModelListener {
        public void treeNodesChanged(TreeModelEvent e) {
            DefaultMutableTreeNode node;
            node = (DefaultMutableTreeNode)(e.getTreePath().getLastPathComponent());

            int index = e.getChildIndices()[0];
            node = (DefaultMutableTreeNode)(node.getChildAt(index));

            System.out.println("The user has finished editing the shoe");
            System.out.println("New value: " + node.getUserObject());
        }

        public void treeNodesInserted(TreeModelEvent e) {
        }

        public void treeNodesRemoved(TreeModelEvent e) {
        }

        public void treeStructureChanged(TreeModelEvent e) {
        }
    }

    // MODIFIES: this
    // EFFECTS : adds a shoe to the tree with its id as the main node
    public void parseShoe(Shoe s) {
        DefaultMutableTreeNode p = addObject(s.getId());
        addObject(p, s.getName());
        addObject(p, s.getSize());
        addObject(p, Double.toString(s.getShoeSize()));
        addObject(p, s.getBrand());
        addObject(p, s.getMainColor());
        addObject(p, s.getCondition());
    }
}
