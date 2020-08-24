package com.example.batchexample.UserCsvToCsv;

import com.example.batchexample.model.User;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CsvWriter implements ItemWriter<User> {


    @Override
    public void write(List<? extends User> users) throws Exception {
        users.size();
    }
}
