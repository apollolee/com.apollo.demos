/*
 * 此代码创建于 2022年3月20日 下午10:26:46。
 */
package com.apollo.demos.base.bytecode;

import java.io.FileOutputStream;
import java.io.IOException;

@SuppressWarnings("unused")
public interface O {

    static void a() {
        try {
            int a = 1;

        } catch (NullPointerException ex) {
            int a = 1;
        }
    }

    static void b() {
        try {
            int a = 1;

        } catch (NullPointerException ex) {
            int a = 1;

        } catch (IllegalAccessError ex) {
            int a = 1;
        }
    }

    static void c() {
        try {
            int a = 1;

        } catch (NullPointerException | IllegalAccessError ex) {
            int a = 1;
        }
    }

    static void d() {
        try {
            int a = 1;

        } catch (NullPointerException ex) {
            int a = 1;

        } finally {
            int a = 1;
        }
    }

    @SuppressWarnings("finally")
    static int e() {
        try {
            int a = 1;
            return a;

        } catch (NullPointerException ex) {
            int a = 1;
            return a;

        } finally {
            int a = 1;
            return a;
        }
    }

    static int f() {
        int a = 1;
        try {
            return a;

        } finally {
            ++a;
        }
    }

    static String g() {
        String a = "hello";
        try {
            return a;

        } finally {
            a = null;
        }
    }

    static void h() throws IOException {
        try (FileOutputStream fos = new FileOutputStream("text.txt")) {
            fos.write(1);
        }
    }

    @SuppressWarnings("finally")
    static void i() {
        try {
            throw new RuntimeException("in try");

        } finally {
            throw new RuntimeException("in finally");
        }
    }

}

