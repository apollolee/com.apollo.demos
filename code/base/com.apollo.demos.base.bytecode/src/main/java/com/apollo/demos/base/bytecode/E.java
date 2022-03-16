/*
 * 此代码创建于 2022年3月16日 下午4:00:19。
 */
package com.apollo.demos.base.bytecode;

import java.io.IOException;
import java.sql.SQLException;

public interface E {

    void testEx(int a) throws IOException, SQLException, NullPointerException;

}

/*-------- javap -v E --------
public interface com.apollo.demos.base.bytecode.E
minor version: 0
major version: 52
flags: (0x0601) ACC_PUBLIC, ACC_INTERFACE, ACC_ABSTRACT
this_class: #1                          // com/apollo/demos/base/bytecode/E
super_class: #3                         // java/lang/Object
interfaces: 0, fields: 0, methods: 1, attributes: 1
Constant pool:
 #1 = Class              #2             // com/apollo/demos/base/bytecode/E
 #2 = Utf8               com/apollo/demos/base/bytecode/E
 #3 = Class              #4             // java/lang/Object
 #4 = Utf8               java/lang/Object
 #5 = Utf8               testEx
 #6 = Utf8               (I)V
 #7 = Utf8               Exceptions
 #8 = Class              #9             // java/io/IOException
 #9 = Utf8               java/io/IOException
#10 = Class              #11            // java/sql/SQLException
#11 = Utf8               java/sql/SQLException
#12 = Class              #13            // java/lang/NullPointerException
#13 = Utf8               java/lang/NullPointerException
#14 = Utf8               SourceFile
#15 = Utf8               E.java
{
public abstract void testEx(int) throws java.io.IOException, java.sql.SQLException, java.lang.NullPointerException;
  descriptor: (I)V
  flags: (0x0401) ACC_PUBLIC, ACC_ABSTRACT
  Exceptions:
    throws java.io.IOException, java.sql.SQLException, java.lang.NullPointerException
}
*/
