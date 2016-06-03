/*
 * 此代码创建于 2016年5月20日 下午1:33:50。
 */
package com.apollo.demos.osgi.app.adapter.api;

import org.osgi.service.component.ComponentFactory;

public interface IFunction {

    public abstract ComponentFactory[] getProcessors();

}