//-------- javap -v O --------
//public interface com.apollo.demos.base.bytecode.O
//minor version: 0
//major version: 52
//flags: (0x0601) ACC_PUBLIC, ACC_INTERFACE, ACC_ABSTRACT
//this_class: #1                          // com/apollo/demos/base/bytecode/O
//super_class: #3                         // java/lang/Object
//interfaces: 0, fields: 0, methods: 9, attributes: 1
//Constant pool:
// #1 = Class              #2             // com/apollo/demos/base/bytecode/O
// #2 = Utf8               com/apollo/demos/base/bytecode/O
// #3 = Class              #4             // java/lang/Object
// #4 = Utf8               java/lang/Object
// #5 = Utf8               a
// #6 = Utf8               ()V
// #7 = Utf8               Code
// #8 = Class              #9             // java/lang/NullPointerException
// #9 = Utf8               java/lang/NullPointerException
//#10 = Utf8               LineNumberTable
//#11 = Utf8               LocalVariableTable
//#12 = Utf8               ex
//#13 = Utf8               Ljava/lang/NullPointerException;
//#14 = Utf8               StackMapTable
//#15 = Utf8               b
//#16 = Class              #17            // java/lang/IllegalAccessError
//#17 = Utf8               java/lang/IllegalAccessError
//#18 = Utf8               Ljava/lang/IllegalAccessError;
//#19 = Utf8               c
//#20 = Utf8               Ljava/lang/Throwable;
//#21 = Class              #22            // java/lang/Throwable
//#22 = Utf8               java/lang/Throwable
//#23 = Utf8               d
//#24 = Utf8               e
//#25 = Utf8               ()I
//#26 = Utf8               I
//#27 = Utf8               f
//#28 = Utf8               g
//#29 = Utf8               ()Ljava/lang/String;
//#30 = String             #31            // hello
//#31 = Utf8               hello
//#32 = Utf8               Ljava/lang/String;
//#33 = Class              #34            // java/lang/String
//#34 = Utf8               java/lang/String
//#35 = Utf8               h
//#36 = Utf8               Exceptions
//#37 = Class              #38            // java/io/IOException
//#38 = Utf8               java/io/IOException
//#39 = Class              #40            // java/io/FileOutputStream
//#40 = Utf8               java/io/FileOutputStream
//#41 = String             #42            // text.txt
//#42 = Utf8               text.txt
//#43 = Methodref          #39.#44        // java/io/FileOutputStream."<init>":(Ljava/lang/String;)V
//#44 = NameAndType        #45:#46        // "<init>":(Ljava/lang/String;)V
//#45 = Utf8               <init>
//#46 = Utf8               (Ljava/lang/String;)V
//#47 = Methodref          #39.#48        // java/io/FileOutputStream.write:(I)V
//#48 = NameAndType        #49:#50        // write:(I)V
//#49 = Utf8               write
//#50 = Utf8               (I)V
//#51 = Methodref          #39.#52        // java/io/FileOutputStream.close:()V
//#52 = NameAndType        #53:#6         // close:()V
//#53 = Utf8               close
//#54 = Methodref          #21.#55        // java/lang/Throwable.addSuppressed:(Ljava/lang/Throwable;)V
//#55 = NameAndType        #56:#57        // addSuppressed:(Ljava/lang/Throwable;)V
//#56 = Utf8               addSuppressed
//#57 = Utf8               (Ljava/lang/Throwable;)V
//#58 = Utf8               fos
//#59 = Utf8               Ljava/io/FileOutputStream;
//#60 = Utf8               i
//#61 = Class              #62            // java/lang/RuntimeException
//#62 = Utf8               java/lang/RuntimeException
//#63 = String             #64            // in try
//#64 = Utf8               in try
//#65 = Methodref          #61.#44        // java/lang/RuntimeException."<init>":(Ljava/lang/String;)V
//#66 = String             #67            // in finally
//#67 = Utf8               in finally
//#68 = Utf8               SourceFile
//#69 = Utf8               O.java
//{
//public static void a();
//  descriptor: ()V
//  flags: (0x0009) ACC_PUBLIC, ACC_STATIC
//  Code:
//    stack=1, locals=2, args_size=0
//       0: iconst_1
//       1: istore_0
//       2: goto          8
//       5: astore_0
//       6: iconst_1
//       7: istore_1
//       8: return
//    Exception table:
//       from    to  target type
//           0     2     5   Class java/lang/NullPointerException
//    LineNumberTable:
//      line 14: 0
//      line 16: 2
//      line 17: 6
//      line 19: 8
//    LocalVariableTable:
//      Start  Length  Slot  Name   Signature
//          6       2     0    ex   Ljava/lang/NullPointerException;
//    StackMapTable: number_of_entries = 2
//      frame_type = 69 /* same_locals_1_stack_item */
//        stack = [ class java/lang/NullPointerException ]
//      frame_type = 2 /* same */
//
//public static void b();
//  descriptor: ()V
//  flags: (0x0009) ACC_PUBLIC, ACC_STATIC
//  Code:
//    stack=1, locals=2, args_size=0
//       0: iconst_1
//       1: istore_0
//       2: goto          14
//       5: astore_0
//       6: iconst_1
//       7: istore_1
//       8: goto          14
//      11: astore_0
//      12: iconst_1
//      13: istore_1
//      14: return
//    Exception table:
//       from    to  target type
//           0     2     5   Class java/lang/NullPointerException
//           0     2    11   Class java/lang/IllegalAccessError
//    LineNumberTable:
//      line 23: 0
//      line 25: 2
//      line 26: 6
//      line 28: 11
//      line 29: 12
//      line 31: 14
//    LocalVariableTable:
//      Start  Length  Slot  Name   Signature
//          6       2     0    ex   Ljava/lang/NullPointerException;
//         12       2     0    ex   Ljava/lang/IllegalAccessError;
//    StackMapTable: number_of_entries = 3
//      frame_type = 69 /* same_locals_1_stack_item */
//        stack = [ class java/lang/NullPointerException ]
//      frame_type = 69 /* same_locals_1_stack_item */
//        stack = [ class java/lang/IllegalAccessError ]
//      frame_type = 2 /* same */
//
//public static void c();
//  descriptor: ()V
//  flags: (0x0009) ACC_PUBLIC, ACC_STATIC
//  Code:
//    stack=1, locals=2, args_size=0
//       0: iconst_1
//       1: istore_0
//       2: goto          8
//       5: astore_0
//       6: iconst_1
//       7: istore_1
//       8: return
//    Exception table:
//       from    to  target type
//           0     2     5   Class java/lang/NullPointerException
//           0     2     5   Class java/lang/IllegalAccessError
//    LineNumberTable:
//      line 35: 0
//      line 37: 2
//      line 38: 6
//      line 40: 8
//    LocalVariableTable:
//      Start  Length  Slot  Name   Signature
//          6       2     0    ex   Ljava/lang/Throwable;
//    StackMapTable: number_of_entries = 2
//      frame_type = 69 /* same_locals_1_stack_item */
//        stack = [ class java/lang/Throwable ]
//      frame_type = 2 /* same */
//
//public static void d();
//  descriptor: ()V
//  flags: (0x0009) ACC_PUBLIC, ACC_STATIC
//  Code:
//    stack=1, locals=4, args_size=0
//       0: iconst_1
//       1: istore_0
//       2: goto          18
//       5: astore_0
//       6: iconst_1
//       7: istore_1
//       8: iconst_1
//       9: istore_3
//      10: goto          20
//      13: astore_2
//      14: iconst_1
//      15: istore_3
//      16: aload_2
//      17: athrow
//      18: iconst_1
//      19: istore_3
//      20: return
//    Exception table:
//       from    to  target type
//           0     2     5   Class java/lang/NullPointerException
//           0     8    13   any
//    LineNumberTable:
//      line 44: 0
//      line 46: 2
//      line 47: 6
//      line 50: 8
//      line 49: 13
//      line 50: 14
//      line 51: 16
//      line 50: 18
//      line 52: 20
//    LocalVariableTable:
//      Start  Length  Slot  Name   Signature
//          6       2     0    ex   Ljava/lang/NullPointerException;
//    StackMapTable: number_of_entries = 4
//      frame_type = 69 /* same_locals_1_stack_item */
//        stack = [ class java/lang/NullPointerException ]
//      frame_type = 71 /* same_locals_1_stack_item */
//        stack = [ class java/lang/Throwable ]
//      frame_type = 4 /* same */
//      frame_type = 1 /* same */
//
//public static int e();
//  descriptor: ()I
//  flags: (0x0009) ACC_PUBLIC, ACC_STATIC
//  Code:
//    stack=1, locals=4, args_size=0
//       0: iconst_1
//       1: istore_0
//       2: goto          12
//       5: astore_0
//       6: iconst_1
//       7: istore_1
//       8: goto          12
//      11: pop
//      12: iconst_1
//      13: istore_3
//      14: iload_3
//      15: ireturn
//    Exception table:
//       from    to  target type
//           0     5     5   Class java/lang/NullPointerException
//           0    11    11   any
//    LineNumberTable:
//      line 57: 0
//      line 58: 2
//      line 60: 5
//      line 61: 6
//      line 62: 8
//      line 64: 11
//      line 65: 12
//      line 66: 14
//    LocalVariableTable:
//      Start  Length  Slot  Name   Signature
//          2       3     0     a   I
//          6       5     0    ex   Ljava/lang/NullPointerException;
//          8       3     1     a   I
//         14       2     3     a   I
//    StackMapTable: number_of_entries = 3
//      frame_type = 69 /* same_locals_1_stack_item */
//        stack = [ class java/lang/NullPointerException ]
//      frame_type = 69 /* same_locals_1_stack_item */
//        stack = [ class java/lang/Throwable ]
//      frame_type = 0 /* same */
//
//public static int f();
//  descriptor: ()I
//  flags: (0x0009) ACC_PUBLIC, ACC_STATIC
//  Code:
//    stack=1, locals=3, args_size=0
//       0: iconst_1
//       1: istore_0
//       2: iload_0
//       3: istore_2
//       4: iinc          0, 1
//       7: iload_2
//       8: ireturn
//       9: astore_1
//      10: iinc          0, 1
//      13: aload_1
//      14: athrow
//    Exception table:
//       from    to  target type
//           2     4     9   any
//    LineNumberTable:
//      line 71: 0
//      line 73: 2
//      line 76: 4
//      line 73: 7
//      line 75: 9
//      line 76: 10
//      line 77: 13
//    LocalVariableTable:
//      Start  Length  Slot  Name   Signature
//          2      13     0     a   I
//    StackMapTable: number_of_entries = 1
//      frame_type = 255 /* full_frame */
//        offset_delta = 9
//        locals = [ int ]
//        stack = [ class java/lang/Throwable ]
//
//public static java.lang.String g();
//  descriptor: ()Ljava/lang/String;
//  flags: (0x0009) ACC_PUBLIC, ACC_STATIC
//  Code:
//    stack=1, locals=3, args_size=0
//       0: ldc           #30                 // String hello
//       2: astore_0
//       3: aload_0
//       4: astore_2
//       5: aconst_null
//       6: astore_0
//       7: aload_2
//       8: areturn
//       9: astore_1
//      10: aconst_null
//      11: astore_0
//      12: aload_1
//      13: athrow
//    Exception table:
//       from    to  target type
//           3     5     9   any
//    LineNumberTable:
//      line 81: 0
//      line 83: 3
//      line 86: 5
//      line 83: 7
//      line 85: 9
//      line 86: 10
//      line 87: 12
//    LocalVariableTable:
//      Start  Length  Slot  Name   Signature
//          3      11     0     a   Ljava/lang/String;
//    StackMapTable: number_of_entries = 1
//      frame_type = 255 /* full_frame */
//        offset_delta = 9
//        locals = [ class java/lang/String ]
//        stack = [ class java/lang/Throwable ]
//
//public static void h() throws java.io.IOException;
//  descriptor: ()V
//  flags: (0x0009) ACC_PUBLIC, ACC_STATIC
//  Exceptions:
//    throws java.io.IOException
//  Code:
//    stack=3, locals=3, args_size=0
//       0: aconst_null
//       1: astore_0
//       2: aconst_null
//       3: astore_1
//       4: new           #39                 // class java/io/FileOutputStream
//       7: dup
//       8: ldc           #41                 // String text.txt
//      10: invokespecial #43                 // Method java/io/FileOutputStream."<init>":(Ljava/lang/String;)V
//      13: astore_2
//      14: aload_2
//      15: iconst_1
//      16: invokevirtual #47                 // Method java/io/FileOutputStream.write:(I)V
//      19: aload_2
//      20: ifnull        63
//      23: aload_2
//      24: invokevirtual #51                 // Method java/io/FileOutputStream.close:()V
//      27: goto          63
//      30: astore_0
//      31: aload_2
//      32: ifnull        39
//      35: aload_2
//      36: invokevirtual #51                 // Method java/io/FileOutputStream.close:()V
//      39: aload_0
//      40: athrow
//      41: astore_1
//      42: aload_0
//      43: ifnonnull     51
//      46: aload_1
//      47: astore_0
//      48: goto          61
//      51: aload_0
//      52: aload_1
//      53: if_acmpeq     61
//      56: aload_0
//      57: aload_1
//      58: invokevirtual #54                 // Method java/lang/Throwable.addSuppressed:(Ljava/lang/Throwable;)V
//      61: aload_0
//      62: athrow
//      63: return
//    Exception table:
//       from    to  target type
//          14    19    30   any
//           4    41    41   any
//    LineNumberTable:
//      line 91: 0
//      line 92: 14
//      line 93: 19
//      line 94: 63
//    LocalVariableTable:
//      Start  Length  Slot  Name   Signature
//         14      25     2   fos   Ljava/io/FileOutputStream;
//    StackMapTable: number_of_entries = 6
//      frame_type = 255 /* full_frame */
//        offset_delta = 30
//        locals = [ class java/lang/Throwable, class java/lang/Throwable, class java/io/FileOutputStream ]
//        stack = [ class java/lang/Throwable ]
//      frame_type = 250 /* chop */
//        offset_delta = 8
//      frame_type = 65 /* same_locals_1_stack_item */
//        stack = [ class java/lang/Throwable ]
//      frame_type = 9 /* same */
//      frame_type = 9 /* same */
//      frame_type = 249 /* chop */
//        offset_delta = 1
//
//public static void i();
//  descriptor: ()V
//  flags: (0x0009) ACC_PUBLIC, ACC_STATIC
//  Code:
//    stack=3, locals=0, args_size=0
//       0: new           #61                 // class java/lang/RuntimeException
//       3: dup
//       4: ldc           #63                 // String in try
//       6: invokespecial #65                 // Method java/lang/RuntimeException."<init>":(Ljava/lang/String;)V
//       9: athrow
//      10: pop
//      11: new           #61                 // class java/lang/RuntimeException
//      14: dup
//      15: ldc           #66                 // String in finally
//      17: invokespecial #65                 // Method java/lang/RuntimeException."<init>":(Ljava/lang/String;)V
//      20: athrow
//    Exception table:
//       from    to  target type
//           0    10    10   any
//    LineNumberTable:
//      line 99: 0
//      line 101: 10
//      line 102: 11
//    LocalVariableTable:
//      Start  Length  Slot  Name   Signature
//    StackMapTable: number_of_entries = 1
//      frame_type = 74 /* same_locals_1_stack_item */
//        stack = [ class java/lang/Throwable ]
//}
