package com.kakaobank.profile.producer.component;

import com.kakaobank.profile.producer.component.impl.WriteDataToFileComponentImpl;
import com.kakaobank.profile.producer.util.TestDataFactory;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

public class WriteDataToFileComponentTest {

    private WriteDataToFileComponent component;
    private String content;
    private String filePath;

    @Before
    public void setup() throws NoSuchAlgorithmException, IOException {
        this.filePath = "logs/test.txt";
        this.component = new WriteDataToFileComponentImpl(filePath);
        this.component.open();
        this.content = TestDataFactory.getCustomer().toString();
    }

    @After
    public void destroy() throws IOException {
        component.close();
        File file = new File(filePath);
        file.delete();
    }

    @Test
    public void doNothingTest() {
        System.out.println("Everything is OK!!");
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenEmptyContent_whenWrite_thenThrowsIllegalArgumentExceptionTest() throws IOException {
        //given
        String emptyContent = null;

        //when
        component.write(emptyContent);

        //then
        List<String> actual = readActualContent();
        Assert.assertThat(actual, is(notNullValue()));
    }

    @Test
    public void givenFilePathAndContent_whenWrite_thenExistLoggingFileTest() throws IOException {
        //when
        component.write(content);

        //then
        List<String> actual = readActualContent();
        for (String actualContent : actual)
            Assert.assertThat(actualContent, is(content));
    }

    private List<String> readActualContent() throws IOException {
        BufferedReader in = new BufferedReader(new FileReader(filePath));
        List<String> actual = new ArrayList<String>();
        String line = "";

        while ((line = in.readLine()) != null)
            actual.add(line);

        in.close();
        return actual;
    }
}
