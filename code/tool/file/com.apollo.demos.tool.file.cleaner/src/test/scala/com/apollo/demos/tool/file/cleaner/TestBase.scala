package com.apollo.demos.tool.file.cleaner

import org.junit.After
import org.junit.AfterClass
import org.junit.Before
import org.junit.BeforeClass
import org.junit.runner.RunWith
import org.mockito.runners.MockitoJUnitRunner

object TestBase {
  @BeforeClass def setUpBeforeClass() {}
  @AfterClass def tearDownAfterClass() {}
}

@RunWith(classOf[MockitoJUnitRunner])
abstract class TestBase extends TestBase4J {
  @Before def setUp() {}
  @After def tearDown() {}

  def expect(exception: Class[_ <: Throwable], exceptionMessage: String) {
    expectedException.expect(exception)
    expectedException.expectMessage(exceptionMessage)
  }
}
