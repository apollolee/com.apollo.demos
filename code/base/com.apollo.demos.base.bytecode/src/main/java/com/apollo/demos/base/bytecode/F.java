/*
 * 此代码创建于 2022年3月16日 下午4:36:15。
 */
package com.apollo.demos.base.bytecode;

import java.io.IOException;
import java.sql.SQLException;

//相对复杂的继承，字节码描述也是比较简洁的。
//虚函数是在运行时确定调用关系的，@Override是SOURCE级别的，不会带到字节码，在字节码层面上没有专门的信息用于标识虚函数。

public class F extends B implements D, E {

    public F() {
    }

    @Override
    public void testEx(int a) throws IOException, SQLException, NullPointerException {
    }

    @Override
    public boolean isTest() {
        return true;
    }

}

/*-------- javap -v F --------
public class com.apollo.demos.base.bytecode.F extends com.apollo.demos.base.bytecode.B implements com.apollo.demos.base.bytecode.D,com.apollo.demos.base.bytecode.E
minor version: 0
major version: 52
flags: (0x0021) ACC_PUBLIC, ACC_SUPER
this_class: #1                          // com/apollo/demos/base/bytecode/F
super_class: #3                         // com/apollo/demos/base/bytecode/B
interfaces: 2, fields: 0, methods: 3, attributes: 1
Constant pool:
 #1 = Class              #2             // com/apollo/demos/base/bytecode/F
 #2 = Utf8               com/apollo/demos/base/bytecode/F
 #3 = Class              #4             // com/apollo/demos/base/bytecode/B
 #4 = Utf8               com/apollo/demos/base/bytecode/B
 #5 = Class              #6             // com/apollo/demos/base/bytecode/D
 #6 = Utf8               com/apollo/demos/base/bytecode/D
 #7 = Class              #8             // com/apollo/demos/base/bytecode/E
 #8 = Utf8               com/apollo/demos/base/bytecode/E
 #9 = Utf8               <init>
#10 = Utf8               ()V
#11 = Utf8               Code
#12 = Methodref          #3.#13         // com/apollo/demos/base/bytecode/B."<init>":()V
#13 = NameAndType        #9:#10         // "<init>":()V
#14 = Utf8               LineNumberTable
#15 = Utf8               LocalVariableTable
#16 = Utf8               this
#17 = Utf8               Lcom/apollo/demos/base/bytecode/F;
#18 = Utf8               testEx
#19 = Utf8               (I)V
#20 = Utf8               Exceptions
#21 = Class              #22            // java/io/IOException
#22 = Utf8               java/io/IOException
#23 = Class              #24            // java/sql/SQLException
#24 = Utf8               java/sql/SQLException
#25 = Class              #26            // java/lang/NullPointerException
#26 = Utf8               java/lang/NullPointerException
#27 = Utf8               a
#28 = Utf8               I
#29 = Utf8               isTest
#30 = Utf8               ()Z
#31 = Utf8               SourceFile
#32 = Utf8               F.java
{
public com.apollo.demos.base.bytecode.F();
  descriptor: ()V
  flags: (0x0001) ACC_PUBLIC
  Code:
    stack=1, locals=1, args_size=1
       0: aload_0
       1: invokespecial #12                 // Method com/apollo/demos/base/bytecode/B."<init>":()V
       4: return
    LineNumberTable:
      line 11: 0
      line 12: 4
    LocalVariableTable:
      Start  Length  Slot  Name   Signature
          0       5     0  this   Lcom/apollo/demos/base/bytecode/F;

public void testEx(int) throws java.io.IOException, java.sql.SQLException, java.lang.NullPointerException;
  descriptor: (I)V
  flags: (0x0001) ACC_PUBLIC
  Exceptions:
    throws java.io.IOException, java.sql.SQLException, java.lang.NullPointerException
  Code:
    stack=0, locals=2, args_size=2
       0: return
    LineNumberTable:
      line 16: 0
    LocalVariableTable:
      Start  Length  Slot  Name   Signature
          0       1     0  this   Lcom/apollo/demos/base/bytecode/F;
          0       1     1     a   I

public boolean isTest();
  descriptor: ()Z
  flags: (0x0001) ACC_PUBLIC
  Code:
    stack=1, locals=1, args_size=1
       0: iconst_1
       1: ireturn
    LineNumberTable:
      line 20: 0
    LocalVariableTable:
      Start  Length  Slot  Name   Signature
          0       2     0  this   Lcom/apollo/demos/base/bytecode/F;
}
*/
