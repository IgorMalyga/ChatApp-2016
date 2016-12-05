public class Command {

    public Command(Command.CommandType t){
        type = t;
    }

    public Command.CommandType type;

    enum CommandType{ACCEPT, DISCONNECT, MESSAGE, NICK, REJECT}






}
