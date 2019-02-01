package com.liujing.pagerouter.compiler;


import com.google.auto.service.AutoService;
import com.liujing.pagerouter.annotation.RouterActivity;
import com.liujing.pagerouter.annotation.RouterFragment;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.util.Elements;

@SupportedSourceVersion(SourceVersion.RELEASE_7)
@SupportedAnnotationTypes({"com.liujing.pagerouter.annotation.RouterActivity", "com.liujing.pagerouter.annotation.RouterFragment"})
@AutoService(Processor.class)
public class RouterProcessor extends AbstractProcessor {
    private Elements mElementUtils;
    private String targetModuleName = "";

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (annotations.size() == 0) {
            return false;
        }
        Set<? extends Element> activityElements = roundEnv.getElementsAnnotatedWith(RouterActivity.class);
        Set<? extends Element> fragmentElements = roundEnv.getElementsAnnotatedWith(RouterFragment.class);

        ClassName activityRouteTableInitializer = ClassName.get("com.liujing.pagerouter", "RouterInitializer");
        TypeSpec.Builder typeSpec = TypeSpec.classBuilder((targetModuleName.length() == 0 ? "Apt" : targetModuleName) + "RouterInitializer")
                .addSuperinterface(activityRouteTableInitializer)
                .addModifiers(Modifier.PUBLIC);

        TypeElement activityRouteTableInitializerTypeElement = mElementUtils.getTypeElement(activityRouteTableInitializer.toString());
        List<? extends Element> members = mElementUtils.getAllMembers(activityRouteTableInitializerTypeElement);
        MethodSpec.Builder activityBindViewMethodSpecBuilder = null;
        MethodSpec.Builder fragmentBindViewMethodSpecBuilder = null;
        for (Element element : members) {
            if ("initActivityTable".equals(element.getSimpleName().toString())) {
                activityBindViewMethodSpecBuilder = MethodSpec.overriding((ExecutableElement) element);
                continue;
            }
            if ("initFragmentTable".equals(element.getSimpleName().toString())) {
                fragmentBindViewMethodSpecBuilder = MethodSpec.overriding((ExecutableElement) element);
            }
        }
        if (activityBindViewMethodSpecBuilder == null) {
            return false;
        }
        if (fragmentBindViewMethodSpecBuilder == null) {
            return false;
        }

        for (Element element : activityElements) {
            RouterActivity routerActivity = element.getAnnotation(RouterActivity.class);
            TypeElement typeElement = (TypeElement) element;
            for (String key : routerActivity.value()) {
                activityBindViewMethodSpecBuilder.addStatement("arg0.put($S, $T.class)", key, typeElement.asType());
            }
        }
        for (Element element : fragmentElements) {
            RouterFragment routerFragment = element.getAnnotation(RouterFragment.class);
            TypeElement fragmentTypeElement = (TypeElement) element;
            TypeElement activityTypeElement = null;
            try {
                routerFragment.activityClazz();
            } catch (MirroredTypeException mte) {
                DeclaredType classTypeMirror = (DeclaredType) mte.getTypeMirror();
                activityTypeElement = (TypeElement) classTypeMirror.asElement();
            }
            if (activityTypeElement == null) {
                continue;
            }
            for (String key : routerFragment.value()) {
                fragmentBindViewMethodSpecBuilder.addStatement("arg0.put($S,new Pair($T.class,$T.class))", key, activityTypeElement.asType(), fragmentTypeElement.asType());
            }
        }

        JavaFile javaFile = JavaFile.builder("com.liujing.pagerouter", typeSpec.addMethod(activityBindViewMethodSpecBuilder.build()).addMethod(fragmentBindViewMethodSpecBuilder.build()).build()).build();
        try {
            javaFile.writeTo(processingEnv.getFiler());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        mElementUtils = processingEnv.getElementUtils();
        Map<String, String> map = processingEnv.getOptions();
        Set<String> keys = map.keySet();
        for (String key : keys) {
            if ("targetModuleName".equals(key)) {
                this.targetModuleName = map.get(key);
            }
        }
    }
}