/*
 * Name: My Nguyen
 * Course: CIS 35A - 61Y
 * Assignment: #6
 * Due date: 03/22/2015
 * Data submitted: 03/21/2015
 * 
 * This program works as an application for Disney's Cottages, a weekend
 * get-away resort that rents cottages and boats to use on the local lake.
 * The application allows users to compute the price of a vacation based
 * on start and end date of a vacation. It includes mutually exclusive 
 * check-boxes to select a one-bedroom cottage at $600.00 per week or a
 * two bedroom cottage at $850.00 per week. The user also can choose a
 * row-boat rental at $60.00 per week. (All prices are calculated based 
 * on the days)
 */

package disneysCottages;

import java.time.*;
import java.time.temporal.ChronoUnit;
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
		this.setSize(400,150);
		
		this.setLayout(new BorderLayout());
		
		this.buildCommandPanel();
		add(commandPanel, BorderLayout.CENTER);
		commandPanel.setBorder(BorderFactory.createLineBorder(Color.BLUE, 2));
		
		// Users click the button to receive the calculation
		button.addActionListener(new buttonListener());
	}
	
	// Create fields and add them to the panel
	public void buildCommandPanel() {
		commandPanel = new JPanel();
		
		startingDateLabel = new JLabel("Starting date");
		startingDateText = new JTextField("YYYY-MM-DD");
		
		endingDateLabel = new JLabel("Ending date");
		endingDateText = new JTextField("YYYY-MM-DD");
		
		commandPanel.add(startingDateLabel);
		commandPanel.add(startingDateText);
		commandPanel.add(endingDateLabel);
		commandPanel.add(endingDateText);
		
		oneBedBox = new JRadioButton("One Bedroom", true);	// Default radio button when the program starts
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
	
	// Convert a string date to Local Date
	public LocalDate stringToDate(String stringDate) {
		LocalDate date = null;
	    
	    try{
	   		date = LocalDate.parse(stringDate);
	    }
	    catch ( Exception ex ){
	        System.out.printf("Error - " + ex);
	    }
		return date;
	}
	
	public long calculateDaysBetween() {
		// Convert each input to Local Date
		LocalDate dateStart = stringToDate(startingDateText.getText());
		LocalDate dateEnd = stringToDate(endingDateText.getText());
		
		long daysBetween = 0;
		
		daysBetween = ChronoUnit.DAYS.between(dateStart, dateEnd);	//Calculate
		
		return daysBetween;
	}
	
	// The total price is calculated based on the days
	public double calculatePrice(long days, String command) {
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
	
	// Check if the string of date is an integer
	public static boolean isInteger(String string) {
		boolean isInteger = true;
		
		try {
			Integer.parseInt(string);
		}
		catch (NumberFormatException ex) {
			isInteger = false;
		}
			
		return isInteger;
	}
	
	// Return true if the date input is valid in the form YYYY-MM-DD
	public static boolean isValidInput (String stringDate) {
		boolean isValidDate = true;
		String year, month, day;
		
		if(stringDate.length() != 10) {
			isValidDate = false;
		}
		else {
			year = stringDate.substring(0, 3);
			month = stringDate.substring(5, 7);
			day = stringDate.substring(8, 10);
			
			if(stringDate.charAt(4) != '-' 
					|| stringDate.charAt(7) != '-' || isInteger(year) == false
					|| isInteger(month) == false || isInteger(day) == false
					|| Integer.parseInt(month) > 12 || Integer.parseInt(month) <= 0
					|| Integer.parseInt(day) > 31 || Integer.parseInt(day) <= 0) {
				isValidDate = false;
			}
		}
		
		return isValidDate;
	}
	
	class buttonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			double totalPrice = 0;
			
			// If both dates are valid and the endingDate is after the startingDate
			// then do the calculation and print the message
			if(isValidInput(startingDateText.getText()) == true &&
					isValidInput(endingDateText.getText()) == true &&
					calculateDaysBetween() >= 0) {
				
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
			else {	// Otherwise, show the error message
				JOptionPane.showMessageDialog(null, "Invalid dates!\nPlease re-enter your dates as YYYY-MM-DD");
			}
		}
	}
	
	public static void main(String[] args) {
		DisneysCottages app = new DisneysCottages();
		app.setVisible(true);
	}
}
