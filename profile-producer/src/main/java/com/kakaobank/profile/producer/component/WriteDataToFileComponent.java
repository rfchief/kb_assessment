package com.kakaobank.profile.producer.component;

import javax.annotation.PreDestroy;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class WriteDataToFileComponent {
    private String filePath;
    private BufferedWriter out;

    public WriteDataToFileComponent(String filePath) throws IOException {
        this.filePath = filePath;
        this.out = new BufferedWriter(getFileWriter(filePath));
    }

    @PreDestroy
    public void close() throws IOException {
        System.out.println("Closing File...... [ File : " + filePath + "]");
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

        file.createNewFile();
        return new FileWriter(file, false);
    }
}
