package hello;

import com.netflix.hystrix.HystrixInvokable;
import com.netflix.hystrix.exception.HystrixRuntimeException;
import com.netflix.hystrix.strategy.HystrixPlugins;
import com.netflix.hystrix.strategy.executionhook.HystrixCommandExecutionHook;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;

import javax.annotation.PostConstruct;

@EnableCircuitBreaker
@RestController
@SpringBootApplication
@Slf4j
public class ReadingApplication {

	@Autowired
	private BookService bookService;

	@PostConstruct
	public void setupHystrixPlugins() {
		HystrixPlugins.getInstance().registerCommandExecutionHook(new MyHystrixCommandExecutionHook());
	}

	@RequestMapping("/to-read")
	public String toRead() {
		log.info("[start] /to-read");
		try {
			return bookService.readingList();
		} finally {
			log.info("[end] /to-read");
		}
	}

	public static void main(String[] args) {
		SpringApplication.run(ReadingApplication.class, args);
	}

	private class MyHystrixCommandExecutionHook extends HystrixCommandExecutionHook {
		public <T> void onStart(HystrixInvokable<T> commandInstance) {
			log.info("[onStart]");
			super.onStart(commandInstance);
		}

		public <T> T onEmit(HystrixInvokable<T> commandInstance, T value) {
			log.info("[onEmit] value:{}", value);
			return super.onEmit(commandInstance, value);
		}

		public <T> Exception onError(HystrixInvokable<T> commandInstance,
				HystrixRuntimeException.FailureType failureType, Exception e) {
			log.info("[onError] failureType:{}, message:{}", failureType.name(), e.getMessage());
			return super.onError(commandInstance, failureType, e);
		}

		public <T> void onSuccess(HystrixInvokable<T> commandInstance) {
			log.info("[onSuccess]");
			super.onSuccess(commandInstance);
		}

		public <T> void onThreadStart(HystrixInvokable<T> commandInstance) {
			log.info("[onThreadStart]");
			super.onThreadStart(commandInstance);
		}

		public <T> void onThreadComplete(HystrixInvokable<T> commandInstance) {
			log.info("[onThreadComplete]");
			super.onThreadComplete(commandInstance);
		}

		public <T> void onExecutionStart(HystrixInvokable<T> commandInstance) {
			log.info("[onExecutionStart]");
			super.onExecutionStart(commandInstance);
		}

		public <T> T onExecutionEmit(HystrixInvokable<T> commandInstance, T value) {
			log.info("[onExecutionEmit] value:{}", value);
			return super.onExecutionEmit(commandInstance, value);
		}

		public <T> Exception onExecutionError(HystrixInvokable<T> commandInstance, Exception e) {
			log.info("[onExecutionError] message:{}", e.getMessage());
			return super.onExecutionError(commandInstance, e);
		}

		public <T> void onExecutionSuccess(HystrixInvokable<T> commandInstance) {
			log.info("[onExecutionSuccess]");
			super.onExecutionSuccess(commandInstance);
		}

		public <T> void onFallbackStart(HystrixInvokable<T> commandInstance) {
			log.info("[onFallbackStart]");
			super.onFallbackStart(commandInstance);
		}

		public <T> T onFallbackEmit(HystrixInvokable<T> commandInstance, T value) {
			log.info("[onFallbackEmit] value:{}", value);
			return super.onFallbackEmit(commandInstance, value);
		}

		public <T> Exception onFallbackError(HystrixInvokable<T> commandInstance, Exception e) {
			log.info("[onFallbackError] message:{}", e.getMessage());
			return super.onFallbackError(commandInstance, e);
		}

		public <T> void onFallbackSuccess(HystrixInvokable<T> commandInstance) {
			log.info("[onFallbackSuccess]");
			super.onFallbackSuccess(commandInstance);
		}

		public <T> void onCacheHit(HystrixInvokable<T> commandInstance) {
			log.info("[onCacheHit]");
			super.onCacheHit(commandInstance);
		}
	}
}
