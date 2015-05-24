package control.button;

public class ButtonEvent {
	private Button button;
	private Long now;
	
	public ButtonEvent(Button button, Long now){
		this.button = button;
		this.now = now;
	}

	public Button getButton() {
		return button;
	}

	public Long getNow() {
		return now;
	}		
}
