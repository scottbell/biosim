package com.traclabs.biosim.editor;

import java.util.Locale;

import javax.swing.ImageIcon;

import org.tigris.gef.util.Localizer;
import org.tigris.gef.util.ResourceLoader;

import com.traclabs.biosim.editor.presentation.EditorFrame;
import com.traclabs.biosim.util.OrbUtils;

public class BiosimEditorMain {
    
    protected BiosimEditorMain() {
        Localizer.addResource("GefBase",
                "org.tigris.gef.base.BaseResourceBundle");
        Localizer.addResource("GefPres",
                "org.tigris.gef.presentation.PresentationResourceBundle");
        Localizer.addLocale(Locale.getDefault());
        Localizer.switchCurrentLocale(Locale.getDefault());
        ResourceLoader.addResourceExtension("gif");
        ResourceLoader.addResourceExtension("png");
        ResourceLoader.addResourceLocation("/org/tigris/gef/Images");

        ImageIcon biosimIcon = new ImageIcon(BiosimEditorMain.class.getClassLoader()
                        .getResource(
                                "com/traclabs/biosim/client/framework/biosim.png"));
        EditorFrame frame = new EditorFrame("Biosim Editor");
        frame.setIconImage(biosimIcon.getImage());
        frame.setSize(830, 600);
        frame.setVisible(true);
    }
    
    public static void main(String args[]) {
        OrbUtils.initializeLog();
    	@SuppressWarnings("unused") BiosimEditorMain theBiosimEditorMain = new BiosimEditorMain();
    }
}