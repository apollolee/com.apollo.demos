/*
 * 此代码创建于 2016年5月20日 下午2:46:24。
 */
package com.apollo.demos.osgi.app.adapter.ptn.impl.function;

import static com.apollo.demos.osgi.app.adapter.api.IAdapterConstants.Property.DeviceType;
import static com.apollo.demos.osgi.app.adapter.api.IAdapterConstants.Property.FunctionId;
import static com.apollo.demos.osgi.app.adapter.api.IAdapterConstants.Property.Version;
import static com.apollo.demos.osgi.app.adapter.api.IAdapterConstants.Target.ProcessorPrefix;
import static com.apollo.demos.osgi.app.adapter.ptn.api.IPtnAdapterConstants.ComponentName.Processor2;
import static com.apollo.demos.osgi.app.adapter.ptn.api.IPtnAdapterConstants.ComponentName.Processor4;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.osgi.service.component.ComponentFactory;

import com.apollo.demos.osgi.app.adapter.api.Processor;
import com.apollo.demos.osgi.app.adapter.ptn.api.PtnBaseFunction;

@Component
@Service
@Properties({ @Property(name = FunctionId, value = "80001"),
             @Property(name = DeviceType, value = "ZXCTN_6150"),
             @Property(name = Version, value = "V1.00/V2.20") })
public class Function3 extends PtnBaseFunction {

    @Reference(target = ProcessorPrefix + Processor2 + ")")
    @Processor(order = 2)
    protected volatile ComponentFactory processor2;

    @Reference(target = ProcessorPrefix + Processor4 + ")")
    @Processor(order = 3)
    protected volatile ComponentFactory processor4;

}
