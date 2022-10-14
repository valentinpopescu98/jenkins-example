package com.valentinpopescu98.testing;

import com.valentinpopescu98.testing.student.StudentController;
import com.valentinpopescu98.testing.student.StudentRepository;
import com.valentinpopescu98.testing.student.StudentService;
import com.valentinpopescu98.testing.template_resources.IndexController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class WebApplicationTests {
	@Autowired
	private StudentController studentController;
	@Autowired
	private IndexController indexController;
	@Autowired
	private StudentService studentService;
	@Autowired
	private StudentRepository studentRepository;

	@Test
	void contextLoads() {
		assertThat(studentController).isNotNull();
		assertThat(indexController).isNotNull();
		assertThat(studentService).isNotNull();
		assertThat(studentRepository).isNotNull();
	}

	@Test
	void shouldTestMain() {
		WebApplication.main(new String[] {});
	}
}
