WiseML
======
The repository contains the following packages:

 * WiseML parser based on [JibX](http://www.ibm.com/developerworks/library/x-databdopt2/) bindings.


Related links:

 * [WISEBED D4.1 Using WiseML to produce testbed traces](http://www.wisebed.eu/images/stories/deliverables/d4.1.pdf)
 * [WISEBED D4.2 Updates to the WiseML standard](http://www.wisebed.eu/images/stories/deliverables/d4.2.pdf)

What do I need?
---------------

  * [JibX](http://www.ibm.com/developerworks/library/x-databdopt2/) is one of the fastest XML object mapping frameworks out there
(check out this performance test). Furthermore, it is very
flexible in binding XML structures to objects.


Latest stable version
---------------

The latest stable version is available from [Hudson](http://ru1.cti.gr/hudson/job/wiseml/), the continuous integration tool.

  * [wiseml-1.0-SNAPSHOT-jar-with-dependencies.jar](http://ru1.cti.gr/hudson/job/wiseml/lastSuccessfulBuild/artifact/target/wiseml-1.0-SNAPSHOT-jar-with-dependencies.jar)

  * [wiseml-1.0-SNAPSHOT-javadoc.jar](http://ru1.cti.gr/hudson/job/wiseml/lastSuccessfulBuild/artifact/target/wiseml-1.0-SNAPSHOT-javadoc.jar)

  * [wiseml-1.0-SNAPSHOT.jar](http://ru1.cti.gr/hudson/job/wiseml/lastSuccessfulBuild/artifact/target/wiseml-1.0-SNAPSHOT.jar)

Simple execution:
======
mvn clean

mvn package

java -cp target/wiseml-1.0-SNAPSHOT-jar-with-dependencies.jar:target/test-classes eu.wisebed.wiseml.test.SetupNewNode marshal

and then check out file "output.xml"

Use in your Maven project
======

Add the following dependency to your pom.xml:
	
		<dependency>
			<groupId>ichatz</groupId>
			<artifactId>wiseml</artifactId>
			<version>1.0-SNAPSHOT</version>
		<dependency>

Add the following repositories to your pom.xml:

	<repositories>
		<repository>
			<id>itm-maven-repository-releases</id>
			<url>http://www.itm.uni-luebeck.de/projects/maven/releases/</url>
			<releases>
				<enabled>true</enabled>
			</releases>
			<snapshots>
				<enabled>false</enabled>
			</snapshots>
		</repository>

		<repository>
			<id>itm-maven-repository-snapshots</id>
			<url>http://www.itm.uni-luebeck.de/projects/maven/snapshots/</url>
			<releases>
				<enabled>false</enabled>
			</releases>
			<snapshots>
				<enabled>true</enabled>
			</snapshots>
		</repository>
	</repositories>


Contact:
======
Any feedback will be greatly appreciated, at the GitHub project page
(https://github.com/ichatz/wiseml) or by contacting
[ichatz](mailto:ichatz@gmail.com)
