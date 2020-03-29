package com.doraemon.test;

import com.doraemon.app.DoraemonApplication;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {DoraemonApplication.class})
public class BaseTest {

}
