package test;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class MyServer {

    int port;
    ClientHandler ch;
    volatile boolean stop;

    MyServer(int port, ClientHandler ch)
    {
        this.port = port;
        this.ch = ch;
        stop = false;
    }

    void start()
    {
        new Thread(()->runServer()).start();
    }

    private void runServer()
    {
        ServerSocket server = null;
        try
        {
            server = new ServerSocket(port);
            server.setSoTimeout(1000);

            while(!stop)
            {
                try
                {
                    Socket client = server.accept();
                    try
                    {
                        ch.handleClient(client.getInputStream(), client.getOutputStream());

                        //client.getInputStream().close();
                        //client.getOutputStream().close();
                        client.close();

                    }
                    catch (IOException e)
                    {
                    }
                }
                catch (SocketTimeoutException e)
                {
                }
            }

            server.close();
        } catch(Exception e)
        {
        }
    }

    void close()
    {
        stop = true;
    }
}