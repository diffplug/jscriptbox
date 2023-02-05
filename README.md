# <img align="left" src="jscriptbox.png"> JScriptBox: Make your scripting API language-independent
<!---freshmark shields
output = [
  link(shield('Maven artifact', 'mavenCentral', 'com.diffplug.jscriptbox:jscriptbox', 'blue'), 'https://central.sonatype.com/artifact/com.diffplug.jscriptbox/jscriptbox/{{versionLast}}'),
  link(shield('Changelog', 'changelog', '{{versionLast}}', 'blue'), 'CHANGELOG.md'),
  link(shield('Javadoc', 'javadoc', 'here', 'blue'), 'https://javadoc.io/doc/com.diffplug.jscriptbox/jscriptbox')
  ].join('\n');
-->
[![Maven artifact](https://img.shields.io/badge/mavenCentral-com.diffplug.jscriptbox%3Ajscriptbox-blue.svg)](https://central.sonatype.com/artifact/com.diffplug.jscriptbox/jscriptbox/3.0.0)
[![Changelog](https://img.shields.io/badge/changelog-3.0.0-blue.svg)](CHANGELOG.md)
[![Javadoc](https://img.shields.io/badge/javadoc-here-blue.svg)](https://javadoc.io/doc/com.diffplug.jscriptbox/jscriptbox)
<!---freshmark /shields -->

When exposing a scripting API, you provide some variables and functions to the script, run the script, then look at which outputs were set and/or functions were called.  JScriptBox provides a mechanism for exposing a Java API to a scripting language in a way which is independent of the script language.  This means that if you write your code using JScriptBox, script authors can use any language supported by JSR-223, such as JavaScript, Ruby, Python, Scheme, [etc.](http://stackoverflow.com/a/14864450/1153071)

At present, only JavaScript is being used in the wild (in the [FreshMark](https://github.com/diffplug/freshmark) project), but PR's which enhance support for other languages are welcome.

## Example

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

## Acknowledgements
* Built by [gradle](http://gradle.org/).
* Tested by [junit](http://junit.org/).
* Maintained by [DiffPlug](http://www.diffplug.com/).
