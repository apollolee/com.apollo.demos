/*
 * 此代码创建于 2022年2月3日 下午7:48:14。
 */
package com.apollo.demos.base.jsr269;

import static com.sun.tools.javac.tree.TreeMaker.instance;
import static com.sun.tools.javac.util.List.of;
import static javax.lang.model.SourceVersion.RELEASE_8;
import static javax.tools.Diagnostic.Kind.MANDATORY_WARNING;

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

import com.sun.tools.javac.model.JavacElements;
import com.sun.tools.javac.processing.JavacProcessingEnvironment;
import com.sun.tools.javac.tree.JCTree.JCExpression;
import com.sun.tools.javac.tree.JCTree.JCMethodDecl;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.util.Context;
import com.sun.tools.javac.util.List;

@SupportedSourceVersion(RELEASE_8)
@SupportedAnnotationTypes("com.apollo.demos.base.jsr269.Log")
public class LogProcessor extends AbstractProcessor {

    void log(String msg) {
        processingEnv.getMessager().printMessage(MANDATORY_WARNING, msg);
    }

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        log("LogProcessor.init");
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        log("LogProcessor.process");

        Context context = ((JavacProcessingEnvironment) processingEnv).getContext();
        JavacElements jes = (JavacElements) processingEnv.getElementUtils();
        TreeMaker tm = instance(context);

        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(Log.class);
        if (elements == null || elements.size() == 0) {
            return true;
        }

        for (Element element : elements) {
            Log annotation = element.getAnnotation(Log.class);
            JCMethodDecl tree = (JCMethodDecl) jes.getTree(element);
            tm.pos = tree.pos;
            tree.body = tm.Block(0,
                                 of(tm.Exec(tm.Apply(List.<JCExpression> nil(),
                                                     tm.Select(tm.Select(tm.Ident(jes.getName("System")), jes.getName("out")), jes.getName("println")),
                                                     List.<JCExpression> of(tm.Literal("jsr269.log.[" + annotation.level().toString() + "][" + element.getSimpleName() + "]")))),
                                    tree.body));
        }

        return true;
    }

}
