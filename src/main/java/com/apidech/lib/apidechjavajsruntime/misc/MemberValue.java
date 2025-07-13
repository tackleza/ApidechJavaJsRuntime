package com.apidech.lib.apidechjavajsruntime.misc;

import java.math.BigInteger;

import org.graalvm.polyglot.Value;

/**
 * A convenience wrapper around GraalVM Polyglot {@link Value} to simplify
 * type-checking, introspection, and conversion of guest-language values.
 */
public class MemberValue {
	
	private final String name;
    private final Value value;

    /**
     * Wraps a Polyglot Value for easier inspection and conversion.
     * 
     * @param value the Polyglot Value to wrap
     */
    public MemberValue(Value value) {
        this(value, null);
    }
    
    /**
     * Wraps a Polyglot Value for easier inspection and conversion.
     * Also with the name of original var name (if provided)
     * @param value
     * @param name
     */
    public MemberValue(Value value, String name) {
    	this.value = value;
        this.name = name;
    }
    
    /**
     * Return true if this MemberValue contain original var name
     * @return
     */
    public boolean hasName() {
    	return name != null;
    }
    
    /**
     * Return the original MemberValue var name (if provided)
     * @return
     */
    public String getName() {
		return name;
	}
    
    /**
     * @return true if the value is null or undefined in the guest language
     */
    public boolean isNull() {
        return value.isNull();
    }

    /**
     * @return true if the value is a JS String
     */
    public boolean isString() {
        return value.isString();
    }

    /**
     * @return true if the value is a numeric type (integer or floating)
     */
    public boolean isNumber() {
        return value.isNumber();
    }

    /**
     * @return true if the value is a JS Boolean
     */
    public boolean isBoolean() {
        return value.isBoolean();
    }

    /**
     * @return true if the value is executable (e.g., a JS function)
     */
    public boolean isFunction() {
        return value.canExecute();
    }

    /**
     * @return true if the value can be instantiated (e.g., a JS class or constructor function)
     */
    public boolean isClass() {
        return value.canInstantiate();
    }
    
    /**
     * @return true if the value has named members (e.g., a JS object)
     */
    public boolean hasMembers() {
        return value.hasMembers();
    }

    /**
     * @return true if the value has indexed elements (e.g., a JS array)
     */
    public boolean hasArrayElements() {
        return value.hasArrayElements();
    }

    /**
     * Convert to Java String.
     * @throws IllegalStateException if the value is not a string
     */
    public String asString() {
        if (!isString()) {
            throw new IllegalStateException("Expected STRING but found " + getType());
        }
        return value.asString();
    }

    /**
     * Convert to Java int.
     * @throws IllegalStateException if the value is not numeric
     */
    public int asInt() {
        if (!isNumber()) {
            throw new IllegalStateException("Expected NUMBER but found " + getType());
        }
        return value.asInt();
    }

    /**
     * Convert to Java long.
     * @throws IllegalStateException if the value is not numeric
     */
    public long asLong() {
        if (!isNumber()) {
            throw new IllegalStateException("Expected NUMBER but found " + getType());
        }
        return value.asLong();
    }

    /**
     * Convert to Java double.
     * @throws IllegalStateException if the value is not numeric
     */
    public double asDouble() {
        if (!isNumber()) {
            throw new IllegalStateException("Expected NUMBER but found " + getType());
        }
        return value.asDouble();
    }

    /**
     * Convert to Java boolean.
     * @throws IllegalStateException if the value is not a boolean
     */
    public boolean asBoolean() {
        if (!isBoolean()) {
            throw new IllegalStateException("Expected BOOLEAN but found " + getType());
        }
        return value.asBoolean();
    }

    /**
     * Convert to {@link BigInteger} (useful for large integers beyond 64-bit).
     * @throws IllegalStateException if the value is not numeric
     */
    public BigInteger asBigInteger() {
        if (!isNumber()) {
            throw new IllegalStateException("Expected NUMBER but found " + getType());
        }
        return value.asBigInteger();
    }

    /**
     * Generic conversion to a host type. Throws if conversion is not possible.
     * 
     * @param <T> the target Java type
     * @param type the {@link Class} object of T
     * @return the converted value
     */
    public <T> T as(Class<T> type) {
        try {
            return value.as(type);
        } catch (ClassCastException e) {
            throw new IllegalStateException(
                "Cannot convert value of type " + getType() + " to " + type.getSimpleName(), e);
        }
    }

    /**
     * @return the GraalVM meta-object description (e.g. "[object Number]")
     */
    public String getType() {
        return value.getMetaObject().toString();
    }

    /**
     * Expose the raw {@link Value} for advanced use cases.
     */
    public Value getRawValue() {
        return value;
    }
}
