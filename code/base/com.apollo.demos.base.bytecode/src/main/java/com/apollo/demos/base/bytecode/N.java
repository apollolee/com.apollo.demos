/*
 * 此代码创建于 2022年3月20日 下午10:26:11。
 */
package com.apollo.demos.base.bytecode;

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

    static int e() {
        int a = 0;

        for (int i = 0; i < 50; i++) {
            a = a++;
        }

        return a;
    }

    static int f() {
        int a = 0;

        for (int i = 0; i < 50; i++) {
            a = ++a;
        }

        return a;
    }

}

//-------- javap -v N --------
//public interface com.apollo.demos.base.bytecode.N
//minor version: 0
//major version: 52
//flags: (0x0601) ACC_PUBLIC, ACC_INTERFACE, ACC_ABSTRACT
//this_class: #1                          // com/apollo/demos/base/bytecode/N
//super_class: #3                         // java/lang/Object
//interfaces: 0, fields: 0, methods: 6, attributes: 1
//Constant pool:
// #1 = Class              #2             // com/apollo/demos/base/bytecode/N
// #2 = Utf8               com/apollo/demos/base/bytecode/N
// #3 = Class              #4             // java/lang/Object
// #4 = Utf8               java/lang/Object
// #5 = Utf8               a
// #6 = Utf8               (I)I
// #7 = Utf8               Code
// #8 = Utf8               LineNumberTable
// #9 = Utf8               LocalVariableTable
//#10 = Utf8               p
//#11 = Utf8               I
//#12 = Utf8               StackMapTable
//#13 = Utf8               b
//#14 = Utf8               c
//#15 = Utf8               ([I)I
//#16 = Utf8               [I
//#17 = Utf8               i
//#18 = Utf8               d
//#19 = Utf8               e
//#20 = Class              #16            // "[I"
//#21 = Utf8               ()I
//#22 = Utf8               f
//#23 = Utf8               SourceFile
//#24 = Utf8               N.java
//{
//public static int a(int);
//  descriptor: (I)I
//  flags: (0x0009) ACC_PUBLIC, ACC_STATIC
//  Code:
//    stack=1, locals=2, args_size=1
//       0: iconst_0
//       1: istore_1
//       2: goto          7
//       5: iconst_1
//       6: istore_1
//       7: iload_0
//       8: ifne          5
//      11: iload_1
//      12: ireturn
//    LineNumberTable:
//      line 9: 0
//      line 11: 2
//      line 12: 5
//      line 11: 7
//      line 15: 11
//    LocalVariableTable:
//      Start  Length  Slot  Name   Signature
//          0      13     0     p   I
//          2      11     1     a   I
//    StackMapTable: number_of_entries = 2
//      frame_type = 252 /* append */
//        offset_delta = 5
//        locals = [ int ]
//      frame_type = 1 /* same */
//
//public static int b(int);
//  descriptor: (I)I
//  flags: (0x0009) ACC_PUBLIC, ACC_STATIC
//  Code:
//    stack=1, locals=2, args_size=1
//       0: iconst_0
//       1: istore_1
//       2: iconst_1
//       3: istore_1
//       4: iload_0
//       5: ifne          2
//       8: iload_1
//       9: ireturn
//    LineNumberTable:
//      line 19: 0
//      line 22: 2
//      line 23: 4
//      line 25: 8
//    LocalVariableTable:
//      Start  Length  Slot  Name   Signature
//          0      10     0     p   I
//          2       8     1     a   I
//    StackMapTable: number_of_entries = 1
//      frame_type = 252 /* append */
//        offset_delta = 2
//        locals = [ int ]
//
//public static int c(int[]);
//  descriptor: ([I)I
//  flags: (0x0009) ACC_PUBLIC, ACC_STATIC
//  Code:
//    stack=3, locals=3, args_size=1
//       0: iconst_0
//       1: istore_1
//       2: iconst_0
//       3: istore_2
//       4: goto          16
//       7: iload_1
//       8: aload_0
//       9: iload_2
//      10: iaload
//      11: iadd
//      12: istore_1
//      13: iinc          2, 1
//      16: iload_2
//      17: aload_0
//      18: arraylength
//      19: if_icmplt     7
//      22: iload_1
//      23: ireturn
//    LineNumberTable:
//      line 29: 0
//      line 31: 2
//      line 32: 7
//      line 31: 13
//      line 35: 22
//    LocalVariableTable:
//      Start  Length  Slot  Name   Signature
//          0      24     0     p   [I
//          2      22     1     a   I
//          4      18     2     i   I
//    StackMapTable: number_of_entries = 2
//      frame_type = 253 /* append */
//        offset_delta = 7
//        locals = [ int, int ]
//      frame_type = 8 /* same */
//
//public static int d(int[]);
//  descriptor: ([I)I
//  flags: (0x0009) ACC_PUBLIC, ACC_STATIC
//  Code:
//    stack=2, locals=6, args_size=1
//       0: iconst_0
//       1: istore_1
//       2: aload_0
//       3: dup
//       4: astore        5
//       6: arraylength
//       7: istore        4
//       9: iconst_0
//      10: istore_3
//      11: goto          26
//      14: aload         5
//      16: iload_3
//      17: iaload
//      18: istore_2
//      19: iload_1
//      20: iload_2
//      21: iadd
//      22: istore_1
//      23: iinc          3, 1
//      26: iload_3
//      27: iload         4
//      29: if_icmplt     14
//      32: iload_1
//      33: ireturn
//    LineNumberTable:
//      line 39: 0
//      line 41: 2
//      line 42: 19
//      line 41: 23
//      line 45: 32
//    LocalVariableTable:
//      Start  Length  Slot  Name   Signature
//          0      34     0     p   [I
//          2      32     1     a   I
//         19       4     2     e   I
//    StackMapTable: number_of_entries = 2
//      frame_type = 255 /* full_frame */
//        offset_delta = 14
//        locals = [ class "[I", int, top, int, int, class "[I" ]
//        stack = []
//      frame_type = 11 /* same */
//
//public static int e();
//  descriptor: ()I
//  flags: (0x0009) ACC_PUBLIC, ACC_STATIC
//  Code:
//    stack=2, locals=2, args_size=0
//       0: iconst_0
//       1: istore_0
//       2: iconst_0
//       3: istore_1
//       4: goto          15
//       7: iload_0
//       8: iinc          0, 1
//      11: istore_0
//      12: iinc          1, 1
//      15: iload_1
//      16: bipush        50
//      18: if_icmplt     7
//      21: iload_0
//      22: ireturn
//    LineNumberTable:
//      line 49: 0
//      line 51: 2
//      line 52: 7
//      line 51: 12
//      line 55: 21
//    LocalVariableTable:
//      Start  Length  Slot  Name   Signature
//          2      21     0     a   I
//          4      17     1     i   I
//    StackMapTable: number_of_entries = 2
//      frame_type = 253 /* append */
//        offset_delta = 7
//        locals = [ int, int ]
//      frame_type = 7 /* same */
//
//public static int f();
//  descriptor: ()I
//  flags: (0x0009) ACC_PUBLIC, ACC_STATIC
//  Code:
//    stack=2, locals=2, args_size=0
//       0: iconst_0
//       1: istore_0
//       2: iconst_0
//       3: istore_1
//       4: goto          15
//       7: iinc          0, 1
//      10: iload_0
//      11: istore_0
//      12: iinc          1, 1
//      15: iload_1
//      16: bipush        50
//      18: if_icmplt     7
//      21: iload_0
//      22: ireturn
//    LineNumberTable:
//      line 59: 0
//      line 61: 2
//      line 62: 7
//      line 61: 12
//      line 65: 21
//    LocalVariableTable:
//      Start  Length  Slot  Name   Signature
//          2      21     0     a   I
//          4      17     1     i   I
//    StackMapTable: number_of_entries = 2
//      frame_type = 253 /* append */
//        offset_delta = 7
//        locals = [ int, int ]
//      frame_type = 7 /* same */
//}
