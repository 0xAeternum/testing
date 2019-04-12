import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.*;

public class PrinterGUI {
	Printer printer;
	Color selectedColor;
	public void init() {
		ArrayList<ColoredInk> colors = new ArrayList<>();
		colors.add(new ColoredInk(50, Color.RED));
		colors.add(new ColoredInk(50, Color.BLUE));
		colors.add(new ColoredInk(50, Color.GREEN));
		
		
		
		printer = new Printer(43, colors);
		
		JFrame frame = new JFrame();
		
		JButton printButton = new JButton("Print");
		JButton scanButton = new JButton("Scan");
		
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		
		Panel btnContainer = new Panel();
		
		JButton loginEmp = new JButton("Login as employee");
		JButton loginUser = new JButton("Login as user");
		JButton logout = new JButton("Logout");
		
		JLabel userStatus = new JLabel("Not logged in");
		JLabel inkLevel = new JLabel(Integer.toString(printer.getInk()));
		JButton fillInk = new JButton("Fill ink");
		fillInk.setVisible(false);
		
		Panel informationContainer = new Panel();
		informationContainer.setLayout(new BoxLayout(informationContainer, BoxLayout.Y_AXIS));
		
		Panel colorPanel = new Panel();
		colorPanel.setLayout(new BoxLayout(colorPanel, BoxLayout.Y_AXIS));
		
		
		//set actionlisteners
		printButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				fileChooser.showOpenDialog(frame);
				if (printer.print(fileChooser.getSelectedFile())) {
					try {
						inkLevel.setText(Integer.toString(printer.getInk()));
						
						//setup new frame, simulating 'paper' print
						String fileContents = printer.getFileContents(fileChooser.getSelectedFile());
						JFrame printFrame = new JFrame();
						JTextArea jta = new JTextArea();
						JScrollPane scrollPane = new JScrollPane(jta);
						jta.setLineWrap(true);
						jta.setWrapStyleWord(true);
						
						jta.setText(fileContents);
						
						if (selectedColor != null) {
							jta.setForeground(selectedColor);
						}
						
						printFrame.add(jta);
						
						printFrame.setSize(600, 800);
						printFrame.setVisible(true);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog(null, "Document was unable to be printed", "Fail", JOptionPane.WARNING_MESSAGE);
				}
			}
			
		});
		
		scanButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				fileChooser.showOpenDialog(frame);
				if (printer.scan(fileChooser.getSelectedFile())) {
					try {
						String fileContents = printer.getFileContents(fileChooser.getSelectedFile());
						System.out.println(fileContents);
						
						JOptionPane.showMessageDialog(null, "Successfully scanned document", "Success", JOptionPane.PLAIN_MESSAGE);
					} catch (IOException e) {
						JOptionPane.showMessageDialog(null, "Document was unable to be scanned", "Fail", JOptionPane.WARNING_MESSAGE);
						e.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog(null, "Document was unable to be scanned", "Fail", JOptionPane.WARNING_MESSAGE);
				}
			}
			
		});
		
		loginUser.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (printer.loginUser()) {
					userStatus.setText("Logged in as user");
					fillInk.setVisible(false);
				}
			}
			
		});
		
		loginEmp.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (printer.loginEmployee()) {
					userStatus.setText("Logged in as employee");
					fillInk.setVisible(true);
					
					if (printer.isInkLow()) {
						JOptionPane.showMessageDialog(null, "Ink is low", "Warning", JOptionPane.WARNING_MESSAGE);
					}
				}
			}
			
		});
		
		logout.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				printer.logout();
				userStatus.setText("Not logged in");
				fillInk.setVisible(false);
			}
			
		});
		
		fillInk.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				printer.loadInk();
				inkLevel.setText(Integer.toString(printer.getInk()));
			}
			
		});
		
		btnContainer.add(printButton);
		btnContainer.add(scanButton);
		btnContainer.add(loginUser);
		btnContainer.add(loginEmp);
		btnContainer.add(logout);
		
		btnContainer.setLayout(new BoxLayout(btnContainer, BoxLayout.PAGE_AXIS));
		
		informationContainer.add(userStatus);
		informationContainer.add(inkLevel);
		informationContainer.add(fillInk);
		
		colors.forEach((color) -> {
			JRadioButton btn = new JRadioButton("Color");
			btn.setForeground(color.getColor());
			btn.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					selectedColor = color.getColor();
				}
				
			});
			colorPanel.add(btn);
		});
		
		frame.add(btnContainer, BorderLayout.WEST);
		frame.add(informationContainer, BorderLayout.EAST);
		frame.add(colorPanel, BorderLayout.SOUTH);
		
		initFrame(frame);
	}
	
	public void initFrame(JFrame f) {
		f.setSize(400,500);
		f.setVisible(true);
	}
	
	public static void main(String[] args) {
		new PrinterGUI().init();
	}
}
