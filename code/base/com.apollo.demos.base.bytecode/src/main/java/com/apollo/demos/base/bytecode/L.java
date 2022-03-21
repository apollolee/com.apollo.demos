/*
 * 此代码创建于 2022年3月20日 下午6:57:54。
 */
package com.apollo.demos.base.bytecode;

//控制转移指令。
//条件跳转指令都是如果xxx，就跳转到xxx，这个逻辑和语言正好是相反的。
//条件跳转指令中的条件，都是判断int类型。
//和0对比很特殊，有独立的指令，0不需要压栈，其余的数都用另外一条通用指令。所以p > 0的效率要高于p > 1
//long，float，double类型比较时，会先使用比较指令，然后用比较结果跟0进行对比来完成控制转移。所以int类型效率要高于其他类型。
//比较指令有5个，如下：
//lcmp 比较long类型值，比较规则：a=b为0，a>b为1，a<b为-1，下同。
//fcmpl 比较float类型值（当遇到NaN时，返回-1）。
//fcmpg 比较float类型值（当遇到NaN时，返回1）。
//dcmpl 比较double类型值（当遇到NaN时，返回-1）。
//dcmpg 比较double类型值（当遇到NaN时，返回1）。
//float和double为什么有2个版本，而long只有一个？因为float和double都是有编码格式的，不像long那样任何二进制值都有效，所以这两个浮点类型存在NaN:not a number，既无效值。
//byte，short，char本身就是int，指令和int完全一样。
//boolean，对象类型只支持相等和不等的判断。
//三目运算符没有特殊指令，应该算得上最早出现的语法糖之一。
//所有跳转类指令占3字节，这是由于跳转的行索引占了2字节，注意：这并不意味着一个方法最大指令行索引是65535。
//goto_w支持无条件跳转到指定的宽索引，但有条件指令都没有宽索引版本，想想也知道如何实现宽索引有条件跳转，效率肯定是低的，所以一个方法别写太长是最好的。

/*
 * 关于StackMapTable。
 * 在Java 6版本之后JVM引入了栈图(Stack Map Table)概念。为了提高验证过程的效率，在字节码规范中添加了Stack Map Table属性，以下简称栈图，其方法的code属性中存储
 * 了局部变量和操作数的类型验证以及字节码的偏移量。也就是一个method需要且仅对应一个Stack Map Table。在Java 7版本之后把栈图作为字节码文件中的强制部分。本来程序员是不需要
 * 关心JVM 中的JIT编译器的细节，也不用知道编译原理或者数据流、控制流的细节。但栈图强制了，如果要生成bytecode，必须准确知道每个字节码指令对应的局部变量和操作数栈的类型。这是因
 * 为Java7在编译的时期做了一些验证期间要做的事情，那就是类型检查，也就是栈图包含的内容。
 * 
 * 想想都比较抓狂，但是JVM 做的这一点点性能优化对整体性能提升也没起到什么卵用。Java的验证在类加载的时候只会运行一次，而占据了大部分时间的操作是IO的消耗，而不是验证过程。即使现在
 * 有了栈图，验证过程依然会执行，栈图的存在只是节省了一部分的验证时间。并且JVM的设计者还必须兼容没有栈图的验证的实现，因为Java7以前版本是没有强制栈图这个概念的，然而Java8 依然
 * 延续了栈图的字节码结构。
 * 
 * StackMapTable主要用来验证跳转前后locals、stack中的类型和大小一致。StackMapTable有多个StackMapFrame组成，第一个StackMapFrame也就是init_frame不
 * 在StackMapTable中，后一个frame需要依赖前一个frame来得到完整的信息。一个frame中包含的信息有locals、stack、offset等，具体的数据结构可以参考规范jvms-4。有几
 * 种frame我们会经常碰到，same_frame、same_frame_extended 、append_frame,例如append_frame就是和前面一个frame相比，这个frame增加了局部变量，这也是
 * 前面说的后一个frame要依靠前一个frame来获取完整信息。
 * 
 * 一般编译器碰到一个跳转指令（ifxx/goto等）就会生成一个frame来描述跳转处的locals情况好到了链接时校验方法字节码的时候会把方法的所有指令都线性扫一遍，碰到store类指
 * 令（istroe、fstore等）就会把在init_frame.locals中保存一个type，碰到跳转指令就会把init_frame和StackMapTable中对应offset的frame对比看看
 * locals stack 类型 大小是否一致来判断跳转前后局部变量是否发生变化。
 */

