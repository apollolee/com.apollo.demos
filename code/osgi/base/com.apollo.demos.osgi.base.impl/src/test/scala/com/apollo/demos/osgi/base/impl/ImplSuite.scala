package com.apollo.demos.osgi.base.impl

import org.junit.runner.RunWith
import org.junit.runners.Suite
import org.junit.runners.Suite.SuiteClasses

import com.apollo.demos.osgi.base.impl.shell.ShellSuite

@RunWith(classOf[Suite])
@SuiteClasses(Array(classOf[UtilitiesImplTest], classOf[ShellSuite]))
class ImplSuite
