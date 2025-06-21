package com.apidech.lib.apidechjavajsruntime.js;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Value;

public class JsClass extends JsFile {

	public JsClass(Context context, Value value) {
		super(context, value);
	}
	
	/**
     * @return all top-level JS constructors
     */
    public List<String> getClasses() throws IOException {
        Context ctx = getContext();
        Value bindings = ctx.getBindings("js");
        List<String> found = new ArrayList<>();
        for (String key : bindings.getMemberKeys()) {
            Value val = bindings.getMember(key);
            if (val.canInstantiate()) {
            	found.add(key);
            }
        }
        return found;
    }
	
	/**
     * @param className      the JS constructor name (e.g. "Cat")
     * @param superClassName the JS constructor it should extend (e.g. "Animal")
     * @return true if superClass.prototype is in className.prototype’s chain
     */
	public boolean isInstanceOf(String className, String superClassName) {
        Value bindings = getBinding();
        if (!bindings.hasMember(className) || !bindings.hasMember(superClassName)) {
            throw new IllegalArgumentException(
              "Class not found: " + className + " or " + superClassName
            );
        }
        // Evaluate Animal.prototype.isPrototypeOf(Cat.prototype)
        String expr = String.format(
          "%s.prototype.isPrototypeOf(%s.prototype)",
          superClassName, className
        );
        Value result = getContext().eval("js", expr);
        return result.asBoolean();
    }
	
	/**
     * Returns the name of the superclass of the given JS class (or null if none).
     */
    public String getExtendedClassName(String className) throws IOException {
        Value bindings = getContext().getBindings("js");
        Value ctor = bindings.getMember(className);
        if (ctor == null) {
            throw new IllegalArgumentException("Class not found: " + className);
        }
        // In JS, a class's [[Prototype]] (accessible as __proto__) points to its superclass constructor
        Value superCtor = ctor.getMember("__proto__");
        if (superCtor == null || superCtor.isNull()) {
            return null;
        }
        return superCtor.getMember("name").asString();
    }

    /**
     * Lists all instance method names declared directly on the class prototype.
     */
    public List<String> getMethods(String className) throws IOException {
        Value proto = getContext().getBindings("js")
                                  .getMember(className)
                                  .getMember("prototype");
        List<String> methods = new ArrayList<>();
        for (String key : proto.getMemberKeys()) {
            Value member = proto.getMember(key);
            // exclude constructor and non-callables
            if (!"constructor".equals(key) && member.canExecute()) {
                methods.add(key);
            }
        }
        return methods;
    }

    /**
     * Checks if a class prototype has a callable member with the given name.
     */
    public boolean hasMethod(String className, String methodName) throws IOException {
        Value proto = getContext().getBindings("js")
                                  .getMember(className)
                                  .getMember("prototype");
        return proto.hasMember(methodName) && proto.getMember(methodName).canExecute();
    }

    /**
     * Returns the declared parameter count of a class's instance method.
     */
    public int getMethodArgsCount(String className, String methodName) throws IOException {
        if (!hasMethod(className, methodName)) {
            throw new IllegalArgumentException("Method '" + methodName + "' not found on '" + className + "'");
        }
        Value fn = getContext().getBindings("js")
                      .getMember(className)
                      .getMember("prototype")
                      .getMember(methodName);
        return fn.getMember("length").asInt();
    }

    /**
     * Returns the list of declared parameter names for a class's instance method.
     */
    public List<String> getMethodArgsNames(String className, String methodName) throws IOException {
        if (!hasMethod(className, methodName)) {
            throw new IllegalArgumentException("Method '" + methodName + "' not found on '" + className + "'");
        }
        Value fn = getContext().getBindings("js")
                      .getMember(className)
                      .getMember("prototype")
                      .getMember(methodName);
        String src = fn.invokeMember("toString").asString();
        int start = src.indexOf('(') + 1;
        int end = src.indexOf(')', start);
        if (start < 1 || end < 0 || end <= start) {
            return Collections.emptyList();
        }
        String params = src.substring(start, end).trim();
        if (params.isEmpty()) {
            return Collections.emptyList();
        }
        return Arrays.stream(params.split(","))
                     .map(String::trim)
                     .toList();
    }
}
