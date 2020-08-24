package com.example.batchexample.UserCsvToCsv;

import com.example.batchexample.model.User;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class CsvToCsvProcessor implements ItemProcessor<User, User> {


    //@Nullable
    @Override
    public User process(User user) throws Exception {
        user.setSalary(5000);

        return user;
    }
}


//tasklet je drugi nacin u odnosu na chunk
//job moze da se sastoji iz nekoliko taskleta
//config, ne treba procesor i rider
//trebace mi klase koje implementiraju tasklet