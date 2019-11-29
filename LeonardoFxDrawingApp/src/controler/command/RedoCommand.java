package controler.command;

public class RedoCommand implements Command {
	private Receiver receiver;

	public RedoCommand(Receiver rec) {
		this.receiver = rec;
	}

	@Override
	public void Do() {
		receiver.performRedo();
	}

}
