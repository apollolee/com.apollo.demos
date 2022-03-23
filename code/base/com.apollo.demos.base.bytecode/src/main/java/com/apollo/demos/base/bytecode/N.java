/*
 * 此代码创建于 2022年3月20日 下午10:26:11。
 */
package com.apollo.demos.base.bytecode;

//循环没有指令，都是用控制转移指令实现的。所以严格的说，循环都是语法糖。
//连for都是语法糖，更别提foreach了，差别只是foreach会自动生成一些隐藏的局部变量。
//iinc是一个较为特殊的指令，它直接针对局部变量操作，不涉及栈操作，所以效率非常高，很适合循环结构使用。
//arraylength是弹出栈顶数组对象，将数组的length压栈。
//i++和++i从字节码层面来看，更加印证了其语言层面的语义逻辑，理解应该不难。

public interface N {

    static int a(int p) {
        int a = 0;

        while (p != 0) {
            a = 1;
        }

        return a;
    }

    static int b(int p) {
        int a = 0;

        do {
            a = 1;
        } while (p != 0);

        return a;
    }

    static int c(int[] p) {
        int a = 0;

        for (int i = 0; i < p.length; i++) {
            a += p[i];
        }

        return a;
    }

    static int d(int[] p) {
        int a = 0;

        for (int e : p) {
            a += e;
        }

        return a;
    }

    static int e(int[] p) {
        int a = 0;

        for (int e : p) {
            if (e == 1) {
                a += e;
                continue;

            } else {
                a -= e;
                break;
            }
        }

        return a;
    }

    static int f() {
        int a = 0;

        for (int i = 0; i < 50; i++) {
            a = a++;
        }

        return a;
    }

    static int g() {
        int a = 0;

        for (int i = 0; i < 50; i++) {
            a = ++a;
        }

        return a;
    }

    static int h() {
        int a = 0;
        a = a++ + ++a;
        return a;
    }

}

