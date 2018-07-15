package com.kakaobank.profile.consumer.service;

import com.kakaobank.profile.consumer.service.impl.WriteAccountLogServiceImpl;
import org.junit.Before;

public class WriteAccountLogServiceTest {


    private WriteAccountLogService service;

    @Before
    public void setup() {
        this.service = new WriteAccountLogServiceImpl();
    }
}
