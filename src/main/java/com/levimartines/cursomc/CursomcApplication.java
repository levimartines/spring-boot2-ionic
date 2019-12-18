package com.levimartines.cursomc;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@SpringBootApplication
public class CursomcApplication {

    public static void main(String[] args) {
        SpringApplication.run(CursomcApplication.class, args);
    }
}
