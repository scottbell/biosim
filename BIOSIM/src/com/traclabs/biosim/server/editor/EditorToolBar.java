package com.traclabs.biosim.server.editor;

import java.awt.Color;
import java.awt.Component;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Vector;

import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;

import org.tigris.gef.base.CmdCreateNode;
import org.tigris.gef.base.CmdSetMode;
import org.tigris.gef.base.Editor;
import org.tigris.gef.base.Globals;
import org.tigris.gef.util.ResourceLoader;

public class EditorToolBar extends JToolBar implements MouseListener {
    protected Vector _lockable = new Vector();

    protected Vector _modeButtons = new Vector();

    private static final Color selectedBack = new Color(153, 153, 153);

    private static final Color buttonBack = new Color(204, 204, 204);

    public EditorToolBar(String pName) {
        setFloatable(false);
        setName(pName);
    }

    /**
     * Add a new JButton which dispatches the action.
     * 
     * @param a
     *            the Action object to add as a new menu item
     */
    public JButton add(Action a) {
        String name = (String) a.getValue(Action.NAME);
        Icon icon = (Icon) a.getValue(Action.SMALL_ICON);
        return add(a, name, icon);
    }

    public JButton add(Action a, String name, String iconResourceStr) {
        Icon icon = ResourceLoader.lookupIconResource(iconResourceStr, name);
        return add(a, name, icon);
    }

    public JButton add(Action a, String name, Icon icon) {
        JButton b = super.add(a);
        b.setIcon(icon);
        b.setToolTipText(name + " ");
        if (a instanceof CmdSetMode || a instanceof CmdCreateNode)
            _modeButtons.addElement(b);
        if (a instanceof CmdSetMode || a instanceof CmdCreateNode)
            _lockable.addElement(b);
        b.addMouseListener(this);
        return b;
    }

    public Component add(Component comp) {
        if (comp instanceof JButton) {
            JButton button = (JButton) comp;
            Action action = button.getAction();
            if (action instanceof CmdSetMode || action instanceof CmdCreateNode)
                _modeButtons.addElement(button);
            if (action instanceof CmdSetMode || action instanceof CmdCreateNode)
                _lockable.addElement(button);
            button.addMouseListener(this);
        }
        return super.add(comp);
    }

    public JToggleButton addToggle(Action a) {
        String name = (String) a.getValue(Action.NAME);
        Icon icon = (Icon) a.getValue(Action.SMALL_ICON);
        return addToggle(a, name, icon);
    }

    public JToggleButton addToggle(Action a, String name, String iconResourceStr) {
        Icon icon = ResourceLoader.lookupIconResource(iconResourceStr, name);
        return addToggle(a, name, icon);
    }

    public JToggleButton addToggle(Action a, String name, Icon icon) {
        JToggleButton b = new JToggleButton(icon);
        b.setToolTipText(name + " ");
        b.setEnabled(a.isEnabled());
        b.addActionListener(a);
        add(b);
        PropertyChangeListener actionPropertyChangeListener = createActionToggleListener(b);
        a.addPropertyChangeListener(actionPropertyChangeListener);
        return b;
    }

    public JToggleButton addToggle(Action a, String name, String upRes,
            String downRes) {
        ImageIcon upIcon = ResourceLoader.lookupIconResource(upRes, name);
        ImageIcon downIcon = ResourceLoader.lookupIconResource(downRes, name);
        JToggleButton b = new JToggleButton(upIcon);
        b.setToolTipText(name + " ");
        b.setEnabled(a.isEnabled());
        b.addActionListener(a);
        b.setMargin(new Insets(0, 0, 0, 0));
        add(b);
        PropertyChangeListener actionPropertyChangeListener = createActionToggleListener(b);
        a.addPropertyChangeListener(actionPropertyChangeListener);
        return b;
    }

