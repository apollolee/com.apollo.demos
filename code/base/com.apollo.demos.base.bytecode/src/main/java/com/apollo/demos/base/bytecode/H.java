/*
 * 此代码创建于 2022年3月19日 下午3:46:15。
 */
package com.apollo.demos.base.bytecode;

public class H {

    //Code中有一句stack=n，这个stack并不是指方法的调用栈，而是指当前方法的操作数栈，这是当前方法栈帧的主要内存消耗，其中n表示最大栈深。
    //最大栈深由编译器计算出来，而不是分配一个固定值，以保证运行时内存不会浪费。
    //典型的基于栈设计的虚拟机有：HotspotJVM，.net CLR。
    //典型的基于寄存器设计的虚拟机有：LuaVM，DalvikVM。
    //Code中有一句args_size=n，这个比较简单，就是方法的参数个数，需要注意的是，非静态方法有一个隐藏的this参数传递，会占用一个位置。

    void a() {
    }

    void b(int p1) {
    }

    void c(int p1, int p2, int p3) {
    }

    void d(int p1, int p2, int p3, int p4, int p5, int p6) {
    }

    void i1() {
        a();
    }

    void i2() {
        b(1);
    }

    void i3() {
        c(1, 2, 3);
    }

    void i4() {
        d(1, 2, 3, 4, 5, 6);
    }

    void i5() {
        a();
        c(1, 2, 3);
        b(1);
    }

}

