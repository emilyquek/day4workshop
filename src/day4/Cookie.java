package day4;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Cookie {
    
    List<String> cookies = new ArrayList<>();
    
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
            }
        }
        return "No cookie found!";
    }

}
