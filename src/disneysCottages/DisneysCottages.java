package disneysCottages;

import java.text.*;
import java.util.*;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

public class DisneysCottages extends JFrame{
	private JPanel commandPanel;
	private JLabel startingDateLabel;
	private JLabel endingDateLabel;
	private JTextField startingDateText;
	private JTextField endingDateText;
	private JButton button;
	private JRadioButton oneBedBox;
	private JRadioButton twoBedBox;
	private JCheckBox rowboatBox;

	public DisneysCottages() {
		this.setTitle("Disney's Cottages Price Calculation");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(400,400);
		
		this.setLayout(new BorderLayout());
		
		this.buildCommandPanel();
		add(commandPanel, BorderLayout.CENTER);
		
		button.addActionListener(new buttonListener());
	}
	
	public void buildCommandPanel() {
		commandPanel = new JPanel();
		
		startingDateLabel = new JLabel("Starting date");
		startingDateText = new JTextField("MMDDYYYY");
		
		endingDateLabel = new JLabel("Ending date");
		endingDateText = new JTextField("MMDDYYYY");
		
		commandPanel.add(startingDateLabel);
		commandPanel.add(startingDateText);
		commandPanel.add(endingDateLabel);
		commandPanel.add(endingDateText);
		
		oneBedBox = new JRadioButton("One Bedroom", true);
		twoBedBox = new JRadioButton("Two Bedroom");
		rowboatBox = new JCheckBox("Rowboat");
		button = new JButton("Calculate");
		
		ButtonGroup group = new ButtonGroup();
		
		group.add(oneBedBox);
		group.add(twoBedBox);
		
		commandPanel.add(oneBedBox);
		commandPanel.add(twoBedBox);
		commandPanel.add(rowboatBox);
		commandPanel.add(button);
	}
	
	public Date stringToDate(String stringDate) {
		Date date = null;
	    DateFormat dateFormat = new SimpleDateFormat("MMddyyyy");
	    
	    try{
	    
	   		date = dateFormat.parse(stringDate);
	    
	    }
	    catch ( Exception ex ){
	        System.out.println(ex);
	    }
		return date;
	}
	
	public int calculateDaysBetween() {
		Date dateStart = stringToDate(startingDateText.getText());
		Date dateEnd = stringToDate(endingDateText.getText());
		
		int dateBetween = 0;
		
		dateBetween = (int) (dateEnd.getTime() - dateStart.getTime()) / (1000*3600*24);
		
		return dateBetween;
	}
	
	public double calculatePrice(int days, String command) {
		double total = 0;
		double week = days/7.0;
		
		if(command.equals("oneBedBox")) {
			total = week * 600.00;
		}
		else if(command.equals("twoBedBox")) {
			total = week * 850.00;
		}
		else {
			total = week * 60.00;
		}
		return total;
	}
	
	class buttonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			double totalPrice = 0;
			
			if(oneBedBox.isSelected() && rowboatBox.isSelected()) {
				totalPrice = calculatePrice(calculateDaysBetween(), "oneBedBox") +
						calculatePrice(calculateDaysBetween(), "rowboatBox");
			}
			else if(oneBedBox.isSelected()) {
				totalPrice = calculatePrice(calculateDaysBetween(), "oneBedBox");
			}
			else if(twoBedBox.isSelected() && rowboatBox.isSelected()) {
				totalPrice = calculatePrice(calculateDaysBetween(), "twoBedBox") +
						calculatePrice(calculateDaysBetween(), "rowboatBox");
			}
			else if(twoBedBox.isSelected()) {
				totalPrice = calculatePrice(calculateDaysBetween(), "twoBedBox");
			}
		
			JOptionPane.showMessageDialog(null, "Total price for " + calculateDaysBetween()
					+ " days of vacation: "+ String.format("%1.2f", totalPrice) + " dollars");
		}
	}
	
	public static void main(String[] args) {
		DisneysCottages app = new DisneysCottages();
		app.setVisible(true);
	}

}
