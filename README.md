# vd-cli-util

A lightweight Java framework for building command-line tools with structured option parsing, help generation, and testable execution.

---

## ✨ Features

- Structured argument and option parsing  
- Auto-generated help output  
- Layered error handling  
- Fully testable CLI programs (no `System.exit()` in tests)  
- Modular design for reusability and extension  

---

## 📦 Maven Dependency

```xml
<dependency>
    <groupId>de.voomdoon.util</groupId>
    <artifactId>vd-cli-util</artifactId>
    <version>0.2.0-SNAPSHOT</version>
</dependency>
```

---

## 🚀 Getting Started

### 1. Extend the `Program` class

```java
public class HelloWorldProgram extends Program {

	public static void main(String[] args) {
		Program.run(args);
	}

	private Option nameOption;

	protected void initOptions() {
		nameOption = addOption().longName("name").hasValue("username").build();
	}

	protected void run() {
		String name = getOptionValue(nameOption).orElse("World");
		System.out.println("Hello, " + name + "!");
	}
}
```

---

## 🧪 Testing

Use `ProgramTestingUtil` to enable test mode and prevent `System.exit()` during unit tests.

```java
@BeforeEach
void setup() {
    ProgramTestingUtil.enableTestingMode();
}
```

---

## 🛠 Packages Overview

| Package | Purpose |
|--------|---------|
| `cli` | Main CLI lifecycle (Program, runner, execution) |
| `cli.args` | Option and argument parsing |
| `cli.args.exception` | Argument validation exceptions |
| `cli.testing` | Test support utilities |
