package launch { class Booster3 } //源码中包名和目录名称不具有一一对应的关系，Scala编译后会按包名形成具体的class。
package com.apollo { //多个包可以写到一个文件中，并且可以逐级嵌套。
  package launch { class Booster2 }
  package system {
    package launch { class Booster1 }
    class Mission {
      val booster1 = new launch.Booster1 //相对访问。
      val booster2 = new com.apollo.launch.Booster2 //绝对访问。
      val booster3 = new _root_.launch.Booster3 //因为顶层launch包和相对访问的launch包同名了，语法上必须通过_root_能让你访问顶层包。
    }
  }
}
