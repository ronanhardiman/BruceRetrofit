package net.iyouqu.bruceretrofit.java;

/**
 * Created by q on 2016/3/23.
 */
public class InnerClass {

    private void test() {
        Person person = new Person() {
            @Override
            void eat() {

            }
        };
        person.eat();
        Man man = new Man() {
            @Override
            public void sleep() {

            }
        };
        man.sleep();
    }
    //匿名内部类的基本实现
    abstract class Person{
        abstract void eat();
    }
    //在接口上使用匿名内部类
    interface Man{
        void sleep();
    }
    //Thread类的匿名内部类实现

    Thread thread = new Thread(){
        @Override
        public void run() {
            super.run();
        }
    };


    //Runnable接口的匿名内部类实现
    Runnable runnable = new Runnable() {
        @Override
        public void run() {

        }
    };
    private void startThread() {
        this.thread.start();
        Thread thread = new Thread(runnable);
        thread.start();
    }
}
