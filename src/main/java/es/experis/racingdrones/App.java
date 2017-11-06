package es.experis.racingdrones;

import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import jssc.SerialPort;
import jssc.SerialPortException;
import jssc.SerialPortList;


public class App implements NativeKeyListener
{
	
    static SerialPort serialPort = null;

    static AtomicInteger acceleration = new AtomicInteger(1500);
    static AtomicInteger turn = new AtomicInteger(1500);
    
    public static void main ( String[] args )
    {
		int choice = 0;
		Scanner scan = new Scanner(System.in);

		System.out.println("Elija el puerto COM que quiere usar:");
    	
        String[] portNames = SerialPortList.getPortNames();
        for(int i = 0; i < portNames.length; i++){
            System.out.println((i+1) +". " + portNames[i]);
        }

		choice = scan.nextInt();
		
		if(choice > 0 && choice <= portNames.length) {
			serialPort = new SerialPort(portNames[(choice-1)]);

	        try {
				serialPort.openPort();
		        serialPort.setParams(SerialPort.BAUDRATE_9600, 
                        SerialPort.DATABITS_8,
                        SerialPort.STOPBITS_1,
                        SerialPort.PARITY_NONE);//Set params. Also you can set params by this string: serialPort.setParams(9600, 8, 1, 0);
		        try {

					Logger l0 = Logger.getLogger("");
					l0.removeHandler(l0.getHandlers()[0]);
					
					GlobalScreen.registerNativeHook();
					
				}
				catch (NativeHookException ex) {

					System.exit(1);
				}

				GlobalScreen.addNativeKeyListener(new App());
		        
			} catch (SerialPortException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}//Open serial port
		}
		
    }
    
	public void nativeKeyPressed(NativeKeyEvent e) {
		
		Integer oldValueAccel = acceleration.get();
		Integer oldValueTurn = turn.get();
		
		// w
		if(e.getKeyCode() == 17) {
			acceleration.set(1550);
		}// s
		else if(e.getKeyCode() == 31) {
			
			if(acceleration.get() > 1500) {
				acceleration.set(1500);
				
				try {
					writeToPort(getPortValueToSend());
				} catch (SerialPortException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
			
			acceleration.set(1450);
		}// a
		else if(e.getKeyCode() == 30) {
			turn.set(2000);
		}// d
		else if(e.getKeyCode() == 32) {
			turn.set(1000);
		}
		
		if(oldValueAccel != acceleration.get() || oldValueTurn != turn.get()) {
			try {
				writeToPort(getPortValueToSend());
			} catch (SerialPortException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	public void nativeKeyReleased(NativeKeyEvent e) {

		Integer oldValueAccel = acceleration.get();
		Integer oldValueTurn = turn.get();
		
		// w
		if(e.getKeyCode() == 17) {
			acceleration.set(1500);
		}// s
		else if(e.getKeyCode() == 31) {
			acceleration.set(1500);
		}// a
		else if(e.getKeyCode() == 30) {
			turn.set(1500);
		}// d
		else if(e.getKeyCode() == 32) {
			turn.set(1500);
		}
		
		if(oldValueAccel != acceleration.get() || oldValueTurn != turn.get()) {
			try {
				writeToPort(getPortValueToSend());
			} catch (SerialPortException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	public void nativeKeyTyped(NativeKeyEvent e) {

	}
	
	private void writeToPort(String msg) throws SerialPortException {
		System.out.println("Send to port: " + msg);
		
		serialPort.writeBytes(msg.getBytes());//Write data to port
	}

	private void closePort() throws SerialPortException {
        serialPort.closePort();//Close serial port
	}
	
	private String getPortValueToSend() {
		return acceleration.get() + "," + turn.get() + "\n";
	}
}
