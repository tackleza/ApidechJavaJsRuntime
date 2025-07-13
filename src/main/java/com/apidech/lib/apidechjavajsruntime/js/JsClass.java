package com.apidech.lib.apidechjavajsruntime.js;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Value;

public class JsClass extends JsAbstract {

	private Value jsClass;
	
	public JsClass(Context context, Value clazz) {
		super(context);
		this.jsClass = clazz;
	}
	
	/**
     * @param superClassName the JS constructor it should extend (e.g. "Animal")
     * @return true if superClass.prototype is in className.prototype’s chain
     */
	public boolean isInstanceOf(String superClassName) {
		
		String className = jsClass.getMember("name").asString();
		
        Value bindings = getContext().getBindings("js");
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
    public String getExtendedClassName() {
        // In JS, a class's [[Prototype]] (accessible as __proto__) points to its superclass constructor
        Value superCtor = jsClass.getMember("__proto__");
        if (superCtor == null || superCtor.isNull()) {
            return null;
        }
        return superCtor.getMember("name").asString();
    }
    
    public boolean isExtendedClass() {
    	String extended = getExtendedClassName();
    	return extended != null && !extended.isEmpty();
    }

    /**
     * Lists all instance method names defined on the prototype of this JS class.
     *
     * @return set of method names (excluding 'constructor')
     * @throws IllegalStateException if this member is not a class/constructor
     */
    public Set<String> getMethodNames() {
        Value extractor = getContext().eval("js",
            "(function(cls) {" +
            "  return Object.getOwnPropertyNames(cls.prototype)" +
            "    .filter(k => typeof cls.prototype[k] === 'function' && k !== 'constructor');" +
            "})"
        );
        Value namesArray = extractor.execute(jsClass);
        @SuppressWarnings("unchecked")
        List<String> list = namesArray.as(List.class);
        return new HashSet<>(list);
    }
    
    /**
     * Checks if a class prototype has a callable member with the given name.
     */
    public boolean hasMethod(String methodName) throws IOException {
        Value proto = jsClass.getMember("prototype");
        return proto.hasMember(methodName) && proto.getMember(methodName).canExecute();
    }

    /**
     * Returns the declared parameter count of a class's instance method.
     */
    public int getMethodArgsCount(String methodName) throws IOException {
        if (!hasMethod(methodName)) {
            throw new IllegalArgumentException("Method '" + methodName + "' not found on '" + getName() + "'");
        }
        Value fn = jsClass.getMember("prototype")
                      .getMember(methodName);
        return fn.getMember("length").asInt();
    }

    /**
     * Returns the list of declared parameter names for a class's instance method.
     */
    public List<String> getMethodArgsNames(String methodName) throws IOException {
        if (!hasMethod(methodName)) {
            throw new IllegalArgumentException("Method '" + methodName + "' not found on '" + getName() + "'");
        }
        Value fn = jsClass.getMember("prototype")
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
    
    public JsClassInstance newInstance(Object... arguments) {
    	return new JsClassInstance(this, jsClass, jsClass.newInstance(arguments));
    }
    
    public JsClassInstance tryNewInstance(Object... arguments) {
    	try {
    		return new JsClassInstance(this, jsClass, jsClass.newInstance(arguments));
    	}
    	catch (Exception e) {}
    	return null;
    }
    
    public boolean canInstantiate() {
    	return jsClass.canInstantiate();
    }
    
    /**
     * Get the class name of javascript
     * @return js class name
     */
    public String getName() {
    	return jsClass.getMember("name").asString();
    }
}
