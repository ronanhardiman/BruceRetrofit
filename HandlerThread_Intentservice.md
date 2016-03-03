# http://www.tuicool.com/articles/fe2qEvv
2016.03.03
```java

/**
 * Handy class for starting a new thread that has a looper. The looper can then be
 * used to create handler classes. Note that start() must still be called.
 */
public class HandlerThread extends Thread {
    int mPriority;
    int mTid = -1;
    Looper mLooper;

    public HandlerThread(String name) {
        super(name);
        mPriority = Process.THREAD_PRIORITY_DEFAULT;
    }

    /**
     * Constructs a HandlerThread.
     * @param name
     * @param priority The priority to run the thread at. The value supplied must be from
     * {@link android.os.Process} and not from java.lang.Thread.
     */
    public HandlerThread(String name, int priority) {
        super(name);
        mPriority = priority;
    }

    /**
     * Call back method that can be explicitly overridden if needed to execute some
     * setup before Looper loops.
     */
    protected void onLooperPrepared() {
    }

    @Override
    public void run() {
        mTid = Process.myTid();
        Looper.prepare();
        synchronized (this) {
            mLooper = Looper.myLooper();
            //一旦准备好Looper，就立马通知被阻塞的线程，防止有别的线程因为调用自身的getLooper()方法而阻塞。
            notifyAll();
        }
        Process.setThreadPriority(mPriority);
        onLooperPrepared();
        Looper.loop();
        mTid = -1;
    }

    /**
     * This method returns the Looper associated with this thread. If this thread not been started
     * or for any reason is isAlive() returns false, this method will return null. If this thread
     * has been started, this method will block until the looper has been initialized.
     * @return The looper.
     */
    public Looper getLooper() {
        if (!isAlive()) {
            return null;
        }

        // If the thread has been started, wait until the looper has been created.
        // getLooper()的时候，线程可能已经启动，但是还没有准备好Looper
        synchronized (this) {
            while (isAlive() && mLooper == null) {
                try {
                    wait();
                } catch (InterruptedException e) {
                }
            }
        }
        return mLooper;
    }

    /**
     * Quits the handler thread's looper.
     * <p>
     * Causes the handler thread's looper to terminate without processing any
     * more messages in the message queue.
     * </p><p>
     * Any attempt to post messages to the queue after the looper is asked to quit will fail.
     * For example, the {@link Handler#sendMessage(Message)} method will return false.
     * </p><p class="note">
     * Using this method may be unsafe because some messages may not be delivered
     * before the looper terminates.  Consider using {@link #quitSafely} instead to ensure
     * that all pending work is completed in an orderly manner.
     * </p>
     *
     * @return True if the looper looper has been asked to quit or false if the
     * thread had not yet started running.
     *
     * @see #quitSafely
     */
    public boolean quit() {
        Looper looper = getLooper();
        if (looper != null) {
            looper.quit();
            return true;
        }
        return false;
    }

    /**
     * Quits the handler thread's looper safely.
     * <p>
     * Causes the handler thread's looper to terminate as soon as all remaining messages
     * in the message queue that are already due to be delivered have been handled.
     * Pending delayed messages with due times in the future will not be delivered.
     * </p><p>
     * Any attempt to post messages to the queue after the looper is asked to quit will fail.
     * For example, the {@link Handler#sendMessage(Message)} method will return false.
     * </p><p>
     * If the thread has not been started or has finished (that is if
     * {@link #getLooper} returns null), then false is returned.
     * Otherwise the looper is asked to quit and true is returned.
     * </p>
     *
     * @return True if the looper looper has been asked to quit or false if the
     * thread had not yet started running.
     */
    public boolean quitSafely() {
        Looper looper = getLooper();
        if (looper != null) {
            looper.quitSafely();
            return true;
        }
        return false;
    }

    /**
     * Returns the identifier of this thread. See Process.myTid().
     */
    public int getThreadId() {
        return mTid;
    }
}

```
##
Service本身是运行在主线程中的（详见：【Android】Service），而主线程中是不适合进行耗时任务的，因而官方文档叮嘱我们一定要在Service中另开线程进行耗时任务处理。
IntentService正是为这个目的而诞生的一个优雅设计，让程序员不用再管理线程的开启和允许.
介绍HandlerThread，一方面是因为IntentService的实现中使用到了HandlerThread，
另一方面是因为IntentService和HandlerThread以及很多Android中的类一样，
其实都是为了方便某个目的，对最基本的类进行的一定的扩充，并且结构精巧，便于使用，很适合阅读研究。

那么为什么需要一个Thread带上looper呢？如果想要了解，可以阅读：Android Handler机制，想要深入了解，
则可以阅读 【Android】Handler、Looper源码分析 。
简而言之，一个类具有的Looper，就可以接受并且处理消息了。当我们不用HandlerThread而直接使用Thread去实现这样一个功能的时候，
需要如下代码：
//
至于其中的Looper.prepare()和Looper.loop()方法起什么作用，读完 【Android】Handler、Looper源码分析 应该一目了然。
OK，很明显，这样创建一个戴Handler的Thread有很多重复的地方，那么怎么复用这些代码，让程序员可以直接而简单的创建呢？
我们来看HandlerThread的实现。

