package control;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Ledstrip {
    private BufferedReader inp;
    private BufferedWriter out;
    private Process p;
    
    public Ledstrip(){
	    try {
	        p = Runtime.getRuntime().exec("sudo python led.py");
	        inp = new BufferedReader( new InputStreamReader(p.getInputStream()) );
	        out = new BufferedWriter( new OutputStreamWriter(p.getOutputStream()) );
	        
	        setLed(15, 100, 100, 100);	        
	        strobo();        
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
    
    public void strobo(){
    	boolean on = true;
    	while(true){
    		if(on){
				for(int i = 1; i < 66; i++){
					setLed(i, 0, 0, 0);
				}
    		}else{
				for(int i = 1; i < 66; i++){
					setLed(i, 255, 255, 255);
				}
    		}
    		on = !on;
    		show();
    		try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
    	}
    }
}
