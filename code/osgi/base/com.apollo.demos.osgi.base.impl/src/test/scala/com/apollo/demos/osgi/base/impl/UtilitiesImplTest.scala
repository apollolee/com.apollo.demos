package com.apollo.demos.osgi.base.impl

import java.util.concurrent.ForkJoinPool
import java.util.concurrent.ThreadPoolExecutor

import org.apache.felix.scr.annotations.Component
import org.apache.felix.scr.annotations.Service
import org.junit.Assert._
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.when
import org.mockito.Spy

class UtilitiesImplTest extends TestBase {
  @Spy val utilities = new UtilitiesImpl

  @Test def testCurrentState_ThreadPoolExecutor() {
    val pool = mock(classOf[ThreadPoolExecutor])
    when(pool.toString).thenReturn("java.util.concurrent.ThreadPoolExecutor@3dc1e9b5[Running, pool size = 100, active threads = 25, queued tasks = 0, completed tasks = 123]")

    val state = utilities.currentState(pool)

    assertEquals("Running", state._1)
    assertEquals(100, state._2)
    assertEquals(25, state._3)
    assertEquals(0, state._4)
    assertEquals(123, state._5)
  }

  @Test def testCurrentState_ForkJoinPool() {
    val pool = mock(classOf[ForkJoinPool])
    when(pool.toString).thenReturn("java.util.concurrent.ForkJoinPool@75b84c92[Running, parallelism = 4, size = 3, active = 2, running = 1, steals = 888, tasks = 789, submissions = 10]")

    val state = utilities.currentState(pool)

    assertEquals("Running", state._1)
    assertEquals(4, state._2)
    assertEquals(3, state._3)
    assertEquals(2, state._4)
    assertEquals(1, state._5)
    assertEquals(888, state._6)
    assertEquals(789, state._7)
    assertEquals(10, state._8)
  }
}
