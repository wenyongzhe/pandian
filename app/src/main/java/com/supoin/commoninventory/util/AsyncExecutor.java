package com.supoin.commoninventory.util;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.*;

/**
 * �첽ִ��
 *
 * @author zhangfan
 */
public class AsyncExecutor {
	//��ȡ��ǰ������
    private static final String TAG = AsyncExecutor.class.getSimpleName();
   
    private static ExecutorService threadPool;
   //��ʼ��handler����,Ĭ��ʹ�����̼߳�UI�̵߳�looper����
    public static Handler handler = new Handler(Looper.getMainLooper());

    public AsyncExecutor() {
        this(null);
    }

    public AsyncExecutor(ExecutorService threadPool) {
        if (AsyncExecutor.threadPool != null) {
            shutdownNow();
        }
        if (threadPool == null) {
            AsyncExecutor.threadPool = Executors.newCachedThreadPool();
        } else {
            AsyncExecutor.threadPool = threadPool;
        }
    }

    public static synchronized void shutdownNow() {
        if (threadPool != null && !threadPool.isShutdown()) threadPool.shutdownNow();
        threadPool = null;
    }

    /**
     * ������Ͷ���̳߳�ִ��
     *
     * @param worker
     * @return
     */
    public <T> FutureTask<T> execute(final Worker<T> worker) {
        Callable<T> call = new Callable<T>() {
            @Override
            public T call() throws Exception {
                return postResult(worker, worker.doInBackground());
            }
        };
        FutureTask<T> task = new FutureTask<T>(call) {
            @Override
            protected void done() {
                try {
                    get();
                } catch (InterruptedException e) {
                    LogUtil.e(TAG, e);
                    worker.abort();
                    postCancel(worker);
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    LogUtil.e(TAG, e.getMessage());
                    e.printStackTrace();
                    throw new RuntimeException("An error occured while executing doInBackground()", e.getCause());
                } catch (CancellationException e) {
                    worker.abort();
                    postCancel(worker);
                    LogUtil.e(TAG, e);
                    e.printStackTrace();
                }
            }
        };
        threadPool.execute(task);
        return task;
    }

    /**
     * �����߳̽�����ݵ�UI�߳�
     *
     * @param worker
     * @param result
     * @return
     */
    private <T> T postResult(final Worker<T> worker, final T result) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                worker.onPostExecute(result);
            }
        });
        return result;
    }

    /**
     * �����߳̽�����ݵ�UI�߳�
     *
     * @param worker
     * @return
     */
    private void postCancel(final Worker worker) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                worker.onCanceled();
            }
        });
    }

    public <T> FutureTask<T> execute(Callable<T> call) {
        FutureTask<T> task = new FutureTask<T>(call);
        threadPool.execute(task);
        return task;
    }

    public static abstract class Worker<T> {
        protected abstract T doInBackground();

        protected void onPostExecute(T data) {}

        protected void onCanceled() {}

        protected void abort() {}
    }
}
