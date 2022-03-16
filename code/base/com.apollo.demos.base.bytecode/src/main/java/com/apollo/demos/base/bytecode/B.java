/*
 * 此代码创建于 2022年3月16日 上午11:52:02。
 */
package com.apollo.demos.base.bytecode;

public class B {

    public static void main(String[] args) {
        System.out.println("Hello bytecode!");
    }

    private int m_a;

    public B() {
        this(1);
    }

    public B(int a) {
        m_a = a;
    }

    public int getA() {
        return m_a;
    }

    public void setA(int a) {
        m_a = a;
    }

}

/*-------- javap -v B --------
public class com.apollo.demos.base.bytecode.B
  minor version: 0
  major version: 52
  flags: (0x0021) ACC_PUBLIC, ACC_SUPER
  this_class: #1                          // com/apollo/demos/base/bytecode/B
  super_class: #3                         // java/lang/Object
  interfaces: 0, fields: 1, methods: 5, attributes: 1
Constant pool:
   #1 = Class              #2             // com/apollo/demos/base/bytecode/B
   #2 = Utf8               com/apollo/demos/base/bytecode/B
   #3 = Class              #4             // java/lang/Object
   #4 = Utf8               java/lang/Object
   #5 = Utf8               m_a
   #6 = Utf8               I
   #7 = Utf8               main
   #8 = Utf8               ([Ljava/lang/String;)V
   #9 = Utf8               Code
  #10 = Fieldref           #11.#13        // java/lang/System.out:Ljava/io/PrintStream;
  #11 = Class              #12            // java/lang/System
  #12 = Utf8               java/lang/System
  #13 = NameAndType        #14:#15        // out:Ljava/io/PrintStream;
  #14 = Utf8               out
  #15 = Utf8               Ljava/io/PrintStream;
  #16 = String             #17            // Hello bytecode!
  #17 = Utf8               Hello bytecode!
  #18 = Methodref          #19.#21        // java/io/PrintStream.println:(Ljava/lang/String;)V
  #19 = Class              #20            // java/io/PrintStream
  #20 = Utf8               java/io/PrintStream
  #21 = NameAndType        #22:#23        // println:(Ljava/lang/String;)V
  #22 = Utf8               println
  #23 = Utf8               (Ljava/lang/String;)V
  #24 = Utf8               LineNumberTable
  #25 = Utf8               LocalVariableTable
  #26 = Utf8               args
  #27 = Utf8               [Ljava/lang/String;
  #28 = Utf8               <init>
  #29 = Utf8               ()V
  #30 = Methodref          #1.#31         // com/apollo/demos/base/bytecode/B."<init>":(I)V
  #31 = NameAndType        #28:#32        // "<init>":(I)V
  #32 = Utf8               (I)V
  #33 = Utf8               this
  #34 = Utf8               Lcom/apollo/demos/base/bytecode/B;
  #35 = Methodref          #3.#36         // java/lang/Object."<init>":()V
  #36 = NameAndType        #28:#29        // "<init>":()V
  #37 = Fieldref           #1.#38         // com/apollo/demos/base/bytecode/B.m_a:I
  #38 = NameAndType        #5:#6          // m_a:I
  #39 = Utf8               a
  #40 = Utf8               getA
  #41 = Utf8               ()I
  #42 = Utf8               setA
  #43 = Utf8               SourceFile
  #44 = Utf8               B.java
{
  public static void main(java.lang.String[]);
    descriptor: ([Ljava/lang/String;)V
    flags: (0x0009) ACC_PUBLIC, ACC_STATIC
    Code:
      stack=2, locals=1, args_size=1
         0: getstatic     #10                 // Field java/lang/System.out:Ljava/io/PrintStream;
         3: ldc           #16                 // String Hello bytecode!
         5: invokevirtual #18                 // Method java/io/PrintStream.println:(Ljava/lang/String;)V
         8: return
      LineNumberTable:
        line 9: 0
        line 10: 8
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
            0       9     0  args   [Ljava/lang/String;

  public com.apollo.demos.base.bytecode.B();
    descriptor: ()V
    flags: (0x0001) ACC_PUBLIC
    Code:
      stack=2, locals=1, args_size=1
         0: aload_0
         1: iconst_1
         2: invokespecial #30                 // Method "<init>":(I)V
         5: return
      LineNumberTable:
        line 15: 0
        line 16: 5
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
            0       6     0  this   Lcom/apollo/demos/base/bytecode/B;

  public com.apollo.demos.base.bytecode.B(int);
    descriptor: (I)V
    flags: (0x0001) ACC_PUBLIC
    Code:
      stack=2, locals=2, args_size=2
         0: aload_0
         1: invokespecial #35                 // Method java/lang/Object."<init>":()V
         4: aload_0
         5: iload_1
         6: putfield      #37                 // Field m_a:I
         9: return
      LineNumberTable:
        line 18: 0
        line 19: 4
        line 20: 9
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
            0      10     0  this   Lcom/apollo/demos/base/bytecode/B;
            0      10     1     a   I

  public int getA();
    descriptor: ()I
    flags: (0x0001) ACC_PUBLIC
    Code:
      stack=1, locals=1, args_size=1
         0: aload_0
         1: getfield      #37                 // Field m_a:I
         4: ireturn
      LineNumberTable:
        line 23: 0
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
            0       5     0  this   Lcom/apollo/demos/base/bytecode/B;

  public void setA(int);
    descriptor: (I)V
    flags: (0x0001) ACC_PUBLIC
    Code:
      stack=2, locals=2, args_size=2
         0: aload_0
         1: iload_1
         2: putfield      #37                 // Field m_a:I
         5: return
      LineNumberTable:
        line 27: 0
        line 28: 5
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
            0       6     0  this   Lcom/apollo/demos/base/bytecode/B;
            0       6     1     a   I
}
*/
