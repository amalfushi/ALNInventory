package GUI;

import aln.Supply.Supply;
import aln.inventory.ALNInventory;
import aln.inventory.SpreadsheetWriter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class UserInterface extends JFrame /*implements ChangeListener /*ActionListener */{

    private JTabbedPane mediaPanel;
    private JTabbedPane inkPanel;
    private JTabbedPane otherPanel;
    private JTabbedPane superPanel;
    private LinkedHashMap<JSpinner, Supply> spinners;
    private LinkedHashMap<Supply, ArrayList<JLabel>> labels;
    private String spreadSheetPath;

    public UserInterface(ALNInventory inv, String spreadSheetPath) {

        super("ALN Inventory");
        
        this.spinners = new LinkedHashMap<>();
        this.labels = new LinkedHashMap<>();
        this.spreadSheetPath = spreadSheetPath;
        
        mediaPanel = new JTabbedPane();
        inkPanel = new JTabbedPane();
        otherPanel = new JTabbedPane();
        
        for (String media : inv.getMedias().keySet()){
            mediaPanel.addTab(media, createSubPanel(inv.getMedias().get(media)));
        }
        
        for(String ink : inv.getInks().keySet()){
            inkPanel.addTab(ink, createSubPanel(inv.getInks().get(ink)));
        }
        
        for(String other : inv.getOtherSupply().keySet()){
            otherPanel.addTab(other, createSubPanel(inv.getOtherSupply().get(other)));
        }
        
        superPanel = new JTabbedPane();
        superPanel.addTab("Media", mediaPanel);
        superPanel.addTab("Inks", inkPanel);
        superPanel.addTab("Other", otherPanel);
        
        JButton saveButton = new JButton("Save");
        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                SpreadsheetWriter writer = new SpreadsheetWriter();
                try {
                    writer.writeSpreadhSheet(spreadSheetPath,
                            inv.getAllSupplies());
                    saveButton.setText("Save -- Done!");
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "The Excel file is open somewhere else!\nThe changes you've made could not be saved",
                    "Error", JOptionPane.ERROR_MESSAGE);                
                }
            }
        };
        
        saveButton.addActionListener(listener);
        
        setLayout(new BorderLayout());
        add(superPanel, BorderLayout.CENTER);
        add(saveButton, BorderLayout.PAGE_END);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450, 600);
        setVisible(true);
    }

    public JPanel createSubPanel(ArrayList<Supply> supplies){
        JPanel subPanel = new JPanel();
        subPanel.setLayout(new BoxLayout(subPanel, BoxLayout.PAGE_AXIS));
        
        
        for(JPanel supply : createUIComponents(supplies)){
            subPanel.add(Box.createRigidArea(new Dimension(0,5)));
            subPanel.add((Component) supply);
        }
        return subPanel;
    }
    
    public ArrayList<JPanel> createUIComponents(ArrayList<Supply> supplies){
        ArrayList<JPanel> panels = new ArrayList();
        JPanel headerPanel = new JPanel();
        headerPanel.setMaximumSize(new Dimension(400, 30));
        headerPanel.add(new JLabel("Supply     | Unopened Count"));
        headerPanel.setAlignmentX(LEFT_ALIGNMENT);
        panels.add(headerPanel);
        
        for(Supply supply : supplies){
            JPanel panel = new JPanel();
            BoxLayout layout = new BoxLayout(panel, BoxLayout.LINE_AXIS);
            panel.setLayout(layout);
            
            panel.add(createLabels(supply));
            //addInkColors(supply, panel);
            panel.add(createSpinner(supply));
            panels.add(panel);
        }
        return panels;
    }
    
    public JPanel createLabels(Supply supply){
        JPanel namePanel= new JPanel();
        namePanel.setLayout(new BorderLayout());
        namePanel.setMinimumSize(new Dimension(400, 30));
        namePanel.setMaximumSize(new Dimension(400, 30));
        
        JLabel name = new JLabel(supply.getName());
        JLabel otherDetail = new JLabel("    ---    " + supply.getOtherDetail() + "   ");
        
        labels.put(supply, new ArrayList<>());
        labels.get(supply).add(name);
        labels.get(supply).add(otherDetail);
        checkForLowQuantity(supply);
        
        namePanel.add(name, BorderLayout.WEST);
        namePanel.add(otherDetail, BorderLayout.EAST);
        namePanel.setAlignmentX(RIGHT_ALIGNMENT);
        
        return namePanel;
    }
    
    public JPanel createSpinner(Supply supply){
        //creates a new spinner type with a min of 0, max of 50, and increments of 1
        SpinnerNumberModel spinModel = new SpinnerNumberModel(supply.getCurrentCount(), 0, 50, 1);
        
        JSpinner spinner = new JSpinner(spinModel);
        spinner.setMaximumSize(new Dimension(50,30));
        
        //centers the text in the spinner's text field
        JComponent editor = spinner.getEditor();
        if (editor instanceof JSpinner.DefaultEditor){
            JSpinner.DefaultEditor spinEditor = (JSpinner.DefaultEditor)editor;
            spinEditor.getTextField().setHorizontalAlignment(JTextField.CENTER);
        }
        
        //creates a listener that updates that supply count on any change to the spinner
        ChangeListener listener = new ChangeListener(){
            @Override
            public void stateChanged(ChangeEvent e) {

                    JSpinner spinner = (JSpinner) e.getSource();
                    Supply toBeChanged = spinners.get(spinner);
                    toBeChanged.adjustCurrentCount((Integer) spinner.getValue());
                    checkForLowQuantity(supply);

            }
        };
        spinner.addChangeListener(listener);
        
        //creates a sub-subpanel for the jspinner
        JPanel spinnerPanel = new JPanel();
        spinnerPanel.setMaximumSize(new Dimension(100,30));
        spinners.put(spinner, supply);
        spinnerPanel.add(spinner);
        
        return spinnerPanel;
    }
    
    public void checkForLowQuantity(Supply supply){
        if (supply.checkForReorder()){
            for(JLabel label : labels.get(supply)){
                label.setForeground(new Color(179,0,0));
            }
        } else if (!supply.checkForReorder()){
            for(JLabel label : labels.get(supply)){
                label.setForeground(Color.BLACK);
            }
        }
    }
    
    public static void main(String[] args) {
        System.out.println(System.getProperty("user.dir"));
        String currentDir = System.getProperty("user.dir");
        currentDir += "\\SpreadSheets\\Example.xlsx";
        
        ALNInventory inv = new ALNInventory(currentDir);
        UserInterface ui = new UserInterface(inv, currentDir);
        System.out.println(inv.getInks());
    }
}