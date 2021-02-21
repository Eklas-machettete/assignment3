package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.controller.HelloController;

@SpringBootTest
public class ControllerTest {

	@Autowired
	HelloController helloControler;
	@Test
	public void checkController() throws Exception {
		assertThat(helloControler).isNotNull();
		
	}

}
