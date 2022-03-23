/*
 * 此代码创建于 2022年3月19日 下午9:48:53。
 */
package com.apollo.demos.base.bytecode;

@SuppressWarnings("unused")
public interface K {

    //小数没有位运算指令。
    //byte，short，boolean，char这4个在字节码上当成int处理。
    //类型转换分为隐式和强制。
    //boolean无法和其他类型互转，语言层面限制，虚拟机应该还是能够支持的。
    //byte，short，char虽然字节码上都是int，但i2b，i2s，i2c还是有必要存在，目的是窄化时截取多余的数据。

    static void a(int p1, int p2) {
        int a = p1 + p2;
        a = p1 - p2;
        a = p1 * p2;
        a = p1 / p2;
        a = p1 % p2;
        a = -p1;
        a = p1 & p2;
        a = p1 | p2;
        a = p1 ^ p2;
    }

    static void b(long p1, long p2) {
        long a = p1 + p2;
        a = p1 - p2;
        a = p1 * p2;
        a = p1 / p2;
        a = p1 % p2;
        a = -p1;
        a = p1 & p2;
        a = p1 | p2;
        a = p1 ^ p2;
    }

    static void c(float p1, float p2) {
        float a = p1 + p2;
        a = p1 - p2;
        a = p1 * p2;
        a = p1 / p2;
        a = p1 % p2;
        a = -p1;
        //小数没有位运算。
        //a = p1 & p2;
        //a = p1 | p2;
        //a = p1 ^ p2;
    }

    static void d(double p1, double p2) {
        double a = p1 + p2;
        a = p1 - p2;
        a = p1 * p2;
        a = p1 / p2;
        a = p1 % p2;
        a = -p1;
        //小数没有位运算。
        //a = p1 & p2;
        //a = p1 | p2;
        //a = p1 ^ p2;
    }

    static void f(byte p) {
        //byte a =  p; //没必要。
        short b = p; //没有cast，从指令层面可以看到，p本来就被当成int了，又是宽化类型转换。
        int c = p; //没有cast，从指令层面可以看到，p本来就被当成int了，又是宽化类型转换。
        long d = p; //i2l，隐式宽化类型转换。
        float e = p; //i2f，隐式窄化类型转换，注意：字节码上是窄化，语言层面上却是宽化。
        double f = p; //i2d，隐式宽化类型转换。
        //boolean g = (boolean)p; //不支持。
        char h = (char) p; //i2c，强制类型转换，窄化。
    }

    static void f(short p) {
        byte a = (byte) p;
        //short b = p; //没必要。
        int c = p;
        long d = p;
        float e = p;
        double f = p;
        //boolean g = (boolean)p; //不支持。
        char h = (char) p;
    }

    static void f(int p) {
        byte a = (byte) p;
        short b = (short) p;
        //int c = p; //没必要。
        long d = p;
        float e = p;
        double f = p;
        //boolean g = (boolean)p; //不支持。
        char h = (char) p;
    }

    static void f(long p) {
        byte a = (byte) p;
        short b = (short) p;
        int c = (int) p;
        //long d = p; //没必要。
        float e = p;
        double f = p;
        //boolean g = (boolean) p; //不支持。
        char h = (char) p;
    }

    static void f(float p) {
        byte a = (byte) p;
        short b = (short) p;
        int c = (int) p;
        long d = (long) p;
        //float e = p; //没必要。
        double f = p;
        //boolean g = (boolean) p; //不支持。
        char h = (char) p;
    }

    static void f(double p) {
        byte a = (byte) p;
        short b = (short) p;
        int c = (int) p;
        long d = (long) p;
        float e = (float) p;
        //double f = p; //没必要。
        //boolean g = (boolean) p; //不支持。
        char h = (char) p;
    }

    static void f(boolean p) {
        //byte a = (byte) p; //不支持。
        //short b = (short) p; //不支持。
        //int c = (int) p; //不支持。
        //long d = (long) p; //不支持。
        //float e = (float) p; //不支持。
        //double f = (double)p; //不支持。
        //boolean g =  p; //没必要。
        //char h = (char) p; //不支持。
    }

    static void f(char p) {
        byte a = (byte) p;
        short b = (short) p;
        int c = p;
        long d = p;
        float e = p;
        double f = p;
        //boolean g = (boolean) p; //不支持。
        //char h = p; //没必要。
    }

}

