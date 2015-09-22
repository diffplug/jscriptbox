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
package com.diffplug.jscriptbox;

import java.util.Map;

import javax.script.ScriptEngine;
import javax.script.ScriptException;

/** Interface which converts the result of a {@link JScriptBox} into a {@link ScriptEngine}. */
public interface Language {
	/**
	 * @param names		a map from names which should be defined in the script to their objects, particularly elements of {@link ArityN}.
	 * @return			a ScriptEngine which has had these names mapped to objects and functions.
	 * @throws ScriptException
	 */
	ScriptEngine initializeEngine(Map<String, Object> names) throws ScriptException;
}
