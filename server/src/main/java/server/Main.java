package server;

import listening.Request;
import listening.Response;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.util.Iterator;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class Main {

    private static final ExecutorService executorService = Executors.newFixedThreadPool(1);
    private static final ServerReceiver serverReceiver = new ServerReceiver();

    private static final int SLEEP_TIME = 300;
    private static final ServerInvoker serverInvoker = new ServerInvoker(serverReceiver);

    public static void main(String[] args) throws IOException {


        Server server = new Server();

        serverReceiver.initCollection();

        while (true) {

            if (System.in.available() > 0) {
                String servcomment;
                try {
                    servcomment = (new Scanner(System.in)).nextLine();
                } catch (NullPointerException e) {
                    return;
                }
                if (servcomment.equals("exit")) {
                    System.out.println("Сервер завершил свою работу.");
                    System.exit(0);
                } else {
                    System.out.println("Сервер поддерживает только команду \"exit.\"");
                }

            }

            server.getSelector().select(3000);
            Set<SelectionKey> keys = server.getSelector().selectedKeys();
            Iterator iterator = keys.iterator();
            while (iterator.hasNext()) {
                if (parseComment()) {
                    return;
                }
                SelectionKey key = (SelectionKey) iterator.next();
                iterator.remove();
                    if (key.isAcceptable()) {
                        server.register();
                    } else if (key.isReadable()) {
                        executorService.execute(() -> {
                            if (!key.isValid()) return;
                            Request request = server.readRequest(key);
                            if (request != null) {
                                new Thread(() -> {
                                    Optional<Response> optionalResponse = null;
                                    optionalResponse = serverInvoker.execute(request);
                                    if (optionalResponse.isPresent()) {
                                        Response response = optionalResponse.get();
                                        server.sendResponse(response, key);
                                    }
                                }).start();
                            }
                        });
                        try {
                            Thread.sleep(SLEEP_TIME);
                        } catch (Exception e) {}
                    }
            }
        }
    }

    private static void disconnectClient() {
        System.out.println("Клиент отключен.");
    }

    private static boolean parseComment() {
        try {
            String comment = "";
            if (System.in.available() > 0) {
                comment = (new Scanner(System.in)).nextLine();
            }
            return comment.equals("exit");
        } catch (IOException | NullPointerException e) {
            return false;
        }
    }
}