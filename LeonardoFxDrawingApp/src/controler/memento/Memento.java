package controler.memento;

public class Memento {
	private String state;

	public Memento(String s) {
		this.state = s;
	}

	public String getState() {
		return this.state;
	}
}
