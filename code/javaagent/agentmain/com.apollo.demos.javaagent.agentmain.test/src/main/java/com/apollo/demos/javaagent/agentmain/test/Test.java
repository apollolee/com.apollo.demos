/*
 * 此代码创建于 2022年2月2日 下午8:42:42。
 */
package com.apollo.demos.javaagent.agentmain.test;

import com.sun.tools.attach.VirtualMachine;

public class Test {

    public static void main(String[] args) throws Throwable {
        VirtualMachine.list().stream().filter(m -> m.displayName().endsWith("com.apollo.demos.javaagent.agentmain.target.Target")).findFirst().ifPresent(m -> {
            System.out.println("attach " + m.displayName() + " with pid: " + m.id());

            try {
                VirtualMachine target = VirtualMachine.attach(m.id());
                //先编译agentmain，再找到agentmain-1.0.0.jar在你的maven本地仓库中的位置，填入loadAgent的入参。
                target.loadAgent("d:/apollo/work/development/maven/local-repository/com/apollo/demos/javaagent/agentmain/1.0.0/agentmain-1.0.0.jar");
                target.detach();

            } catch (Throwable ex) {
                ex.printStackTrace();
            }
        });
    }

}
