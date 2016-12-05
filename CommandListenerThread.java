import java.util.Observable;

public class CommandListenerThread extends Observable implements Runnable {

    private Command lastCommand;
    private Connection connection;

    public CommandListenerThread() {

    }

    public CommandListenerThread(Connection con) {
        connection = con;
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
//dfgfdgdfgfd
}
