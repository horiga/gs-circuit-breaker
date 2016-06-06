package hello;

import java.net.URI;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;
import org.springframework.stereotype.Service;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
@Slf4j
public class BookService {

	@HystrixCommand(fallbackMethod = "reliable", commandKey = "bookservice")
	public String readingList() {
		try {
			RestTemplate restTemplate = new RestTemplate();
			URI uri = URI.create("http://localhost:8090/recommended");
			final String reading = restTemplate.getForObject(uri, String.class);
			log.info("(Success in reading list)");
			return reading;
		} catch (Throwable e) {
			log.error("<<Failed>> Reading lists in Book service. cause:{}", e.getMessage());
			throw e;
		}
	}

	public String reliable() {
		log.error("(In fallback method)");
		return "Cloud Native Java (O'Reilly)";
	}

}
