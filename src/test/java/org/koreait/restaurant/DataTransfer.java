package org.koreait.restaurant;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

@SpringBootTest
public class DataTransfer {
    @Test
    void process() throws Exception {
        try (FileInputStream fis = new FileInputStream("C:/restaurant/data.csv");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr)) {
            String line = null;
            while((line = br.readLine()) != null) {
                System.out.println(line);
            }
        }
    }
}