public interface L {

    static int a(int p) {
        int a = 0;

        if (p == 0) { //3: ifne          8 解释：弹出栈顶数据，并判断，不为0则跳转到序号为8的指令。
            a = 1;
        }

        if (p != 0) { //9: ifeq          14 解释：弹出栈顶数据，并判断，为0则跳转到序号为14的指令。
            a = 1;
        }

        if (p < 0) {
            a = 1;
        }

        if (p <= 0) {
            a = 1;
        }

        if (p > 0) {
            a = 1;
        }

        if (p >= 0) {
            a = 1;
        }

        if (p >= 0) {
            a = 1;
        }

        return a;
    }

    static int b(int p) {
        int a = 0;

        if (p == 1) { //4: if_icmpne     9 解释：弹出栈顶两个数据，并判断，不为0则跳转到序号为9的指令。
            a = 1;
        }

        if (p != 1) { //11: if_icmpeq     16 解释：弹出栈顶两个数据，并判断，为0则跳转到序号为16的指令。
            a = 1;
        }

        if (p < 1) {
            a = 1;
        }

        if (p <= 1) {
            a = 1;
        }

        if (p > 1) {
            a = 1;
        }

        if (p >= 1) {
            a = 1;
        }

        if (p >= 1) {
            a = 1;
        }

        return a;
    }

    static int b(long p) {
        int a = 0;

        if (p == 1) {
            a = 1;
        }
        //4: lcmp             解释：弹出栈顶两数据进行比较，a=b为0，a>b为1，a<b为-1，对比结果为int类型，把对比结果压栈。
        //5: ifne          10 解释：弹出栈顶数据，并判断，不为0则跳转到序号为10的指令。

        if (p != 1) {
            a = 1;
        }

        if (p < 1) {
            a = 1;
        }

        if (p <= 1) {
            a = 1;
        }

        if (p > 1) {
            a = 1;
        }

        if (p >= 1) {
            a = 1;
        }

        if (p >= 1) {
            a = 1;
        }

        return a;
    }

    static int b(float p) {
        int a = 0;

        if (p == 1) {
            a = 1;
        }

        if (p != 1) {
            a = 1;
        }

        if (p < 1) {
            a = 1;
        }

        if (p <= 1) {
            a = 1;
        }

        if (p > 1) {
            a = 1;
        }

        if (p >= 1) {
            a = 1;
        }

        if (p >= 1) {
            a = 1;
        }

        return a;
    }

    static int b(double p) {
        int a = 0;

        if (p == 1) {
            a = 1;
        }

        if (p != 1) {
            a = 1;
        }

        if (p < 1) {
            a = 1;
        }

        if (p <= 1) {
            a = 1;
        }

        if (p > 1) {
            a = 1;
        }

        if (p >= 1) {
            a = 1;
        }

        if (p >= 1) {
            a = 1;
        }

        return a;
    }

    static int c(boolean p) {
        int a = 0;

        if (p == true) { //和下面if (p)的写法生成的字节码一模一样。
            a = 1;
        }

        if (p) {
            a = 1;
        }

        if (p == false) {
            a = 1;
        }

        if (!p) {
            a = 1;
        }

        return a;
    }

    static int d(String p1, String p2) {
        int a = 0;

        if (p1 == p2) {
            a = 1;
        }

        if (p1 != p2) {
            a = 1;
        }

        if (p1 == null) {
            a = 1;
        }

        if (p1 != null) {
            a = 1;
        }

        return a;
    }

    static int e(int p1, int p2) {
        return p1 > p2 ? p1 : p2;
    }

    static int f(int p1, int p2) {
        if (p1 > p2) {
            return p1;

        } else {
            return p2;
        }
    }

}

