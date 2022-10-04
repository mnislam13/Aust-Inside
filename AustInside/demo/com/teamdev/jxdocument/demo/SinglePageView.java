/*
 * Copyright (c) 2000-2014 TeamDev Ltd. All rights reserved.
 * TeamDev PROPRIETARY and CONFIDENTIAL.
 * Use is subject to license terms.
 */

package com.teamdev.jxdocument.demo;

import com.teamdev.jxdocument.Document;
import com.teamdev.jxdocument.SinglePagePresentation;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class SinglePageView extends JComponent {

    private Document document;

    private final JPanel pagePreview;
    private final ToolBar toolBar;

    public SinglePageView() {
        toolBar = new ToolBar();
        pagePreview = new JPanel(new BorderLayout());

        setLayout(new BorderLayout());
        add(pagePreview, BorderLayout.CENTER);
        add(toolBar.getComponent(), BorderLayout.NORTH);

        toolBar.updateControls();
    }

    private void openPDFFile(final File file) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                if (document != null) {
                    document.close();
                }
                document = new Document(file);
                final SinglePagePresentation documentPresentation = new SinglePagePresentation(document);
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        pagePreview.removeAll();
                        pagePreview.add(documentPresentation.getComponent(), BorderLayout.CENTER);
                        pagePreview.revalidate();
                        pagePreview.repaint();
                    }
                });
                Window windowAncestor = SwingUtilities.getWindowAncestor(SinglePageView.this);
                if (windowAncestor instanceof JFrame) {
                    ((JFrame) windowAncestor).setTitle("JxDocument Demo: " + file.getAbsolutePath());
                }
                toolBar.setPresentationModel(new SinglePageViewModel(documentPresentation));
                toolBar.updateControls();
            }
        });
    }

    private class ToolBar {
        private final JToolBar toolBar;
        private final JButton prevPageButton;
        private final JButton nextPageButton;
        private final JLabel pagesCountLabel;
        private final JTextField currentPageTextField;

        private SinglePageViewModel model;

        public ToolBar() {
            prevPageButton = createPrevPageButton();
            nextPageButton = createNextPageButton();
            pagesCountLabel = createPagesCountLabel();
            currentPageTextField = createCurrentPageTextField();

            toolBar = new JToolBar(JToolBar.HORIZONTAL);
            toolBar.setFloatable(false);
            toolBar.add(createOpenPDFButton());
            toolBar.add(Box.createHorizontalGlue());
            toolBar.add(prevPageButton);
            toolBar.add(nextPageButton);
            toolBar.add(Box.createHorizontalGlue());
            toolBar.add(createPageLabel());
            toolBar.add(currentPageTextField);
            toolBar.add(pagesCountLabel);
        }

        public void setPresentationModel(SinglePageViewModel model) {
            this.model = model;
        }

        public void updateControls() {
            if (model != null) {
                prevPageButton.setEnabled(model.canSelectPrevPage());
                nextPageButton.setEnabled(model.canSelectNextPage());
                pagesCountLabel.setText(" of " + model.getPagesCount());
                currentPageTextField.setText(String.valueOf(model.getSelectedPageIndex() + 1));
                currentPageTextField.setEnabled(true);
            } else {
                prevPageButton.setEnabled(false);
                nextPageButton.setEnabled(false);
                pagesCountLabel.setText(" of ");
                currentPageTextField.setText("");
                currentPageTextField.setEnabled(false);
            }
        }

        private JLabel createPagesCountLabel() {
            return new JLabel(" of ");
        }

        private JLabel createPageLabel() {
            return new JLabel("Page: ");
        }

        private JTextField createCurrentPageTextField() {
            final JTextField currentPageTextField = new JTextField("");
            currentPageTextField.setHorizontalAlignment(JTextField.RIGHT);
            Dimension currentPageTextFieldSize = new Dimension(50, 25);
            currentPageTextField.setPreferredSize(currentPageTextFieldSize);
            currentPageTextField.setMinimumSize(currentPageTextFieldSize);
            currentPageTextField.setMaximumSize(currentPageTextFieldSize);
            currentPageTextField.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String text = currentPageTextField.getText();
                    try {
                        Integer index = Integer.valueOf(text);
                        if (index > 0 && index <= model.getPagesCount()) {
                            model.setSelectedPageIndex(index - 1);
                            updateControls();
                        } else {
                            Toolkit.getDefaultToolkit().beep();
                            currentPageTextField.setText(Integer.toString(model.getSelectedPageIndex() + 1));
                        }
                    } catch (NumberFormatException e1) {
                        Toolkit.getDefaultToolkit().beep();
                        currentPageTextField.setText(Integer.toString(model.getSelectedPageIndex() + 1));
                    }
                }
            });
            return currentPageTextField;
        }

        private JButton createNextPageButton() {
            final JButton nextPageButton = new JButton("Next Page");
            nextPageButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    model.selectNextPage();
                    updateControls();
                }
            });
            return nextPageButton;
        }

        private JButton createPrevPageButton() {
            final JButton prevPageButton = new JButton("Previous Page");
            prevPageButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    model.selectPrevPage();
                    updateControls();
                }
            });
            return prevPageButton;
        }

        private JButton createOpenPDFButton() {
            JButton openPDFButton = new JButton("Open PDF File...");
            openPDFButton.setFocusPainted(false);
            openPDFButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setFileFilter(new FileFilter() {
                        @Override
                        public boolean accept(File f) {
                            return f.isDirectory() || (f.isFile() && f.getName().endsWith(".pdf"));
                        }

                        @Override
                        public String getDescription() {
                            return "PDF Documents (*.pdf)";
                        }
                    });
                    if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                        openPDFFile(fileChooser.getSelectedFile());
                    }
                }
            });
            return openPDFButton;
        }

        public JComponent getComponent() {
            return toolBar;
        }
    }
}
