import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Consumer;

public class Server {
    public Consumer<Socket> getConsumer() {
        return (clientSocket)  -> {
            try{
                PrintWriter toClient = new PrintWriter(clientSocket.getOutputStream());
                toClient.println("Hi I am server sending this!");
                toClient.close();
                clientSocket.close();
            } catch(IOException ex){
                ex.printStackTrace();
            }
        };
    }

    public static void main(String[] args) {
        int port = 8010;
        Server server = new Server();

        try{
            ServerSocket serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(10000);
            System.out.println("Server is listening on port: "+ port);

            while(true){
                Socket acceptedSocket = serverSocket.accept();
                Thread thread = new Thread(()-> server.getConsumer().accept(acceptedSocket));
                //why a direct void function couldnt've been used which
                // takes a clientSocket and performs certain sideeffects?
                // why did we need server.getConsumer when server wasn't used
                // in the accept function defined above as lambda?

//                answer of both is : its not necessary, its a design choice
//                so to get me familiar with functional interfaces like 'consumer'
                thread.start();
            }
        } catch (IOException ex){
            ex.printStackTrace();
        }
    }


}
