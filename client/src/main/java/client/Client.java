package client;

import listening.Request;
import listening.Response;

//import javax.xml.bind.JAXBException;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.util.Locale;
import java.util.Optional;
import java.util.Scanner;

public class Client {

    private final int PORT = 9007;
    private final int BUFFER_SIZE = 1048576;
    private Socket socket;
    private InetAddress address;
    private InputStream inputStream;
    private OutputStream outputStream;
    private static Client client;

    public Client() {

        this.socket = new Socket();
    }

    public void setClient(final Client client) {
        Client.client = client;
    }

    public static Client getClient() {
        return Client.client;
    }

    public boolean connect() {
        try {
            address = InetAddress.getLoopbackAddress();
            socket = new Socket(address, PORT);
            inputStream = socket.getInputStream();
            this.outputStream = socket.getOutputStream();

            System.out.println("Успешно подключились к серверу.");
            return true;
        } catch (IOException e) {
            System.out.println("Ошибка создания сокета. Сервер не может начать работу.");
            return false;
        }

    }

    public boolean reconnect(){
        int number = 1;

        while (!connect()) {
            System.out.println("Переподключение к серверу...");
            System.out.println("Попытка № :" + number);
            if (number % 3 == 0) {
                System.out.println("Продолжить подключение? Введите 'n', чтобы прекратить или любую клавишу, чтобы продолжить.");
                System.out.print(">");
                Scanner input = new Scanner(System.in);
                String choose = input.nextLine().trim().toLowerCase(Locale.ROOT);
                if (choose.equals("n")) {
                    return false;
                }
            }
            number++;
        }
        return true;
    }

    public boolean isConnected() {
        return socket.isConnected();
    }

    public void sendRequest(Request request){
        try{
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(request);

            byte[] sendArray = byteArrayOutputStream.toByteArray();
            socket.getOutputStream().write(sendArray);
        } catch (SocketException e){
            System.out.println("Сервер недоступен.");
//            reconnect();
        } catch (IOException e) {
            System.out.println("Невозможно создать запрос.");;
        }
    }

    public Optional<Response> getResponse() {

        final ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
        try {
            inputStream.read(buffer.array());
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(buffer.array());
            ObjectInputStream input = new ObjectInputStream(byteArrayInputStream);

            return Optional.of((Response)input.readObject());
        }
        catch (IOException e) {
            System.out.println("Ошибка получения данных с сервера.");
            return Optional.empty();
        } catch (ClassNotFoundException e) {
            System.out.println("Некорректные данные с сервера.");
            return Optional.empty();
        } catch (NullPointerException e){
            return Optional.empty();
        }
    }
}
