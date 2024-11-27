package com.projet.reseau.udp;
import java.io.Console;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPClient {
    private static final int DEFAULT_PORT = 8080;
    private String serverAddress;
    private int port;

    public UDPClient(String serverAddress, int port) {
        this.serverAddress = serverAddress;
        this.port = port;
    }

    public void sendMessage(String message) {
        try (DatagramSocket socket = new DatagramSocket()) {
            //converting message to bytes using UTF-8 encoding
            byte[] buffer = message.getBytes("UTF-8");
            //creating packet with message, server, address and port
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(serverAddress), port);
            socket.send(packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String serverAddress = "localhost";
        int portToUse = DEFAULT_PORT;

        if (args.length > 0) {
            serverAddress = args[0];
        }
        if (args.length > 1) {
            portToUse = Integer.parseInt(args[1]);
        }

        UDPClient client = new UDPClient(serverAddress, portToUse);
        //getting console for user input
        Console console = System.console();

        if (console == null) {
            System.err.println("no console available.");
            return;
        }

        //loop to send and read messages from the console
        while (true) {
            String message = console.readLine("enter your message (or 'exit' to leave) : ");
            if ("exit".equalsIgnoreCase(message)) {
                break;
            }
            client.sendMessage(message);
        }
    }
}
