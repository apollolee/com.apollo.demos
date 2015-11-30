/*
 * 此代码创建于 2015年11月19日 下午4:50:26。
 */
package com.apollo.demos.test.junit;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ JUnitBaseDemo.class, JUnitRuleDemo.class, JUnitParameterizedDemo.class, JUnitTheoriesDemo.class })
public class JUnitSuiteDemo {

}
