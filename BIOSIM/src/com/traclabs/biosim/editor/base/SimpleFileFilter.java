/*
 * Copyright © 2004 S&K Technologies, Inc, 56 Old Hwy 93, St Ignatius, MT 98865
 * All rights reserved.
 * U.S. Government Rights - Commercial software.  Government users are subject
 * to S&K Technologies, Inc, standard license agreement and applicable 
 * provisions of the FAR and its supplements.
 * Use is subject to license terms.
 */
/*
 * SimpleFileFilter.java
 *
 * Created on February 7, 2003, 3:30 PM
 */

package com.traclabs.biosim.editor.base;

import javax.swing.filechooser.FileFilter;

/**
 *  
 */
public class SimpleFileFilter extends FileFilter {
    String[] extensions;

    String description;

    /** Creates a new instance of SimpleFileFilter */
    public SimpleFileFilter(String ext) {
        this(new String[] { ext }, null);
    }

    public SimpleFileFilter(String[] exts, String descr) {
        // clone and lowercase the extensions.
        extensions = new String[exts.length];
        for (int i = exts.length - 1; i >= 0; i--) {
            extensions[i] = exts[i].toLowerCase();
        }
        // make sure we have a valid (if simplistic) description
        description = (descr == null ? exts[0] + " files" : descr);
    }

    public boolean accept(java.io.File f) {
        // we allow all directories, regardless of their extension
        if (f.isDirectory()) {
            return true;
        }

        // ok, it's a regular file so check the extension
        String name = f.getName().toLowerCase();
        for (int i = extensions.length - 1; i >= 0; i--) {
            if (name.endsWith(extensions[i])) {
                return true;
            }
        }

        return false;
    }

    public String getDescription() {
        return description;
    }

    public String[] getExtensions() {
        return (String[]) extensions.clone();
    }
}