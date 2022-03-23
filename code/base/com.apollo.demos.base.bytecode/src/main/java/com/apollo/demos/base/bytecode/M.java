/*
 * 此代码创建于 2022年3月20日 下午10:22:00。
 */
package com.apollo.demos.base.bytecode;

import java.lang.annotation.RetentionPolicy;

//switch可能是占用字节最长的指令。
//switch指令只能接受int类型的case。
//编译器会根据case的稀疏程度选择具体指令，tableswitch效率高，但需要补充断层case以实现O(1)时间复杂度的查找，lookupswitch则使用二分查找，时间复杂度为O(log n)。
//switch字符串时会先把String转为hashCode，再把hashCode作为case，最后在case中还要调用String.equals做最终判断，这是因为hashCode并不唯一。
//如果case多个字符串的hashCode相同，还会在case中加入类似elseif的分支进一步处理。所以总体来说，switch字符串的效率要远低于int，甚至还不如直接使用ifelse做分支控制。
//switch枚举需要生成静态方法辅助实现，也有一定开销，但由于Enum.ordinal的存在，一个潜在的好处是switch枚举能固定使用效率较高的tableswitch，而且还不用补充断层。

public interface M {

    static int a(int p) {
        int a = 0;

        switch (p) {
        case 1:
            a = 1;
            break;
        case 2:
            a = 1;
            break;
        case 3:
            a = 1;
            break;
        case 4:
            a = 1;
            break;
        case 5:
            a = 1;
            break;

        default:
            a = 1;
            break;
        }

        return a;
    }

    static int b(int p) {
        int a = 0;

        switch (p) {
        case 1:
            a = 1;
            break;
        case 5:
            a = 1;
            break;

        default:
            a = 1;
            break;
        }

        return a;
    }

    static int c(int p) {
        int a = 0;

        switch (p) {
        case 1:
            a = 1;
            break;
        case 2:
        case 3:
        case 4:
        case 5:
            a = 1;
            break;

        default:
            a = 1;
            break;
        }

        return a;
    }

    static int d(int p) {
        int a = 0;

        switch (p) {
        case 1:
            a = 1;
            break;
        case 200:
            a = 1;
            break;
        case 300:
            a = 1;
            break;
        case 400:
            a = 1;
            break;
        case 500:
            a = 1;
            break;

        default:
            a = 1;
            break;
        }

        return a;
    }

    static int e(String p) {
        int a = 0;

        switch (p) {
        case "1":
            a = 1;
            break;
        case "2":
            a = 1;
            break;
        case "3":
            a = 1;
            break;
        case "4":
            a = 1;
            break;
        case "5":
            a = 1;
            break;

        default:
            a = 1;
            break;
        }

        return a;
    }

    static int f(String p) {
        int a = 0;

        switch (p) {
        case "Aa":
            a = 1;
            break;
        case "BB":
            a = 1;
            break;

        default:
            a = 1;
            break;
        }

        return a;
    }

    static int g(RetentionPolicy p) {
        int a = 0;

        switch (p) {
        case SOURCE:
            a = 1;
            break;
        case CLASS:
            a = 1;
            break;
        case RUNTIME:
            a = 1;
            break;

        default:
            a = 1;
            break;
        }

        return a;
    }

}

