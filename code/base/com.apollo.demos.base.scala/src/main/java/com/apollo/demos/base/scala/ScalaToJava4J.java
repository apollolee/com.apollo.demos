package com.apollo.demos.base.scala;

public class ScalaToJava4J {

    public static final void log4j(String msg, Object... arguments) {
        StringBuilder sb = new StringBuilder();
        sb.append("Log4J:").append(msg);
        for (Object argument : arguments) {
            sb.append("[").append(argument).append("]");
        }
        System.out.println(sb);
    }

}
