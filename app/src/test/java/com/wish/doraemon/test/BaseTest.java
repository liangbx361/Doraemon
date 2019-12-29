package com.wish.doraemon.test;

import com.wish.doraemon.DoraemonApplication;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {DoraemonApplication.class})
public class BaseTest {

}