# IntentService

/**
 * IntentService is a base class for {@link Service}s that handle asynchronous
 * requests (expressed as {@link Intent}s) on demand.  Clients send requests
 * through {@link android.content.Context#startService(Intent)} calls; the
 * service is started as needed, handles each Intent in turn using a worker
 * thread, and stops itself when it runs out of work.
 *
 * <p>This "work queue processor" pattern is commonly used to offload tasks
 * from an application's main thread.  The IntentService class exists to
 * simplify this pattern and take care of the mechanics.  To use it, extend
 * IntentService and implement {@link #onHandleIntent(Intent)}.  IntentService
 * will receive the Intents, launch a worker thread, and stop the service as
 * appropriate.
 *
 * <p>All requests are handled on a single worker thread -- they may take as
 * long as necessary (and will not block the application's main loop), but
 * only one request will be processed at a time.
 *
 * <div class="special reference">
 * <h3>Developer Guides</h3>
 * <p>For a detailed discussion about how to create services, read the
 * <a href="{@docRoot}guide/topics/fundamentals/services.html">Services</a> developer guide.</p>
 * </div>
 *
 * @see android.os.AsyncTask
 */
public abstract class IntentService extends Service {
    private volatile Looper mServiceLooper;
    private volatile ServiceHandler mServiceHandler;
    private String mName;
    private boolean mRedelivery;

