package com.traclabs.biosim.editor.graph;

import javax.swing.JPanel;



public abstract class ActiveNode extends ModuleNode {
    private JPanel myConsumerAndProducerEditorPanel;
    
    public ActiveNode(){
        myConsumerAndProducerEditorPanel = createConsumerAndProducerEditorPanel();
    }

    /**
     * @return
     */
    private JPanel createConsumerAndProducerEditorPanel() {
        JPanel newConsumerAndProducerEditorPanel = new JPanel();
        return newConsumerAndProducerEditorPanel;
    }

    protected JPanel getConsumerAndProducerEditorPanel(){
        return myConsumerAndProducerEditorPanel;
    }
    
}
