package com.example.batchexample.batch;

import com.example.batchexample.model.User;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class Processor implements ItemProcessor<User,User>{
    //ima kao ulaz red iz fajla, uzima ga i kao izlaz mora isti taj red da da... ja tu mogu da uzmem npr email da ga promenim i u izlaznom redu da stoji promenjen mail
    //ono sto udje u procesor mora i da izadje

    //za izlazni fajl nova klasa umesto DBWritter klase koja nasledjuje FileWritter
    //ako menjam kolone moracu u procesor klasi

    /* static final Map<String, String> DEPT_NAMES =
            new HashMap<>();

    public Processor() {
        DEPT_NAMES.put("001", "Technology");
        DEPT_NAMES.put("002", "Operations");
        DEPT_NAMES.put("003", "Accounts");
    }*/

    @Override
    public User process(User user) throws Exception {
        //String deptCode = user.getEmail();
        //String dept = DEPT_NAMES.get(deptCode);
        //user.setEmail(dept);
        return user;
    }
}