//-------- javap -v L --------
//public interface com.apollo.demos.base.bytecode.L
//minor version: 0
//major version: 52
//flags: (0x0601) ACC_PUBLIC, ACC_INTERFACE, ACC_ABSTRACT
//this_class: #1                          // com/apollo/demos/base/bytecode/L
//super_class: #3                         // java/lang/Object
//interfaces: 0, fields: 0, methods: 9, attributes: 1
//Constant pool:
// #1 = Class              #2             // com/apollo/demos/base/bytecode/L
// #2 = Utf8               com/apollo/demos/base/bytecode/L
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
//#14 = Utf8               (J)I
//#15 = Utf8               J
//#16 = Utf8               (F)I
//#17 = Utf8               F
//#18 = Utf8               (D)I
//#19 = Utf8               D
//#20 = Utf8               c
//#21 = Utf8               (Z)I
//#22 = Utf8               Z
//#23 = Utf8               d
//#24 = Utf8               (Ljava/lang/String;Ljava/lang/String;)I
//#25 = Utf8               p1
//#26 = Utf8               Ljava/lang/String;
//#27 = Utf8               p2
//#28 = Utf8               e
//#29 = Utf8               (II)I
//#30 = Utf8               f
//#31 = Utf8               SourceFile
//#32 = Utf8               L.java
//{
//public static int a(int);
//  descriptor: (I)I
//  flags: (0x0009) ACC_PUBLIC, ACC_STATIC
//  Code:
//    stack=1, locals=2, args_size=1
//       0: iconst_0
//       1: istore_1
//       2: iload_0
//       3: ifne          8
//       6: iconst_1
//       7: istore_1
//       8: iload_0
//       9: ifeq          14
//      12: iconst_1
//      13: istore_1
//      14: iload_0
//      15: ifge          20
//      18: iconst_1
//      19: istore_1
//      20: iload_0
//      21: ifgt          26
//      24: iconst_1
//      25: istore_1
//      26: iload_0
//      27: ifle          32
//      30: iconst_1
//      31: istore_1
//      32: iload_0
//      33: iflt          38
//      36: iconst_1
//      37: istore_1
//      38: iload_0
//      39: iflt          44
//      42: iconst_1
//      43: istore_1
//      44: iload_1
//      45: ireturn
//    LineNumberTable:
//      line 9: 0
//      line 11: 2
//      line 12: 6
//      line 15: 8
//      line 16: 12
//      line 19: 14
//      line 20: 18
//      line 23: 20
//      line 24: 24
//      line 27: 26
//      line 28: 30
//      line 31: 32
//      line 32: 36
//      line 35: 38
//      line 36: 42
//      line 39: 44
//    LocalVariableTable:
//      Start  Length  Slot  Name   Signature
//          0      46     0     p   I
//          2      44     1     a   I
//    StackMapTable: number_of_entries = 7
//      frame_type = 252 /* append */
//        offset_delta = 8
//        locals = [ int ]
//      frame_type = 5 /* same */
//      frame_type = 5 /* same */
//      frame_type = 5 /* same */
//      frame_type = 5 /* same */
//      frame_type = 5 /* same */
//      frame_type = 5 /* same */
//
//public static int b(int);
//  descriptor: (I)I
//  flags: (0x0009) ACC_PUBLIC, ACC_STATIC
//  Code:
//    stack=2, locals=2, args_size=1
//       0: iconst_0
//       1: istore_1
//       2: iload_0
//       3: iconst_1
//       4: if_icmpne     9
//       7: iconst_1
//       8: istore_1
//       9: iload_0
//      10: iconst_1
//      11: if_icmpeq     16
//      14: iconst_1
//      15: istore_1
//      16: iload_0
//      17: iconst_1
//      18: if_icmpge     23
//      21: iconst_1
//      22: istore_1
//      23: iload_0
//      24: iconst_1
//      25: if_icmpgt     30
//      28: iconst_1
//      29: istore_1
//      30: iload_0
//      31: iconst_1
//      32: if_icmple     37
//      35: iconst_1
//      36: istore_1
//      37: iload_0
//      38: iconst_1
//      39: if_icmplt     44
//      42: iconst_1
//      43: istore_1
//      44: iload_0
//      45: iconst_1
//      46: if_icmplt     51
//      49: iconst_1
//      50: istore_1
//      51: iload_1
//      52: ireturn
//    LineNumberTable:
//      line 43: 0
//      line 45: 2
//      line 46: 7
//      line 49: 9
//      line 50: 14
//      line 53: 16
//      line 54: 21
//      line 57: 23
//      line 58: 28
//      line 61: 30
//      line 62: 35
//      line 65: 37
//      line 66: 42
//      line 69: 44
//      line 70: 49
//      line 73: 51
//    LocalVariableTable:
//      Start  Length  Slot  Name   Signature
//          0      53     0     p   I
//          2      51     1     a   I
//    StackMapTable: number_of_entries = 7
//      frame_type = 252 /* append */
//        offset_delta = 9
//        locals = [ int ]
//      frame_type = 6 /* same */
//      frame_type = 6 /* same */
//      frame_type = 6 /* same */
//      frame_type = 6 /* same */
//      frame_type = 6 /* same */
//      frame_type = 6 /* same */
//
//public static int b(long);
//  descriptor: (J)I
//  flags: (0x0009) ACC_PUBLIC, ACC_STATIC
//  Code:
//    stack=4, locals=3, args_size=1
//       0: iconst_0
//       1: istore_2
//       2: lload_0
//       3: lconst_1
//       4: lcmp
//       5: ifne          10
//       8: iconst_1
//       9: istore_2
//      10: lload_0
//      11: lconst_1
//      12: lcmp
//      13: ifeq          18
//      16: iconst_1
//      17: istore_2
//      18: lload_0
//      19: lconst_1
//      20: lcmp
//      21: ifge          26
//      24: iconst_1
//      25: istore_2
//      26: lload_0
//      27: lconst_1
//      28: lcmp
//      29: ifgt          34
//      32: iconst_1
//      33: istore_2
//      34: lload_0
//      35: lconst_1
//      36: lcmp
//      37: ifle          42
//      40: iconst_1
//      41: istore_2
//      42: lload_0
//      43: lconst_1
//      44: lcmp
//      45: iflt          50
//      48: iconst_1
//      49: istore_2
//      50: lload_0
//      51: lconst_1
//      52: lcmp
//      53: iflt          58
//      56: iconst_1
//      57: istore_2
//      58: iload_2
//      59: ireturn
//    LineNumberTable:
//      line 77: 0
//      line 79: 2
//      line 80: 8
//      line 83: 10
//      line 84: 16
//      line 87: 18
//      line 88: 24
//      line 91: 26
//      line 92: 32
//      line 95: 34
//      line 96: 40
//      line 99: 42
//      line 100: 48
//      line 103: 50
//      line 104: 56
//      line 107: 58
//    LocalVariableTable:
//      Start  Length  Slot  Name   Signature
//          0      60     0     p   J
//          2      58     2     a   I
//    StackMapTable: number_of_entries = 7
//      frame_type = 252 /* append */
//        offset_delta = 10
//        locals = [ int ]
//      frame_type = 7 /* same */
//      frame_type = 7 /* same */
//      frame_type = 7 /* same */
//      frame_type = 7 /* same */
//      frame_type = 7 /* same */
//      frame_type = 7 /* same */
//
//public static int b(float);
//  descriptor: (F)I
//  flags: (0x0009) ACC_PUBLIC, ACC_STATIC
//  Code:
//    stack=2, locals=2, args_size=1
//       0: iconst_0
//       1: istore_1
//       2: fload_0
//       3: fconst_1
//       4: fcmpl
//       5: ifne          10
//       8: iconst_1
//       9: istore_1
//      10: fload_0
//      11: fconst_1
//      12: fcmpl
//      13: ifeq          18
//      16: iconst_1
//      17: istore_1
//      18: fload_0
//      19: fconst_1
//      20: fcmpg
//      21: ifge          26
//      24: iconst_1
//      25: istore_1
//      26: fload_0
//      27: fconst_1
//      28: fcmpg
//      29: ifgt          34
//      32: iconst_1
//      33: istore_1
//      34: fload_0
//      35: fconst_1
//      36: fcmpl
//      37: ifle          42
//      40: iconst_1
//      41: istore_1
//      42: fload_0
//      43: fconst_1
//      44: fcmpl
//      45: iflt          50
//      48: iconst_1
//      49: istore_1
//      50: fload_0
//      51: fconst_1
//      52: fcmpl
//      53: iflt          58
//      56: iconst_1
//      57: istore_1
//      58: iload_1
//      59: ireturn
//    LineNumberTable:
//      line 111: 0
//      line 113: 2
//      line 114: 8
//      line 117: 10
//      line 118: 16
//      line 121: 18
//      line 122: 24
//      line 125: 26
//      line 126: 32
//      line 129: 34
//      line 130: 40
//      line 133: 42
//      line 134: 48
//      line 137: 50
//      line 138: 56
//      line 141: 58
//    LocalVariableTable:
//      Start  Length  Slot  Name   Signature
//          0      60     0     p   F
//          2      58     1     a   I
//    StackMapTable: number_of_entries = 7
//      frame_type = 252 /* append */
//        offset_delta = 10
//        locals = [ int ]
//      frame_type = 7 /* same */
//      frame_type = 7 /* same */
//      frame_type = 7 /* same */
//      frame_type = 7 /* same */
//      frame_type = 7 /* same */
//      frame_type = 7 /* same */
//
//public static int b(double);
//  descriptor: (D)I
//  flags: (0x0009) ACC_PUBLIC, ACC_STATIC
//  Code:
//    stack=4, locals=3, args_size=1
//       0: iconst_0
//       1: istore_2
//       2: dload_0
//       3: dconst_1
//       4: dcmpl
//       5: ifne          10
//       8: iconst_1
//       9: istore_2
//      10: dload_0
//      11: dconst_1
//      12: dcmpl
//      13: ifeq          18
//      16: iconst_1
//      17: istore_2
//      18: dload_0
//      19: dconst_1
//      20: dcmpg
//      21: ifge          26
//      24: iconst_1
//      25: istore_2
//      26: dload_0
//      27: dconst_1
//      28: dcmpg
//      29: ifgt          34
//      32: iconst_1
//      33: istore_2
//      34: dload_0
//      35: dconst_1
//      36: dcmpl
//      37: ifle          42
//      40: iconst_1
//      41: istore_2
//      42: dload_0
//      43: dconst_1
//      44: dcmpl
//      45: iflt          50
//      48: iconst_1
//      49: istore_2
//      50: dload_0
//      51: dconst_1
//      52: dcmpl
//      53: iflt          58
//      56: iconst_1
//      57: istore_2
//      58: iload_2
//      59: ireturn
//    LineNumberTable:
//      line 145: 0
//      line 147: 2
//      line 148: 8
//      line 151: 10
//      line 152: 16
//      line 155: 18
//      line 156: 24
//      line 159: 26
//      line 160: 32
//      line 163: 34
//      line 164: 40
//      line 167: 42
//      line 168: 48
//      line 171: 50
//      line 172: 56
//      line 175: 58
//    LocalVariableTable:
//      Start  Length  Slot  Name   Signature
//          0      60     0     p   D
//          2      58     2     a   I
//    StackMapTable: number_of_entries = 7
//      frame_type = 252 /* append */
//        offset_delta = 10
//        locals = [ int ]
//      frame_type = 7 /* same */
//      frame_type = 7 /* same */
//      frame_type = 7 /* same */
//      frame_type = 7 /* same */
//      frame_type = 7 /* same */
//      frame_type = 7 /* same */
//
//public static int c(boolean);
//  descriptor: (Z)I
//  flags: (0x0009) ACC_PUBLIC, ACC_STATIC
//  Code:
//    stack=1, locals=2, args_size=1
//       0: iconst_0
//       1: istore_1
//       2: iload_0
//       3: ifeq          8
//       6: iconst_1
//       7: istore_1
//       8: iload_0
//       9: ifeq          14
//      12: iconst_1
//      13: istore_1
//      14: iload_0
//      15: ifne          20
//      18: iconst_1
//      19: istore_1
//      20: iload_0
//      21: ifne          26
//      24: iconst_1
//      25: istore_1
//      26: iload_1
//      27: ireturn
//    LineNumberTable:
//      line 179: 0
//      line 181: 2
//      line 182: 6
//      line 185: 8
//      line 186: 12
//      line 189: 14
//      line 190: 18
//      line 193: 20
//      line 194: 24
//      line 197: 26
//    LocalVariableTable:
//      Start  Length  Slot  Name   Signature
//          0      28     0     p   Z
//          2      26     1     a   I
//    StackMapTable: number_of_entries = 4
//      frame_type = 252 /* append */
//        offset_delta = 8
//        locals = [ int ]
//      frame_type = 5 /* same */
//      frame_type = 5 /* same */
//      frame_type = 5 /* same */
//
//public static int d(java.lang.String, java.lang.String);
//  descriptor: (Ljava/lang/String;Ljava/lang/String;)I
//  flags: (0x0009) ACC_PUBLIC, ACC_STATIC
//  Code:
//    stack=2, locals=3, args_size=2
//       0: iconst_0
//       1: istore_2
//       2: aload_0
//       3: aload_1
//       4: if_acmpne     9
//       7: iconst_1
//       8: istore_2
//       9: aload_0
//      10: aload_1
//      11: if_acmpeq     16
//      14: iconst_1
//      15: istore_2
//      16: aload_0
//      17: ifnonnull     22
//      20: iconst_1
//      21: istore_2
//      22: aload_0
//      23: ifnull        28
//      26: iconst_1
//      27: istore_2
//      28: iload_2
//      29: ireturn
//    LineNumberTable:
//      line 201: 0
//      line 203: 2
//      line 204: 7
//      line 207: 9
//      line 208: 14
//      line 211: 16
//      line 212: 20
//      line 215: 22
//      line 216: 26
//      line 219: 28
//    LocalVariableTable:
//      Start  Length  Slot  Name   Signature
//          0      30     0    p1   Ljava/lang/String;
//          0      30     1    p2   Ljava/lang/String;
//          2      28     2     a   I
//    StackMapTable: number_of_entries = 4
//      frame_type = 252 /* append */
//        offset_delta = 9
//        locals = [ int ]
//      frame_type = 6 /* same */
//      frame_type = 5 /* same */
//      frame_type = 5 /* same */
//
//public static int e(int, int);
//  descriptor: (II)I
//  flags: (0x0009) ACC_PUBLIC, ACC_STATIC
//  Code:
//    stack=2, locals=2, args_size=2
//       0: iload_0
//       1: iload_1
//       2: if_icmple     9
//       5: iload_0
//       6: goto          10
//       9: iload_1
//      10: ireturn
//    LineNumberTable:
//      line 223: 0
//    LocalVariableTable:
//      Start  Length  Slot  Name   Signature
//          0      11     0    p1   I
//          0      11     1    p2   I
//    StackMapTable: number_of_entries = 2
//      frame_type = 9 /* same */
//      frame_type = 64 /* same_locals_1_stack_item */
//        stack = [ int ]
//
//public static int f(int, int);
//  descriptor: (II)I
//  flags: (0x0009) ACC_PUBLIC, ACC_STATIC
//  Code:
//    stack=2, locals=2, args_size=2
//       0: iload_0
//       1: iload_1
//       2: if_icmple     7
//       5: iload_0
//       6: ireturn
//       7: iload_1
//       8: ireturn
//    LineNumberTable:
//      line 227: 0
//      line 228: 5
//      line 231: 7
//    LocalVariableTable:
//      Start  Length  Slot  Name   Signature
//          0       9     0    p1   I
//          0       9     1    p2   I
//    StackMapTable: number_of_entries = 1
//      frame_type = 7 /* same */
//}
