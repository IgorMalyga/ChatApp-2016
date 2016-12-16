/*public class Command {

    public Command(Command.CommandType t){
      //  type = t;
    }

    public enum CommandType {
        ACCEPT("ACCEPT"), DISCONNECT("DISCONNECT"), MESSAGE("MESSAGE"), NICK("NICK"), REJECT("REJECT");

        private final String name;

        private CommandType(String s) {
            name = s;
        }

        public boolean equalsName(String otherName){
            return (otherName == null) ? false : name.equals(otherName);
        }

        public String toString(){
            return this.name;
        }

        }

    //public Command.CommandType type;
}*/
package Chat;
public class Command {

    public Command(Command.CommandType t) {
        type = t;
    }

    public Command.CommandType type;

    enum CommandType {ACCEPT, DISCONNECT, MESSAGE, NICK, REJECT, WAIT}

    public boolean compareEnums(Command com) {
        if (this.toString().compareTo(com.toString()) == 0) {
            return true;
        } else {
            return false;
        }
    }
}