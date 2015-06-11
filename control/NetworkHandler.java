package control;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import control.button.ButtonHandler;
import control.joystick.JoystickHandler;

public class NetworkHandler implements Runnable{
	
	DatagramSocket udp;
	
	String host;
	int port;
	
	boolean running;
	Thread t;
	
	byte[] send;
	byte[] receive;
	
	ButtonHandler bth;
	JoystickHandler jth;
	
	public NetworkHandler(String host, int port, ButtonHandler bth, JoystickHandler jth)
	{
		this.host = host;
		this.port = port;
		
		this.bth = bth;
		this.jth = jth;
		
		udp = null;
		
		send = new byte[1024];
		receive = new byte[1024];
		
			try {
				udp = new DatagramSocket(1112);
			} catch (SocketException e) {
				e.printStackTrace();
			}
		
		running = true;
		t = new Thread(this);
		t.start();
	}
	
	public void setLed(int led, int r, int g , int b)
	{
		String cmd = "1|" + led + "|" + r + "|" + g + "|" + b + "\n";
		send(cmd);
	}
	
	public void show(){
		String cmd = "0\n";
		send(cmd);
	}
	
	private void send(String str)
	{
		send = str.getBytes();
		try {
			udp.send(new DatagramPacket(send, send.length));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void close()
	{
		running = false;
		udp.disconnect();
		udp.close();
	}

	@Override
	public void run() {
		
		while(running)
		{
			DatagramPacket receivePacket = new DatagramPacket(receive, receive.length);     
			try {
				udp.receive(receivePacket);
			} catch (IOException e) {
				e.printStackTrace();
			}                   
			String sentence = new String( receivePacket.getData());   
			System.out.println("RECEIVED: " + sentence); 
			
			String[] controls = sentence.split("\\|");
			for(int i = 0; i < 7; i++){
				if(Integer.parseInt(controls[i]) != ButtonHandler.getButton(i).pressed)
				{
					System.out.println("PRESS BITCH " + controls[i]);
					ButtonHandler.getButton(i).pressed = Integer.parseInt(controls[i]);
					if(Integer.parseInt(controls[i]) == 0)
						bth.buttonPress(ButtonHandler.getButton(i));
				}
			}
		}
			
	}
}
