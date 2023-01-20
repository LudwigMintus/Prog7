package server;

import listening.Request;
import listening.Response;
import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RecursiveAction;

public class Server {

    private static final int BUFFER_SIZE = 1024 * 1024;
    private Selector selector;
    private ServerSocketChannel serverSocketChannel;


    public Server() {
        try{
            System.out.println("Сервер начал свою работу.");
            int PORT = 9007;
            serverSocketChannel = ServerSocketChannel.open();
            selector = Selector.open();
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.socket().bind(new InetSocketAddress(PORT));
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (ClosedChannelException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Selector getSelector() {
        return selector;
    }


    public void register() {
        try {
            SocketChannel client = serverSocketChannel.accept();
            client.configureBlocking(false);
            client.register(selector, SelectionKey.OP_READ);
            System.out.println("Канал зарегистрирован. ");
        }
        catch (IOException e) {
            System.out.println("Ошибка в регистрации канала.");
        }
    }

    public void sendResponse(Response response, SelectionKey key){
        ExecutorService fixedPool = Executors.newCachedThreadPool();
        fixedPool.execute(() -> {
            SocketChannel socketChannel = (SocketChannel) key.channel();
            try {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
                objectOutputStream.writeObject(response);
                socketChannel.write(ByteBuffer.wrap((byteArrayOutputStream.toByteArray())));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public Request readRequest(SelectionKey key) {
        Request request = null;
        SocketChannel socketChannel = (SocketChannel) key.channel();
        ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
        try {
            socketChannel.read(buffer);
            ByteArrayInputStream bais = new ByteArrayInputStream(buffer.array());
            ObjectInputStream input = new ObjectInputStream(bais);
            request = (Request) input.readObject();
        }
        catch (IOException e) {
            key.cancel();
        }
        catch (ClassNotFoundException e) {
            System.out.println("Ошибка получения запроса от клиента.");
        }
        return request;
    }

}