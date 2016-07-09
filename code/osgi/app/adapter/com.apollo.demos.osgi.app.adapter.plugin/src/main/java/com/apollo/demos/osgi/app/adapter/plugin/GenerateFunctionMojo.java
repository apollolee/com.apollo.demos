/*
 * 此代码创建于 2016年6月14日 上午9:55:03。
 */
package com.apollo.demos.osgi.app.adapter.plugin;

import static java.io.File.separatorChar;
import static java.lang.Boolean.FALSE;
import static java.util.Arrays.asList;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static java.util.Optional.ofNullable;
import static java.util.stream.Stream.concat;
import static javassist.ClassPool.getDefault;
import static javassist.CtNewMethod.make;
import static javassist.Modifier.isAbstract;
import static org.apache.maven.plugins.annotations.LifecyclePhase.COMPILE;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javassist.ClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.NotFoundException;

import org.apache.commons.io.FileUtils;
import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.DependencyResolutionRequiredException;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.descriptor.PluginDescriptor;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import com.apollo.demos.osgi.app.adapter.api.IFunction;
import com.apollo.demos.osgi.app.adapter.api.Processor;

@Mojo(name = "generateFunction", defaultPhase = COMPILE)
public class GenerateFunctionMojo extends AbstractMojo {

    @Parameter(defaultValue = "${project}", readonly = true)
    private MavenProject project;

    @Parameter(defaultValue = "${plugin}", readonly = true)
    private PluginDescriptor plugin;

    private Optional<Boolean> m_hasError = of(FALSE);

    private ClassPool m_pool = getDefault();

    public void execute() throws MojoExecutionException, MojoFailureException {
        Optional<List<String>> classpaths = empty();
        try {
            classpaths = ofNullable(project.getCompileClasspathElements());

        } catch (DependencyResolutionRequiredException ex) {
            throw new MojoExecutionException("Get compile classpaths is failed.", ex);
        }

        project.getDependencyArtifacts().forEach(this::insertClassPath);
        plugin.getArtifacts().forEach(this::insertClassPath);

        classpaths.ifPresent(cs -> cs.forEach(this::processClasspath));
        m_hasError.orElseThrow(() -> new MojoExecutionException("Execute is failed."));
    }

    private void insertClassPath(Artifact artifact) {
        try {
            m_pool.insertClassPath(artifact.getFile().getAbsolutePath());

        } catch (NotFoundException ex) {
            getLog().error(ex);
            m_hasError = empty();
        }
    }

    private void processClasspath(String classpath) {
        Log log = getLog();

        log.info("Processing " + classpath);

        Collection<File> cfs = FileUtils.listFiles(new File(classpath), new String[] { "class" }, true);

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
        classname = classname.substring(classpath.length() + 1, classname.length() - 6).replace(separatorChar, '.');

        try {
            CtClass cc = m_pool.get(classname);
            if (!isAbstract(cc.getModifiers()) && cc.subtypeOf(m_pool.get(IFunction.class.getName()))) { //这里直接使用IFunction没有关系，因为这里不会篡改IFunction。
                log.info("Processing " + classname);
                processFunctionClass(cc);
                cc.writeFile(classpath);
            }

        } catch (Exception ex) {
            log.error(ex);
            m_hasError = empty();
        }
    }

    private void processFunctionClass(CtClass cc) throws Exception {
        Stream<CtField> ps = getProcessors(cc);

        //有两个方案可选，一个是getProcessors方法不管实现与否，都强行覆盖插件的逻辑。另一个是如果getProcessors已经被实现就不再覆盖了，给应用一定的自主空间。我们这里选后者作为实现方案。
        //注意：maven-compiler-plugin会因为源代码没有更新而不进行编译，这会导致class是上一次已经被篡改过的了，所以即使应用没有实现getProcessors，下面这个警告还是会出现。
        //一个比较好的方式是每次install前先clean一下。另外，如果能像maven-compiler-plugin提前知道不需要处理直接提示all classes are up to date更好，不过目前没有找到相关api。
        try {
            cc.getDeclaredMethod("getProcessors", new CtClass[0]); //这里不能用getMethod，有无Declared意义和java反射包中的定义是一样的。因为BaseFunction实现了这个方法，getMethod会把BaseFunction.getProcessors获取到。
            getLog().warn("The Method is already implemented.[" + cc.getName() + ".getProcessors]");

        } catch (NotFoundException ex) {
            StringBuilder src = new StringBuilder();
            src.append("public org.osgi.service.component.ComponentFactory[] getProcessors() { return new org.osgi.service.component.ComponentFactory[] { ");
            src.append(ps.sorted(this::compare).map(f -> f.getName()).collect(Collectors.joining(",")));
            src.append(" }; }");
            cc.addMethod(make(src.toString(), cc));
        }
    }

    private Stream<CtField> getProcessors(CtClass cc) {
        Stream<CtField> ps = asList(cc.getDeclaredFields()).stream().filter(f -> f.hasAnnotation(Processor.class));

        Optional<CtClass> osc = empty();
        try {
            osc = ofNullable(cc.getSuperclass());

        } catch (NotFoundException ex) {
            throw new RuntimeException(ex);
        }

        if (osc.isPresent()) {
            ps = concat(ps, getProcessors(osc.get()));
        }

        return ps;
    }

    private int compare(CtField f1, CtField f2) {
        try {
            Processor p1 = (Processor) f1.getAnnotation(Processor.class);
            Processor p2 = (Processor) f2.getAnnotation(Processor.class);
            return Integer.compare(p1.order(), p2.order());

        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex); //这个地方就是用非检查异常包装检查异常在Lambda表达式中传递，前提是我明确知道在这里调用它的高阶函数没有另启线程处理。
        }
    }

}
