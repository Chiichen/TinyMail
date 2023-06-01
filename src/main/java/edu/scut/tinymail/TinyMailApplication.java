package edu.scut.tinymail;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("edu.scut.tinymail.mapper")
@SpringBootApplication
public class TinyMailApplication {

    public static void main(String[] args) {
        SpringApplication.run(TinyMailApplication.class, args);
    }

}