/*-------- javap -v K --------
public interface com.apollo.demos.base.bytecode.K
  minor version: 0
  major version: 52
  flags: (0x0601) ACC_PUBLIC, ACC_INTERFACE, ACC_ABSTRACT
  this_class: #1                          // com/apollo/demos/base/bytecode/K
  super_class: #3                         // java/lang/Object
  interfaces: 0, fields: 0, methods: 12, attributes: 1
Constant pool:
   #1 = Class              #2             // com/apollo/demos/base/bytecode/K
   #2 = Utf8               com/apollo/demos/base/bytecode/K
   #3 = Class              #4             // java/lang/Object
   #4 = Utf8               java/lang/Object
   #5 = Utf8               a
   #6 = Utf8               (II)V
   #7 = Utf8               Code
   #8 = Utf8               LineNumberTable
   #9 = Utf8               LocalVariableTable
  #10 = Utf8               p1
  #11 = Utf8               I
  #12 = Utf8               p2
  #13 = Utf8               b
  #14 = Utf8               (JJ)V
  #15 = Utf8               J
  #16 = Utf8               c
  #17 = Utf8               (FF)V
  #18 = Utf8               F
  #19 = Utf8               d
  #20 = Utf8               (DD)V
  #21 = Utf8               D
  #22 = Utf8               f
  #23 = Utf8               (B)V
  #24 = Utf8               p
  #25 = Utf8               B
  #26 = Utf8               S
  #27 = Utf8               e
  #28 = Utf8               h
  #29 = Utf8               C
  #30 = Utf8               (S)V
  #31 = Utf8               (I)V
  #32 = Utf8               (J)V
  #33 = Utf8               (F)V
  #34 = Utf8               (D)V
  #35 = Utf8               (Z)V
  #36 = Utf8               Z
  #37 = Utf8               (C)V
  #38 = Utf8               SourceFile
  #39 = Utf8               K.java
{
  public static void a(int, int);
    descriptor: (II)V
    flags: (0x0009) ACC_PUBLIC, ACC_STATIC
    Code:
      stack=2, locals=3, args_size=2
         0: iload_0
         1: iload_1
         2: iadd
         3: istore_2
         4: iload_0
         5: iload_1
         6: isub
         7: istore_2
         8: iload_0
         9: iload_1
        10: imul
        11: istore_2
        12: iload_0
        13: iload_1
        14: idiv
        15: istore_2
        16: iload_0
        17: iload_1
        18: irem
        19: istore_2
        20: iload_0
        21: ineg
        22: istore_2
        23: iload_0
        24: iload_1
        25: iand
        26: istore_2
        27: iload_0
        28: iload_1
        29: ior
        30: istore_2
        31: iload_0
        32: iload_1
        33: ixor
        34: istore_2
        35: return
      LineNumberTable:
        line 16: 0
        line 17: 4
        line 18: 8
        line 19: 12
        line 20: 16
        line 21: 20
        line 22: 23
        line 23: 27
        line 24: 31
        line 25: 35
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
            0      36     0    p1   I
            0      36     1    p2   I
            4      32     2     a   I

  public static void b(long, long);
    descriptor: (JJ)V
    flags: (0x0009) ACC_PUBLIC, ACC_STATIC
    Code:
      stack=4, locals=6, args_size=2
         0: lload_0
         1: lload_2
         2: ladd
         3: lstore        4
         5: lload_0
         6: lload_2
         7: lsub
         8: lstore        4
        10: lload_0
        11: lload_2
        12: lmul
        13: lstore        4
        15: lload_0
        16: lload_2
        17: ldiv
        18: lstore        4
        20: lload_0
        21: lload_2
        22: lrem
        23: lstore        4
        25: lload_0
        26: lneg
        27: lstore        4
        29: lload_0
        30: lload_2
        31: land
        32: lstore        4
        34: lload_0
        35: lload_2
        36: lor
        37: lstore        4
        39: lload_0
        40: lload_2
        41: lxor
        42: lstore        4
        44: return
      LineNumberTable:
        line 28: 0
        line 29: 5
        line 30: 10
        line 31: 15
        line 32: 20
        line 33: 25
        line 34: 29
        line 35: 34
        line 36: 39
        line 37: 44
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
            0      45     0    p1   J
            0      45     2    p2   J
            5      40     4     a   J

  public static void c(float, float);
    descriptor: (FF)V
    flags: (0x0009) ACC_PUBLIC, ACC_STATIC
    Code:
      stack=2, locals=3, args_size=2
         0: fload_0
         1: fload_1
         2: fadd
         3: fstore_2
         4: fload_0
         5: fload_1
         6: fsub
         7: fstore_2
         8: fload_0
         9: fload_1
        10: fmul
        11: fstore_2
        12: fload_0
        13: fload_1
        14: fdiv
        15: fstore_2
        16: fload_0
        17: fload_1
        18: frem
        19: fstore_2
        20: fload_0
        21: fneg
        22: fstore_2
        23: return
      LineNumberTable:
        line 40: 0
        line 41: 4
        line 42: 8
        line 43: 12
        line 44: 16
        line 45: 20
        line 50: 23
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
            0      24     0    p1   F
            0      24     1    p2   F
            4      20     2     a   F

  public static void d(double, double);
    descriptor: (DD)V
    flags: (0x0009) ACC_PUBLIC, ACC_STATIC
    Code:
      stack=4, locals=6, args_size=2
         0: dload_0
         1: dload_2
         2: dadd
         3: dstore        4
         5: dload_0
         6: dload_2
         7: dsub
         8: dstore        4
        10: dload_0
        11: dload_2
        12: dmul
        13: dstore        4
        15: dload_0
        16: dload_2
        17: ddiv
        18: dstore        4
        20: dload_0
        21: dload_2
        22: drem
        23: dstore        4
        25: dload_0
        26: dneg
        27: dstore        4
        29: return
      LineNumberTable:
        line 53: 0
        line 54: 5
        line 55: 10
        line 56: 15
        line 57: 20
        line 58: 25
        line 63: 29
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
            0      30     0    p1   D
            0      30     2    p2   D
            5      25     4     a   D

  public static void f(byte);
    descriptor: (B)V
    flags: (0x0009) ACC_PUBLIC, ACC_STATIC
    Code:
      stack=2, locals=9, args_size=1
         0: iload_0
         1: istore_1
         2: iload_0
         3: istore_2
         4: iload_0
         5: i2l
         6: lstore_3
         7: iload_0
         8: i2f
         9: fstore        5
        11: iload_0
        12: i2d
        13: dstore        6
        15: iload_0
        16: i2c
        17: istore        8
        19: return
      LineNumberTable:
        line 67: 0
        line 68: 2
        line 69: 4
        line 70: 7
        line 71: 11
        line 73: 15
        line 74: 19
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
            0      20     0     p   B
            2      18     1     b   S
            4      16     2     c   I
            7      13     3     d   J
           11       9     5     e   F
           15       5     6     f   D
           19       1     8     h   C

  public static void f(short);
    descriptor: (S)V
    flags: (0x0009) ACC_PUBLIC, ACC_STATIC
    Code:
      stack=2, locals=9, args_size=1
         0: iload_0
         1: i2b
         2: istore_1
         3: iload_0
         4: istore_2
         5: iload_0
         6: i2l
         7: lstore_3
         8: iload_0
         9: i2f
        10: fstore        5
        12: iload_0
        13: i2d
        14: dstore        6
        16: iload_0
        17: i2c
        18: istore        8
        20: return
      LineNumberTable:
        line 77: 0
        line 79: 3
        line 80: 5
        line 81: 8
        line 82: 12
        line 84: 16
        line 85: 20
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
            0      21     0     p   S
            3      18     1     a   B
            5      16     2     c   I
            8      13     3     d   J
           12       9     5     e   F
           16       5     6     f   D
           20       1     8     h   C

  public static void f(int);
    descriptor: (I)V
    flags: (0x0009) ACC_PUBLIC, ACC_STATIC
    Code:
      stack=2, locals=9, args_size=1
         0: iload_0
         1: i2b
         2: istore_1
         3: iload_0
         4: i2s
         5: istore_2
         6: iload_0
         7: i2l
         8: lstore_3
         9: iload_0
        10: i2f
        11: fstore        5
        13: iload_0
        14: i2d
        15: dstore        6
        17: iload_0
        18: i2c
        19: istore        8
        21: return
      LineNumberTable:
        line 88: 0
        line 89: 3
        line 91: 6
        line 92: 9
        line 93: 13
        line 95: 17
        line 96: 21
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
            0      22     0     p   I
            3      19     1     a   B
            6      16     2     b   S
            9      13     3     d   J
           13       9     5     e   F
           17       5     6     f   D
           21       1     8     h   C

  public static void f(long);
    descriptor: (J)V
    flags: (0x0009) ACC_PUBLIC, ACC_STATIC
    Code:
      stack=2, locals=9, args_size=1
         0: lload_0
         1: l2i
         2: i2b
         3: istore_2
         4: lload_0
         5: l2i
         6: i2s
         7: istore_3
         8: lload_0
         9: l2i
        10: istore        4
        12: lload_0
        13: l2f
        14: fstore        5
        16: lload_0
        17: l2d
        18: dstore        6
        20: lload_0
        21: l2i
        22: i2c
        23: istore        8
        25: return
      LineNumberTable:
        line 99: 0
        line 100: 4
        line 101: 8
        line 103: 12
        line 104: 16
        line 106: 20
        line 107: 25
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
            0      26     0     p   J
            4      22     2     a   B
            8      18     3     b   S
           12      14     4     c   I
           16      10     5     e   F
           20       6     6     f   D
           25       1     8     h   C

  public static void f(float);
    descriptor: (F)V
    flags: (0x0009) ACC_PUBLIC, ACC_STATIC
    Code:
      stack=2, locals=9, args_size=1
         0: fload_0
         1: f2i
         2: i2b
         3: istore_1
         4: fload_0
         5: f2i
         6: i2s
         7: istore_2
         8: fload_0
         9: f2i
        10: istore_3
        11: fload_0
        12: f2l
        13: lstore        4
        15: fload_0
        16: f2d
        17: dstore        6
        19: fload_0
        20: f2i
        21: i2c
        22: istore        8
        24: return
      LineNumberTable:
        line 110: 0
        line 111: 4
        line 112: 8
        line 113: 11
        line 115: 15
        line 117: 19
        line 118: 24
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
            0      25     0     p   F
            4      21     1     a   B
            8      17     2     b   S
           11      14     3     c   I
           15      10     4     d   J
           19       6     6     f   D
           24       1     8     h   C

  public static void f(double);
    descriptor: (D)V
    flags: (0x0009) ACC_PUBLIC, ACC_STATIC
    Code:
      stack=2, locals=9, args_size=1
         0: dload_0
         1: d2i
         2: i2b
         3: istore_2
         4: dload_0
         5: d2i
         6: i2s
         7: istore_3
         8: dload_0
         9: d2i
        10: istore        4
        12: dload_0
        13: d2l
        14: lstore        5
        16: dload_0
        17: d2f
        18: fstore        7
        20: dload_0
        21: d2i
        22: i2c
        23: istore        8
        25: return
      LineNumberTable:
        line 121: 0
        line 122: 4
        line 123: 8
        line 124: 12
        line 125: 16
        line 128: 20
        line 129: 25
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
            0      26     0     p   D
            4      22     2     a   B
            8      18     3     b   S
           12      14     4     c   I
           16      10     5     d   J
           20       6     7     e   F
           25       1     8     h   C

  public static void f(boolean);
    descriptor: (Z)V
    flags: (0x0009) ACC_PUBLIC, ACC_STATIC
    Code:
      stack=0, locals=1, args_size=1
         0: return
      LineNumberTable:
        line 140: 0
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
            0       1     0     p   Z

  public static void f(char);
    descriptor: (C)V
    flags: (0x0009) ACC_PUBLIC, ACC_STATIC
    Code:
      stack=2, locals=9, args_size=1
         0: iload_0
         1: i2b
         2: istore_1
         3: iload_0
         4: i2s
         5: istore_2
         6: iload_0
         7: istore_3
         8: iload_0
         9: i2l
        10: lstore        4
        12: iload_0
        13: i2f
        14: fstore        6
        16: iload_0
        17: i2d
        18: dstore        7
        20: return
      LineNumberTable:
        line 143: 0
        line 144: 3
        line 145: 6
        line 146: 8
        line 147: 12
        line 148: 16
        line 151: 20
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
            0      21     0     p   C
            3      18     1     a   B
            6      15     2     b   S
            8      13     3     c   I
           12       9     4     d   J
           16       5     6     e   F
           20       1     7     f   D
}
*/
