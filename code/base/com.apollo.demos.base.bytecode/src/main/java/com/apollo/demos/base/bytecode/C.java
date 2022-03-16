/*
 * 此代码创建于 2022年3月16日 下午2:13:51。
 */
package com.apollo.demos.base.bytecode;

public final class C {

    @SuppressWarnings("unused")
    private byte m_a;

    protected short m_b;

    public int m_c;

    long m_d;

    final float m_e = 0.8f;

    static double m_f;

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
interfaces: 0, fields: 10, methods: 1, attributes: 1
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
#29 = Utf8               <init>
#30 = Utf8               ()V
#31 = Utf8               Code
#32 = Methodref          #3.#33         // java/lang/Object."<init>":()V
#33 = NameAndType        #29:#30        // "<init>":()V
#34 = Fieldref           #1.#35         // com/apollo/demos/base/bytecode/C.m_e:F
#35 = NameAndType        #13:#14        // m_e:F
#36 = Utf8               LineNumberTable
#37 = Utf8               LocalVariableTable
#38 = Utf8               this
#39 = Utf8               Lcom/apollo/demos/base/bytecode/C;
#40 = Utf8               SourceFile
#41 = Utf8               C.java
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

public com.apollo.demos.base.bytecode.C();
  descriptor: ()V
  flags: (0x0001) ACC_PUBLIC
  Code:
    stack=2, locals=1, args_size=1
       0: aload_0
       1: invokespecial #32                 // Method java/lang/Object."<init>":()V
       4: aload_0
       5: ldc           #16                 // float 0.8f
       7: putfield      #34                 // Field m_e:F
      10: return
    LineNumberTable:
      line 6: 0
      line 17: 4
      line 6: 10
    LocalVariableTable:
      Start  Length  Slot  Name   Signature
          0      11     0  this   Lcom/apollo/demos/base/bytecode/C;
}
*/