//-------- javap -v M --------
//public interface com.apollo.demos.base.bytecode.M
//  minor version: 0
//  major version: 52
//  flags: (0x0601) ACC_PUBLIC, ACC_INTERFACE, ACC_ABSTRACT
//  this_class: #1                          // com/apollo/demos/base/bytecode/M
//  super_class: #3                         // java/lang/Object
//  interfaces: 0, fields: 1, methods: 8, attributes: 1
//Constant pool:
//   #1 = Class              #2             // com/apollo/demos/base/bytecode/M
//   #2 = Utf8               com/apollo/demos/base/bytecode/M
//   #3 = Class              #4             // java/lang/Object
//   #4 = Utf8               java/lang/Object
//   #5 = Utf8               $SWITCH_TABLE$java$lang$annotation$RetentionPolicy
//   #6 = Utf8               [I
//   #7 = Utf8               a
//   #8 = Utf8               (I)I
//   #9 = Utf8               Code
//  #10 = Utf8               LineNumberTable
//  #11 = Utf8               LocalVariableTable
//  #12 = Utf8               p
//  #13 = Utf8               I
//  #14 = Utf8               StackMapTable
//  #15 = Utf8               b
//  #16 = Utf8               c
//  #17 = Utf8               d
//  #18 = Utf8               e
//  #19 = Utf8               (Ljava/lang/String;)I
//  #20 = Methodref          #21.#23        // java/lang/String.hashCode:()I
//  #21 = Class              #22            // java/lang/String
//  #22 = Utf8               java/lang/String
//  #23 = NameAndType        #24:#25        // hashCode:()I
//  #24 = Utf8               hashCode
//  #25 = Utf8               ()I
//  #26 = String             #27            // 1
//  #27 = Utf8               1
//  #28 = Methodref          #21.#29        // java/lang/String.equals:(Ljava/lang/Object;)Z
//  #29 = NameAndType        #30:#31        // equals:(Ljava/lang/Object;)Z
//  #30 = Utf8               equals
//  #31 = Utf8               (Ljava/lang/Object;)Z
//  #32 = String             #33            // 2
//  #33 = Utf8               2
//  #34 = String             #35            // 3
//  #35 = Utf8               3
//  #36 = String             #37            // 4
//  #37 = Utf8               4
//  #38 = String             #39            // 5
//  #39 = Utf8               5
//  #40 = Utf8               Ljava/lang/String;
//  #41 = Utf8               f
//  #42 = String             #43            // Aa
//  #43 = Utf8               Aa
//  #44 = String             #45            // BB
//  #45 = Utf8               BB
//  #46 = Utf8               g
//  #47 = Utf8               (Ljava/lang/annotation/RetentionPolicy;)I
//  #48 = InterfaceMethodref #1.#49         // com/apollo/demos/base/bytecode/M.$SWITCH_TABLE$java$lang$annotation$RetentionPolicy:()[I
//  #49 = NameAndType        #5:#50         // $SWITCH_TABLE$java$lang$annotation$RetentionPolicy:()[I
//  #50 = Utf8               ()[I
//  #51 = Methodref          #52.#54        // java/lang/annotation/RetentionPolicy.ordinal:()I
//  #52 = Class              #53            // java/lang/annotation/RetentionPolicy
//  #53 = Utf8               java/lang/annotation/RetentionPolicy
//  #54 = NameAndType        #55:#25        // ordinal:()I
//  #55 = Utf8               ordinal
//  #56 = Utf8               Ljava/lang/annotation/RetentionPolicy;
//  #57 = Fieldref           #1.#58         // com/apollo/demos/base/bytecode/M.$SWITCH_TABLE$java$lang$annotation$RetentionPolicy:[I
//  #58 = NameAndType        #5:#6          // $SWITCH_TABLE$java$lang$annotation$RetentionPolicy:[I
//  #59 = Methodref          #52.#60        // java/lang/annotation/RetentionPolicy.values:()[Ljava/lang/annotation/RetentionPolicy;
//  #60 = NameAndType        #61:#62        // values:()[Ljava/lang/annotation/RetentionPolicy;
//  #61 = Utf8               values
//  #62 = Utf8               ()[Ljava/lang/annotation/RetentionPolicy;
//  #63 = Fieldref           #52.#64        // java/lang/annotation/RetentionPolicy.CLASS:Ljava/lang/annotation/RetentionPolicy;
//  #64 = NameAndType        #65:#56        // CLASS:Ljava/lang/annotation/RetentionPolicy;
//  #65 = Utf8               CLASS
//  #66 = Fieldref           #52.#67        // java/lang/annotation/RetentionPolicy.RUNTIME:Ljava/lang/annotation/RetentionPolicy;
//  #67 = NameAndType        #68:#56        // RUNTIME:Ljava/lang/annotation/RetentionPolicy;
//  #68 = Utf8               RUNTIME
//  #69 = Fieldref           #52.#70        // java/lang/annotation/RetentionPolicy.SOURCE:Ljava/lang/annotation/RetentionPolicy;
//  #70 = NameAndType        #71:#56        // SOURCE:Ljava/lang/annotation/RetentionPolicy;
//  #71 = Utf8               SOURCE
//  #72 = Class              #73            // java/lang/NoSuchFieldError
//  #73 = Utf8               java/lang/NoSuchFieldError
//  #74 = Class              #6             // "[I"
//  #75 = Utf8               SourceFile
//  #76 = Utf8               M.java
//{
//  public static final int[] $SWITCH_TABLE$java$lang$annotation$RetentionPolicy;
//    descriptor: [I
//    flags: (0x1019) ACC_PUBLIC, ACC_STATIC, ACC_FINAL, ACC_SYNTHETIC
//
//  public static int a(int);
//    descriptor: (I)I
//    flags: (0x0009) ACC_PUBLIC, ACC_STATIC
//    Code:
//      stack=1, locals=2, args_size=1
//         0: iconst_0
//         1: istore_1
//         2: iload_0
//         3: tableswitch   { // 1 to 5
//                       1: 36
//                       2: 41
//                       3: 46
//                       4: 51
//                       5: 56
//                 default: 61
//            }
//        36: iconst_1
//        37: istore_1
//        38: goto          63
//        41: iconst_1
//        42: istore_1
//        43: goto          63
//        46: iconst_1
//        47: istore_1
//        48: goto          63
//        51: iconst_1
//        52: istore_1
//        53: goto          63
//        56: iconst_1
//        57: istore_1
//        58: goto          63
//        61: iconst_1
//        62: istore_1
//        63: iload_1
//        64: ireturn
//      LineNumberTable:
//        line 18: 0
//        line 20: 2
//        line 22: 36
//        line 23: 38
//        line 25: 41
//        line 26: 43
//        line 28: 46
//        line 29: 48
//        line 31: 51
//        line 32: 53
//        line 34: 56
//        line 35: 58
//        line 38: 61
//        line 42: 63
//      LocalVariableTable:
//        Start  Length  Slot  Name   Signature
//            0      65     0     p   I
//            2      63     1     a   I
//      StackMapTable: number_of_entries = 7
//        frame_type = 252 /* append */
//          offset_delta = 36
//          locals = [ int ]
//        frame_type = 4 /* same */
//        frame_type = 4 /* same */
//        frame_type = 4 /* same */
//        frame_type = 4 /* same */
//        frame_type = 4 /* same */
//        frame_type = 1 /* same */
//
//  public static int b(int);
//    descriptor: (I)I
//    flags: (0x0009) ACC_PUBLIC, ACC_STATIC
//    Code:
//      stack=1, locals=2, args_size=1
//         0: iconst_0
//         1: istore_1
//         2: iload_0
//         3: tableswitch   { // 1 to 5
//                       1: 36
//                       2: 46
//                       3: 46
//                       4: 46
//                       5: 41
//                 default: 46
//            }
//        36: iconst_1
//        37: istore_1
//        38: goto          48
//        41: iconst_1
//        42: istore_1
//        43: goto          48
//        46: iconst_1
//        47: istore_1
//        48: iload_1
//        49: ireturn
//      LineNumberTable:
//        line 46: 0
//        line 48: 2
//        line 50: 36
//        line 51: 38
//        line 53: 41
//        line 54: 43
//        line 57: 46
//        line 61: 48
//      LocalVariableTable:
//        Start  Length  Slot  Name   Signature
//            0      50     0     p   I
//            2      48     1     a   I
//      StackMapTable: number_of_entries = 4
//        frame_type = 252 /* append */
//          offset_delta = 36
//          locals = [ int ]
//        frame_type = 4 /* same */
//        frame_type = 4 /* same */
//        frame_type = 1 /* same */
//
//  public static int c(int);
//    descriptor: (I)I
//    flags: (0x0009) ACC_PUBLIC, ACC_STATIC
//    Code:
//      stack=1, locals=2, args_size=1
//         0: iconst_0
//         1: istore_1
//         2: iload_0
//         3: tableswitch   { // 1 to 5
//                       1: 36
//                       2: 41
//                       3: 41
//                       4: 41
//                       5: 41
//                 default: 46
//            }
//        36: iconst_1
//        37: istore_1
//        38: goto          48
//        41: iconst_1
//        42: istore_1
//        43: goto          48
//        46: iconst_1
//        47: istore_1
//        48: iload_1
//        49: ireturn
//      LineNumberTable:
//        line 65: 0
//        line 67: 2
//        line 69: 36
//        line 70: 38
//        line 75: 41
//        line 76: 43
//        line 79: 46
//        line 83: 48
//      LocalVariableTable:
//        Start  Length  Slot  Name   Signature
//            0      50     0     p   I
//            2      48     1     a   I
//      StackMapTable: number_of_entries = 4
//        frame_type = 252 /* append */
//          offset_delta = 36
//          locals = [ int ]
//        frame_type = 4 /* same */
//        frame_type = 4 /* same */
//        frame_type = 1 /* same */
//
//  public static int d(int);
//    descriptor: (I)I
//    flags: (0x0009) ACC_PUBLIC, ACC_STATIC
//    Code:
//      stack=1, locals=2, args_size=1
//         0: iconst_0
//         1: istore_1
//         2: iload_0
//         3: lookupswitch  { // 5
//                       1: 52
//                     200: 57
//                     300: 62
//                     400: 67
//                     500: 72
//                 default: 77
//            }
//        52: iconst_1
//        53: istore_1
//        54: goto          79
//        57: iconst_1
//        58: istore_1
//        59: goto          79
//        62: iconst_1
//        63: istore_1
//        64: goto          79
//        67: iconst_1
//        68: istore_1
//        69: goto          79
//        72: iconst_1
//        73: istore_1
//        74: goto          79
//        77: iconst_1
//        78: istore_1
//        79: iload_1
//        80: ireturn
//      LineNumberTable:
//        line 87: 0
//        line 89: 2
//        line 91: 52
//        line 92: 54
//        line 94: 57
//        line 95: 59
//        line 97: 62
//        line 98: 64
//        line 100: 67
//        line 101: 69
//        line 103: 72
//        line 104: 74
//        line 107: 77
//        line 111: 79
//      LocalVariableTable:
//        Start  Length  Slot  Name   Signature
//            0      81     0     p   I
//            2      79     1     a   I
//      StackMapTable: number_of_entries = 7
//        frame_type = 252 /* append */
//          offset_delta = 52
//          locals = [ int ]
//        frame_type = 4 /* same */
//        frame_type = 4 /* same */
//        frame_type = 4 /* same */
//        frame_type = 4 /* same */
//        frame_type = 4 /* same */
//        frame_type = 1 /* same */
//
//  public static int e(java.lang.String);
//    descriptor: (Ljava/lang/String;)I
//    flags: (0x0009) ACC_PUBLIC, ACC_STATIC
//    Code:
//      stack=2, locals=3, args_size=1
//         0: iconst_0
//         1: istore_1
//         2: aload_0
//         3: dup
//         4: astore_2
//         5: invokevirtual #20                 // Method java/lang/String.hashCode:()I
//         8: lookupswitch  { // 5
//                      49: 60
//                      50: 72
//                      51: 84
//                      52: 96
//                      53: 108
//                 default: 145
//            }
//        60: aload_2
//        61: ldc           #26                 // String 1
//        63: invokevirtual #28                 // Method java/lang/String.equals:(Ljava/lang/Object;)Z
//        66: ifne          120
//        69: goto          145
//        72: aload_2
//        73: ldc           #32                 // String 2
//        75: invokevirtual #28                 // Method java/lang/String.equals:(Ljava/lang/Object;)Z
//        78: ifne          125
//        81: goto          145
//        84: aload_2
//        85: ldc           #34                 // String 3
//        87: invokevirtual #28                 // Method java/lang/String.equals:(Ljava/lang/Object;)Z
//        90: ifne          130
//        93: goto          145
//        96: aload_2
//        97: ldc           #36                 // String 4
//        99: invokevirtual #28                 // Method java/lang/String.equals:(Ljava/lang/Object;)Z
//       102: ifne          135
//       105: goto          145
//       108: aload_2
//       109: ldc           #38                 // String 5
//       111: invokevirtual #28                 // Method java/lang/String.equals:(Ljava/lang/Object;)Z
//       114: ifne          140
//       117: goto          145
//       120: iconst_1
//       121: istore_1
//       122: goto          147
//       125: iconst_1
//       126: istore_1
//       127: goto          147
//       130: iconst_1
//       131: istore_1
//       132: goto          147
//       135: iconst_1
//       136: istore_1
//       137: goto          147
//       140: iconst_1
//       141: istore_1
//       142: goto          147
//       145: iconst_1
//       146: istore_1
//       147: iload_1
//       148: ireturn
//      LineNumberTable:
//        line 115: 0
//        line 117: 2
//        line 119: 120
//        line 120: 122
//        line 122: 125
//        line 123: 127
//        line 125: 130
//        line 126: 132
//        line 128: 135
//        line 129: 137
//        line 131: 140
//        line 132: 142
//        line 135: 145
//        line 139: 147
//      LocalVariableTable:
//        Start  Length  Slot  Name   Signature
//            0     149     0     p   Ljava/lang/String;
//            2     147     1     a   I
//      StackMapTable: number_of_entries = 12
//        frame_type = 253 /* append */
//          offset_delta = 60
//          locals = [ int, class java/lang/String ]
//        frame_type = 11 /* same */
//        frame_type = 11 /* same */
//        frame_type = 11 /* same */
//        frame_type = 11 /* same */
//        frame_type = 11 /* same */
//        frame_type = 4 /* same */
//        frame_type = 4 /* same */
//        frame_type = 4 /* same */
//        frame_type = 4 /* same */
//        frame_type = 4 /* same */
//        frame_type = 250 /* chop */
//          offset_delta = 1
//
//  public static int f(java.lang.String);
//    descriptor: (Ljava/lang/String;)I
//    flags: (0x0009) ACC_PUBLIC, ACC_STATIC
//    Code:
//      stack=2, locals=3, args_size=1
//         0: iconst_0
//         1: istore_1
//         2: aload_0
//         3: dup
//         4: astore_2
//         5: invokevirtual #20                 // Method java/lang/String.hashCode:()I
//         8: lookupswitch  { // 1
//                    2112: 28
//                 default: 59
//            }
//        28: aload_2
//        29: ldc           #42                 // String Aa
//        31: invokevirtual #28                 // Method java/lang/String.equals:(Ljava/lang/Object;)Z
//        34: ifne          49
//        37: aload_2
//        38: ldc           #44                 // String BB
//        40: invokevirtual #28                 // Method java/lang/String.equals:(Ljava/lang/Object;)Z
//        43: ifne          54
//        46: goto          59
//        49: iconst_1
//        50: istore_1
//        51: goto          61
//        54: iconst_1
//        55: istore_1
//        56: goto          61
//        59: iconst_1
//        60: istore_1
//        61: iload_1
//        62: ireturn
//      LineNumberTable:
//        line 143: 0
//        line 145: 2
//        line 147: 49
//        line 148: 51
//        line 150: 54
//        line 151: 56
//        line 154: 59
//        line 158: 61
//      LocalVariableTable:
//        Start  Length  Slot  Name   Signature
//            0      63     0     p   Ljava/lang/String;
//            2      61     1     a   I
//      StackMapTable: number_of_entries = 5
//        frame_type = 253 /* append */
//          offset_delta = 28
//          locals = [ int, class java/lang/String ]
//        frame_type = 20 /* same */
//        frame_type = 4 /* same */
//        frame_type = 4 /* same */
//        frame_type = 250 /* chop */
//          offset_delta = 1
//
//  public static int g(java.lang.annotation.RetentionPolicy);
//    descriptor: (Ljava/lang/annotation/RetentionPolicy;)I
//    flags: (0x0009) ACC_PUBLIC, ACC_STATIC
//    Code:
//      stack=2, locals=2, args_size=1
//         0: iconst_0
//         1: istore_1
//         2: invokestatic  #48                 // InterfaceMethod $SWITCH_TABLE$java$lang$annotation$RetentionPolicy:()[I
//         5: aload_0
//         6: invokevirtual #51                 // Method java/lang/annotation/RetentionPolicy.ordinal:()I
//         9: iaload
//        10: tableswitch   { // 1 to 3
//                       1: 36
//                       2: 41
//                       3: 46
//                 default: 51
//            }
//        36: iconst_1
//        37: istore_1
//        38: goto          53
//        41: iconst_1
//        42: istore_1
//        43: goto          53
//        46: iconst_1
//        47: istore_1
//        48: goto          53
//        51: iconst_1
//        52: istore_1
//        53: iload_1
//        54: ireturn
//      LineNumberTable:
//        line 162: 0
//        line 164: 2
//        line 166: 36
//        line 167: 38
//        line 169: 41
//        line 170: 43
//        line 172: 46
//        line 173: 48
//        line 176: 51
//        line 180: 53
//      LocalVariableTable:
//        Start  Length  Slot  Name   Signature
//            0      55     0     p   Ljava/lang/annotation/RetentionPolicy;
//            2      53     1     a   I
//      StackMapTable: number_of_entries = 5
//        frame_type = 252 /* append */
//          offset_delta = 36
//          locals = [ int ]
//        frame_type = 4 /* same */
//        frame_type = 4 /* same */
//        frame_type = 4 /* same */
//        frame_type = 1 /* same */
//
//  public static int[] $SWITCH_TABLE$java$lang$annotation$RetentionPolicy();
//    descriptor: ()[I
//    flags: (0x1009) ACC_PUBLIC, ACC_STATIC, ACC_SYNTHETIC
//    Code:
//      stack=3, locals=1, args_size=0
//         0: getstatic     #57                 // Field $SWITCH_TABLE$java$lang$annotation$RetentionPolicy:[I
//         3: dup
//         4: ifnull        8
//         7: areturn
//         8: pop
//         9: invokestatic  #59                 // Method java/lang/annotation/RetentionPolicy.values:()[Ljava/lang/annotation/RetentionPolicy;
//        12: arraylength
//        13: newarray       int
//        15: astore_0
//        16: aload_0
//        17: getstatic     #63                 // Field java/lang/annotation/RetentionPolicy.CLASS:Ljava/lang/annotation/RetentionPolicy;
//        20: invokevirtual #51                 // Method java/lang/annotation/RetentionPolicy.ordinal:()I
//        23: iconst_2
//        24: iastore
//        25: goto          29
//        28: pop
//        29: aload_0
//        30: getstatic     #66                 // Field java/lang/annotation/RetentionPolicy.RUNTIME:Ljava/lang/annotation/RetentionPolicy;
//        33: invokevirtual #51                 // Method java/lang/annotation/RetentionPolicy.ordinal:()I
//        36: iconst_3
//        37: iastore
//        38: goto          42
//        41: pop
//        42: aload_0
//        43: getstatic     #69                 // Field java/lang/annotation/RetentionPolicy.SOURCE:Ljava/lang/annotation/RetentionPolicy;
//        46: invokevirtual #51                 // Method java/lang/annotation/RetentionPolicy.ordinal:()I
//        49: iconst_1
//        50: iastore
//        51: goto          55
//        54: pop
//        55: aload_0
//        56: dup
//        57: putstatic     #57                 // Field $SWITCH_TABLE$java$lang$annotation$RetentionPolicy:[I
//        60: areturn
//      Exception table:
//         from    to  target type
//            16    25    28   Class java/lang/NoSuchFieldError
//            29    38    41   Class java/lang/NoSuchFieldError
//            42    51    54   Class java/lang/NoSuchFieldError
//      LineNumberTable:
//        line 15: 0
//      LocalVariableTable:
//        Start  Length  Slot  Name   Signature
//      StackMapTable: number_of_entries = 7
//        frame_type = 72 /* same_locals_1_stack_item */
//          stack = [ class "[I" ]
//        frame_type = 255 /* full_frame */
//          offset_delta = 19
//          locals = [ class "[I" ]
//          stack = [ class java/lang/NoSuchFieldError ]
//        frame_type = 0 /* same */
//        frame_type = 75 /* same_locals_1_stack_item */
//          stack = [ class java/lang/NoSuchFieldError ]
//        frame_type = 0 /* same */
//        frame_type = 75 /* same_locals_1_stack_item */
//          stack = [ class java/lang/NoSuchFieldError ]
//        frame_type = 0 /* same */
//}
