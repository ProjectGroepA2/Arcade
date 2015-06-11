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
			udp = new DatagramSocket();
			udp.connect(InetAddress.getByName(host), port);
		} catch (SocketException | UnknownHostException e) {
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
			DatagramPacket pckg = new DatagramPacket(receive, receive.length);
			try {
				udp.receive(pckg);
			} catch (IOException e) {
				e.printStackTrace();
			}	
			
			String str = new String(pckg.getData());
			System.out.println(str);
		}
			
	}
}
