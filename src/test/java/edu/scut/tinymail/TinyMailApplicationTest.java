package edu.scut.tinymail;

import edu.scut.tinymail.domain.entry.Userauth;
import edu.scut.tinymail.service.UserauthService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TinyMailApplicationTest {

    @Autowired
    UserauthService userauthService;

    @Test
    public void context() {
        System.out.println(userauthService.register(new Userauth("ch", "asd", "123")).getMsg());
    }
}
