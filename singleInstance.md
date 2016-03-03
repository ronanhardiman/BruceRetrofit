[引用url!](http://blog.csdn.net/fangyoayu2013/article/details/50042119)

1. 单例模式Singleton解释
2. 版本一单线程环境
3. 版本二多线程环境 同步延迟加载
4. 版本三多线程环境进阶1 双重检测同步延迟加载
5. 版本四多线程环境进阶2 非延迟加载单例类
6. 版本五多线程环境进阶3 使用内部类实现延迟加载

## 单例模式（Singleton）解释
### 版本一：单线程环境
```

public class Singleton{
    private static Singleton instance = null;
    private Singleton(){}
    public static Singleton getInstance(){
        if(instance == null){
            instance = new Singleton();
        }
        return instance;
    }
}

```

### 版本二：多线程环境 （同步延迟加载）
版本一可以在单线程中正常运行，但多线程就出现了问题。假想现在有两个线程同时运行判断语句instance == null，
并且此时instance还没有创建，那么两个线程都会创建一个实例，此时这个类就不满足单例模式了。当然，
如果有同时又更多的线程执行判断语句instance == null，并且此时instance还没有创建，那么会创建更多的实例。

为了保证在多线程的环境下只能得到类的一个实例，我们在getInstance()方法上加一个同步关键词synchronized ，
这样可以保证在多个线程同时访问getInstance()方法时，只有一个线程能否进入函数体，其他线程等待，
当第一个线程执行完函数体后，第二线程进入，剩余线程等待…..，这样就保证类只有一个实例

```

public class Singleton{
    private static Singleton instance = null;
    private Singleton(){}
    public static synchronized Singleton getInstance(){
        if(instance == null){
            instance = new Singleton();
        }
        return instance;
    }
}

```

### 版本三：多线程环境（进阶1 双重检测同步延迟加载 ）
版本二固然可以在多线程环境下正常运行，但是效率不高。考虑如下的情况：当instance 已经被初始化后，
有多个线程访问getInstance()方法，但是由于线程同步锁的原因，线程只能一个接一个的执行getInstance()方法，
这期间线程的等待就浪费了许多时间。为了解决这个问题就出现了如何下解决方案，现在只有当instance为null的时候才会加锁操作，否则无需加锁操作。

```

public class Singleton{
    private static Singleton instance = null;
    private Singleton(){}
    public static Singleton getInstance() {
        if (instance == null) {
            synchronized (Singleton.class) {
                instance = new Singleton();
            }
        }
        return instance;
    }
}

```

### 版本四：多线程环境（进阶2 非延迟加载单例类 ）
为了让代码更加优雅，简洁，同时健壮性很好，就出现了非延迟加载单例类，又被称之为饿汉式.

```
public class Singleton{
    private static Singleton instance = new Singleton();
    private Singleton(){}
    public static Singleton getInstance() {
        return instance;
    }
}

```

这里的instance 实例在第一次使用Singleton这个类型的时候自动初始化，假设Singleton类中还有其他的静态函数，
那么调用该静态函数也会初始化instance 变量，这就导致过早的初始化instance 变量，从而降低了内存的使用效率。

### 版本五：多线程环境（进阶3 使用内部类实现延迟加载 ）
```
public class Singleton{
    private Singleton(){}
    public static Singleton getInstance() {
        return Holder.instance;
    }
    private static class Holder {
        static Singleton instance = new Singleton();
    }
}

```

上面代码在Singleton类中定义了一个私有类Holder ，当第一次使用到Holder 类型时，会创建instance ，
而Holder 类型只在getInstance()方法中使用到，如果我们不调用getInstance()，就不会触发instance的创建操作，从而做到真正的按需创建。