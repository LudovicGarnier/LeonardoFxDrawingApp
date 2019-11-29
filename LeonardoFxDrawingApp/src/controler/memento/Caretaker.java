package controler.memento;

public class Caretaker {

	private Memento memento;

	public void saveMemento(Memento m) {
		m = memento;
	}

	public Memento retrieve() {
		return memento;
	}
}