/*-------- javap -v H --------
public class com.apollo.demos.base.bytecode.H
minor version: 0
major version: 52
flags: (0x0021) ACC_PUBLIC, ACC_SUPER
this_class: #1                          // com/apollo/demos/base/bytecode/H
super_class: #3                         // java/lang/Object
interfaces: 0, fields: 0, methods: 10, attributes: 1
Constant pool:
 #1 = Class              #2             // com/apollo/demos/base/bytecode/H
 #2 = Utf8               com/apollo/demos/base/bytecode/H
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
#13 = Utf8               Lcom/apollo/demos/base/bytecode/H;
#14 = Utf8               a
#15 = Utf8               b
#16 = Utf8               (I)V
#17 = Utf8               p1
#18 = Utf8               I
#19 = Utf8               c
#20 = Utf8               (III)V
#21 = Utf8               p2
#22 = Utf8               p3
#23 = Utf8               d
#24 = Utf8               (IIIIII)V
#25 = Utf8               p4
#26 = Utf8               p5
#27 = Utf8               p6
#28 = Utf8               i1
#29 = Methodref          #1.#30         // com/apollo/demos/base/bytecode/H.a:()V
#30 = NameAndType        #14:#6         // a:()V
#31 = Utf8               i2
#32 = Methodref          #1.#33         // com/apollo/demos/base/bytecode/H.b:(I)V
#33 = NameAndType        #15:#16        // b:(I)V
#34 = Utf8               i3
#35 = Methodref          #1.#36         // com/apollo/demos/base/bytecode/H.c:(III)V
#36 = NameAndType        #19:#20        // c:(III)V
#37 = Utf8               i4
#38 = Methodref          #1.#39         // com/apollo/demos/base/bytecode/H.d:(IIIIII)V
#39 = NameAndType        #23:#24        // d:(IIIIII)V
#40 = Utf8               i5
#41 = Utf8               SourceFile
#42 = Utf8               H.java
{
public com.apollo.demos.base.bytecode.H();
  descriptor: ()V
  flags: (0x0001) ACC_PUBLIC
  Code:
    stack=1, locals=1, args_size=1
       0: aload_0
       1: invokespecial #8                  // Method java/lang/Object."<init>":()V
       4: return
    LineNumberTable:
      line 6: 0
    LocalVariableTable:
      Start  Length  Slot  Name   Signature
          0       5     0  this   Lcom/apollo/demos/base/bytecode/H;

void a();
  descriptor: ()V
  flags: (0x0000)
  Code:
    stack=0, locals=1, args_size=1
       0: return
    LineNumberTable:
      line 9: 0
    LocalVariableTable:
      Start  Length  Slot  Name   Signature
          0       1     0  this   Lcom/apollo/demos/base/bytecode/H;

void b(int);
  descriptor: (I)V
  flags: (0x0000)
  Code:
    stack=0, locals=2, args_size=2
       0: return
    LineNumberTable:
      line 12: 0
    LocalVariableTable:
      Start  Length  Slot  Name   Signature
          0       1     0  this   Lcom/apollo/demos/base/bytecode/H;
          0       1     1    p1   I

void c(int, int, int);
  descriptor: (III)V
  flags: (0x0000)
  Code:
    stack=0, locals=4, args_size=4
       0: return
    LineNumberTable:
      line 15: 0
    LocalVariableTable:
      Start  Length  Slot  Name   Signature
          0       1     0  this   Lcom/apollo/demos/base/bytecode/H;
          0       1     1    p1   I
          0       1     2    p2   I
          0       1     3    p3   I

void d(int, int, int, int, int, int);
  descriptor: (IIIIII)V
  flags: (0x0000)
  Code:
    stack=0, locals=7, args_size=7
       0: return
    LineNumberTable:
      line 18: 0
    LocalVariableTable:
      Start  Length  Slot  Name   Signature
          0       1     0  this   Lcom/apollo/demos/base/bytecode/H;
          0       1     1    p1   I
          0       1     2    p2   I
          0       1     3    p3   I
          0       1     4    p4   I
          0       1     5    p5   I
          0       1     6    p6   I

void i1();
  descriptor: ()V
  flags: (0x0000)
  Code:
    stack=1, locals=1, args_size=1
       0: aload_0
       1: invokevirtual #29                 // Method a:()V
       4: return
    LineNumberTable:
      line 21: 0
      line 22: 4
    LocalVariableTable:
      Start  Length  Slot  Name   Signature
          0       5     0  this   Lcom/apollo/demos/base/bytecode/H;

void i2();
  descriptor: ()V
  flags: (0x0000)
  Code:
    stack=2, locals=1, args_size=1
       0: aload_0
       1: iconst_1
       2: invokevirtual #32                 // Method b:(I)V
       5: return
    LineNumberTable:
      line 25: 0
      line 26: 5
    LocalVariableTable:
      Start  Length  Slot  Name   Signature
          0       6     0  this   Lcom/apollo/demos/base/bytecode/H;

void i3();
  descriptor: ()V
  flags: (0x0000)
  Code:
    stack=4, locals=1, args_size=1
       0: aload_0
       1: iconst_1
       2: iconst_2
       3: iconst_3
       4: invokevirtual #35                 // Method c:(III)V
       7: return
    LineNumberTable:
      line 29: 0
      line 30: 7
    LocalVariableTable:
      Start  Length  Slot  Name   Signature
          0       8     0  this   Lcom/apollo/demos/base/bytecode/H;

void i4();
  descriptor: ()V
  flags: (0x0000)
  Code:
    stack=7, locals=1, args_size=1
       0: aload_0
       1: iconst_1
       2: iconst_2
       3: iconst_3
       4: iconst_4
       5: iconst_5
       6: bipush        6
       8: invokevirtual #38                 // Method d:(IIIIII)V
      11: return
    LineNumberTable:
      line 33: 0
      line 34: 11
    LocalVariableTable:
      Start  Length  Slot  Name   Signature
          0      12     0  this   Lcom/apollo/demos/base/bytecode/H;

void i5();
  descriptor: ()V
  flags: (0x0000)
  Code:
    stack=4, locals=1, args_size=1
       0: aload_0
       1: invokevirtual #29                 // Method a:()V
       4: aload_0
       5: iconst_1
       6: iconst_2
       7: iconst_3
       8: invokevirtual #35                 // Method c:(III)V
      11: aload_0
      12: iconst_1
      13: invokevirtual #32                 // Method b:(I)V
      16: return
    LineNumberTable:
      line 37: 0
      line 38: 4
      line 39: 11
      line 40: 16
    LocalVariableTable:
      Start  Length  Slot  Name   Signature
          0      17     0  this   Lcom/apollo/demos/base/bytecode/H;
}
*/
