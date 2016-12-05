import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Connection {

    private Socket incoming;
    PrintWriter out;
    Scanner in;

    public Connection(Socket s1) throws IOException {
        this.incoming = s1;
        InputStream inputStream = incoming.getInputStream();
        OutputStream outputStream = incoming.getOutputStream();
        PrintWriter out = new PrintWriter(outputStream, true);

        Scanner in = new Scanner(inputStream);

    }

    public void sendnNickHello(String nick) throws IOException {
        //InputStream inputStream = incoming.getInputStream();
        out.println(Command.CommandType.NICK.toString() + "ChatApp2015 user" + nick + " 0x0a");
    }

    public void sendBusy(String nick) throws IOException {
        out.println(Command.CommandType.NICK.toString() + "Sorry, user " + nick + " is busy" + " 0x0a");
    }

    public void accept() throws IOException {
        out.println(Command.CommandType.ACCEPT.toString() + " 0x0a");
    }

    public void reject() throws IOException {
        out.println(Command.CommandType.REJECT.toString() + " 0x0a");
    }

    public void sendMessage(String message) throws IOException {
        out.println(Command.CommandType.MESSAGE.toString());
        out.println(message + " 0x0a");
    }

    public void disconnect() throws IOException {
        out.println(Command.CommandType.DISCONNECT.toString() + " 0x0a");
    }

    public void clsoe() throws IOException {
        incoming.close();
    }

    public String getNickHello() {
        boolean done = false;
        int count_words = 0;
        String nick = "";
        while (!done && in.hasNextLine()) {
            nick = in.nextLine();
            count_words++;
            if (count_words == 3) {
                done = true;
            }
        }
        return nick;
    }

    public Command receive() {
        String readCommand = in.nextLine();
        Command command = null;

        switch (readCommand) {
            case "ACCEPT":
                command = new Command(Command.CommandType.ACCEPT);
                break;
            case "DISCONNECT":
                command = new Command(Command.CommandType.DISCONNECT);
                break;
            case "MESSAGE":
                command = new Command(Command.CommandType.MESSAGE);
                break;
            case "NICK":
                command = new Command(Command.CommandType.NICK);
                break;
            case "REJECT":
                command = new Command(Command.CommandType.REJECT);
                break;
        }
        return command;
    }


}
