/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package java2plant.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java2plant.control.Controller;
import java2plant.model.AppData;
import java2plant.model.ClassList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListModel;
import javax.swing.border.LineBorder;

/**
 *
 * @author arthur
 */
public class MainWindow extends JFrame implements ActionListener {

	private JList classList = null;
	private JList filterList = null;
	private JButton writeButton = null;
	private JPanel actionPanel = null;
	
	public MainWindow() {
		initComponants();
		setLocationRelativeTo(null);
        setPreferredSize(new Dimension(650, 400));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Java2Plant");
		pack();
		setVisible(true);
	}

	private void initComponants() {
		setLayout(new BorderLayout());

		MouseListener mouseClassListener = new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					int index = classList.locationToIndex(e.getPoint());
					ListModel dlm = classList.getModel();
					Object item = dlm.getElementAt(index);
					classList.ensureIndexIsVisible(index);
					AppData.getInstance().getCurrentFilter().add((String) item);
					System.out.println("Double clicked on " + item);
					System.out.println("Double clicked on Item " + index);
				}
			}
		};

		classList = new JList();
		classList.setModel(ClassList.getInstance());
		classList.addMouseListener(mouseClassListener);
		classList.setMaximumSize(null);
        classList.setBorder(new LineBorder(Color.GRAY, 1, false));
		add("West", classList);

		filterList = new JList();
		filterList.setModel(AppData.getInstance().getCurrentFilter());
		filterList.setMaximumSize(null);
        filterList.setBorder(new LineBorder(Color.GRAY, 1, false));
		add("East", filterList);

		writeButton = new JButton("Write");
		writeButton.addActionListener(this);
		actionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		actionPanel.add(writeButton);
		add("South", actionPanel);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == writeButton) {
			Controller.getInstance().writePlant(AppData.getInstance().getCurrentFilter());
		}
	}
}
