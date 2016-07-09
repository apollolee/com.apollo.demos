/*
 * 此代码创建于 2016年6月14日 上午9:55:03。
 */
package com.apollo.demos.maven.plugin;

import static java.io.File.separatorChar;
import static java.lang.Boolean.FALSE;
import static java.util.Arrays.asList;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static java.util.Optional.ofNullable;
import static javassist.ClassPool.getDefault;
import static org.apache.maven.plugins.annotations.LifecyclePhase.COMPILE;

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
import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.DependencyResolutionRequiredException;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecution;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.descriptor.PluginDescriptor;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugins.annotations.Execute;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.apache.maven.settings.Settings;

@Mojo(name = "base", defaultPhase = COMPILE)
@Execute(goal = "pre-base")
public class BaseMojo extends AbstractMojo {

    @Parameter(property = "base.greeting", defaultValue = "Hello, world.", readonly = true)
    private String greeting; //字段名才是pom中配置的名称，上面的property会在help中显示出来。

    @Parameter(defaultValue = "${session}", readonly = true)
    private MavenSession session;

    @Parameter(defaultValue = "${project}", readonly = true)
    private MavenProject project;

    @Parameter(defaultValue = "${mojoExecution}", readonly = true)
    private MojoExecution mojo;

    @Parameter(defaultValue = "${plugin}", readonly = true)
    private PluginDescriptor plugin;

    @Parameter(defaultValue = "${settings}", readonly = true)
    private Settings settings;

    @Parameter(defaultValue = "${project.basedir}", readonly = true)
    private File basedir;

    @Parameter(defaultValue = "${project.build.sourceDirectory}", readonly = true)
    private File sourcedir;

    @Parameter(defaultValue = "${project.build.directory}", readonly = true)
    private File target;

    private Optional<Boolean> m_hasError = of(FALSE); //empty表示有错误，而不是of(TRUE)。

    private ClassPool m_pool = getDefault();

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void execute() throws MojoExecutionException, MojoFailureException {
        Log log = getLog();

        log.info("greeting = " + greeting);
        log.info("session = " + session.getClass());
        log.info("project = " + project.getClass());
        log.info("mojo = " + mojo.getClass());
        log.info("plugin = " + plugin.getClass());
        log.info("settings = " + settings.getClass());
        log.info("basedir = " + basedir);
        log.info("sourcedir = " + sourcedir);
        log.info("target = " + target);

        //一般使用上面的注入属性就够了，但也可以通过Context逐级获取。
        Map pc = getPluginContext();
        pc.forEach((k, v) -> getLog().info("[Key = " + k + " , Value = " + v.getClass().getName() + "]"));
        MavenProject mp = (MavenProject) pc.get("project"); //这里mp和project是一样的。
        log.info("[Base Dir = " + mp.getBasedir() + "]"); //这里basedir和上面的basedir是一样的。

        Optional<List<String>> classpaths = empty();
        try {
            classpaths = ofNullable(project.getCompileClasspathElements()); //编译路径可以有个，但一般只有一个，目前没找到注入属性，只能从Context获取。

        } catch (DependencyResolutionRequiredException ex) {
            throw new MojoExecutionException("Get compile classpaths is failed.", ex); //不可恢复的严重问题抛MojoExecutionException，类似单元测试执行失败的错误抛MojoFailureException。
        }

        //下面这两项在这个例子中没有用到，但很多实际场景下需求，特别是依赖关系很复杂的时候，依赖的类找不到javassist就直接抛异常了。
        project.getDependencyArtifacts().forEach(this::insertClassPath);
        plugin.getArtifacts().forEach(this::insertClassPath); //插件的依赖有时候是单独的，所以也需要处理。

        classpaths.ifPresent(cs -> cs.forEach(this::processClasspath)); //这里我们选择检查所有错误并输出错误日志，而不是发现一个错误后就终止检查。
        m_hasError.orElseThrow(() -> new MojoExecutionException("Execute is failed.")); //Lambda表达式在传递异常上是个问题，orElseThrow提供了一种解决方式，但占用了返回值，只适合无返回值的函数接口。将检查异常转换为非检查异常也是一个办法，但要求高阶函数内不另启线程调用函数接口。
    }

    private void insertClassPath(Artifact artifact) {
        try {
            m_pool.insertClassPath(artifact.getFile().getAbsolutePath());

        } catch (NotFoundException ex) {
            getLog().error(ex); //记录错误。
            m_hasError = empty(); //置错误标识。如果Lambda表达式能解决异常传递问题这里就不需要用这种方式处理错误了。
        }
    }

    private void processClasspath(String classpath) {
        Log log = getLog();

        log.info("Processing " + classpath);

        Collection<File> cfs = FileUtils.listFiles(new File(classpath), new String[] { "class" }, true); //取文件的目的是为了获取所有编译目录下的所有类名，目前没有发现方便获取指定classpath下所有类名的方法。
        Optional<ClassPath> ocp = empty();
        try {
            ocp = ofNullable(m_pool.insertClassPath(classpath));

        } catch (NotFoundException ex) {
            log.error(ex);
            m_hasError = empty();

        } finally {
            ocp.ifPresent(cp -> cfs.stream().forEach(cf -> processClass(classpath, cf)));
            ocp.ifPresent(cp -> m_pool.removeClassPath(cp));
            log.info("Processed " + classpath);
        }
    }

    private void processClass(String classpath, File classfile) {
        Log log = getLog();

        String classname = classfile.getAbsolutePath();
        classname = classname.substring(classpath.length() + 1, classname.length() - 6).replace(separatorChar, '.'); //获取类名的方式比较原始。

        log.info("Processing " + classname);

        try {
            CtClass cc = m_pool.get(classname);
            asList(cc.getDeclaredMethods()).stream().filter(this::isGetGreeting).forEach(this::processGetGreeting);
            cc.writeFile(classpath);

        } catch (Exception ex) {
            log.error(ex);
            m_hasError = empty();
        }
    }

    private boolean isGetGreeting(CtMethod cm) {
        try {
            return cm.getName().equals("getGreeting") && cm.getReturnType().getName().equals(String.class.getName());

        } catch (NotFoundException ex) {
            getLog().error(ex);
            m_hasError = empty();
            return false;
        }
    }

    private void processGetGreeting(CtMethod cm) {
        try {
            cm.setBody("return \"Welcome.\";");

        } catch (CannotCompileException ex) {
            getLog().error(ex);
            m_hasError = empty();
        }
    }

}
