/*
 * 此代码创建于 2016年6月14日 上午9:55:03。
 */
package com.apollo.demos.maven.plugin;

import static java.io.File.separatorChar;
import static java.util.Arrays.asList;
import static java.util.Optional.empty;
import static java.util.Optional.ofNullable;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javassist.CannotCompileException;
import javassist.ClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;

import org.apache.commons.io.FileUtils;
import org.apache.maven.artifact.DependencyResolutionRequiredException;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

@Mojo(name = "base")
public class BaseMojo extends AbstractMojo {

    @Parameter(property = "base.greeting", defaultValue = "Hello, world.")
    private String greeting; //字段名才是pom中配置的名称，上面的property会在help中显示出来。

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void execute() throws MojoExecutionException, MojoFailureException {
        Log log = getLog();
        Map pc = getPluginContext();

        log.info(greeting);
        pc.forEach((k, v) -> getLog().info("[Key = " + k + " , Value = " + v.getClass().getName() + "]"));

        MavenProject mp = (MavenProject) pc.get("project");
        log.info("[Base Dir = " + mp.getBasedir() + "]");

        Optional<List<String>> classpaths = empty();
        try {
            classpaths = ofNullable(mp.getCompileClasspathElements());

        } catch (DependencyResolutionRequiredException ex) {
            log.error(ex);
        }

        classpaths.ifPresent(cs -> cs.forEach(this::processClasspath));
    }

    private void processClasspath(String classpath) {
        Log log = getLog();

        log.info("Processing " + classpath);

        Collection<File> cfs = FileUtils.listFiles(new File(classpath), new String[] { "class" }, true);
        ClassPool pool = ClassPool.getDefault();
        Optional<ClassPath> ocp = empty();
        try {
            ocp = ofNullable(pool.insertClassPath(classpath));

        } catch (NotFoundException ex) {
            log.error(ex);

        } finally {
            ocp.ifPresent(cp -> cfs.stream().forEach(cf -> processClass(pool, classpath, cf)));
            ocp.ifPresent(cp -> pool.removeClassPath(cp));
            log.info("Processed " + classpath);
        }
    }

    private void processClass(ClassPool pool, String classpath, File classfile) {
        Log log = getLog();

        String classname = classfile.getAbsolutePath();
        classname = classname.substring(classpath.length() + 1, classname.length() - 6).replace(separatorChar, '.');

        log.info("Processing " + classname);

        try {
            CtClass cc = pool.get(classname);
            asList(cc.getDeclaredMethods()).stream().filter(this::isGetGreeting).forEach(this::processGetGreeting);
            cc.writeFile(classpath);

        } catch (Exception ex) {
            log.error(ex);
        }
    }

    private boolean isGetGreeting(CtMethod cm) {
        try {
            return cm.getName().equals("getGreeting") && cm.getReturnType().getName().equals(String.class.getName());

        } catch (NotFoundException ex) {
            getLog().error(ex);
            return false;
        }
    }

    private void processGetGreeting(CtMethod cm) {
        try {
            cm.setBody("return \"Welcome.\";");

        } catch (CannotCompileException ex) {
            getLog().error(ex);
        }
    }

}
