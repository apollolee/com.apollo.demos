/*
 * 此代码创建于 2022年3月19日 下午3:46:15。
 */
package com.apollo.demos.base.bytecode;

@SuppressWarnings("unused")
public interface I {

    //存储和载入指令是针对局部变量的，而另一端是操作数栈，比如：把栈顶数据存储到局部变量1中是一个存储指令，载入局部变量2数据并压入操作数栈的栈顶是一个载入指令。
    //把一些常量或常量池数据压入栈顶是属于操作数栈指令中相对比较简单的一类。

    static void a() {
        String a = null;
    }

    static void b() {
        int a = -1;
        a = 0;
        a = 1;
        a = 2;
        a = 3;
        a = 4;
        a = 5;
        a = 6;
        a = 127;
        a = 128;
        a = 32767;
        a = 32768;
        a = -2;
    }

    static void c() {
        long a = 0;
        a = 1;
        a = 2;
        a = 88888888;
        a = -1;
    }

    static void d() {
        float a = 0;
        a = 1;
        a = 2;
        a = 3;
        a = 8888.8888f;
        a = -1;
    }

    static void e() {
        double a = 0;
        a = 1;
        a = 2;
        a = 8888.8888;
        a = -1;
    }

    static void f() {
        byte a = 1;
        short b = 1;
        boolean c = true;
        char d = 'A';
    }

    static void g(int a, long b, float c, double d, String e) {
        int la = a;
        long lb = b;
        float lc = c;
        double ld = d;
        String le = e;
    }

    static void h(boolean[] a) {
        a[6] = a[8];
    }

}

