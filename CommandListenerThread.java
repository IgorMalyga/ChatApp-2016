package Chat;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CommandListenerThread extends Thread {

    private Command lastCommand;
    private Connection connection;

    public CommandListenerThread() {

    }

    public CommandListenerThread(Connection con) {
        connection = con;
        lastCommand = new Command(Command.CommandType.ACCEPT);
    }

    @Override
    public void run() {
        while (true){
            lastCommand = connection.receive();
        }
    }

    public boolean isDisconnected() {
        if (lastCommand.equals(Command.CommandType.DISCONNECT)) {
            return true;
        }else{return false;}
    }

    public Command getLastCommand(){return lastCommand;}

    private class NewCommandAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
        }
    }
}
