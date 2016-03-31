# static (from:http://blog.csdn.net/u011225629/article/details/45340745)
1. static 变量
按照是否静态的对类成员变量进行分类可分两种：一种是被static修饰的变量，叫静态变量或类变量；另一种是没有被static修饰的变量，叫实例变量。两者的区别是：
　 对于静态变量在内存中只有一个拷贝（节省内存），JVM只为静态分配一次内存，在加载类的过程中完成静态变量的内存分配，可用类名直接访问（方便），当然也可以通过对象来访问（但是这是不推荐的）。对于实例变量，没创建一个实例，就会为实例变量分配一次内存，实例变量可以在内存中有多个拷贝，互不影响（灵活）。
2. static 代码块
static代码块是类加载时，初始化自动执行的。如果static代码块有多个，JVM将按照它们在类中出现的先后顺序依次执行它们，每个代码块只会被执行一次。

3. static 方法
static方法可以直接通过类名调用，任何的实例也都可以调用，因此static方法中不能用this和super关键字，不能直接访问所属类的实例变量和实例方法(就是不带static的成员变量和成员成员方法)，只能访问所属类的静态成员变量和成员方法。因为static方法独立于任何实例，因此static方法必须被实现，而不能是抽象的abstract。
    static方法只能访问static的变量和方法，因为非static的变量和方法是需要创建一个对象才能访问的，而static的变量/方法不需要创建任何对象。
********
static的数据或方法，属于整个类的而不是属于某个对象的，是不会和类的任何对象实例联系到一起。所以子类和父类之间可以存在同名的static方法名，这里不涉及重载。所以不能把任何方法体内的变量声明为static，例如：
fun() {
   static int i=0; //非法。
}
其实理解static是只有一个存储地方，而使用时直接使用，不需要创建对象，就能明白以上的注意事项。

另外，一般的类是没有static的，只有内部类可以加上static来表示嵌套类。

4.

5.
# final

   在Java中声明属性、方法和类时，可使用关键字final来修饰。
   final变量即为常量，只能赋值一次；
   final方法不能被子类重写；
   final类不能被继承。
1. final 变量
   声明 final 字段有助于优化器作出更好的优化决定，因为如果编译器知道字段的值不会更改，那么它能安全地在寄存器中高速缓存该值。final 字段还通过让编译器强制该字段为只读来提供额外的安全级别。
       其初始化可以在两个地方，一是其定义处，也就是说在final变量定义时直接给其赋值，二是在构造函数中。这两个地方只能选其一，要么在定义时给值，要么在构造函数中给值，不能同时既在定义时给了值，又在构造函数中给另外的值。
       一旦被初始化便不可改变，这里不可改变的意思对基本类型来说是其值不可变，而对于对象变量来说其引用不可再变。
       当函数参数为final类型时，你可以读取使用该参数，但是无法改变该参数的值。
       另外方法中的内部类在用到方法中的参变量时，此参变也必须声明为final才可使用。
       在java中，普通变量系统是自动初始化的，数值变量自动初始化为0，其余类型变量自动初始化为空。但是final类型的变量必须显示初始化，且初始化的方法必须是在申明时或者在构造方法中直接赋值，而不能通过调用函数赋值。
2. final 方法
   如果一个类不允许其子类覆盖某个方法，则可以把这个方法声明为final方法。
       使用final方法的原因有二：
       第一、把方法锁定，防止任何继承类修改它的意义和实现。
       第二、高效。编译器在遇到调用final方法时候会转入内嵌inline机制，大大提高执行效率。

   注意，类中所有的private方法都被隐含是final的。由于无法取用private方法，则也无法重载之。
3. final 类
   final类不能被继承，因此final类的成员方法没有机会被覆盖，默认都是final的。在设计类时候，如果这个类不需要有子类，类的实现细节不允许改变，并且确信这个类不会载被扩展，那么就设计为final类。

import java.util.ArrayList;

public class TestStaticFinal {
        private static final String strStaticFinalVar = "aaa";
        private static String strStaticVar = null;
        private final String strFinalVar = null;
        private static final int intStaticFinalVar = 0;
        private static final Integer integerStaticFinalVar = new Integer(8);
        private static final ArrayList<String> alStaticFinalVar = new ArrayList<String>();

        private void test() {
                System.out.println("-------------值处理前----------");
                System.out.println("strStaticFinalVar=" + strStaticFinalVar + "");
                System.out.println("strStaticVar=" + strStaticVar + "");
                System.out.println("strFinalVar=" + strFinalVar + "");
                System.out.println("intStaticFinalVar=" + intStaticFinalVar + "");
                System.out.println("integerStaticFinalVar=" + integerStaticFinalVar + "");
                System.out.println("alStaticFinalVar=" + alStaticFinalVar + "");


                //strStaticFinalVar="哈哈哈哈";//错误，final表示终态,不可以改变变量本身.
                strStaticVar = "哈哈哈哈";         //正确，static表示类变量,值可以改变.
                //strFinalVar="呵呵呵呵";            //错误, final表示终态，在定义的时候就要初值（哪怕给个null），一旦给定后就不可再更改。
                //intStaticFinalVar=2;                 //错误, final表示终态，在定义的时候就要初值（哪怕给个null），一旦给定后就不可再更改。
                //integerStaticFinalVar=new Integer(8);                //错误, final表示终态，在定义的时候就要初值（哪怕给个null），一旦给定后就不可再更改。
                alStaticFinalVar.add("aaa");     //正确，容器变量本身没有变化，但存放内容发生了变化。这个规则是非常常用的，有很多用途。
                alStaticFinalVar.add("bbb");     //正确，容器变量本身没有变化，但存放内容发生了变化。这个规则是非常常用的，有很多用途。

                System.out.println("-------------值处理后----------");
                System.out.println("strStaticFinalVar=" + strStaticFinalVar + "");
                System.out.println("strStaticVar=" + strStaticVar + "");
                System.out.println("strFinalVar=" + strFinalVar + "");
                System.out.println("intStaticFinalVar=" + intStaticFinalVar + "");
                System.out.println("integerStaticFinalVar=" + integerStaticFinalVar + "");
                System.out.println("alStaticFinalVar=" + alStaticFinalVar + "");
        }

        public static void main(String args[]) {
                new TestStaticFinal().test();
        }
}

