package com.apollo.demos.base.scala

object Extractors extends App {
  EMail.check("John@epfl.ch")
  EMail.check("John In EPFL")

  EMailX.check("John@epfl.ch")
  EMailX.check("John In EPFL")
  EMailX.checkDuplicateName(List("John@epfl.ch", "John@126.com", "Nina@qq.com"))
  EMailX.checkDuplicateName(List("John@epfl.ch", "Apollo@126.com", "Nina@qq.com"))

  val addr: Any = "John@epfl.ch"
  addr match { //这里并不强制一定要是String进行匹配，任何类型都可以。
    case EMailX(user, domain) => println("Address is " + user + " AT " + domain)
    case _                    => println("\"" + addr + "\" not an email address")
  }

  //保持apply和unapply的对偶性是一个良好的设计原则。
  println(EMailX.unapply(EMailX.apply("John", "epfl.ch")))
  println((EMailX.unapply("John@epfl.ch"): @unchecked) match { case Some((u, d)) => EMailX.apply(u, d) })

  def userTwiceUpper(s: String) = s match { //复合使用。
    case EMailX(Twice(x @ UpperCase()), domain) => println("match: " + x + " in domain " + domain)
    case _                                      => println("no match")
  }
  userTwiceUpper("DIDI@hotmail.com")
  userTwiceUpper("DIDO@hotmail.com")
  userTwiceUpper("didi@hotmail.com")

  def domain(s: String) = s match { //变参抽取器使用。
    case Domain("org", "acm")         => println("acm.org")
    case Domain("com", "sun", "java") => println("java.sun.com")
    case Domain("net", _*)            => println("a .net domain")
  }
  domain("acm.org")
  domain("java.sun.com")
  domain("163.net")

  def isTomInDotCom(s: String) = s match {
    case EMailX("tom", Domain("com", _*)) => true
    case _                                => false
  }
  println(isTomInDotCom("tom@sum.com"))
  println(isTomInDotCom("peter@sum.com"))
  println(isTomInDotCom("tom@acm.org"))

  val ExpandedEMail(name, topdom, subdoms @ _*) = "tom@support.epfl.ch"
  println(name); println(topdom); println(subdoms)

  List(1, 2, 3) match { case List(x, y, _*) => } //这里其实是List对象中定义了unapplySeq。

  //抽取器和样本类各有所长，抽取器功能更加强大，而且表征独立，样本类则使用更加简易，而且编辑器对样本类有优化。

  //抽取器结合正则表达式使用的例子。
  import scala.util.matching.Regex
  val Decimal = new Regex("(-)?(\\d+)(\\.\\d*)?") //普通写法。
  val Decimal0 = new Regex("""(-)?(\d+)(\.\d*)?""") //三引号写法，可以避免转译嵌套。
  val DecimalX = """(-)?(\d+)(\.\d*)?""".r //简化写法。
  val input = "for -1.0 to 99 by 3"
  for (s <- DecimalX.findAllIn(input)) println(s)
  println(DecimalX.findFirstIn(input))
  println(DecimalX.findPrefixOf(input))
  val DecimalX(sign1, integerPart1, decimalPart1) = "-1.23"
  println(sign1); println(integerPart1); println(decimalPart1)
  val DecimalX(sign2, integerPart2, decimalPart2) = "1.0"
  println(sign2); println(integerPart2); println(decimalPart2)
  for (DecimalX(s, i, d) <- DecimalX.findAllIn(input)) println("sign: " + s + ", integer: " + i + ", decimal: " + d)
}

object EMail { //传统做法。
  def isEMail(addr: String) = if (addr.split("@").length == 2) true else false
  def user(addr: String) = addr.split("@")(0)
  def domain(addr: String) = addr.split("@")(1) //这个地方并不安全，isEMail == true是前置条件。
  def check(addr: String) {
    if (isEMail(addr)) println("Address is " + user(addr) + " AT " + domain(addr)) //实现起来并不够优雅。
    else println("\"" + addr + "\" is not an email address")
  }
}

object EMailX { //抽取器做法。String并不是样本类，抽取器的能力是让非样本类也能适用于模式匹配。
  def apply(user: String, domain: String) = user + "@" + domain //注入方法（可选的）。
  def unapply(addr: String): Option[(String, String)] = { val parts = addr.split("@"); if (parts.length == 2) Some(parts(0), parts(1)) else None } //抽取方法。
  def check(addr: String) = addr match {
    case EMailX(user, domain) => println("Address is " + user + " AT " + domain)
    case _                    => println("\"" + addr + "\" not an email address")
  }
  def checkDuplicateName(as: List[String]) = as match { //判断是否有连续2个地址的user相同，在传统做法中实现较为繁琐。
    case EMailX(u1, d1) :: EMailX(u2, d2) :: _ if (u1 == u2) => println(u1 + " has tow domain: " + d1 + ", " + d2)
    case _ => println("There is no duplicate name.")
  }
}

object Twice {
  def apply(s: String) = s + s
  def unapply(s: String) = { val length = s.length / 2; val half = s.substring(0, length); if (half == s.substring(length)) Some(half) else None }
}

object UpperCase {
  def unapply(s: String) = s.toUpperCase == s //用Boolean表明匹配成功还是失败。注意：没有apply，因为没有意义，没有什么东西可以构造。
}

object Domain {
  def apply(parts: String*): String = parts.reverse.mkString(".")
  def unapplySeq(whole: String): Option[Seq[String]] = Some(whole.split("\\.").reverse) //变参抽取器，注意名称unapplySeq，另外，返回值在这里必须是 Option[Seq[T]]。
}

object ExpandedEMail {
  def unapplySeq(email: String): Option[(String, Seq[String])] = { //返回这种也是允许的。
    val parts = email.split("@")
    if (parts.length == 2) Some(parts(0), parts(1).split("\\.").reverse) else None
  }
}
