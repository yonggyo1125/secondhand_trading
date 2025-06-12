package org.koreait.restaurant;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.List;

@SpringBootTest
public class DataTransfer {
    @Test
    void process() throws Exception {
        CsvMapper mapper = new CsvMapper();

        try (FileInputStream fis = new FileInputStream("C:/restaurant/data.csv");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr)) {
            int cnt = 0;
            String line = null;
            while((line = br.readLine()) != null) {
                List<String> item = mapper.readValue(line, new TypeReference<>() {});

                String operation = item.get(8);
                String zipcode = item.get(17);
                String address = item.get(18);
                String roadAddress1 = item.get(19);
                String roadAddress2 = item.get(20);
                String zonecode = item.get(21);
                String restaurant = item.get(22);
                String category = item.get(26);
                String loc1 = item.get(27);
                String loc2 = item.get(28);

                if (cnt > 10) break;
                cnt++;
            }
        }
    }
}
