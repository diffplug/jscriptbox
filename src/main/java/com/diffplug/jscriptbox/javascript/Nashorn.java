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
	/** Language implementation for javascript using the nashorn engine. */
	public static Language language() {
		return map -> {
			ScriptEngine jsEngine = new ScriptEngineManager().getEngineByName("nashorn");
			ScriptContext context = jsEngine.getContext();

			String mapName = "nashornScriptBoxMap";
			context.setAttribute(mapName, map, ScriptContext.ENGINE_SCOPE);

			StringBuilder builder = new StringBuilder();
			map.entrySet().forEach(entry -> {
				builder.append("var ");
				builder.append(normalize(entry.getKey()));
				builder.append("=");
				builder.append(mapName);
				builder.append(".get('");
				builder.append(entry.getKey());
				builder.append("');\n");
			});
			builder.append("delete " + mapName + ";\n");
			jsEngine.eval(builder.toString());
			return jsEngine;
		};
	}

	private static String normalize(String input) {
		if (restrictedWords.contains(input)) {
			return "_" + input;
		} else {
			return input;
		}
	}

	private static final Set<String> restrictedWords = new HashSet<>(Arrays.asList(
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
