/*
 * 此代码创建于 2022年3月20日 下午11:11:50。
 */
package com.apollo.demos.base.bytecode;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

//无聊静态还是非静态，在字段定义时直接赋值只是语法糖，最终都是在初始化方法中执行对应的赋值指令。
//一个对象的创建至少需要new，dup，调用<init>三个指令，dup的存在是因为调用<init>需要一个对象，但<init>又不带返回值，调用完后不能再次压栈，如果不在之前dup，new出来的对象就丢失了，无法再赋值到其它地方了。

@SuppressWarnings("unused")
public class P {

    int a = 10;

    public P() {
        int c = 30;
    }

    {
        int b = 20;
    }

}

class P1 {

    FileOutputStream m_fos = new FileOutputStream("text.txt");

    public P1() throws FileNotFoundException {
    }

}

class P2 {

    static int s_a = 0;

    static {
        System.out.println("static");
    }

}

/*-------- javap -v P --------
public class com.apollo.demos.base.bytecode.P
minor version: 0
major version: 52
flags: (0x0021) ACC_PUBLIC, ACC_SUPER
this_class: #1                          // com/apollo/demos/base/bytecode/P
super_class: #3                         // java/lang/Object
interfaces: 0, fields: 1, methods: 1, attributes: 1
Constant pool:
 #1 = Class              #2             // com/apollo/demos/base/bytecode/P
 #2 = Utf8               com/apollo/demos/base/bytecode/P
 #3 = Class              #4             // java/lang/Object
 #4 = Utf8               java/lang/Object
 #5 = Utf8               a
 #6 = Utf8               I
 #7 = Utf8               <init>
 #8 = Utf8               ()V
 #9 = Utf8               Code
#10 = Methodref          #3.#11         // java/lang/Object."<init>":()V
#11 = NameAndType        #7:#8          // "<init>":()V
#12 = Fieldref           #1.#13         // com/apollo/demos/base/bytecode/P.a:I
#13 = NameAndType        #5:#6          // a:I
#14 = Utf8               LineNumberTable
#15 = Utf8               LocalVariableTable
#16 = Utf8               this
#17 = Utf8               Lcom/apollo/demos/base/bytecode/P;
#18 = Utf8               c
#19 = Utf8               SourceFile
#20 = Utf8               P.java
{
int a;
  descriptor: I
  flags: (0x0000)

public com.apollo.demos.base.bytecode.P();
  descriptor: ()V
  flags: (0x0001) ACC_PUBLIC
  Code:
    stack=2, locals=2, args_size=1
       0: aload_0
       1: invokespecial #10                 // Method java/lang/Object."<init>":()V
       4: aload_0
       5: bipush        10
       7: putfield      #12                 // Field a:I
      10: bipush        20
      12: istore_1
      13: bipush        30
      15: istore_1
      16: return
    LineNumberTable:
      line 14: 0
      line 12: 4
      line 19: 10
      line 15: 13
      line 16: 16
    LocalVariableTable:
      Start  Length  Slot  Name   Signature
          0      17     0  this   Lcom/apollo/demos/base/bytecode/P;
         16       1     1     c   I
}
*/

/*-------- javap -v P1 --------
class com.apollo.demos.base.bytecode.P1
minor version: 0
major version: 52
flags: (0x0020) ACC_SUPER
this_class: #1                          // com/apollo/demos/base/bytecode/P1
super_class: #3                         // java/lang/Object
interfaces: 0, fields: 1, methods: 1, attributes: 1
Constant pool:
 #1 = Class              #2             // com/apollo/demos/base/bytecode/P1
 #2 = Utf8               com/apollo/demos/base/bytecode/P1
 #3 = Class              #4             // java/lang/Object
 #4 = Utf8               java/lang/Object
 #5 = Utf8               m_fos
 #6 = Utf8               Ljava/io/FileOutputStream;
 #7 = Utf8               <init>
 #8 = Utf8               ()V
 #9 = Utf8               Exceptions
#10 = Class              #11            // java/io/FileNotFoundException
#11 = Utf8               java/io/FileNotFoundException
#12 = Utf8               Code
#13 = Methodref          #3.#14         // java/lang/Object."<init>":()V
#14 = NameAndType        #7:#8          // "<init>":()V
#15 = Class              #16            // java/io/FileOutputStream
#16 = Utf8               java/io/FileOutputStream
#17 = String             #18            // text.txt
#18 = Utf8               text.txt
#19 = Methodref          #15.#20        // java/io/FileOutputStream."<init>":(Ljava/lang/String;)V
#20 = NameAndType        #7:#21         // "<init>":(Ljava/lang/String;)V
#21 = Utf8               (Ljava/lang/String;)V
#22 = Fieldref           #1.#23         // com/apollo/demos/base/bytecode/P1.m_fos:Ljava/io/FileOutputStream;
#23 = NameAndType        #5:#6          // m_fos:Ljava/io/FileOutputStream;
#24 = Utf8               LineNumberTable
#25 = Utf8               LocalVariableTable
#26 = Utf8               this
#27 = Utf8               Lcom/apollo/demos/base/bytecode/P1;
#28 = Utf8               SourceFile
#29 = Utf8               P.java
{
java.io.FileOutputStream m_fos;
  descriptor: Ljava/io/FileOutputStream;
  flags: (0x0000)

public com.apollo.demos.base.bytecode.P1() throws java.io.FileNotFoundException;
  descriptor: ()V
  flags: (0x0001) ACC_PUBLIC
  Exceptions:
    throws java.io.FileNotFoundException
  Code:
    stack=4, locals=1, args_size=1
       0: aload_0
       1: invokespecial #13                 // Method java/lang/Object."<init>":()V
       4: aload_0
       5: new           #15                 // class java/io/FileOutputStream
       8: dup
       9: ldc           #17                 // String text.txt
      11: invokespecial #19                 // Method java/io/FileOutputStream."<init>":(Ljava/lang/String;)V
      14: putfield      #22                 // Field m_fos:Ljava/io/FileOutputStream;
      17: return
    LineNumberTable:
      line 28: 0
      line 26: 4
      line 29: 17
    LocalVariableTable:
      Start  Length  Slot  Name   Signature
          0      18     0  this   Lcom/apollo/demos/base/bytecode/P1;
}
*/

