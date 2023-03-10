package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        Socket socket;
        {
            try {
                // Подключаемся к серверу
                socket = new Socket("127.0.0.1", 9178);
                // Поток вывода (для отправки сообщения серверу)
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                DataInputStream in = new DataInputStream(socket.getInputStream());
                Scanner scanner = new Scanner(System.in);
                Thread thread=new Thread(new Runnable() {
                   @Override
                   public void run() {
                       try {
                           while (true){
                               System.out.println(in.readUTF());
                           }
                       } catch (IOException e) {
                           throw new RuntimeException(e);
                       }
                   }
               });
                thread.start();
                while (true) {
                    String message = scanner.nextLine();
                    out.writeUTF(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
