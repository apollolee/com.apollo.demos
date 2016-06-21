/*
 * 此代码创建于 2016年6月14日 上午9:55:03。
 */
package com.apollo.demos.osgi.app.adapter.plugin;

import static java.io.File.separatorChar;
import static java.util.Arrays.asList;
import static java.util.Optional.empty;
import static java.util.Optional.ofNullable;
import static java.util.stream.Stream.concat;
import static javassist.Modifier.isAbstract;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javassist.ClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;
import javassist.NotFoundException;

import org.apache.commons.io.FileUtils;
import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.DependencyResolutionRequiredException;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.project.MavenProject;

import com.apollo.demos.osgi.app.adapter.api.IFunction;
import com.apollo.demos.osgi.app.adapter.api.Processor;

@Mojo(name = "generateFunction")
public class GenerateFunctionMojo extends AbstractMojo {

    @SuppressWarnings("rawtypes")
    public void execute() throws MojoExecutionException, MojoFailureException {
        Log log = getLog();
        Map pc = getPluginContext();
        MavenProject mp = (MavenProject) pc.get("project");

        Optional<List<String>> ocps = empty();
        try {
            ocps = ofNullable(mp.getCompileClasspathElements());

        } catch (DependencyResolutionRequiredException ex) {
            log.error(ex);
        }

        ocps.ifPresent(cs -> cs.forEach(this::processClasspath));
    }

    @SuppressWarnings("rawtypes")
    private void processClasspath(String classpath) {
        Log log = getLog();
        Map pc = getPluginContext();
        MavenProject mp = (MavenProject) pc.get("project");

        log.info("Processing " + classpath);

        Collection<File> cfs = FileUtils.listFiles(new File(classpath), new String[] { "class" }, true);
        ClassPool pool = ClassPool.getDefault();
        mp.getDependencyArtifacts().forEach(a -> insertClassPath(pool, a));
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

    private void insertClassPath(ClassPool pool, Artifact artifact) {
        try {
            pool.insertClassPath(artifact.getFile().getAbsolutePath());

        } catch (NotFoundException ex) {
            getLog().error(ex);
        }
    }

    private void processClass(ClassPool pool, String classpath, File classfile) {
        Log log = getLog();

        String classname = classfile.getAbsolutePath();
        classname = classname.substring(classpath.length() + 1, classname.length() - 6).replace(separatorChar, '.');

        try {
            CtClass cc = pool.get(classname);
            if (!isAbstract(cc.getModifiers()) && cc.subtypeOf(pool.get(IFunction.class.getName()))) {
                log.info("Processing " + classname);
                processFunctionClass(pool, cc);
                cc.writeFile(classpath);
            }

        } catch (Exception ex) {
            log.error(ex);
        }
    }

    private void processFunctionClass(ClassPool pool, CtClass cc) throws Exception {
        Stream<CtField> ps = getProcessors(cc);

        CtMethod cm = pool.get(IFunction.class.getName()).getDeclaredMethod("getProcessors");
        cm = new CtMethod(cm, cc, null);

        StringBuilder src = new StringBuilder();
        src.append("return new org.osgi.service.component.ComponentFactory[] {");
        src.append(ps.sorted(this::compare).map(f -> f.getName()).collect(Collectors.joining(",")));
        src.append("};");

        cm.setBody(src.toString());
        cc.addMethod(cm);
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
            throw new RuntimeException(ex);
        }
    }

}
