/*
 * 此代码创建于 2016年5月23日 上午10:19:10。
 */
package com.apollo.demos.osgi.app.message.api;

import static com.apollo.demos.osgi.app.message.api.IMessageConstants.Factory.ComputeParticle;
import static org.osgi.service.component.ComponentConstants.COMPONENT_FACTORY;

public interface IMessageConstants {

    public static interface Factory {

        public static final String ComputeParticle = "message.computeParticle";

    }

    public static interface Target {

        public static final String AllComputeParticle = "(" + COMPONENT_FACTORY + "=" + ComputeParticle + "*)";

    }

    public static interface Property {

        public static final String Type = "message.type";

        public static final String Scope = "message.scope";

        public static final String Function = "message.function";

    }

    public static interface Type {

        public static final String NE = "NE";

        public static final String Board = "Board";

        public static final String Port = "Port";

    }

    public static interface Scope {

        public static final String ANode = "ANode";

        public static final String CNode = "CNode";

    }

}
