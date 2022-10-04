/*
 * Copyright (c) 2000-2014 TeamDev Ltd. All rights reserved.
 * TeamDev PROPRIETARY and CONFIDENTIAL.
 * Use is subject to license terms.
 */

package com.teamdev.jxdocument.demo;

import javax.swing.*;
import java.awt.*;

/**
 * JxDocument Demo Application.
 */
public class JxDocumentDemo {
    public static void main(String[] args) {
        JFrame frame = new JFrame("JxDocument Demo");
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.getContentPane().add(new SinglePageView(), BorderLayout.CENTER);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
