![Icon](https://user-images.githubusercontent.com/58639173/92552424-a0e4fb80-f22e-11ea-9f77-ba335242ddaa.png)

# Mikedeejay2Lib
![GitHub stars](https://img.shields.io/github/stars/Mikedeejay2/Mikedeejay2Lib)
![GitHub issues](https://img.shields.io/github/issues/Mikedeejay2/Mikedeejay2Lib)
![GitHub license](https://img.shields.io/github/license/Mikedeejay2/Mikedeejay2Lib)

Library classes for my plugins.

:warning: Mikedeejay2Lib is an internal library. Therefore, this code in itself is not a plugin by itself but instead 
the backbone for whichever plugin it pilots. This is done by shading the library into an uber-jar by shading the required
classes into the `groupid.artifactid.internal.mikedeejay2lib` package to be used interally by the plugin. For usage cases,
please refer to the [Using Mikedeejay2Lib](#Using-Mikedeejay2Lib) section.

### Features

There used to be a list of features here. Now there isn't, it simply got too long. Check source for a list of features. 
Check issues for a list of planned features. If you have a question open an issue or contact me on Discord through 
`Mikedeejay2#3606`.

### Using Mikedeejay2Lib
To use this library, you should import Mikedeejay2Lib using Maven.

Add the repository for Mikedeejay2Lib:
```
    <repositories>
        <repository>
            <id>Mikedeejay2-Maven-Repo</id>
            <url>https://github.com/Mikedeejay2/Mikedeejay2-Maven-Repo/raw/master/</url>
        </repository>
    </repositories>
```

Then, add the dependency for Mikedeejay2Lib:
```
    <dependencies>
        <dependency>
            <groupId>com.mikedeejay2</groupId>
            <artifactId>mikedeejay2lib</artifactId>
            <version>1.16.5-SNAPSHOT</version>
        </dependency>
    </dependencies>
```

Finally, shade Mikedeejay2Lib into your plugin like so:
```
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-shade-plugin</artifactId>
                <version>3.2.4</version>
                <executions>
                    <execution>
                        <phase>package</phase>
                        <goals>
                            <goal>shade</goal>
                        </goals>
                        <configuration>
                            <finalName>${project.name}-${project.version}</finalName>
                            <filters>
                                <filter>
                                    <artifact>*:*</artifact>
                                    <excludes>
                                        <exclude>META-INF/*.SF</exclude>
                                        <exclude>META-INF/*.DSA</exclude>
                                        <exclude>META-INF/*.RSA</exclude>
                                    </excludes>
                                </filter>
                            </filters>
                            <relocations>
                                <relocation>
                                    <pattern>com.mikedeejay2.mikedeejay2lib</pattern>
                                    <shadedPattern>${project.groupId}.${project.artifactId}.internal.mikedeejay2lib
                                    </shadedPattern>
                                </relocation>
                            </relocations>
                            <minimizeJar>true</minimizeJar>
                            <outputFile>${serverDirectory}</outputFile>
                        </configuration>
                    </execution>
                </executions>
            </plugin>
        </plugins>
```

After Maven is setup and Mikedeejay2Lib is imported, make your plugin's main class extend Mikedeejay2Lib's `PluginBase`.

### How to Use

Currently, the only way to figure out how this library works is by looking through source code. Usage examples and a wiki will be posted eventually.

### Maven Repository Updates

Maven updates are provided as snapshots of the Minecraft version that Mikedeejay2Lib is natively supporting. In the case of 1.16.5, the Mikedeejay2Lib version to fetch from the maven repository would be `1.16.5-SNAPSHOT`. There are no "versioned" updates. Updates are automatically fetched by Maven from the repository because of the `SNAPSHOT` keyword in the version.

:warning: Mikedeejay2Lib snapshot updates can easily break or move existing code without warning. To avoid this, disable snapshot auto-updating or manually update code as updates are pushed. This purposely occurs because Mikedeejay2Lib is still in heavy development. When using Mikedeejay2Lib, you should be prepared to update your plugin's code as needed to stay updated.
