package control;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class LedHandler {
    private BufferedReader inp;
    private BufferedWriter out;
    private Process p;
    
    public LedHandler(){
	    try {
	        p = Runtime.getRuntime().exec("sudo python led.py");
	        inp = new BufferedReader( new InputStreamReader(p.getInputStream()) );
	        out = new BufferedWriter( new OutputStreamWriter(p.getOutputStream()) );      
	    }
	    catch (Exception err) {
	        err.printStackTrace();
	    }
    }
    
    public void close(){
        try {
			inp.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
        try {
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
        p.destroy();
    }
    
    
    /**
     * Set the color of a specific led
     * @param Pixel number of the led
     * @param Red value
     * @param Green value
     * @param Blue value
     * Remember: Button leds are GRB, normal ledstrip is RGB
     */
    public void setLed(int led, int r, int g, int b) {
	    if(led<0)
	    	return;
    	try {
	        out.write( "1|" + led + "|" + r + "|" + g + "|" + b + "\n" );
	        out.flush();
	    }
	    catch (Exception err) {
	    	err.printStackTrace();
	    }
    }

    /**
     * Shows the (new) colors 
     */
    public void show(){
	    try {
	        out.write( "0\n" );
	        out.flush();
	    }
	    catch (Exception err) {
	    	err.printStackTrace();
	    }
    }
}
