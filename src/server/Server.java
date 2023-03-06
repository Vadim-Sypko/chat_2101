package server;

import client.Client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.Scanner;

public class Server {
    public static void main(String[] args) {
        ArrayList<Socket> sockets= new ArrayList<>();
        try {
            ServerSocket serverSocket = new ServerSocket(9178);
            System.out.println("Сервер запущен");
            while (true){
                // Ждём клиента и сохраняем его в socket
                Socket socket = serverSocket.accept();
                System.out.println("Клиент подключился");
                sockets.add(socket);
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            //Поток ввода (для получения сообщения от клиента)
                            DataInputStream in = new DataInputStream(socket.getInputStream());
                            while (true){
                                String clientMessage = in.readUTF();
                                System.out.println(clientMessage);
                                for (int i = 0; i < sockets.size(); i++) {
                                    Socket socket1= sockets.get(i);
                                    //Поток ввода (для получения сообщения от клиента)
                                    DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                                    // Сервер отправляет сообщение
                                    out.writeUTF(clientMessage.toUpperCase());
                                }
                            }
                        }catch (IOException e){
                            System.out.println("Потеряно соединение с клиентом");
                        }

                    }
                });
                thread.start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
