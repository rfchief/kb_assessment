package com.kakaobank.profile.producer.component;

import java.io.IOException;

public interface WriteDataToFileComponent {
    void open() throws IOException;

    void close() throws IOException;

    void write(String content) throws IOException;
}
