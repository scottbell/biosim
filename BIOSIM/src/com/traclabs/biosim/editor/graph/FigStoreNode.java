package com.traclabs.biosim.editor.graph;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.text.NumberFormat;
import java.util.StringTokenizer;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.tigris.gef.presentation.Fig;
import org.tigris.gef.presentation.FigCircle;

import com.traclabs.biosim.server.simulation.framework.StoreImpl;

public abstract class FigStoreNode extends ModuleFigLabelNode {
    private JFrame myEditFrame;

    public FigStoreNode() {
        super();
        setShowShadow(false);
    }

    protected Fig createBgFig() {
        return new FigCircle(0, 0, 75, 50);
    }

    public String getPrivateData() {
        return null; //"text=\"" + _label.getText() + "\"";
    }

    public void setPrivateData(String data) {
        StringTokenizer tokenizer = new StringTokenizer(data, "=\"' ");

        while (tokenizer.hasMoreTokens()) {
            String tok = tokenizer.nextToken();
            if (tok.equals("text")) {
                String s = tokenizer.nextToken();
                _label.setText(s);
            } else {
                /* Unknown value */
            }
        }
    }

    public Point connectionPoint(Point anotherPt) {
        return connectionPoint((FigCircle) _bgFig, anotherPt);
    }

    public Dimension getMinimumSize() {
        Dimension dim = _label.getSize();

        double x = dim.getWidth() / 2.0;
        double y = dim.getHeight() / 2.0;
        double c = x;
        double a = (Math.sqrt((x + c) * (x + c) + y * y) + Math.sqrt((x - c)
                * (x - c) + y * y)) / 2.0;
        double b = Math.sqrt(a * a - c * c);
        int w = Math.max((int) (2 * a) + 6, 75);
        int h = Math.max((int) (2 * b) + 6, 50);
        return new Dimension(w, h);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.traclabs.biosim.editor.graph.ModuleFigNode#getPropertyEditor()
     */
    protected JFrame getPropertyEditor() {
        if (myEditFrame != null)
            return myEditFrame;
        myEditFrame = new StorePropertiesFrame(this);
        myEditFrame.pack();
        return myEditFrame;
    }

    private class StorePropertiesFrame extends JFrame {
        private JTextField myNameField;

        private JFormattedTextField myCapacityField;

        private JFormattedTextField myLevelField;
        
        private JButton myOKButton;

        private StoreImpl myStoreImpl;
        
        private FigStoreNode myFigStoreNode;

        public StorePropertiesFrame(FigStoreNode pNode) {
            myFigStoreNode = pNode;
            ModuleNode owner = (ModuleNode) pNode.getOwner();
            myStoreImpl = (StoreImpl) owner.getSimBioModuleImpl();
            myNameField = new JTextField(myStoreImpl.getModuleName());
            myCapacityField = new JFormattedTextField(NumberFormat.getNumberInstance());
            myCapacityField.setValue(new Float(myStoreImpl.getInitialCapacity()));
            myLevelField = new JFormattedTextField(NumberFormat.getNumberInstance());
            myLevelField.setValue(new Float(myStoreImpl.getInitialLevel()));
            myOKButton = new JButton(new OKAction());

            setLayout(new GridLayout(4, 3));
            add(new JLabel("Name"));
            add(myNameField);
            add(new JLabel("Capacity"));
            add(myCapacityField);
            add(new JLabel("Level"));
            add(myLevelField);
            add(myOKButton);
            setTitle(getText() + " Properties");
        }
        
        /**
         * Action that auto-arranges internal menus
         */
        private class OKAction extends AbstractAction {
            public OKAction() {
                super("OK");
            }

            public void actionPerformed(ActionEvent ae) {
                myStoreImpl.setModuleName(myNameField.getText());
                float capacity = ((Number)myCapacityField.getValue()).floatValue();
                float level = ((Number)myCapacityField.getValue()).floatValue();
                myStoreImpl.setInitialCapacity(capacity);
                myStoreImpl.setInitialLevel(level);
                myFigStoreNode.update();
                dispose();
            }
        }

    }
} /* end class FigGoToNode */
