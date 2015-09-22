/*
 * Copyright 2015 DiffPlug
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.diffplug.jscriptbox.javascript;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import com.diffplug.jscriptbox.Language;

public class Nashorn {
	/**
	 * Language implementation for javascript using the nashorn engine.
	 * <p>
	 * If any bindings are created which conflict with reserved
	 * keywords, an IllegalArgumentException will be thrown.
	 */
	public static Language language() {
		return language(OnReservedKeyword.ERROR);
	}

	/** Language implementation for javascript using the given policy for resolving any potential conflicts with reserved keywords. */
	public static Language language(OnReservedKeyword policy) {
		return map -> {
			ScriptEngine jsEngine = new ScriptEngineManager().getEngineByName("nashorn");
			ScriptContext context = jsEngine.getContext();

			String mapName = "nashornScriptBoxMap";
			context.setAttribute(mapName, map, ScriptContext.ENGINE_SCOPE);

			StringBuilder builder = new StringBuilder();
			for (String key : map.keySet()) {
				if (isReserved(key)) {
					switch (policy) {
					case ERROR:
						throw new IllegalArgumentException("'" + key + "' is a reserved keyword.");
					case MANGLE:
						key = key + "_";
						break;
					case SKIP:
						continue;
					default:
						throw new IllegalArgumentException("Unhandled enum value '" + policy + "'");
					}
				}
				builder.append("var ");
				builder.append(key);
				builder.append("=");
				builder.append(mapName);
				builder.append(".get('");
				builder.append(key);
				builder.append("');\n");
			}
			builder.append("delete " + mapName + ";\n");
			jsEngine.eval(builder.toString());
			return jsEngine;
		};
	}

	/** Describes a policy for dealing with reserved keywords. */
	public enum OnReservedKeyword {
		ERROR, MANGLE, SKIP;
	}

	/** Returns true if the given identifier is a JavaScript reserved keyword. */
	public static boolean isReserved(String word) {
		return reservedKeywords.contains(word);
	}

	private static final Set<String> reservedKeywords = new HashSet<>(Arrays.asList(
			// JavaScript Reserved Words
			"abstract", "arguments", "boolean", "break", "byte",
			"case", "catch", "char", "class", "const",
			"continue", "debugger", "default", "delete", "do",
			"double", "else", "enum", "eval", "export",
			"extends", "false", "final", "finally", "float",
			"for", "function", "goto", "if", "implements",
			"import", "in", "instanceof", "int", "interface",
			"let", "long", "native", "new", "null",
			"package", "private", "protected", "public", "return",
			"short", "static", "super", "switch", "synchronized",
			"this", "throw", "throws", "transient", "true",
			"try", "typeof", "var", "void", "volatile",
			"while", "with", "yield",
			// JavaScript Objects, Properties, and Methods
			"Array", "Date", "eval", "function", "hasOwnProperty",
			"Infinity", "isFinite", "isNaN", "isPrototypeOf", "length",
			"Math", "NaN", "name", "Number", "Object",
			"prototype", "String", "toString", "undefined", "valueOf",
			// Java Reserved Words
			"getClass", "java", "JavaArray", "javaClass", "JavaObject", "JavaPackage"));
}
