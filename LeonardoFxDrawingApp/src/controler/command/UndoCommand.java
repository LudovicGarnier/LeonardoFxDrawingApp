package controler.command;

public class UndoCommand implements Command {

	private Receiver receiver;

	public UndoCommand(Receiver rec) {
		this.receiver = rec;
	}

	@Override
	public void Do() {
		receiver.performUndo();
	}

}
