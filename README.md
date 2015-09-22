# <img align="left" src="jscriptbox.png"> JScriptBox: Make your scripting API language-independent
<!---freshmark shields
output = [
	link(shield('Maven artifact', 'mavenCentral', '{{group}}:{{name}}', 'blue'), 'https://bintray.com/{{org}}/opensource/{{name}}/view'),
	link(shield('Latest version', 'latest', '{{stable}}', 'blue'), 'https://github.com/{{org}}/{{name}}/releases/latest'),
	link(shield('Javadoc', 'javadoc', 'OK', 'blue'), 'https://{{org}}.github.io/{{name}}/javadoc/{{stable}}/'),
	link(shield('License Apache', 'license', 'Apache', 'blue'), 'https://tldrlegal.com/license/apache-license-2.0-(apache-2.0)'),
	'',
	link(shield('Changelog', 'changelog', '{{version}}', 'brightgreen'), 'CHANGES.md'),
	link(image('Travis CI', 'https://travis-ci.org/{{org}}/{{name}}.svg?branch=master'), 'https://travis-ci.org/{{org}}/{{name}}'),
	link(shield('Live chat', 'gitter', 'live chat', 'brightgreen'), 'https://gitter.im/{{org}}/{{name}}')
	].join('\n');
-->
[![Maven artifact](https://img.shields.io/badge/mavenCentral-com.diffplug.jscriptbox%3Ajscriptbox-blue.svg)](https://bintray.com/diffplug/opensource/jscriptbox/view)
[![Latest version](https://img.shields.io/badge/latest-3.0.0-blue.svg)](https://github.com/diffplug/jscriptbox/releases/latest)
[![Javadoc](https://img.shields.io/badge/javadoc-OK-blue.svg)](https://diffplug.github.io/jscriptbox/javadoc/3.0.0/)
[![License Apache](https://img.shields.io/badge/license-Apache-blue.svg)](https://tldrlegal.com/license/apache-license-2.0-(apache-2.0))

[![Changelog](https://img.shields.io/badge/changelog-3.1.0--SNAPSHOT-brightgreen.svg)](CHANGES.md)
[![Travis CI](https://travis-ci.org/diffplug/jscriptbox.svg?branch=master)](https://travis-ci.org/diffplug/jscriptbox)
[![Live chat](https://img.shields.io/badge/gitter-live_chat-brightgreen.svg)](https://gitter.im/diffplug/jscriptbox)
<!---freshmark /shields -->

When exposing a scripting API, you provide some variables and functions to the script, run the script, then look at which outputs were set and/or functions were called.  JScriptBox provides a mechanism for exposing a Java API to a scripting language in a way which is independent of the script language.  This means that if you write your code using JScriptBox, script authors can use any language supported by JSR-223, such as JavaScript, Ruby, Python, Scheme, [etc.](http://stackoverflow.com/a/14864450/1153071)

At present, only JavaScript is being used in the wild (in the [FreshMark](https://github.com/diffplug/freshmark) project), but PR's which enhance support for other languages are welcome.

## Example

<!---freshmark javadoc
output = prefixDelimiterReplace(input, 'https://{{org}}.github.io/{{name}}/javadoc/', '/', stable);
-->

```java
private int square(int x) {
	return x * x;
}

@Test
public void example() throws ScriptException {
	TypedScriptEngine engine = JScriptBox.create()
		.set("square").toFunc1(this::square)
		.set("x").toValue(9)
		.buildTyped(Nashorn.language());
	int squareOfX = engine.eval("square(x)", Integer.class);
	Assert.assertEquals(81, squareOfX);
}
```

In the code above, we provide a `square`  function and an `x` variable.  We then build a Nashorn Javascript engine, but we could have passed any other language too.  To add a new language, just implement [Language](src/main/java/com/diffplug/jscriptbox/Language.java) (the existing [Nashorn](src/main/java/com/diffplug/jscriptbox/javascript/Nashorn.java) can be a helpful starting point).

<!---freshmark /javadoc -->

## Acknowledgements
* Readme formatting by [FreshMark](https://github.com/diffplug/freshmark).
* Bugs found by [findbugs](http://findbugs.sourceforge.net/), [as such](https://github.com/diffplug/durian-rx/blob/v1.0/build.gradle?ts=4#L92-L116).
* Scripts in the `.ci` folder are inspired by [Ben Limmer's work](http://benlimmer.com/2013/12/26/automatically-publish-javadoc-to-gh-pages-with-travis-ci/).
* Built by [gradle](http://gradle.org/).
* Tested by [junit](http://junit.org/).
* Maintained by [DiffPlug](http://www.diffplug.com/).
