package control;

public class ButtonEvent {
	private Button button;
	private Long now;
	
	public ButtonEvent(Button button, Long now){
		this.button = button;
		this.now = now;
	}

	/**
	 * @return the button
	 */
	public Button getButton() {
		return button;
	}

	/**
	 * @return the time when event was triggered
	 */
	public Long getNow() {
		return now;
	}	
	
	
	
}
