package com.apollo.demos.osgi.base.impl.shell

import org.junit.runner.RunWith
import org.junit.runners.Suite
import org.junit.runners.Suite.SuiteClasses

@RunWith(classOf[Suite])
@SuiteClasses(Array(classOf[ShellHelperTest]))
class ShellSuite
