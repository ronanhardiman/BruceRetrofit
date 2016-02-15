package net.iyouqu.bruceretrofit.task;

/**
 * Created by frodo on 2015/7/6.
 */
public interface BackgroundExecutor {
    <R> void execute(NetworkCallTask<R> runnable);

    <R> void execute(BackgroundCallTask<R> runnable);
}