/*-------- javap -v P2 --------
class com.apollo.demos.base.bytecode.P2
minor version: 0
major version: 52
flags: (0x0020) ACC_SUPER
this_class: #1                          // com/apollo/demos/base/bytecode/P2
super_class: #3                         // java/lang/Object
interfaces: 0, fields: 1, methods: 2, attributes: 1
Constant pool:
 #1 = Class              #2             // com/apollo/demos/base/bytecode/P2
 #2 = Utf8               com/apollo/demos/base/bytecode/P2
 #3 = Class              #4             // java/lang/Object
 #4 = Utf8               java/lang/Object
 #5 = Utf8               s_a
 #6 = Utf8               I
 #7 = Utf8               <clinit>
 #8 = Utf8               ()V
 #9 = Utf8               Code
#10 = Fieldref           #1.#11         // com/apollo/demos/base/bytecode/P2.s_a:I
#11 = NameAndType        #5:#6          // s_a:I
#12 = Fieldref           #13.#15        // java/lang/System.out:Ljava/io/PrintStream;
#13 = Class              #14            // java/lang/System
#14 = Utf8               java/lang/System
#15 = NameAndType        #16:#17        // out:Ljava/io/PrintStream;
#16 = Utf8               out
#17 = Utf8               Ljava/io/PrintStream;
#18 = String             #19            // static
#19 = Utf8               static
#20 = Methodref          #21.#23        // java/io/PrintStream.println:(Ljava/lang/String;)V
#21 = Class              #22            // java/io/PrintStream
#22 = Utf8               java/io/PrintStream
#23 = NameAndType        #24:#25        // println:(Ljava/lang/String;)V
#24 = Utf8               println
#25 = Utf8               (Ljava/lang/String;)V
#26 = Utf8               LineNumberTable
#27 = Utf8               LocalVariableTable
#28 = Utf8               <init>
#29 = Methodref          #3.#30         // java/lang/Object."<init>":()V
#30 = NameAndType        #28:#8         // "<init>":()V
#31 = Utf8               this
#32 = Utf8               Lcom/apollo/demos/base/bytecode/P2;
#33 = Utf8               SourceFile
#34 = Utf8               P.java
{
static int s_a;
  descriptor: I
  flags: (0x0008) ACC_STATIC

static {};
  descriptor: ()V
  flags: (0x0008) ACC_STATIC
  Code:
    stack=2, locals=0, args_size=0
       0: iconst_0
       1: putstatic     #10                 // Field s_a:I
       4: getstatic     #12                 // Field java/lang/System.out:Ljava/io/PrintStream;
       7: ldc           #18                 // String static
       9: invokevirtual #20                 // Method java/io/PrintStream.println:(Ljava/lang/String;)V
      12: return
    LineNumberTable:
      line 35: 0
      line 38: 4
      line 39: 12
    LocalVariableTable:
      Start  Length  Slot  Name   Signature

com.apollo.demos.base.bytecode.P2();
  descriptor: ()V
  flags: (0x0000)
  Code:
    stack=1, locals=1, args_size=1
       0: aload_0
       1: invokespecial #29                 // Method java/lang/Object."<init>":()V
       4: return
    LineNumberTable:
      line 33: 0
    LocalVariableTable:
      Start  Length  Slot  Name   Signature
          0       5     0  this   Lcom/apollo/demos/base/bytecode/P2;
}
*/
