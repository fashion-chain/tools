package org.fok.tools.thread;

import java.util.concurrent.ExecutorService;

import lombok.Data;
import lombok.Setter;

@Data
public class ThreadExecutor {
	@Setter(lombok.AccessLevel.PRIVATE)
	private String name;
	@Setter(lombok.AccessLevel.PRIVATE)
	private int poolSize;
	@Setter(lombok.AccessLevel.PRIVATE)
	private ExecutorService executorService = null;

	public ThreadExecutor(String name, int poolSize, ExecutorService executorService) {
		this.executorService = executorService;
		this.name = name;
		this.poolSize = poolSize;
	}

	public String getExecutorType() {
		if (executorService == null) {
			return "null";
		} else {
			return executorService.getClass().getName();
		}
	}
}
