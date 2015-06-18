package control;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import control.button.ButtonHandler;
import control.joystick.Joystick.Position;
import control.joystick.JoystickHandler;

public class NetworkHandler implements Runnable{
	
	DatagramSocket udp;
	DatagramSocket udpsend;
	
	String host;
	int port;
	
	boolean running;
	Thread t;
	
	byte[] send;
	byte[] receive;
	
	ButtonHandler bth;
	JoystickHandler jth;
	
	InetAddress adr;
	
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
			adr = InetAddress.getByName("192.168.1.5");
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
//			try {
//				udp = new DatagramSocket(1112);
//			} catch (SocketException e) {
//				e.printStackTrace();
//			}
			 try {
				udpsend = new DatagramSocket();
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
			System.out.println(send);
			udpsend.send(new DatagramPacket(send, send.length, adr, port));
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
				udpsend.receive(receivePacket);
			} catch (IOException e) {
				e.printStackTrace();
			}                   
			String sentence = new String( receivePacket.getData());  
			System.out.println("RECEIVED: " + sentence); 

					
			sentence = sentence.trim();
			String[] controls = sentence.split("\\|");
			int[] control = new int[controls.length];
			for(int i=0; i<controls.length; i++){
				control[i] = Integer.parseInt(controls[i]);
			}
			for(int i = 0; i < 7; i++){
				if(control[i] != ButtonHandler.getButton(i).pressed)
				{
					System.out.println("PRESS BITCH " + controls[i]);
					ButtonHandler.getButton(i).pressed = control[i];
					if(control[i] == 0)
						bth.buttonPress(ButtonHandler.getButton(i));
				}
			}
			
			if(control[7] == 0 && control[8] == 0){
				if(JoystickHandler.j.getPos() != Position.UP_LEFT)
				{
					JoystickHandler.j.setPosition(Position.UP_LEFT);
					jth.onJoystickMoved(JoystickHandler.j);
				}
			}
			else if(control[7] == 0 && control[9] == 0){
				if(JoystickHandler.j.getPos() != Position.UP_RIGHT)
				{
					JoystickHandler.j.setPosition(Position.UP_RIGHT);
					jth.onJoystickMoved(JoystickHandler.j);
				}
			}
			else if(control[10] == 0 && control[8] == 0){
				if(JoystickHandler.j.getPos() != Position.DOWN_LEFT)
				{
					JoystickHandler.j.setPosition(Position.DOWN_LEFT);
					jth.onJoystickMoved(JoystickHandler.j);
				}
			}
			else if(control[10] == 0 && control[9] == 0){
				if(JoystickHandler.j.getPos() != Position.DOWN_RIGHT)
				{
					JoystickHandler.j.setPosition(Position.DOWN_RIGHT);
					jth.onJoystickMoved(JoystickHandler.j);
				}
			}
			
			else if(control[7] == 0){
				if(JoystickHandler.j.getPos() != Position.UP)
				{
					JoystickHandler.j.setPosition(Position.UP);
					jth.onJoystickMoved(JoystickHandler.j);
				}
			}
			else if(control[8] == 0){
				if(JoystickHandler.j.getPos() != Position.LEFT)
				{
					JoystickHandler.j.setPosition(Position.LEFT);
					jth.onJoystickMoved(JoystickHandler.j);
				}
			}
			else if(control[9] == 0){
				if(JoystickHandler.j.getPos() != Position.RIGHT)
				{
					JoystickHandler.j.setPosition(Position.RIGHT);
					jth.onJoystickMoved(JoystickHandler.j);
				}
			}
			else if(control[10] == 0){
				if(JoystickHandler.j.getPos() != Position.DOWN)
				{
					JoystickHandler.j.setPosition(Position.DOWN);
					jth.onJoystickMoved(JoystickHandler.j);
				}
			}
		}
			
	}
}