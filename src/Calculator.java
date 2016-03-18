import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class Calculator implements ActionListener{
    JLabel lbTotal;
    long temp = 0, total = 0;
    String str = "";
    boolean initialized = false, addPressed = false, subPressed = false, multPressed = false, divPressed = false;
    JFrame fr;
    public Calculator(String s){
    	fr = new JFrame("Calculator");
        fr.setSize(300,300);
        fr.setLocationRelativeTo (null);
        fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fr.setIconImage(new ImageIcon(s).getImage());
        
        JPanel panel1 = new JPanel(new GridLayout(1,1));
        panel1.setPreferredSize(new Dimension(50,50));
        JPanel panel2 = new JPanel(new GridLayout(4,4,4,4));
        lbTotal = new JLabel("" + total , SwingConstants.RIGHT);
        panel1.add(lbTotal);
        
        String[] names = {"7","8","9","+",
        				  "4","5","6","-",
        				  "1","2","3","*",
        				  "0","C","=","/"};
        JButton bt[] = new JButton[16];
        for(int i = 0; i < bt.length; i++){
            bt[i] = new JButton(names[i]);
            bt[i].addActionListener(this);
            panel2.add(bt[i]);
        }
        
        fr.getRootPane().setDefaultButton(bt[14]);
        fr.add(panel1,BorderLayout.NORTH);
        fr.add(panel2);
        fr.setVisible(true);
    }
    public void actionPerformed(ActionEvent ae){
    	switch(ae.getActionCommand()){
    		case "+":
    			initialize();
                addPressed = true;
                reset();
                break;
    		case "-":
    			initialize();
                subPressed = true;
                reset();
                break;
    		case "*":
    			initialize();
                multPressed = true;
                reset();
                break;
    		case "/":
    			initialize();
                divPressed = true;
                reset();
                break;
    		case "=":
    			operation();
    			reset();
                break; 
    		case "C":
    			if((ae.getModifiers() & KeyEvent.CTRL_MASK) == KeyEvent.CTRL_MASK && ae.getActionCommand().equals("C")){
    	    		lbTotal.setText("(c) 2015 Jonathan Ma");
    	    		str = "";
    	    	}
    			else{
    				total = 0;
        			reset();
                	lbTotal.setText("" + total);
                    initialized = false;
    			}
                break;
            default:
            	if(str.length() < 10){
            	    str += ae.getActionCommand();
            	}
                temp = Long.parseLong(str);
                lbTotal.setText("" + temp);
            	break;
    	}
    }
    public void initialize(){
    	if(!initialized){ 
    		total = temp;
    		temp = 0;
    		initialized = true;
    	}
    }
    private void reset() {
		str = "";
		temp = 0;
	}
    private void checkOverflow() {
    	if(lbTotal.getText().contains("-") && lbTotal.getText().length() > 11){
    		lbTotal.setText("Overflow");
			reset();
			total = 0;
			initialized = false;
			return;
    	}
    	if(!lbTotal.getText().contains("-") && lbTotal.getText().length() > 10){
			lbTotal.setText("Overflow");
			reset();
			total = 0;
			initialized = false;
		}
	}
    public void operation(){
    	if(addPressed){
    		total += temp;
            lbTotal.setText("" + total);
            checkOverflow();
            addPressed = false;
    	}
    	else if(subPressed){
    		total -= temp;
            lbTotal.setText("" + total);
            checkOverflow();
            subPressed = false;
    	}
    	else if(multPressed){
    		total *= temp;
            lbTotal.setText("" + total);
            checkOverflow();
            multPressed = false;
    	}
    	else if(divPressed){
    		if(temp == 0){
    			lbTotal.setText("Can't divide by 0");
    			total = 0;
    			str = "";
    			initialized = false;
    		}
    		else{
    			total /= temp;
                lbTotal.setText("" + total);
                checkOverflow();
                divPressed = false;
    		}	
    	}
    }
    public static void main(final String[] args) {
    	SwingUtilities.invokeLater(new Runnable(){
    		public void run(){
    			String s = "";
    	    	//s = args[0];
    			new Calculator(s);
    		}
    	});  
    }
}