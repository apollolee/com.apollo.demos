/*
 * 此代码创建于 2022年3月16日 下午3:22:00。
 */
package com.apollo.demos.base.bytecode;

//interface和class在字节码结构上差别几乎没有，interface在flags中有一个ACC_INTERFACE。
//可以看到interface也有缺省的super_class，和class一样，都是Object。
//接口的default方法并没有特别的字节码支持。

public interface D {

    int m_a = 1;

    boolean isTest();

    default void test() {
        System.out.println(isTest());
    }

}

/*-------- javap -v D --------
public interface com.apollo.demos.base.bytecode.D
  minor version: 0
  major version: 52
  flags: (0x0601) ACC_PUBLIC, ACC_INTERFACE, ACC_ABSTRACT
  this_class: #1                          // com/apollo/demos/base/bytecode/D
  super_class: #3                         // java/lang/Object
  interfaces: 0, fields: 1, methods: 2, attributes: 1
Constant pool:
   #1 = Class              #2             // com/apollo/demos/base/bytecode/D
   #2 = Utf8               com/apollo/demos/base/bytecode/D
   #3 = Class              #4             // java/lang/Object
   #4 = Utf8               java/lang/Object
   #5 = Utf8               m_a
   #6 = Utf8               I
   #7 = Utf8               ConstantValue
   #8 = Integer            1
   #9 = Utf8               isTest
  #10 = Utf8               ()Z
  #11 = Utf8               test
  #12 = Utf8               ()V
  #13 = Utf8               Code
  #14 = Fieldref           #15.#17        // java/lang/System.out:Ljava/io/PrintStream;
  #15 = Class              #16            // java/lang/System
  #16 = Utf8               java/lang/System
  #17 = NameAndType        #18:#19        // out:Ljava/io/PrintStream;
  #18 = Utf8               out
  #19 = Utf8               Ljava/io/PrintStream;
  #20 = InterfaceMethodref #1.#21         // com/apollo/demos/base/bytecode/D.isTest:()Z
  #21 = NameAndType        #9:#10         // isTest:()Z
  #22 = Methodref          #23.#25        // java/io/PrintStream.println:(Z)V
  #23 = Class              #24            // java/io/PrintStream
  #24 = Utf8               java/io/PrintStream
  #25 = NameAndType        #26:#27        // println:(Z)V
  #26 = Utf8               println
  #27 = Utf8               (Z)V
  #28 = Utf8               LineNumberTable
  #29 = Utf8               LocalVariableTable
  #30 = Utf8               this
  #31 = Utf8               Lcom/apollo/demos/base/bytecode/D;
  #32 = Utf8               SourceFile
  #33 = Utf8               D.java
{
  public static final int m_a;
    descriptor: I
    flags: (0x0019) ACC_PUBLIC, ACC_STATIC, ACC_FINAL
    ConstantValue: int 1

  public abstract boolean isTest();
    descriptor: ()Z
    flags: (0x0401) ACC_PUBLIC, ACC_ABSTRACT

  public default void test();
    descriptor: ()V
    flags: (0x0001) ACC_PUBLIC
    Code:
      stack=2, locals=1, args_size=1
         0: getstatic     #14                 // Field java/lang/System.out:Ljava/io/PrintStream;
         3: aload_0
         4: invokeinterface #20,  1           // InterfaceMethod isTest:()Z
         9: invokevirtual #22                 // Method java/io/PrintStream.println:(Z)V
        12: return
      LineNumberTable:
        line 17: 0
        line 18: 12
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
            0      13     0  this   Lcom/apollo/demos/base/bytecode/D;
}
*/
