package main;

public class Main {

	public static void main(String[] args) {
		if(args.length != 1)
		{
			new Window(false);
		}
		else
		{
			new Window(true);
		}
	}

}
