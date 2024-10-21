package day4;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Cookie implements Runnable {
    
    List<String> cookies = new ArrayList<>();

    Socket s;
    String fullPathName;

    public Cookie(Socket s, String dirPathFileName) {
        this.s = s;
        this.fullPathName = dirPathFileName;
    }
    
    public void readCookieFile(String dirPathFileName) throws IOException {
        FileReader reader = new FileReader(dirPathFileName);
        BufferedReader breader = new BufferedReader(reader);

        String cookie = "";
        while ((cookie = breader.readLine()) != null) {
            cookies.add(cookie);
        }
        breader.close();
        reader.close();
    }

    public void printCookies() {
        if (cookies.size() > 0) {
            cookies.forEach(System.out::println);
        }
    }
    public String getRandomCookie() {
        Random rand = new Random();
        if (cookies != null) {
            if (cookies.size() > 0) {
                return cookies.get(rand.nextInt(cookies.size()));
            } else {
                return "No cookie found!";
            }
        }
        return "No cookie found!";
    }

    @Override
    public void run() {
        try {
            readCookieFile(this.fullPathName);

            InputStream is;
            try {
                is = s.getInputStream();
                BufferedInputStream bis = new BufferedInputStream(is);
                DataInputStream dis = new DataInputStream(bis);
                String messageReceived = "";

                OutputStream os = s.getOutputStream();
                BufferedOutputStream bos = new BufferedOutputStream(os);
                DataOutputStream dos = new DataOutputStream(bos);

                while (!messageReceived.toLowerCase().equals("quit")) {
                    System.out.println("Waiting for client input...");

                    messageReceived = dis.readUTF(dis);

                    String retrievedCookie = getRandomCookie();

                    dos.writeUTF(retrievedCookie);
                    dos.flush();
                }
            dos.close();
            bos.close();
            os.close();

            dis.close();
            bis.close();
            is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception ex) {
            System.err.println(ex.toString());
        } finally {}
    }

}