    public ButtonGroup addRadioGroup(String name1, ImageIcon oneUp,
            ImageIcon oneDown, String name2, ImageIcon twoUp, ImageIcon twoDown) {
        JRadioButton b1 = new JRadioButton(oneUp, true);
        b1.setSelectedIcon(oneDown);
        b1.setToolTipText(name1 + " ");
        b1.setMargin(new Insets(0, 0, 0, 0));
        b1.getAccessibleContext().setAccessibleName(name1);

        JRadioButton b2 = new JRadioButton(twoUp, false);
        b2.setSelectedIcon(twoDown);
        b2.setToolTipText(name2 + " ");
        b2.setMargin(new Insets(0, 0, 0, 0));
        b2.getAccessibleContext().setAccessibleName(name2);

        add(b1);
        add(b2);
        ButtonGroup bg = new ButtonGroup();
        bg.add(b1);
        bg.add(b2);
        return bg;
    }

    protected PropertyChangeListener createActionToggleListener(JToggleButton b) {
        return new ActionToggleChangedListener(b);
    }

    private class ActionToggleChangedListener implements PropertyChangeListener {
        JToggleButton button;

        ActionToggleChangedListener(JToggleButton b) {
            super();
            this.button = b;
        }

        public void propertyChange(PropertyChangeEvent e) {
            String propertyName = e.getPropertyName();
            if (e.getPropertyName().equals(Action.NAME)) {
                String text = (String) e.getNewValue();
                button.setText(text);
                button.repaint();
            } else if (propertyName.equals("enabled")) {
                Boolean enabledState = (Boolean) e.getNewValue();
                button.setEnabled(enabledState.booleanValue());
                button.repaint();
            } else if (e.getPropertyName().equals(Action.SMALL_ICON)) {
                Icon icon = (Icon) e.getNewValue();
                button.setIcon(icon);
                button.invalidate();
                button.repaint();
            }
        }
    }
    
    public void mouseEntered(MouseEvent me) {
    }

    public void mouseExited(MouseEvent me) {
    }

    public void mousePressed(MouseEvent me) {
    }

    public void mouseReleased(MouseEvent me) {
    }

    public void mouseClicked(MouseEvent me) {
        Object src = me.getSource();
        if (isModeButton(src)) {
            unpressAllButtonsExcept(src);
            Editor ce = Globals.curEditor();
            if (ce != null)
                ce.finishMode();
            Globals.setSticky(false);
        }
        if (me.getClickCount() >= 2) {
            if (!(src instanceof JButton))
                return;
            JButton b = (JButton) src;
            if (canLock(b)) {
                b.getModel().setPressed(true);
                b.getModel().setArmed(true);
                b.setBackground(selectedBack);
                Globals.setSticky(true);
            }
        } else if (me.getClickCount() == 1) {
            if (src instanceof JButton && isModeButton(src)) {
                JButton b = (JButton) src;
                b.setFocusPainted(false);
                b.getModel().setPressed(true);
                b.setBackground(selectedBack);
            }
        }
    }

    protected boolean canLock(Object b) {
        return _lockable.contains(b);
    }

    protected boolean isModeButton(Object b) {
        return _modeButtons.contains(b);
    }

    protected void unpressAllButtonsExcept(Object src) {
        int size = getComponentCount();
        for (int i = 0; i < size; i++) {
            Component c = getComponent(i);
            if (!(c instanceof JButton))
                continue;
            if (c == src)
                continue;
            ((JButton) c).getModel().setArmed(false);
            ((JButton) c).getModel().setPressed(false);
            ((JButton) c).setBackground(buttonBack);
        }
    }

    public void unpressAllButtons() {
        int size = getComponentCount();
        for (int i = 0; i < size; i++) {
            Component c = getComponent(i);
            if (!(c instanceof JButton))
                continue;
            ((JButton) c).getModel().setArmed(false);
            ((JButton) c).getModel().setPressed(false);
            ((JButton) c).setBackground(buttonBack);
        }
        // press the first button (usually ModeSelect)
        for (int i = 0; i < size; i++) {
            Component c = getComponent(i);
            if (!(c instanceof JButton))
                continue;
            JButton select = (JButton) c;
            select.getModel().setArmed(true);
            select.getModel().setPressed(true);
            select.setBackground(selectedBack);
            return;
        }
    }

} /* end class ToolBar */