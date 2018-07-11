package com.kakaobank.profile.producer.component.impl;

import com.kakaobank.profile.producer.component.WriteDataToFileComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PreDestroy;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class WriteDataToFileComponentImpl implements WriteDataToFileComponent {
    private final Logger logger = LoggerFactory.getLogger(WriteDataToFileComponentImpl.class);

    private String filePath;
    private BufferedWriter out;

    public WriteDataToFileComponentImpl(String filePath) {
        this.filePath = filePath;
    }

    public void open() throws IOException {
        this.out = new BufferedWriter(getFileWriter(filePath));
    }

    @PreDestroy
    public void close() throws IOException {
        logger.info("Closing File...... [ File : " + filePath + "]");
        if(out != null)
            out.close();
    }

    public void write(String content) throws IOException {
        if(content == null || content == "")
            throw new IllegalArgumentException("Content is Empty!!");

        out.write(content + "\n");
    }

    private FileWriter getFileWriter(String filePath) throws IOException {
        File file = new File(filePath);
        if(file.exists())
            return new FileWriter(file, true);

        file.getParentFile().mkdir();
        file.createNewFile();
        return new FileWriter(file, false);
    }
}
