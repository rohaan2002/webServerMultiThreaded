import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class Client {

    public Runnable getRunnable() {
        return new Runnable() {
            @Override
            public void run() {
                int port = 8010;
                try {
                    InetAddress address = InetAddress.getByName("localhost");
                    Socket socket = new Socket(address, port);

                    try {
                        PrintWriter toSocket = new PrintWriter(socket.getOutputStream());
                        BufferedReader fromSocket = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                        toSocket.println("Here as ONE client I'm sending this to Socket " + socket.getLocalSocketAddress());
                        String line = fromSocket.readLine();
                        System.out.println("Response from the socket is: " + line);
                    } catch (IOException ex) {
//                        ex.printStackTrace();
                        return;
                    }

                } catch (IOException ex) {
                    ex.printStackTrace();
                }


            }
        };
    }

    public static void main(String[] args) {
        Client client = new Client();

        for(int i =0;  i<100; i++){
            try{
                Thread thread = new Thread(client.getRunnable());
                thread.start();
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }
}

