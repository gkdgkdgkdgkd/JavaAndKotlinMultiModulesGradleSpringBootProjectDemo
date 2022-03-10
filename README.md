# 1 环境
- `Gradle 6.8.2`
- `Spring Boot 2.4.3`
- `Kotlin 1.4.30`
- `Open JDK 11`

# 2 `Java + Gradle`
主要步骤：

- 使用`Spring Initializer`创建项目
- 修改`build.gradle`
- 创建模块
- 编写模块
- 运行
- 测试

## 2.1 创建项目
直接使用`IDEA`提供的`Spring Initializer`即可，构建工具选择`Gradle`：

![在这里插入图片描述](https://img-blog.csdnimg.cn/20210302182258479.png)

依赖：

![在这里插入图片描述](https://img-blog.csdnimg.cn/20210302203252834.png)

**构建完成后删除`src`目录**，因为根目录属于管理模块目录不提供运行的应用：

![在这里插入图片描述](https://img-blog.csdnimg.cn/20210302182628108.png)
## 2.2 修改`build.gradle`
这是最复杂的一步，并且`Gradle`版本更新的话步骤可能会不一样，**首先在底部添加一个空的`subprojects`**：

![在这里插入图片描述](https://img-blog.csdnimg.cn/2021030218292823.png)

接着**把`dependencies`以及`test`移动进去**：

![在这里插入图片描述](https://img-blog.csdnimg.cn/20210302183050141.png)

最后一步是，**在`subprojects`开头，添加插件`apply`，根据默认初始化创建的`plugins`，逐一添加**。

比如这里默认使用了三个插件：

![在这里插入图片描述](https://img-blog.csdnimg.cn/20210302183301588.png)

`apply`到`subprojects`中：

![在这里插入图片描述](https://img-blog.csdnimg.cn/20210302183725544.png)

## 2.3 创建模块
`File -> New -> Module`：

![在这里插入图片描述](https://img-blog.csdnimg.cn/20210302183544698.png)

输入模块名即可，这里的例子是创建两个模块：

- **`service`**
- **`app`**

![在这里插入图片描述](https://img-blog.csdnimg.cn/2021030218391243.png)

![在这里插入图片描述](https://img-blog.csdnimg.cn/20210302183958660.png)

创建好后如图所示：

![在这里插入图片描述](https://img-blog.csdnimg.cn/2021030218410737.png)

完成创建之后，**把两个模块中的`build.gradle`除了`repositories`之外的全部删去**，仅保留`repositories`：

![在这里插入图片描述](https://img-blog.csdnimg.cn/20210302184254983.png)

![在这里插入图片描述](https://img-blog.csdnimg.cn/20210302184353774.png)

## 2.4 编写模块
### 2.4.1 `service`模块
首先**创建包**，根据根目录中的`group`创建：

![在这里插入图片描述](https://img-blog.csdnimg.cn/20210302184845857.png)

![在这里插入图片描述](https://img-blog.csdnimg.cn/20210302184922482.png)

接着编写一个叫`TestService`的带`@Service`注解的类，里面包含一个`test`方法：

![在这里插入图片描述](https://img-blog.csdnimg.cn/20210302185028647.png)

同时修改`service`模块的`build.gradle`，**添加`bootJar`以及`jar`选项**：
```bash
bootJar{
    enabled = false
}

jar{
    enabled = true
}
```
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210302184713211.png)

### 2.4.2 `app`模块
同样先根据根目录的`group`**创建包**：

![在这里插入图片描述](https://img-blog.csdnimg.cn/20210302185147370.png)

接着在`app`模块的`build.gradle`**添加`service`模块的依赖**：

![在这里插入图片描述](https://img-blog.csdnimg.cn/20210302185447644.png)

再创建启动类以及一个`Controller`：

![在这里插入图片描述](https://img-blog.csdnimg.cn/20210302185526487.png)

代码如下：
```java
package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }
}
```

```java
package com.example.controller;

import com.example.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TestController {
    private final TestService service;
    @GetMapping("/test")
    public String test(){
        return service.test();
    }
}
```

## 2.5 运行
接下来就可以运行了，可以直接点击`Application`旁边的绿色小三角：

![在这里插入图片描述](https://img-blog.csdnimg.cn/2021030218570925.png)

或者从运行配置中选择`Application`运行（`IDEA`自动创建的，原来的那个`DemoApplication`带一个×是因为启动文件已经删除了，可以顺便把该配置删除）：

![在这里插入图片描述](https://img-blog.csdnimg.cn/20210302185745193.png)

没问题的话就可以成功运行了：

![在这里插入图片描述](https://img-blog.csdnimg.cn/20210302185917750.png)

同时浏览器访问`localhost:8080/test`会出现`test`字样：

![在这里插入图片描述](https://img-blog.csdnimg.cn/20210302190006675.png)

## 2.6 测试
在创建测试类之前，也需要**先创建包，且需要确保包名与启动类的包名一致**：

![在这里插入图片描述](https://img-blog.csdnimg.cn/20210302202906337.png)

再创建测试类：

![在这里插入图片描述](https://img-blog.csdnimg.cn/20210302203102853.png)

```java
package com.example;

import com.example.service.TestService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JavaTest {
    @Autowired
    private TestService service;
    @Test
    public void test(){
        System.out.println(service.test());
    }
}
```
接着进行测试：

![在这里插入图片描述](https://img-blog.csdnimg.cn/20210302203048671.png)

这样使用`Java`+`Gradle`构建一个多模块的`Spring Boot`项目就成功了。

# 3 `Kotlin + Gradle + Kotlin DSL`
`Kotlin DSL`在原生`Gradle`（`Groovy DSL`）的基础上进行改进，但同时语法也变得更加陌生，难度因此也加大了不少，不过这并没有难倒笔者。构建多模块的基本步骤与上面类似：

- 使用`Spring Initializer`创建项目
- 修改`build.gradle.kts`
- 创建模块
- 编写模块
- 运行
- 测试

## 3.1 创建项目
选择`Kotlin`+`Gradle`：

![在这里插入图片描述](https://img-blog.csdnimg.cn/20210302191228379.png)

依赖：

![在这里插入图片描述](https://img-blog.csdnimg.cn/20210302191254471.png)

同样**删除`src`**：

![在这里插入图片描述](https://img-blog.csdnimg.cn/20210302194145804.png)
## 3.2 修改`build.gradle.kts`
同样在尾部**添加一个空的`subprojects`**：

![在这里插入图片描述](https://img-blog.csdnimg.cn/20210302191442159.png)

**把`dependencies`以及`tasks`移动进去**：

![在这里插入图片描述](https://img-blog.csdnimg.cn/20210302191518343.png)

![在这里插入图片描述](https://img-blog.csdnimg.cn/20210302191614874.png)

最后**在`subprojects`开始处`apply`插件**，**根据默认的插件进行`apply`**：

![在这里插入图片描述](https://img-blog.csdnimg.cn/20210302194246705.png)

代码如下：
```bash
apply{
    plugin("io.spring.dependency-management")
    plugin("org.springframework.boot")
    plugin("org.jetbrains.kotlin.plugin.spring")
    plugin("org.jetbrains.kotlin.jvm")
}
```
**`plugins`中的`kotlin`是`org.jetbrains.kotlin`的简写**，在`subprjects`中注意加上即可。

## 3.3 创建模块
`File -> New -> Module`，把一些必要选项勾选上：

![在这里插入图片描述](https://img-blog.csdnimg.cn/20210302194457119.png)

这里同样创建两个模块：

- `app`
- `service`

![在这里插入图片描述](https://img-blog.csdnimg.cn/2021030219454215.png)

![在这里插入图片描述](https://img-blog.csdnimg.cn/20210302194600752.png)

同样把两个模块中的**`build.gradle.kts`删除其他部分留下`repositories`**：

![在这里插入图片描述](https://img-blog.csdnimg.cn/2021030219473681.png)

## 3.4 编写模块
### 3.4.1 `service`模块
首先根据根目录的`build.gradle.kts`**创建包**：

![在这里插入图片描述](https://img-blog.csdnimg.cn/2021030219485442.png)

![在这里插入图片描述](https://img-blog.csdnimg.cn/20210302194928711.png)

编写`TestService`：

![在这里插入图片描述](https://img-blog.csdnimg.cn/20210302195018197.png)

最后修改`build.gradle.kts`，**加上`tasks.bootJar`与`tasks.jar`**：
```bash
tasks.bootJar{
    enabled = false
}

tasks.jar{
    enabled = true
}
```

![在这里插入图片描述](https://img-blog.csdnimg.cn/20210302195205578.png)
### 3.4.2 `app`模块
先**创建包**：

![在这里插入图片描述](https://img-blog.csdnimg.cn/20210302195304362.png)

**添加对`service`模块的依赖**：

![在这里插入图片描述](https://img-blog.csdnimg.cn/20210302195354683.png)

再创建一个启动类以及一个`Controller`：

![在这里插入图片描述](https://img-blog.csdnimg.cn/20210302195607272.png)

代码如下：
```kotlin
package com.example

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class Application

fun main(args:Array<String>) {
    SpringApplication.run(Application::class.java,*args)
}
```

```kotlin
package com.example.controller

import com.example.service.TestService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TestController {
    @Autowired
    lateinit var service: TestService
    @GetMapping("/test")
    fun test() = service.test()
}
```

## 3.5 运行
点击`main`旁边的绿色小三角即可：

![在这里插入图片描述](https://img-blog.csdnimg.cn/20210302195715732.png)

运行成功：

![在这里插入图片描述](https://img-blog.csdnimg.cn/2021030219575668.png)

同样可以访问`localhost:8080/test`：

![在这里插入图片描述](https://img-blog.csdnimg.cn/20210302195852323.png)

## 3.6 测试
注意在编写测试之前需要保证测试类与启动类在同一个包下，也就是需要**先创建包**：

![在这里插入图片描述](https://img-blog.csdnimg.cn/20210302201002664.png)

再创建测试类：

![在这里插入图片描述](https://img-blog.csdnimg.cn/20210302201052939.png)

```kotlin
package com.example

import com.example.service.TestService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class KotlinTest {
    @Autowired
    lateinit var service: TestService
    @Test
    fun test(){
        println(service.test())
    }
}
```
直接点击小三角测试即可：

![在这里插入图片描述](https://img-blog.csdnimg.cn/20210302201159661.png)

测试通过，这样`Kotlin+Gradle+Kotlin DSL`的多模块`Spring Boot`项目就算创建完成了。
