/*
 * 此代码创建于 2022年3月16日 下午2:13:51。
 */
package com.apollo.demos.base.bytecode;

//看看字段表的定义。
//加final时字段有ConstantValue属性指向常量池，不加final实际上是一个语法糖，赋值指令移到缺省构造方法中了。
//常量池索引并不是依次递增的，long和double会占用2个索引位置。
//Utf8虽然是个很长的字符串，但只占一个索引位置。

public final class C {

    @SuppressWarnings("unused")
    private byte m_a;

    protected short m_b;

    public int m_c;

    long m_d = 66;

    final float m_e = 0.8f;

    static double m_f = 88.88;

    volatile boolean m_g;

    static final char m_h = 'h';

    public static final String m_i = "test";

    public static volatile String m_j;

}

/*-------- javap -v C --------
public final class com.apollo.demos.base.bytecode.C
  minor version: 0
  major version: 52
  flags: (0x0031) ACC_PUBLIC, ACC_FINAL, ACC_SUPER
  this_class: #1                          // com/apollo/demos/base/bytecode/C
  super_class: #3                         // java/lang/Object
  interfaces: 0, fields: 10, methods: 2, attributes: 1
Constant pool:
   #1 = Class              #2             // com/apollo/demos/base/bytecode/C
   #2 = Utf8               com/apollo/demos/base/bytecode/C
   #3 = Class              #4             // java/lang/Object
   #4 = Utf8               java/lang/Object
   #5 = Utf8               m_a
   #6 = Utf8               B
   #7 = Utf8               m_b
   #8 = Utf8               S
   #9 = Utf8               m_c
  #10 = Utf8               I
  #11 = Utf8               m_d
  #12 = Utf8               J
  #13 = Utf8               m_e
  #14 = Utf8               F
  #15 = Utf8               ConstantValue
  #16 = Float              0.8f
  #17 = Utf8               m_f
  #18 = Utf8               D
  #19 = Utf8               m_g
  #20 = Utf8               Z
  #21 = Utf8               m_h
  #22 = Utf8               C
  #23 = Integer            104
  #24 = Utf8               m_i
  #25 = Utf8               Ljava/lang/String;
  #26 = String             #27            // test
  #27 = Utf8               test
  #28 = Utf8               m_j
  #29 = Utf8               <clinit>
  #30 = Utf8               ()V
  #31 = Utf8               Code
  #32 = Double             88.88d
  #34 = Fieldref           #1.#35         // com/apollo/demos/base/bytecode/C.m_f:D
  #35 = NameAndType        #17:#18        // m_f:D
  #36 = Utf8               LineNumberTable
  #37 = Utf8               LocalVariableTable
  #38 = Utf8               <init>
  #39 = Methodref          #3.#40         // java/lang/Object."<init>":()V
  #40 = NameAndType        #38:#30        // "<init>":()V
  #41 = Long               66l
  #43 = Fieldref           #1.#44         // com/apollo/demos/base/bytecode/C.m_d:J
  #44 = NameAndType        #11:#12        // m_d:J
  #45 = Fieldref           #1.#46         // com/apollo/demos/base/bytecode/C.m_e:F
  #46 = NameAndType        #13:#14        // m_e:F
  #47 = Utf8               this
  #48 = Utf8               Lcom/apollo/demos/base/bytecode/C;
  #49 = Utf8               SourceFile
  #50 = Utf8               C.java
{
  protected short m_b;
    descriptor: S
    flags: (0x0004) ACC_PROTECTED

  public int m_c;
    descriptor: I
    flags: (0x0001) ACC_PUBLIC

  long m_d;
    descriptor: J
    flags: (0x0000)

  final float m_e;
    descriptor: F
    flags: (0x0010) ACC_FINAL
    ConstantValue: float 0.8f

  static double m_f;
    descriptor: D
    flags: (0x0008) ACC_STATIC

  volatile boolean m_g;
    descriptor: Z
    flags: (0x0040) ACC_VOLATILE

  static final char m_h;
    descriptor: C
    flags: (0x0018) ACC_STATIC, ACC_FINAL
    ConstantValue: int 104

  public static final java.lang.String m_i;
    descriptor: Ljava/lang/String;
    flags: (0x0019) ACC_PUBLIC, ACC_STATIC, ACC_FINAL
    ConstantValue: String test

  public static volatile java.lang.String m_j;
    descriptor: Ljava/lang/String;
    flags: (0x0049) ACC_PUBLIC, ACC_STATIC, ACC_VOLATILE

  static {};
    descriptor: ()V
    flags: (0x0008) ACC_STATIC
    Code:
      stack=2, locals=0, args_size=0
         0: ldc2_w        #32                 // double 88.88d
         3: putstatic     #34                 // Field m_f:D
         6: return
      LineNumberTable:
        line 21: 0
        line 29: 6
      LocalVariableTable:
        Start  Length  Slot  Name   Signature

  public com.apollo.demos.base.bytecode.C();
    descriptor: ()V
    flags: (0x0001) ACC_PUBLIC
    Code:
      stack=3, locals=1, args_size=1
         0: aload_0
         1: invokespecial #39                 // Method java/lang/Object."<init>":()V
         4: aload_0
         5: ldc2_w        #41                 // long 66l
         8: putfield      #43                 // Field m_d:J
        11: aload_0
        12: ldc           #16                 // float 0.8f
        14: putfield      #45                 // Field m_e:F
        17: return
      LineNumberTable:
        line 8: 0
        line 17: 4
        line 19: 11
        line 8: 17
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
            0      18     0  this   Lcom/apollo/demos/base/bytecode/C;
}
*/
