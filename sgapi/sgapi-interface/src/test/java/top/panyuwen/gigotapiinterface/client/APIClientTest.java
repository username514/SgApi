package com.sg.sgapiinterface.client;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.sg.sgapiclientsdk.client.SgApiClient;
import com.sg.sgapiclientsdk.model.entity.User;

@SpringBootTest
class APIClientTest {

    @Autowired
    private SgApiClient sgApiClient;

    @Test
    void APIClientTest(){
        User user = new User();
        user.setName("潘誉文");

    }
}