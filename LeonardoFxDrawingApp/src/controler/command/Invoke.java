package controler.command;

public class Invoke {
	Command command;

	public void executeCommand(Command com) {
		this.command = com;
		com.Do();
	}
}
