/*
 * 此代码创建于 2022年3月16日 下午6:03:11。
 */
package com.apollo.demos.base.bytecode;

@SuppressWarnings("unused")
public class G {

    //LocalVariableTable：局部变量表，栈帧中的内存消耗之一。Code第一句中有locals=n，其中n表示本方法局部变量占用的槽位数。
    //Start：局部变量使用从哪个指令开始。
    //Length：局部变量使用到哪个指令结束，一般是到最后一条指令。
    //Slot：局部变量从第几个槽位开始，注意：不是一个变量一个槽位。
    //所有非静态方法的第一个局部变量都是this，而静态方法则没有这个消耗，所以静态方法的栈帧消耗要低一些（节省一个槽位），如无必要，尽量使用静态方法。

    public void a(int p1, int p2) {
        int temp = p1 + p2;
    }

    public static void b() {
        if (true) {
            String a = "a"; //局部且限定作用域，连名字都没有存，在LocalVariableTable找不到，但占用Slot，且复用，比如下面的b占用的Slot和这里的a一样，不冲突，因为是不同作用域。
        }

        if (true) {
            String b = "b"; //复用上面a的Slot，这样可以减少每个栈帧中的局部变量表的大小，节省实际内存开销。这就是局部变量作用域控制的越小越好的原因。
        }
        //这里没有this消耗，但locals=1，表示还是有一个槽位消耗，但局部变量表是空的，这是因为方法域内的局部作用域内会临时占用槽位，且多个局部作用域能够共享，但这些局部变量都不记录到局部变量表。
        //从栈帧消耗上看，合理的控制局部变量的作用域能够更精细化的控制栈内存消耗。
    }

    public void c() {
        double a = 0.8; //这个占两个Slot，所以局部变量只有3个，但locals=4。
        int b = 8;
    }

}

/*-------- javap -v G --------
public class com.apollo.demos.base.bytecode.G
  minor version: 0
  major version: 52
  flags: (0x0021) ACC_PUBLIC, ACC_SUPER
  this_class: #1                          // com/apollo/demos/base/bytecode/G
  super_class: #3                         // java/lang/Object
  interfaces: 0, fields: 0, methods: 4, attributes: 1
Constant pool:
   #1 = Class              #2             // com/apollo/demos/base/bytecode/G
   #2 = Utf8               com/apollo/demos/base/bytecode/G
   #3 = Class              #4             // java/lang/Object
   #4 = Utf8               java/lang/Object
   #5 = Utf8               <init>
   #6 = Utf8               ()V
   #7 = Utf8               Code
   #8 = Methodref          #3.#9          // java/lang/Object."<init>":()V
   #9 = NameAndType        #5:#6          // "<init>":()V
  #10 = Utf8               LineNumberTable
  #11 = Utf8               LocalVariableTable
  #12 = Utf8               this
  #13 = Utf8               Lcom/apollo/demos/base/bytecode/G;
  #14 = Utf8               a
  #15 = Utf8               (II)V
  #16 = Utf8               p1
  #17 = Utf8               I
  #18 = Utf8               p2
  #19 = Utf8               temp
  #20 = Utf8               b
  #21 = String             #14            // a
  #22 = String             #20            // b
  #23 = Utf8               c
  #24 = Double             0.8d
  #26 = Utf8               D
  #27 = Utf8               SourceFile
  #28 = Utf8               G.java
{
  public com.apollo.demos.base.bytecode.G();
    descriptor: ()V
    flags: (0x0001) ACC_PUBLIC
    Code:
      stack=1, locals=1, args_size=1
         0: aload_0
         1: invokespecial #8                  // Method java/lang/Object."<init>":()V
         4: return
      LineNumberTable:
        line 7: 0
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
            0       5     0  this   Lcom/apollo/demos/base/bytecode/G;

  public void a(int, int);
    descriptor: (II)V
    flags: (0x0001) ACC_PUBLIC
    Code:
      stack=2, locals=4, args_size=3
         0: iload_1
         1: iload_2
         2: iadd
         3: istore_3
         4: return
      LineNumberTable:
        line 16: 0
        line 17: 4
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
            0       5     0  this   Lcom/apollo/demos/base/bytecode/G;
            0       5     1    p1   I
            0       5     2    p2   I
            4       1     3  temp   I

  public static void b();
    descriptor: ()V
    flags: (0x0009) ACC_PUBLIC, ACC_STATIC
    Code:
      stack=1, locals=1, args_size=0
         0: ldc           #21                 // String a
         2: astore_0
         3: ldc           #22                 // String b
         5: astore_0
         6: return
      LineNumberTable:
        line 21: 0
        line 25: 3
        line 29: 6
      LocalVariableTable:
        Start  Length  Slot  Name   Signature

  public void c();
    descriptor: ()V
    flags: (0x0001) ACC_PUBLIC
    Code:
      stack=2, locals=4, args_size=1
         0: ldc2_w        #24                 // double 0.8d
         3: dstore_1
         4: bipush        8
         6: istore_3
         7: return
      LineNumberTable:
        line 32: 0
        line 33: 4
        line 34: 7
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
            0       8     0  this   Lcom/apollo/demos/base/bytecode/G;
            4       4     1     a   D
            7       1     3     b   I
}
*/
