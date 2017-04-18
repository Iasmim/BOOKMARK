package IU;


import java.awt.Font;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

public class PanelRadioButton extends JPanel {
    
    private JRadioButton rdbtnAll, rdbtnDiff;
    
    /**
     * Create the panel.
     */
    public PanelRadioButton() {
        JPanel panelRadioButton = new JPanel();
        panelRadioButton.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
//        panelMain.add(panelRadioButton, BorderLayout.NORTH);

        rdbtnAll = new JRadioButton("All");
        rdbtnAll.setSelected(true);
        rdbtnAll.setFont(new Font("Arial", Font.PLAIN, 14));
        panelRadioButton.add(rdbtnAll);
        
        rdbtnDiff = new JRadioButton("Diff");
        rdbtnDiff.setFont(new Font("Arial", Font.PLAIN, 14));
        panelRadioButton.add(rdbtnDiff);

        ButtonGroup buttonGroup = new ButtonGroup();
        setLayout(new BorderLayout(0, 0));
        buttonGroup.add(rdbtnAll);
        buttonGroup.add(rdbtnDiff);
        
        add(panelRadioButton, BorderLayout.NORTH);
    }
    public JRadioButton getRdbtnAll() {
        return rdbtnAll;
    }
    public void setRdbtnAll(JRadioButton rdbtnAll) {
        this.rdbtnAll = rdbtnAll;
    }
    public JRadioButton getRdbtnDiff() {
        return rdbtnDiff;
    }
    public void setRdbtnDiff(JRadioButton rdbtnDiff) {
        this.rdbtnDiff = rdbtnDiff;
    }

}
