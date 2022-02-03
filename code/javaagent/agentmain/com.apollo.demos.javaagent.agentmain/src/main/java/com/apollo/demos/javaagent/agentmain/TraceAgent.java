/*
 * 此代码创建于 2022年2月2日 下午8:42:42。
 */
package com.apollo.demos.javaagent.agentmain;

import static java.util.stream.Stream.of;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

public class TraceAgent {

    public static void agentmain(String agentArgs, Instrumentation inst) {
        System.out.println("Agent Args: " + agentArgs);

        Class<?>[] alcs = inst.getAllLoadedClasses();
        System.out.println("\nAll Loaded Classes Size: " + alcs.length);
        of(alcs).forEach(c -> System.out.println("Loaded Class: " + c.getName()));

        System.out.println("\nAgent Load Class Now.");
        inst.addTransformer(new DefineTransformer(), true);
    }

    static class DefineTransformer implements ClassFileTransformer {

        @Override
        public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
            System.out.println("Agent Load Class: " + className);
            return classfileBuffer;
        }

    }

}