//-------- javap -v N --------
//public interface com.apollo.demos.base.bytecode.N
//  minor version: 0
//  major version: 52
//  flags: (0x0601) ACC_PUBLIC, ACC_INTERFACE, ACC_ABSTRACT
//  this_class: #1                          // com/apollo/demos/base/bytecode/N
//  super_class: #3                         // java/lang/Object
//  interfaces: 0, fields: 0, methods: 8, attributes: 1
//Constant pool:
//   #1 = Class              #2             // com/apollo/demos/base/bytecode/N
//   #2 = Utf8               com/apollo/demos/base/bytecode/N
//   #3 = Class              #4             // java/lang/Object
//   #4 = Utf8               java/lang/Object
//   #5 = Utf8               a
//   #6 = Utf8               (I)I
//   #7 = Utf8               Code
//   #8 = Utf8               LineNumberTable
//   #9 = Utf8               LocalVariableTable
//  #10 = Utf8               p
//  #11 = Utf8               I
//  #12 = Utf8               StackMapTable
//  #13 = Utf8               b
//  #14 = Utf8               c
//  #15 = Utf8               ([I)I
//  #16 = Utf8               [I
//  #17 = Utf8               i
//  #18 = Utf8               d
//  #19 = Utf8               e
//  #20 = Class              #16            // "[I"
//  #21 = Utf8               f
//  #22 = Utf8               ()I
//  #23 = Utf8               g
//  #24 = Utf8               h
//  #25 = Utf8               SourceFile
//  #26 = Utf8               N.java
//{
//  public static int a(int);
//    descriptor: (I)I
//    flags: (0x0009) ACC_PUBLIC, ACC_STATIC
//    Code:
//      stack=1, locals=2, args_size=1
//         0: iconst_0
//         1: istore_1
//         2: goto          7
//         5: iconst_1
//         6: istore_1
//         7: iload_0
//         8: ifne          5
//        11: iload_1
//        12: ireturn
//      LineNumberTable:
//        line 15: 0
//        line 17: 2
//        line 18: 5
//        line 17: 7
//        line 21: 11
//      LocalVariableTable:
//        Start  Length  Slot  Name   Signature
//            0      13     0     p   I
//            2      11     1     a   I
//      StackMapTable: number_of_entries = 2
//        frame_type = 252 /* append */
//          offset_delta = 5
//          locals = [ int ]
//        frame_type = 1 /* same */
//
//  public static int b(int);
//    descriptor: (I)I
//    flags: (0x0009) ACC_PUBLIC, ACC_STATIC
//    Code:
//      stack=1, locals=2, args_size=1
//         0: iconst_0
//         1: istore_1
//         2: iconst_1
//         3: istore_1
//         4: iload_0
//         5: ifne          2
//         8: iload_1
//         9: ireturn
//      LineNumberTable:
//        line 25: 0
//        line 28: 2
//        line 29: 4
//        line 31: 8
//      LocalVariableTable:
//        Start  Length  Slot  Name   Signature
//            0      10     0     p   I
//            2       8     1     a   I
//      StackMapTable: number_of_entries = 1
//        frame_type = 252 /* append */
//          offset_delta = 2
//          locals = [ int ]
//
//  public static int c(int[]);
//    descriptor: ([I)I
//    flags: (0x0009) ACC_PUBLIC, ACC_STATIC
//    Code:
//      stack=3, locals=3, args_size=1
//         0: iconst_0
//         1: istore_1
//         2: iconst_0
//         3: istore_2
//         4: goto          16
//         7: iload_1
//         8: aload_0
//         9: iload_2
//        10: iaload
//        11: iadd
//        12: istore_1
//        13: iinc          2, 1
//        16: iload_2
//        17: aload_0
//        18: arraylength
//        19: if_icmplt     7
//        22: iload_1
//        23: ireturn
//      LineNumberTable:
//        line 35: 0
//        line 37: 2
//        line 38: 7
//        line 37: 13
//        line 41: 22
//      LocalVariableTable:
//        Start  Length  Slot  Name   Signature
//            0      24     0     p   [I
//            2      22     1     a   I
//            4      18     2     i   I
//      StackMapTable: number_of_entries = 2
//        frame_type = 253 /* append */
//          offset_delta = 7
//          locals = [ int, int ]
//        frame_type = 8 /* same */
//
//  public static int d(int[]);
//    descriptor: ([I)I
//    flags: (0x0009) ACC_PUBLIC, ACC_STATIC
//    Code:
//      stack=2, locals=6, args_size=1
//         0: iconst_0
//         1: istore_1
//         2: aload_0
//         3: dup
//         4: astore        5
//         6: arraylength
//         7: istore        4
//         9: iconst_0
//        10: istore_3
//        11: goto          26
//        14: aload         5
//        16: iload_3
//        17: iaload
//        18: istore_2
//        19: iload_1
//        20: iload_2
//        21: iadd
//        22: istore_1
//        23: iinc          3, 1
//        26: iload_3
//        27: iload         4
//        29: if_icmplt     14
//        32: iload_1
//        33: ireturn
//      LineNumberTable:
//        line 45: 0
//        line 47: 2
//        line 48: 19
//        line 47: 23
//        line 51: 32
//      LocalVariableTable:
//        Start  Length  Slot  Name   Signature
//            0      34     0     p   [I
//            2      32     1     a   I
//           19       4     2     e   I
//      StackMapTable: number_of_entries = 2
//        frame_type = 255 /* full_frame */
//          offset_delta = 14
//          locals = [ class "[I", int, top, int, int, class "[I" ]
//          stack = []
//        frame_type = 11 /* same */
//
//  public static int e(int[]);
//    descriptor: ([I)I
//    flags: (0x0009) ACC_PUBLIC, ACC_STATIC
//    Code:
//      stack=2, locals=6, args_size=1
//         0: iconst_0
//         1: istore_1
//         2: aload_0
//         3: dup
//         4: astore        5
//         6: arraylength
//         7: istore        4
//         9: iconst_0
//        10: istore_3
//        11: goto          41
//        14: aload         5
//        16: iload_3
//        17: iaload
//        18: istore_2
//        19: iload_2
//        20: iconst_1
//        21: if_icmpne     31
//        24: iload_1
//        25: iload_2
//        26: iadd
//        27: istore_1
//        28: goto          38
//        31: iload_1
//        32: iload_2
//        33: isub
//        34: istore_1
//        35: goto          47
//        38: iinc          3, 1
//        41: iload_3
//        42: iload         4
//        44: if_icmplt     14
//        47: iload_1
//        48: ireturn
//      LineNumberTable:
//        line 55: 0
//        line 57: 2
//        line 58: 19
//        line 59: 24
//        line 60: 28
//        line 63: 31
//        line 64: 35
//        line 57: 38
//        line 68: 47
//      LocalVariableTable:
//        Start  Length  Slot  Name   Signature
//            0      49     0     p   [I
//            2      47     1     a   I
//           19      19     2     e   I
//      StackMapTable: number_of_entries = 5
//        frame_type = 255 /* full_frame */
//          offset_delta = 14
//          locals = [ class "[I", int, top, int, int, class "[I" ]
//          stack = []
//        frame_type = 255 /* full_frame */
//          offset_delta = 16
//          locals = [ class "[I", int, int, int, int, class "[I" ]
//          stack = []
//        frame_type = 255 /* full_frame */
//          offset_delta = 6
//          locals = [ class "[I", int, top, int, int, class "[I" ]
//          stack = []
//        frame_type = 2 /* same */
//        frame_type = 255 /* full_frame */
//          offset_delta = 5
//          locals = [ class "[I", int ]
//          stack = []
//
//  public static int f();
//    descriptor: ()I
//    flags: (0x0009) ACC_PUBLIC, ACC_STATIC
//    Code:
//      stack=2, locals=2, args_size=0
//         0: iconst_0
//         1: istore_0
//         2: iconst_0
//         3: istore_1
//         4: goto          15
//         7: iload_0
//         8: iinc          0, 1
//        11: istore_0
//        12: iinc          1, 1
//        15: iload_1
//        16: bipush        50
//        18: if_icmplt     7
//        21: iload_0
//        22: ireturn
//      LineNumberTable:
//        line 72: 0
//        line 74: 2
//        line 75: 7
//        line 74: 12
//        line 78: 21
//      LocalVariableTable:
//        Start  Length  Slot  Name   Signature
//            2      21     0     a   I
//            4      17     1     i   I
//      StackMapTable: number_of_entries = 2
//        frame_type = 253 /* append */
//          offset_delta = 7
//          locals = [ int, int ]
//        frame_type = 7 /* same */
//
//  public static int g();
//    descriptor: ()I
//    flags: (0x0009) ACC_PUBLIC, ACC_STATIC
//    Code:
//      stack=2, locals=2, args_size=0
//         0: iconst_0
//         1: istore_0
//         2: iconst_0
//         3: istore_1
//         4: goto          15
//         7: iinc          0, 1
//        10: iload_0
//        11: istore_0
//        12: iinc          1, 1
//        15: iload_1
//        16: bipush        50
//        18: if_icmplt     7
//        21: iload_0
//        22: ireturn
//      LineNumberTable:
//        line 82: 0
//        line 84: 2
//        line 85: 7
//        line 84: 12
//        line 88: 21
//      LocalVariableTable:
//        Start  Length  Slot  Name   Signature
//            2      21     0     a   I
//            4      17     1     i   I
//      StackMapTable: number_of_entries = 2
//        frame_type = 253 /* append */
//          offset_delta = 7
//          locals = [ int, int ]
//        frame_type = 7 /* same */
//
//  public static int h();
//    descriptor: ()I
//    flags: (0x0009) ACC_PUBLIC, ACC_STATIC
//    Code:
//      stack=2, locals=1, args_size=0
//         0: iconst_0
//         1: istore_0
//         2: iload_0
//         3: iinc          0, 1
//         6: iinc          0, 1
//         9: iload_0
//        10: iadd
//        11: istore_0
//        12: iload_0
//        13: ireturn
//      LineNumberTable:
//        line 92: 0
//        line 93: 2
//        line 94: 12
//      LocalVariableTable:
//        Start  Length  Slot  Name   Signature
//            2      12     0     a   I
//}
