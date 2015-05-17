import sys
import time
from neopixel import *

# LED strip configuration:
LED_COUNT      = 66      # Number of LED pixels.
LED_PIN        = 18      # GPIO pin connected to the pixels (must support PWM!).
LED_FREQ_HZ    = 700000  # LED signal frequency in hertz (usually 800khz)
LED_DMA        = 5       # DMA channel to use for generating signal (try 5)
LED_BRIGHTNESS = 255     # Set to 0 for darkest and 255 for brightest
LED_INVERT     = False   # True to invert the signal (when using NPN transistor level shift)

def setLed(strip, l,r,g,b):
	strip.setPixelColor(l,Color(r,g,b))
	
def strobetest(strip):
	on = True
	while True:
		if on:			
			on = False
			for i in range(strip.numPixels()):
				strip.setPixelColor(i, Color(255,255,255))
		else:
			on = True
			for i in range(strip.numPixels()):
				strip.setPixelColor(i, Color(0,0,0))
		strip.show()
		time.sleep(100.0/1000)
		
if __name__ == '__main__':
	ledstrip = Adafruit_NeoPixel(LED_COUNT, LED_PIN, LED_FREQ_HZ, LED_DMA, LED_INVERT, LED_BRIGHTNESS)
	ledstrip.begin()
	s = sys.stdin.readline().strip()
	while s not in ['break', 'quit']:
		message =  s.split("|")
		returnms = 'e'
		if(message[0] == '1'):
			if (len(message) == 5):
				try: 
					led = int(message[1])
					r = int(message[2])
					g = int(message[3])
					b = int(message[4])
					setLed(ledstrip, led,r,g,b)
					returnms = 'a'
				except ValueError:
					returnms = 'e'
		elif (message[0] == '0'):
			ledstrip.show()
			returnms = 'a'
		s = sys.stdin.readline().strip()

