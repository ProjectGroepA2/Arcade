package main;

import java.io.File;
import java.io.IOException;

import audio.Song;
import audio.io.JSONReader;

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
