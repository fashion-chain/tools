package org.fok.tools.thread;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Provides;
import org.fok.tools.thread.exception.ThreadPoolAlreadyExistsException;
import org.fok.tools.thread.exception.ThreadPoolNotExistsException;

import onight.osgi.annotation.NActorProvider;
import onight.tfw.ntrans.api.ActorService;

@NActorProvider
@Provides(specifications = { ActorService.class }, strategy = "SINGLETON")
@Instantiate(name = "fok_thread_manager")
public class ThreadExecutorManager implements ActorService {
	private ConcurrentHashMap<String, ThreadExecutor> executorMap = new ConcurrentHashMap<>();

	public ExecutorService get() throws ThreadPoolNotExistsException {
		String name = Thread.currentThread().getName();
		if (!executorMap.containsKey(name)) {
			throw new ThreadPoolNotExistsException("没有找到名称为 " + name + " 的线程池");
		} else {
			return executorMap.get(name).getExecutorService();
		}
	}

	public String statistics() {
		StringBuilder builder = new StringBuilder();
		for (String key : this.executorMap.keySet()) {
			ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) executorMap.get(key).getExecutorService();
			builder.append(key);
			builder.append("alive:" + String.format("%-12s", threadPoolExecutor.getKeepAliveTime(TimeUnit.SECONDS))
					+ "pool:" + String.format("%-10s", threadPoolExecutor.getPoolSize()) + " task:"
					+ String.format("%-10s", threadPoolExecutor.getTaskCount()) + " queue:"
					+ String.format("%-10s", threadPoolExecutor.getQueue().size()));
		}
		return builder.toString();
	}

	public void createScheduleThreadPool(int corePoolSize) throws ThreadPoolAlreadyExistsException {
		String name = Thread.currentThread().getName();
		check(name);
		ExecutorService svc = Executors.newScheduledThreadPool(corePoolSize);
		ThreadExecutor oThreadExecutor = new ThreadExecutor(Thread.currentThread().getName(), corePoolSize, svc);
		executorMap.put(name, oThreadExecutor);
	}

	public void createFixedThreadPool(int nThreads) throws ThreadPoolAlreadyExistsException {
		String name = Thread.currentThread().getName();
		check(name);
		ExecutorService svc = Executors.newFixedThreadPool(nThreads);
		ThreadExecutor oThreadExecutor = new ThreadExecutor(Thread.currentThread().getName(), nThreads, svc);
		executorMap.put(name, oThreadExecutor);
	}

	public void createForkJoinPool(int size) throws ThreadPoolAlreadyExistsException {
		String name = Thread.currentThread().getName();
		check(name);
		ExecutorService svc = new ForkJoinPool(size);
		ThreadExecutor oThreadExecutor = new ThreadExecutor(Thread.currentThread().getName(), size, svc);
		executorMap.put(name, oThreadExecutor);
	}

	private void check(String name) throws ThreadPoolAlreadyExistsException {
		if (executorMap.containsKey(name)) {
			// 如果已经创建了对象的线程池，抛出异常
			throw new ThreadPoolAlreadyExistsException(
					"线程[" + name + "]已经创建了类型为" + executorMap.get(name).getExecutorType() + "的线程池");
		}
	}
}
