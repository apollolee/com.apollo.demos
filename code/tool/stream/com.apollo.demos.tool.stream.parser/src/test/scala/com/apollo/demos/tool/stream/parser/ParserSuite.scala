package com.apollo.demos.tool.stream.parser

import org.junit.runner.RunWith
import org.junit.runners.Suite
import org.junit.runners.Suite.SuiteClasses

@RunWith(classOf[Suite])
@SuiteClasses(Array(classOf[HelperTest], classOf[HeaderTest]))
class ParserSuite
