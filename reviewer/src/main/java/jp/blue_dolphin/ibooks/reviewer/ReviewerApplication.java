package jp.blue_dolphin.ibooks.reviewer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"jp.blue_dolphin.ibooks.common",
		"jp.blue_dolphin.ibooks.reviewer"})
public class ReviewerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReviewerApplication.class, args);
	}

}
