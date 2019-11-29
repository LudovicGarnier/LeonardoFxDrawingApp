package controler.memento;

public class Originator {

	private String state;
	Memento m;

	public void setState(String st) {
		this.state = st;
	}

	public Memento originatorMemento() {
		return m = new Memento(state);

	}

	public void revert(Memento memento) {
		state = memento.getState();
	}

}