/*-------- javap -v I --------
public interface com.apollo.demos.base.bytecode.I
  minor version: 0
  major version: 52
  flags: (0x0601) ACC_PUBLIC, ACC_INTERFACE, ACC_ABSTRACT
  this_class: #1                          // com/apollo/demos/base/bytecode/I
  super_class: #3                         // java/lang/Object
  interfaces: 0, fields: 0, methods: 8, attributes: 1
Constant pool:
   #1 = Class              #2             // com/apollo/demos/base/bytecode/I
   #2 = Utf8               com/apollo/demos/base/bytecode/I
   #3 = Class              #4             // java/lang/Object
   #4 = Utf8               java/lang/Object
   #5 = Utf8               a
   #6 = Utf8               ()V
   #7 = Utf8               Code
   #8 = Utf8               LineNumberTable
   #9 = Utf8               LocalVariableTable
  #10 = Utf8               Ljava/lang/String;
  #11 = Utf8               b
  #12 = Integer            32768
  #13 = Utf8               I
  #14 = Utf8               c
  #15 = Long               2l
  #17 = Long               88888888l
  #19 = Long               -1l
  #21 = Utf8               J
  #22 = Utf8               d
  #23 = Float              3.0f
  #24 = Float              8888.889f
  #25 = Float              -1.0f
  #26 = Utf8               F
  #27 = Utf8               e
  #28 = Double             2.0d
  #30 = Double             8888.8888d
  #32 = Double             -1.0d
  #34 = Utf8               D
  #35 = Utf8               f
  #36 = Utf8               B
  #37 = Utf8               S
  #38 = Utf8               Z
  #39 = Utf8               C
  #40 = Utf8               g
  #41 = Utf8               (IJFDLjava/lang/String;)V
  #42 = Utf8               la
  #43 = Utf8               lb
  #44 = Utf8               lc
  #45 = Utf8               ld
  #46 = Utf8               le
  #47 = Utf8               h
  #48 = Utf8               ([Z)V
  #49 = Utf8               [Z
  #50 = Utf8               SourceFile
  #51 = Utf8               I.java
{
  public static void a();
    descriptor: ()V
    flags: (0x0009) ACC_PUBLIC, ACC_STATIC
    Code:
      stack=1, locals=1, args_size=0
         0: aconst_null
         1: astore_0
         2: return
      LineNumberTable:
        line 13: 0
        line 14: 2
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
            2       1     0     a   Ljava/lang/String;

  public static void b();
    descriptor: ()V
    flags: (0x0009) ACC_PUBLIC, ACC_STATIC
    Code:
      stack=1, locals=1, args_size=0
         0: iconst_m1
         1: istore_0
         2: iconst_0
         3: istore_0
         4: iconst_1
         5: istore_0
         6: iconst_2
         7: istore_0
         8: iconst_3
         9: istore_0
        10: iconst_4
        11: istore_0
        12: iconst_5
        13: istore_0
        14: bipush        6
        16: istore_0
        17: bipush        127
        19: istore_0
        20: sipush        128
        23: istore_0
        24: sipush        32767
        27: istore_0
        28: ldc           #12                 // int 32768
        30: istore_0
        31: bipush        -2
        33: istore_0
        34: return
      LineNumberTable:
        line 17: 0
        line 18: 2
        line 19: 4
        line 20: 6
        line 21: 8
        line 22: 10
        line 23: 12
        line 24: 14
        line 25: 17
        line 26: 20
        line 27: 24
        line 28: 28
        line 29: 31
        line 30: 34
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
            2      33     0     a   I

  public static void c();
    descriptor: ()V
    flags: (0x0009) ACC_PUBLIC, ACC_STATIC
    Code:
      stack=2, locals=2, args_size=0
         0: lconst_0
         1: lstore_0
         2: lconst_1
         3: lstore_0
         4: ldc2_w        #15                 // long 2l
         7: lstore_0
         8: ldc2_w        #17                 // long 88888888l
        11: lstore_0
        12: ldc2_w        #19                 // long -1l
        15: lstore_0
        16: return
      LineNumberTable:
        line 33: 0
        line 34: 2
        line 35: 4
        line 36: 8
        line 37: 12
        line 38: 16
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
            2      15     0     a   J

  public static void d();
    descriptor: ()V
    flags: (0x0009) ACC_PUBLIC, ACC_STATIC
    Code:
      stack=1, locals=1, args_size=0
         0: fconst_0
         1: fstore_0
         2: fconst_1
         3: fstore_0
         4: fconst_2
         5: fstore_0
         6: ldc           #23                 // float 3.0f
         8: fstore_0
         9: ldc           #24                 // float 8888.889f
        11: fstore_0
        12: ldc           #25                 // float -1.0f
        14: fstore_0
        15: return
      LineNumberTable:
        line 41: 0
        line 42: 2
        line 43: 4
        line 44: 6
        line 45: 9
        line 46: 12
        line 47: 15
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
            2      14     0     a   F

  public static void e();
    descriptor: ()V
    flags: (0x0009) ACC_PUBLIC, ACC_STATIC
    Code:
      stack=2, locals=2, args_size=0
         0: dconst_0
         1: dstore_0
         2: dconst_1
         3: dstore_0
         4: ldc2_w        #28                 // double 2.0d
         7: dstore_0
         8: ldc2_w        #30                 // double 8888.8888d
        11: dstore_0
        12: ldc2_w        #32                 // double -1.0d
        15: dstore_0
        16: return
      LineNumberTable:
        line 50: 0
        line 51: 2
        line 52: 4
        line 53: 8
        line 54: 12
        line 55: 16
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
            2      15     0     a   D

  public static void f();
    descriptor: ()V
    flags: (0x0009) ACC_PUBLIC, ACC_STATIC
    Code:
      stack=1, locals=4, args_size=0
         0: iconst_1
         1: istore_0
         2: iconst_1
         3: istore_1
         4: iconst_1
         5: istore_2
         6: bipush        65
         8: istore_3
         9: return
      LineNumberTable:
        line 58: 0
        line 59: 2
        line 60: 4
        line 61: 6
        line 62: 9
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
            2       8     0     a   B
            4       6     1     b   S
            6       4     2     c   Z
            9       1     3     d   C

  public static void g(int, long, float, double, java.lang.String);
    descriptor: (IJFDLjava/lang/String;)V
    flags: (0x0009) ACC_PUBLIC, ACC_STATIC
    Code:
      stack=2, locals=14, args_size=5
         0: iload_0
         1: istore        7
         3: lload_1
         4: lstore        8
         6: fload_3
         7: fstore        10
         9: dload         4
        11: dstore        11
        13: aload         6
        15: astore        13
        17: return
      LineNumberTable:
        line 65: 0
        line 66: 3
        line 67: 6
        line 68: 9
        line 69: 13
        line 70: 17
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
            0      18     0     a   I
            0      18     1     b   J
            0      18     3     c   F
            0      18     4     d   D
            0      18     6     e   Ljava/lang/String;
            3      15     7    la   I
            6      12     8    lb   J
            9       9    10    lc   F
           13       5    11    ld   D
           17       1    13    le   Ljava/lang/String;

  public static void h(boolean[]);
    descriptor: ([Z)V
    flags: (0x0009) ACC_PUBLIC, ACC_STATIC
    Code:
      stack=4, locals=1, args_size=1
         0: aload_0
         1: bipush        6
         3: aload_0
         4: bipush        8
         6: baload
         7: bastore
         8: return
      LineNumberTable:
        line 73: 0
        line 74: 8
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
            0       9     0     a   [Z
}
*/
