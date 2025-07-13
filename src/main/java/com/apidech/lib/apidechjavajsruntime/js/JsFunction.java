package com.apidech.lib.apidechjavajsruntime.js;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.graalvm.polyglot.PolyglotException;
import org.graalvm.polyglot.Value;

import com.apidech.lib.apidechjavajsruntime.misc.MemberValue;

public class JsFunction {
	
	private Value fn;
	public JsFunction(Value fn) {
		this.fn = fn;
	}
	
	public JsResult executeVoid(Object... arguments) {
		JsResult result = null;
		try {
			fn.executeVoid(arguments);
			result = new JsResult((MemberValue)null);
		}
		catch (PolyglotException e) {
			result = new JsResult(e);
		}
		return result;
	}
	
	public JsResult execute(Object... arguments) {
		JsResult result = null;
		try {
			result = new JsResult(new MemberValue(fn.execute(arguments)));
		}
		catch (PolyglotException e) {
			result = new JsResult(e);
		}
		return result;
	}
	
	public int getArgumentCount() {
		return fn.getMember("length").asInt();
	}
	
	public List<String> getArgumentNames() {
        // Extract source and parse between parentheses
        String fnSrc = fn.invokeMember("toString").asString();
        int start = fnSrc.indexOf('(') + 1;
        int end = fnSrc.indexOf(')', start);
        if (start < 0 || end < 0 || end <= start) {
            return Collections.emptyList();
        }
        String paramsSection = fnSrc.substring(start, end).trim();
        if (paramsSection.isEmpty()) {
            return Collections.emptyList();
        }
        // Split names by comma and trim
        return Arrays.stream(paramsSection.split(","))
                     .map(String::trim)
                     .collect(Collectors.toList());
    }
	
	public String getName() {
		return fn.getMember("name").asString();
	}
	
	public Set<String> getMembers() {
		return fn.getMemberKeys();
	}
}
