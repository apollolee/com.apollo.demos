/*
 * 此代码创建于 2022年3月16日 上午11:19:00。
 */
package com.apollo.demos.base.bytecode;

//什么都没有，看看javac编译出来的字节码是不是空的。
//一些隐藏的Java语言特效决定了，空的接口比空的类在字节码上要简洁一些，主要是少了自动生成的缺省构造方法。

public class A {
}

interface IA {
}

/*-------- javap -v A --------
public class com.apollo.demos.base.bytecode.A
minor version: 0
major version: 52
flags: (0x0021) ACC_PUBLIC, ACC_SUPER
this_class: #1                          // com/apollo/demos/base/bytecode/A
super_class: #3                         // java/lang/Object
interfaces: 0, fields: 0, methods: 1, attributes: 1
Constant pool:
 #1 = Class              #2             // com/apollo/demos/base/bytecode/A
 #2 = Utf8               com/apollo/demos/base/bytecode/A
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
#13 = Utf8               Lcom/apollo/demos/base/bytecode/A;
#14 = Utf8               SourceFile
#15 = Utf8               A.java
{
public com.apollo.demos.base.bytecode.A();
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
          0       5     0  this   Lcom/apollo/demos/base/bytecode/A;
}
*/

/*-------- javap -v IA --------
interface com.apollo.demos.base.bytecode.IA
minor version: 0
major version: 52
flags: (0x0600) ACC_INTERFACE, ACC_ABSTRACT
this_class: #1                          // com/apollo/demos/base/bytecode/IA
super_class: #3                         // java/lang/Object
interfaces: 0, fields: 0, methods: 0, attributes: 1
Constant pool:
#1 = Class              #2              // com/apollo/demos/base/bytecode/IA
#2 = Utf8               com/apollo/demos/base/bytecode/IA
#3 = Class              #4              // java/lang/Object
#4 = Utf8               java/lang/Object
#5 = Utf8               SourceFile
#6 = Utf8               A.java
{
}
*/
