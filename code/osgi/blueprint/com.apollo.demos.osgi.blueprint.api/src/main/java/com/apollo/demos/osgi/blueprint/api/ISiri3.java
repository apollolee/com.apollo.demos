/*
 * 此代码创建于 2015年12月24日 上午10:14:59。
 */
package com.apollo.demos.osgi.blueprint.api;

public interface ISiri3 { //运行时标注用法（POM中加属性支持），这种目前没有标准，和具体的blueprint实现强绑定，而且和配置文件方式有冲突，不建议使用。

    public abstract String sayHello();

}
