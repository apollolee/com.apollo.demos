/*
 * 此代码创建于 2016年5月20日 下午4:31:35。
 */
package com.apollo.demos.osgi.app.adapter.api;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.osgi.service.component.ComponentFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseFunction implements IFunction {

    private static final class ProcessorWrapper implements Comparable<ProcessorWrapper> {

        private ComponentFactory m_processor;

        private Integer m_order;

        public ProcessorWrapper(ComponentFactory processor, Integer order) {
            m_processor = processor;
            m_order = order;
        }

        @Override
        public int compareTo(ProcessorWrapper o) {
            return m_order.compareTo(o.m_order);
        }

    }

    private static final Logger s_logger = LoggerFactory.getLogger(BaseFunction.class);

    @Override
    public final ComponentFactory[] getProcessors() {
        try {
            List<ProcessorWrapper> pws = getProcessorWrappers();
            ComponentFactory[] processors = new ComponentFactory[pws.size()];

            for (int i = 0; i < processors.length; i++) {
                processors[i] = pws.get(i).m_processor;
            }

            return processors;

        } catch (Exception ex) {
            s_logger.error("Get processors is failed.", ex);
            throw new RuntimeException(ex);
        }
    }

    private List<ProcessorWrapper> getProcessorWrappers() throws Exception {
        List<ProcessorWrapper> pws = getProcessorWrappers(getClass());
        Collections.sort(pws);
        return pws;
    }

    @SuppressWarnings("unchecked")
    private List<ProcessorWrapper> getProcessorWrappers(Class<? extends BaseFunction> functionClass) throws Exception {
        List<ProcessorWrapper> pws = new ArrayList<ProcessorWrapper>();

        for (Field f : functionClass.getDeclaredFields()) {
            Processor p = f.getAnnotation(Processor.class);
            if (p != null && !Modifier.isStatic(f.getModifiers()) && ComponentFactory.class.isAssignableFrom(f.getType())) {
                f.setAccessible(true);
                ComponentFactory cf = (ComponentFactory) f.get(this);

                if (cf == null) {
                    s_logger.debug("Field is was hidden. [{}#{}]", f, getClass());

                } else {
                    pws.add(new ProcessorWrapper(cf, p.order()));
                }
            }
        }

        Class<?> superclass = functionClass.getSuperclass();
        if (superclass != null && BaseFunction.class.isAssignableFrom(superclass)) {
            pws.addAll(getProcessorWrappers((Class<? extends BaseFunction>) superclass));
        }

        return pws;
    }

}
