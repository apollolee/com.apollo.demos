package com.apollo.demos.base.scala

import scala.actors._
import scala.actors.Actor._

object Actor extends App {
  object SillyActor extends Actor { //注意：Scala自带的Actor以及建议换成akka的了。
    def act() {
      for (i <- 1 to 5) {
        println("I'm acting!")
        Thread.sleep(1000)
      }
    }
  }
  SillyActor.start()
  Thread.sleep(6000)

  object SeriousActor extends Actor {
    def act() {
      for (i <- 1 to 5) {
        println("To be or not to be.")
        Thread.sleep(1000)
      }
    }
  }
  SillyActor.restart() //可以重启启动，但必须保证已经终止了，否则会有异常。
  SeriousActor.start()
  Thread.sleep(6000)

  val seriousActorX = actor { //传名参数。这种写法连start都省了。
    for (i <- 1 to 5) {
      println("That is the question.")
      Thread.sleep(1000)
    }
  }
  Thread.sleep(6000)

  SillyActor ! "hi there" //发这个消息不会有任何响应，因为SillyActor中没有处理响应的代码，但这个消息会进入SillyActor的邮箱。
  Thread.sleep(2000)

  val echoActor = actor {
    for (i <- 1 to 2) { //只收2次消息，如果多于2次，而没有发送消息，那么actor会一直等，所在进程也不会结束。
      receive { //receive接受一个偏函数。
        case msg => println("received message: " + msg)
      }
    }
  }
  echoActor ! "hi there"
  echoActor ! 15
  Thread.sleep(2000)

  val intActor = actor {
    receive { //这里只接受一次消息。
      case x: Int => println("Got an Int: " + x)
    }
  }
  intActor ! "hello" //被忽略。
  intActor ! Math.PI //被忽略。
  intActor ! 12
  Thread.sleep(2000)

  self ! "hollo"
  self.receive { case x => println("self received message: " + x) }
  self.receiveWithin(1000) { case x => println("self received message: " + x) } //会收到TIMEOUT消息。
  Thread.sleep(2000)

  object NameResolver extends Actor {
    import java.net.{ InetAddress, UnknownHostException }
    def getIp(name: String) = try { Some(InetAddress.getByName(name)) } catch { case _: UnknownHostException => None }
    def act() {
      react { //这里还是只接受一次消息，注意：react和receive的区别是react底层线程资源可以重复使用，而receive不行。
        case (name: String, actor: Actor) => actor ! getIp(name)
        case "EXIT"                       => println("Name resolver exiting.")
        case msg                          => println("Unhandled message: " + msg); act() //重新调用react，相当于接受到可以处理的消息为止。
      }
    }
  }
  NameResolver.start()
  NameResolver ! "www.scala-lang.org"
  NameResolver ! "EXIT"
  Thread.sleep(2000)

  NameResolver.restart()
  NameResolver ! ("www.scala-lang.org", self)
  self.receiveWithin(5000) { case x => println("Name resolver: " + x) }
  Thread.sleep(2000)

  NameResolver.restart()
  NameResolver ! ("wwwwww.scala-lang.org", self)
  self.receiveWithin(5000) { case x => println("Name resolver: " + x) }
  Thread.sleep(2000)

  object NameResolverX extends Actor {
    import java.net.{ InetAddress, UnknownHostException }
    def getIp(name: String) = try { Some(InetAddress.getByName(name)) } catch { case _: UnknownHostException => None }
    def act() {
      var isExited = false
      loopWhile(!isExited) { //控制循环退出。虽然react会被不同的线程处理，尽管这样，Scala会保证react依次处理，不会产生线程问题。
        react {
          case (name: String, actor: Actor) => actor ! getIp(name)
          case "EXIT"                       => isExited = true //这里是同一个线程内的，不需要volatile。
          case msg                          => println("Unhandled message: " + msg)
        }
      }
    }
  }
  NameResolverX.start()

  NameResolverX ! ("www.baidu.com", self)
  self.receiveWithin(5000) { case x => println("Name resolver: " + x) }
  Thread.sleep(2000)

  NameResolverX ! ("www.taobao.com", self)
  self.receiveWithin(5000) { case x => println("Name resolver: " + x) }
  Thread.sleep(2000)

  NameResolverX ! "EXIT"
  Thread.sleep(2000)

  val sillyActor = actor {
    def emoteLater() {
      val mainActor = self
      actor {
        Thread.sleep(1000) //这里阻塞并不会影响接受消息，因为没有消息处理。
        mainActor ! "Emote" //阻塞完通过消息通知到发起阻塞的actor，这是一种常用做法。
      }
    }

    var emoted = 0
    emoteLater()

    var isExited = false
    loopWhile(!isExited) {
      react {
        case "Emote" => { println("I'm acting!"); emoted += 1; if (emoted < 5) emoteLater() }
        case "EXIT"  => isExited = true
        case msg     => println("Received: " + msg)
      }
    }
  }
  Thread.sleep(2000)

  sillyActor ! "hi there"
  Thread.sleep(2000)

  sillyActor ! "EXIT"
  Thread.sleep(2000)

  object NameResolverY extends Actor { //这是一个相对来说完整的例子。
    import java.net.{ InetAddress, UnknownHostException }
    case class LookupIp(hostname: String, requester: Actor) //用样本类封装，让发送消息可读性更高。
    case class LookupIpResult(hostname: String, address: Option[InetAddress]) //查询结果中还关联了发送源的信息hostname，用于识别查询的调用者。
    def getIp(name: String) = try { Some(InetAddress.getByName(name)) } catch { case _: UnknownHostException => None }
    def act() {
      var isExited = false
      loopWhile(!isExited) {
        react {
          case LookupIp(hostname, requester) => actor { requester ! LookupIpResult(hostname, getIp(hostname)) } //这里相当于新启了一个线程，处理耗时的域名解析。
          case "EXIT"                        => isExited = true
          case msg                           => println("Unhandled message: " + msg)
        }
      }
    }
  }
  import NameResolverY.{ LookupIp, LookupIpResult }
  NameResolverY.start()

  val requester = actor { //接受器，可以并行接收解析结果。
    var isExited = false
    loopWhile(!isExited) {
      react {
        case LookupIpResult(hostname, address) => println("Name resolver: " + hostname + " -> " + address)
        case "EXIT"                            => isExited = true
        case msg                               => println("Unhandled message: " + msg)
      }
    }
  }

  NameResolverY ! LookupIp("wwwwww.scala-lang.org", requester)
  NameResolverY ! LookupIp("www.twitter.com", requester)
  NameResolverY ! LookupIp("www.youtube.com", requester)
  NameResolverY ! LookupIp("www.facebook.com", requester)
  NameResolverY ! LookupIp("www.google.com", requester)
  NameResolverY ! LookupIp("www.eclipse.org", requester)
  NameResolverY ! LookupIp("www.jd.com", requester)
  NameResolverY ! "EXIT" //这个地方不需要等待，发送的消息队列会有顺序保证。
  Thread.sleep(6000)
  requester ! "EXIT" //这个地方需要等待一下，因为requester的消息会从多个线程发送，无顺序保证。
}