    private final class ServiceHandler extends Handler {
        public ServiceHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            onHandleIntent((Intent)msg.obj);
            stopSelf(msg.arg1);
        }
    }

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public IntentService(String name) {
        super();
        mName = name;
    }

    /**
     * Sets intent redelivery preferences.  Usually called from the constructor
     * with your preferred semantics.
     *
     * <p>If enabled is true,
     * {@link #onStartCommand(Intent, int, int)} will return
     * {@link Service#START_REDELIVER_INTENT}, so if this process dies before
     * {@link #onHandleIntent(Intent)} returns, the process will be restarted
     * and the intent redelivered.  If multiple Intents have been sent, only
     * the most recent one is guaranteed to be redelivered.
     *
     * <p>If enabled is false (the default),
     * {@link #onStartCommand(Intent, int, int)} will return
     * {@link Service#START_NOT_STICKY}, and if the process dies, the Intent
     * dies along with it.
     */
    public void setIntentRedelivery(boolean enabled) {
        mRedelivery = enabled;
    }

    @Override
    public void onCreate() {
        // TODO: It would be nice to have an option to hold a partial wakelock
        // during processing, and to have a static startService(Context, Intent)
        // method that would launch the service & hand off a wakelock.

        super.onCreate();
        HandlerThread thread = new HandlerThread("IntentService[" + mName + "]");
        thread.start();

        mServiceLooper = thread.getLooper();
        mServiceHandler = new ServiceHandler(mServiceLooper);
    }

    @Override
    public void onStart(Intent intent, int startId) {
        Message msg = mServiceHandler.obtainMessage();
        msg.arg1 = startId;
        msg.obj = intent;
        mServiceHandler.sendMessage(msg);
    }

    /**
     * You should not override this method for your IntentService. Instead,
     * override {@link #onHandleIntent}, which the system calls when the IntentService
     * receives a start request.
     * @see android.app.Service#onStartCommand
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        onStart(intent, startId);
        return mRedelivery ? START_REDELIVER_INTENT : START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        mServiceLooper.quit();
    }

    /**
     * Unless you provide binding for your service, you don't need to implement this
     * method, because the default implementation returns null.
     * @see android.app.Service#onBind
     */
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * This method is invoked on the worker thread with a request to process.
     * Only one Intent is processed at a time, but the processing happens on a
     * worker thread that runs independently from other application logic.
     * So, if this code takes a long time, it will hold up other requests to
     * the same IntentService, but it will not hold up anything else.
     * When all requests have been handled, the IntentService stops itself,
     * so you should not call {@link #stopSelf}.
     *
     * @param intent The value passed to {@link
     *               android.content.Context#startService(Intent)}.
     */
    @WorkerThread
    protected abstract void onHandleIntent(Intent intent);
}

IntentService实现了"work queue processor"，
可以将任务剥离主线程（即不会阻塞主线程）并按次序完成任务，当任务完成之后，则会自动关闭自身~听起来非常神奇，非常方便，那如何实现呢？

![image](http://img0.tuicool.com/UvyMfy.png!web)

这两列分别表示两种启动Service的方法：startService和bindService。
源码144-147行的实现否决了右边调用bindService启动Service的方法（不是不可用，
程序员可以重载IntentService进行进一步的定制，只不过直接使用IntentService的bindService是不能没有意义的~）。
既然如此，我们按照左边的生命周期查看源码。

首先看100-112行的OnCreate()方法，看到没有？HandlerThread！
源码在这里创建了一个HandlerThread，HandlerThread的使用方法就在107-111行处~首先是创建实例，
然后必须start()(想想run()方法里面干了什么？)，接着通过getLooper方法取出Looper用于第111行的ServiceHandler创建，
这是一个内部类，继承了Handler而已，重载handleMessage方法，执行两个动作：回调onHandleIntent方法，终止Service。

128-132行重载了onStartCommand方法，这个方法每次在startService()方法调用的时候都会被执行，
它可以根据一个boolean值决定返回值（这里可以去查看一下Service该方法返回值的含义，
它决定了Service被杀死之后如何复苏），另外也调用了onStart()方法，即执行114-120行的代码。
onStart方法则将传入的intent以及startId包装秤一个msg，交给mServiceHandler发送给HandlerThread的实例去处理。

OKOK，到这里一口气吃的有点多，停下来整理一下~IntentService到底干了什么？IntentService在内部启动了一个带Looper的Thread，
当然，这个Thread也就具备类消息处理的能力，外部每一次调用startService，都会传进一个Intent，
而该Intent稍后就会被封装成消息交给HandlerThread处理，处理完毕之后，HandlerThread会主动调用stopSelf()停止服务。

不知道有没有读者感到奇怪，这里都已经停止服务了，怎么还能继续处理消息呢？
其实Service在这里只是主线程和工作线程之间的一个桥梁，Service的Intance唯一性保证了不管调用几次startService，
只会有一个工作线程被实例化，从而接受工作，HandlerThread其实是一个一直在等待消息的工作线程，
而Service只是负责将任务封装成消息交给它，而每次要传递任务，都必须调用IntentService的startService()方法启动Service，
因而在任务执行完毕后关闭Service是没有问题，不会影响后续的任务传递，但是如果任务正在传递中，
比如新的任务传递执行到117行，前一个任务刚好执行完毕，这个时候调用stopSelf，即66行，会发生什么呢？官方文档有解释：

However, if your service handles multiple requests to onStartCommand() concurrently,
then you shouldn't stop the service when you're done processing a start request,
because you might have since received a new start request (stopping at the end of the
first request would terminate the second one). To avoid this problem, you can use
stopSelf(int) to ensure that your request to stop the service is always based on
the most recent start request. That is, when you call stopSelf(int), you pass the ID
of the start request (the startId delivered to onStartCommand()) to which your stop
request corresponds. Then if the service received a new start request before you were
able to call stopSelf(int), then the ID will not match and the service will not stop.
翻译如下:
然而,如果你的服务处理多个请求onStartCommand同时(),那么你不应该停止服务,当你处理一个请求开始,
因为你可能已经收到了新的开始请求(停在第一个请求终止第二个)。为了避免这个问题,您可以使用stopSelf(int),
以确保您的请求停止服务总是基于最近开始请求。也就是说,当你叫stopSelf(int),
你传递的ID请求开始(startId送到onStartCommand()),你停止请求对应。
如果服务收到了新的开始请求之前你能够叫stopSelf(int),然后ID不匹配,服务不会停止。

## 再介绍另一个很有意思的方法，setIntentRedelivery()。从字面理解是设置intent重投递。
如果设置为true，onStartCommand(Intent, int, int)将会返回START_REDELIVER_INTENT，
如果onHandleIntent(Intent)返回之前进程死掉了，那么进程将会重新启动，intent重新投递，
如果有大量的intent投递了，那么只保证最近的intent会被重投递。这个机制也很好，大家可以尝试着用。

[!url](http://blog.csdn.net/fangyoayu2013/article/details/50578780)

Android中Service类中onStartCommand返回值介绍:

START_STICKY
如果service进程被kill掉，保留service的状态为开始状态，但不保留递送的intent对象。随后系统会尝试重新创建service，由于服务状态为开始状态，所以创建服务后一定会调用onStartCommand(Intent,int,int)方法。如果在此期间没有任何启动命令被传递到service，那么参数Intent将为null。

START_NOT_STICKY
“非粘性的”。使用这个返回值时，如果在执行完onStartCommand后，服务被异常kill掉，系统不会自动重启该服务。

START_REDELIVER_INTENT
重传Intent。使用这个返回值时，如果在执行完onStartCommand后，服务被异常kill掉，系统会自动重启该服务，并将Intent的值传入。

START_STICKY_COMPATIBILITY
START_STICKY的兼容版本，但不保证服务被kill后一定能重启。

## 系统默认策略
Service的onStartCommand策略

```
public int onStartCommand(Intent intent, int flags, int startId) {

      onStart(intent, startId);

      return mStartCompatibility ? START_STICKY_COMPATIBILITY : START_STICKY;

}

```

可见，默认的策略是START_STICKY，支持服务意外终止重新创建的。

## IntentService的onStartCommand策略
```

@Override
  public int onStartCommand(Intent intent, int flags, int startId) {

      onStart(intent, startId);
      return mRedelivery ? START_REDELIVER_INTENT : START_NOT_STICKY;
  }

  public void setIntentRedelivery(boolean enabled) {
         mRedelivery = enabled;
   }

```

可见， IntentService 默认只支持两种返回值 START_REDELIVER_INTENT 或者 START_NOT_STICKY ，
并且由 setIntentRedelivery 方法决定，默认是 START_NOT_STICKY ,不重新创建


