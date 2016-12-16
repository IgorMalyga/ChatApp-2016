package Chat;
public class CommandListenerThread3 extends Thread {
		private Command lastCommand;
		private Connection connection;
		private boolean isStopped = false;

		public CommandListenerThread3(Connection con, JFrame jf) {
			connection = con;
			lastCommand = new Command(Command.CommandType.ACCEPT);
		}

		@Override
		public void run() {
			while (true) {
				lastCommand = connection.receive();
				if (lastCommand.type.equals(Command.CommandType.MESSAGE)) {
					String message = connection.getMessage();
					textArea.append(connection.getNickHello() + ": " + message + "\n");
				}
			}
		}

		public boolean isDisconnected() {
			if (lastCommand.equals(Command.CommandType.DISCONNECT)) {
				return true;
			} else
				return false;
		}

		public Command getLastCommand() {
			return lastCommand;
		}

		public void setStoppedStatus(boolean status) {
			isStopped = status;
		}

	}

