package com.example.batchexample.batch;

import com.example.batchexample.model.User;
import com.example.batchexample.repository.UserRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DBWriter implements ItemWriter<User>{

    @Autowired
    private UserRepository userRepository;

    @Override
    public void write(List<? extends User> users) throws Exception {
        System.out.println("Data Saved for Users: " + users);
        //userRepository.save(users);

        //probe radi, uzimam ime jednog od usera iz csv fajla, i smestam ga u String
            //String nameOfUserFromCsvFile = users.get(2).getName();

        //User user = userRepository.findByName("Peter");
        //trazim usera iz baze sa imenom koji sam dohvatio iz csv fajla
            //User user = userRepository.findByName(nameOfUserFromCsvFile);
        //setujem mu ime radi update-a
            //user.setName("Johan");
            //userRepository.save(user);
        userRepository.saveAll(users);
    }
}
