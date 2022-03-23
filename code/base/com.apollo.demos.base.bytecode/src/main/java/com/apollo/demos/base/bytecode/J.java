/*
 * 此代码创建于 2022年3月19日 下午3:46:15。
 */
package com.apollo.demos.base.bytecode;

public class J {

    //很多指令对操作数栈顶自带出栈动作，所以操作数栈指令中，单独对栈顶的操作指令比较少，但都很特别，在一些特殊算法中有奇效。

    int m_i = 0;

    int a() {
        return ++m_i;
        // 0: aload_0                                          解释：把this入栈顶，操作后的栈：[this]。
        // 1: dup                                              解释：复制栈顶数据，并入栈顶，操作后的栈：[this,this]。
        // 2: getfield      #12                 // Field m_i:I 解释：弹出栈顶数据当对象，取出#12常量当字段，取出这个对象的这个字段的值，既this.m_i，值是0，把这个值再入栈顶，操作后的栈：[this,0]。
        // 5: iconst_1                                         解释：把常量1压入栈顶，操作后的栈：[this,0,1]。
        // 6: iadd                                             解释：连续弹出栈顶的两个值并相加，把结果压入栈顶，操作后的栈：[this,1]。
        // 7: dup_x1                                           解释：复制栈顶元素，并设置到当前栈顶向下的第二个元素之下（注意，不是第一个，如果第一个就和dup指令效果一样），操作后的栈：[1,this,1]。
        // 8: putfield      #12                 // Field m_i:I 解释：连续弹出栈顶的两个值，[this,1]，第一个值当对象，第二个值当参数，取出#12常量当字段，设置这个对象的这个字段的值为参数，既设置this.m_i值为1，操作后的栈：[1]。
        //11: ireturn                                          解释：弹出栈顶的值，并把这个值返回。
    }

    void b() {
        a();
    }

    static long c() {
        return 1;
    }

    static void d() {
        c(); //有返回值，但用不上，此时invokevirtual已经把返回值入栈顶了，需要自动弹出丢弃，可能是怕影响其他算法。
    }

}

/*-------- javap -v J --------
public class com.apollo.demos.base.bytecode.J
  minor version: 0
  major version: 52
  flags: (0x0021) ACC_PUBLIC, ACC_SUPER
  this_class: #1                          // com/apollo/demos/base/bytecode/J
  super_class: #3                         // java/lang/Object
  interfaces: 0, fields: 1, methods: 5, attributes: 1
Constant pool:
   #1 = Class              #2             // com/apollo/demos/base/bytecode/J
   #2 = Utf8               com/apollo/demos/base/bytecode/J
   #3 = Class              #4             // java/lang/Object
   #4 = Utf8               java/lang/Object
   #5 = Utf8               m_i
   #6 = Utf8               I
   #7 = Utf8               <init>
   #8 = Utf8               ()V
   #9 = Utf8               Code
  #10 = Methodref          #3.#11         // java/lang/Object."<init>":()V
  #11 = NameAndType        #7:#8          // "<init>":()V
  #12 = Fieldref           #1.#13         // com/apollo/demos/base/bytecode/J.m_i:I
  #13 = NameAndType        #5:#6          // m_i:I
  #14 = Utf8               LineNumberTable
  #15 = Utf8               LocalVariableTable
  #16 = Utf8               this
  #17 = Utf8               Lcom/apollo/demos/base/bytecode/J;
  #18 = Utf8               a
  #19 = Utf8               ()I
  #20 = Utf8               b
  #21 = Methodref          #1.#22         // com/apollo/demos/base/bytecode/J.a:()I
  #22 = NameAndType        #18:#19        // a:()I
  #23 = Utf8               c
  #24 = Utf8               ()J
  #25 = Utf8               d
  #26 = Methodref          #1.#27         // com/apollo/demos/base/bytecode/J.c:()J
  #27 = NameAndType        #23:#24        // c:()J
  #28 = Utf8               SourceFile
  #29 = Utf8               J.java
{
  int m_i;
    descriptor: I
    flags: (0x0000)

  public com.apollo.demos.base.bytecode.J();
    descriptor: ()V
    flags: (0x0001) ACC_PUBLIC
    Code:
      stack=2, locals=1, args_size=1
         0: aload_0
         1: invokespecial #10                 // Method java/lang/Object."<init>":()V
         4: aload_0
         5: iconst_0
         6: putfield      #12                 // Field m_i:I
         9: return
      LineNumberTable:
        line 6: 0
        line 10: 4
        line 6: 9
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
            0      10     0  this   Lcom/apollo/demos/base/bytecode/J;

  int a();
    descriptor: ()I
    flags: (0x0000)
    Code:
      stack=3, locals=1, args_size=1
         0: aload_0
         1: dup
         2: getfield      #12                 // Field m_i:I
         5: iconst_1
         6: iadd
         7: dup_x1
         8: putfield      #12                 // Field m_i:I
        11: ireturn
      LineNumberTable:
        line 13: 0
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
            0      12     0  this   Lcom/apollo/demos/base/bytecode/J;

  void b();
    descriptor: ()V
    flags: (0x0000)
    Code:
      stack=1, locals=1, args_size=1
         0: aload_0
         1: invokevirtual #21                 // Method a:()I
         4: pop
         5: return
      LineNumberTable:
        line 25: 0
        line 26: 5
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
            0       6     0  this   Lcom/apollo/demos/base/bytecode/J;

  static long c();
    descriptor: ()J
    flags: (0x0008) ACC_STATIC
    Code:
      stack=2, locals=0, args_size=0
         0: lconst_1
         1: lreturn
      LineNumberTable:
        line 29: 0
      LocalVariableTable:
        Start  Length  Slot  Name   Signature

  static void d();
    descriptor: ()V
    flags: (0x0008) ACC_STATIC
    Code:
      stack=2, locals=0, args_size=0
         0: invokestatic  #26                 // Method c:()J
         3: pop2
         4: return
      LineNumberTable:
        line 33: 0
        line 34: 4
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
}
*/
