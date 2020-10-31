![Icon](https://user-images.githubusercontent.com/58639173/92552424-a0e4fb80-f22e-11ea-9f77-ba335242ddaa.png)

# Mikedeejay2Lib
![GitHub stars](https://img.shields.io/github/stars/Mikedeejay2/Mikedeejay2Lib)
![GitHub issues](https://img.shields.io/github/issues/Mikedeejay2/Mikedeejay2Lib)
![GitHub license](https://img.shields.io/github/license/Mikedeejay2/Mikedeejay2Lib)

Library classes for my plugins.

:warning: You do not need to install this! My plugins already come with the required classes from this library.

### Features

* Command Management
* Subcommand System
* Automatic Tab Completion for added commands
* Listener Manager
* File Management
* Several supported file types and databases
  * YAML (Supports comments above keys)
  * JSON
* File Section Accessors (For key-value structured hierarchical file types)
* Robust GUI System
  * Animation System
  * GUI Layers
  * Automatically Managed
  * Navigation Systems
  * Custom Item Interactions
  * Appendable Click Events
    * Navigation Events
    * Open / Close Events
    * List Events
    * Chat Events
  * Customizable GUI Modules
    * List Module
    * Tree Module
    * Confirmation Module
    * Navigator Module
    * Large GUI Scroller Module
    * Border Module
    * Animation Module
  * Ability to have GUIs larger than 6x9 (Double chest size)
* Language Localization Manager
  * Reads from JSON Files
* Enhanced Runnable
  * New Counted Task (Stops after a specified amount of runs)
* Array Utilities
* Block Iterators
* Falling Block Utilities
* Chat Printing Utilities
* Debugging Utilities
  * Debug Timer for printing time between code
* List of Base 64 Heads
* Item Comparison Utilities
* Item Creation Utilities
* Math Utilities
  * Getting a vector around a circle
  * Rotating a vector around an origin point
  * Get a vector facing towards another vector
  * Get a hollow sphere
  * Get a filled sphere
  * Get a hollow cube
  * Get an outline of a cube
  * Get a filled cube
* Particle Utilities
  * Create a line of particles
  * Add a particle to an entity
* Ray Tracing Utilities
  * Ray trace to a location base off of where an entity is looking
  * Ray traces can hit entities
  * Large amount of customizability (Water states, entity predicates)
* Item Meta Search Utilities
  * Search meta fuzzy
  * Search meta non-fuzzy
  * Search meta exact
* Formatted Time Utilities
* Minecraft Version getter

### Using Mikedeejay2Lib
To use this library, you should import Mikedeejay2Lib using Maven.

Add the repository for Mikedeejay2Lib:
```
    <repositories>
        <repository>
            <id>Mikedeejay2Lib-mvn-repo</id>
            <url>https://github.com/Mikedeejay2/Mikedeejay2Lib/raw/mvn-repo/</url>
            <snapshots>
                <enabled>true</enabled>
                <updatePolicy>always</updatePolicy>
            </snapshots>
        </repository>
    </repositories>
```

Then, add the dependency for Mikedeejay2Lib:
```
    <dependencies>
        <dependency>
            <groupId>com.mikedeejay2</groupId>
            <artifactId>mikedeejay2lib</artifactId>
            <version>1.0.0</version>
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
                            <finalName>${project.artifactId}-${project.version}</finalName>
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
                        </configuration>
                    </execution>
                </executions>
            </plugin>
        </plugins>
```

After Maven is setup and Mikedeejay2Lib is imported, make your plugin's main class extend Mikedeejay2Lib's `PluginBase`.

### How to Use

Currently, the only way to figure out how this library works is by looking through source code. Usage examples and a wiki will be posted eventually.
