package com.github.mybatis.easy.go.processor;

import com.github.mybatis.easy.go.sourceAnnotation.FunctionBag;
import com.sun.source.tree.Tree;
import com.sun.source.util.TreePath;
import com.sun.source.util.Trees;
import com.sun.tools.javac.api.JavacTrees;
import com.sun.tools.javac.code.Flags;
import com.sun.tools.javac.processing.JavacProcessingEnvironment;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.tree.TreeTranslator;
import com.sun.tools.javac.util.Context;
import com.sun.tools.javac.util.List;
import com.sun.tools.javac.util.ListBuffer;
import com.sun.tools.javac.util.Names;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.*;

/**
 * @author zhouyu74748585@hotmail.com
 * @date 2021/4/16 21:04
 */
@SupportedAnnotationTypes("com.github.mybatis.easy.go.methodAnnotation.*")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class AnnotationProcessor extends AbstractProcessor {

    // 打印log
    private Messager messager;
    // 抽象语法树
    private JavacTrees trees;
    // 封装了创建AST节点的一些方法
    private TreeMaker treeMaker;
    // 提供了创建标识符的一些方法
    private Names names;

    public AnnotationProcessor() {
    }

    public static <T> Object getFieldValue(T object, String property) {
        if (object != null && property != null) {
            Class<T> currClass = (Class<T>) object.getClass();
            try {
                Field field = currClass.getDeclaredField(property);
                field.setAccessible(true);
                return field.get(object);
            } catch (NoSuchFieldException e) {
                throw new IllegalArgumentException(currClass + " has no property: " + property);
            } catch (IllegalArgumentException e) {
                throw e;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    // 初始化方法
    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        this.messager = processingEnv.getMessager();
        this.trees = JavacTrees.instance(processingEnv);
        Context context = ((JavacProcessingEnvironment) processingEnv).getContext();
        this.treeMaker = TreeMaker.instance(context);
        this.names = Names.instance(context);
    }

    // 真正处理注解的方法
    @Override
    public synchronized boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        HashMap<String, List<JCTree>> methodMap = new HashMap<>();
        HashMap<String, String> importMap = new HashMap<>();
        //获取方法仓库对象类型
        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(FunctionBag.class);
        for (Element element : elements) {
            java.util.List<? extends Element> methods = element.getEnclosedElements();
            for (Element method : methods) {
                for (AnnotationMirror am : method.getAnnotationMirrors()) {
                    if (am.getAnnotationType().toString().equals("com.github.mybatis.easy.go.sourceAnnotation.FunctionSource")) {
                        try {
                            Annotation annotation = method.getAnnotation((Class) Class.forName(am.getAnnotationType().toString()));
                            InvocationHandler invo = Proxy.getInvocationHandler(annotation); //获取被代理的对象
                            Map map = (Map) getFieldValue(invo, "memberValues");
                            String importString = map.get("requiredClass").toString();
                            String targetAnnotation = map.get("targetAnnotation").toString();
                            JCTree jct = trees.getTree(method);
                            if (methodMap.containsKey(targetAnnotation)) {
                                methodMap.put(targetAnnotation, methodMap.get(targetAnnotation).append(jct));
                            } else {
                                methodMap.put(targetAnnotation, List.of(jct));
                            }
                            importMap.put(jct.toString(), importString);
                        } catch (Exception e) {
                            messager.printMessage(Diagnostic.Kind.ERROR, e.toString());
                        }
                    }
                }
            }
        }
        //正式处理添加注解的类
        for (TypeElement annotation : annotations) {
            String sourceName = annotation.getQualifiedName().toString();
            Set<? extends Element> elements1 = roundEnv.getElementsAnnotatedWith(annotation);
            List<JCTree> methods = methodMap.get(sourceName);
            if (methods != null && methods.length() > 0) {
                elements1.forEach(element -> {
                    JCTree jcTree = trees.getTree(element);
                    jcTree.accept(new TreeTranslator() {
                        @Override
                        public void visitClassDef(JCTree.JCClassDecl jcClassDecl) {
                            HashSet<String> classHashSet = new HashSet<>();
                            for (JCTree method : methods) {
                                String importStr = importMap.get(method.toString());
                                if (importStr != null && importStr.trim().length() > 0) {
                                    for (String s : importStr.split(",")) {
                                        classHashSet.add(s);
                                    }
                                }
                                classHashSet.add(((JCTree.JCMethodDecl) method).sym.owner.type.toString());
                                JCTree.JCMethodDecl jcm = cloneMethod((JCTree.JCMethodDecl) method);
                                messager.printMessage(Diagnostic.Kind.NOTE, "jcm:" + jcm);
                                jcClassDecl.defs = jcClassDecl.defs.append(jcm);
                            }
                            for (String aClass : classHashSet) {
                                String packageName = aClass.substring(0, aClass.lastIndexOf("."));
                                String className = aClass.substring(aClass.lastIndexOf(".") + 1);
                                addImportInfo(element, packageName, className);
                            }
                            super.visitClassDef(jcClassDecl);
                        }
                    });
                });
            }
        }
        return true;
    }

    public JCTree.JCMethodDecl cloneMethod(JCTree.JCMethodDecl jcm) {
        String owner = jcm.sym.owner.type.toString();
        owner = owner.substring(owner.lastIndexOf(".") + 1);
        ListBuffer<JCTree.JCStatement> statements = new ListBuffer<>();
        List<JCTree.JCExpression> expressions = List.of(treeMaker.Ident(names.fromString("this")));
        List<JCTree.JCVariableDecl> newParam = List.nil();
        for (JCTree.JCVariableDecl param : jcm.params) {
            if (param.name.toString().equals("action")) {
                continue;
            }
            newParam = newParam.append((JCTree.JCVariableDecl) param.clone());
            expressions = expressions.append(treeMaker.Ident(names.fromString(param.name.toString())));
        }

        JCTree.JCExpressionStatement exec0 = treeMaker.Exec(
                treeMaker.Apply(
                        com.sun.tools.javac.util.List.nil(),
                        treeMaker.Select(
                                treeMaker.Ident(names.fromString(owner)), // . 左边的内容
                                names.fromString(jcm.getName().toString()) // . 右边的内容
                        ),
                        expressions // 方法中的内容
                )
        );
        JCTree.JCVariableDecl callMethod = treeMaker.VarDef(
                treeMaker.Modifiers(Flags.PARAMETER),
                names.fromString("obj"),
                treeMaker.Ident(names.fromString(jcm.getReturnType().toString())),
                exec0.getExpression()
        );
        statements.append(callMethod);
        statements.append(treeMaker.Return(treeMaker.Ident(names.fromString("obj"))));
        JCTree.JCBlock body = treeMaker.Block(0, statements.toList());
        JCTree.JCMethodDecl action = treeMaker.MethodDef(
                treeMaker.Modifiers(Flags.PUBLIC), // 方法限定值
                names.fromString(jcm.getName().toString()), // 方法名
                treeMaker.Ident(names.fromString(jcm.getReturnType().toString())), // 返回类型
                List.nil(),
                newParam,
                List.nil(),
                body,
                null
        );
        return action;
    }

    ;

    private void addImportInfo(Element element, String packagePath, String className) {
        Trees _trees = Trees.instance(processingEnv);
        TreePath treePath = _trees.getPath(element);
        Tree leaf = treePath.getLeaf();
        if (treePath.getCompilationUnit() instanceof JCTree.JCCompilationUnit && leaf instanceof JCTree) {
            JCTree.JCCompilationUnit jccu = (JCTree.JCCompilationUnit) treePath.getCompilationUnit();
            for (JCTree jcTree : jccu.getImports()) {
                if (jcTree != null && jcTree instanceof JCTree.JCImport) {
                    JCTree.JCImport jcImport = (JCTree.JCImport) jcTree;
                    if (jcImport.qualid != null && jcImport.qualid instanceof JCTree.JCFieldAccess) {
                        JCTree.JCFieldAccess jcFieldAccess = (JCTree.JCFieldAccess) jcImport.qualid;
                        try {
                            if (packagePath.equals(jcFieldAccess.selected.toString()) && className.equals(jcFieldAccess.name.toString())) {
                                return;
                            }
                        } catch (NullPointerException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            java.util.List<JCTree> trees = new ArrayList<>();
            trees.addAll(jccu.defs);
            JCTree.JCIdent ident = treeMaker.Ident(names.fromString(packagePath));
            JCTree.JCImport jcImport = treeMaker.Import(treeMaker.Select(
                    ident, names.fromString(className)), false);
            if (!trees.contains(jcImport)) {
                trees.add(0, jcImport);
            }
            jccu.defs = List.from(trees);


        }

    }

}
