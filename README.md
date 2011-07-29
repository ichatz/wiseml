WiseML
======
The repository contains the following packages:

 * WiseML parser based on [JibX](http://www.ibm.com/developerworks/library/x-databdopt2/) bindings.
 * WiseConfig parser based on [JibX](http://www.ibm.com/developerworks/library/x-databdopt2/) bindings.
 * WiseDB relational persistence based on [Hibernate](http://www.hibernate.org/) bindings.
 * WiseRDF mappings based on [Jena](http://jena.sourceforge.net/) framework.


Related links:

 * [WISEBED D4.1 Using WiseML to produce testbed traces](http://www.wisebed.eu/images/stories/deliverables/d4.1.pdf)
 * [WISEBED D4.2 Updates to the WiseML standard](http://www.wisebed.eu/images/stories/deliverables/d4.2.pdf)

What do I need?
---------------

  * [JibX](http://www.ibm.com/developerworks/library/x-databdopt2/) is one of the fastest XML object mapping frameworks out there (check out this performance test). Furthermore, it is very flexible in binding XML structures to objects.
  * [Hibernate](http://www.hibernate.org/) is probably the most well establish relational persistence framework for Java. Hibernate facilitates the storage and retrieval of Java domain objects via Object/Relational Mapping (ORM).


Latest stable version
---------------

The latest stable version is available from [Hudson](http://ru1.cti.gr/hudson/job/wiseml/), the continuous integration tool.

  * [wiseml-1.1-SNAPSHOT-jar-with-dependencies.jar](http://ru1.cti.gr/hudson/job/wiseml/lastSuccessfulBuild/artifact/target/wiseml-1.1-SNAPSHOT-jar-with-dependencies.jar)

  * [wiseml-1.1-SNAPSHOT-javadoc.jar](http://ru1.cti.gr/hudson/job/wiseml/lastSuccessfulBuild/artifact/target/wiseml-1.1-SNAPSHOT-javadoc.jar)

  * [wiseml-1.1-SNAPSHOT.jar](http://ru1.cti.gr/hudson/job/wiseml/lastSuccessfulBuild/artifact/target/wiseml-1.1-SNAPSHOT.jar)

(testing the SCM polling mechanism of Hudson)

Hibernate Configuration:
======
You can configure Hibernate by providing the appropriate configuration in your local Maven settings file `~/.m2/settings.xml`:

	<settings>
    	...
		<profiles>
        	<profile>
				<id>inject-hibernate-details-wisedb</id>
            	<properties>
                	<hibernate.dialect>org.hibernate.dialect.MySQLDialect</hibernate.dialect>
                	<jdbc.connection.driver_class>com.mysql.jdbc.Driver</jdbc.connection.driver_class>
                	<jdbc.connection.url>jdbc:mysql://ip-on-remote-wisedb/wisdeb</jdbc.connection.url>
                	<jdbc.connection.username>wisedb</jdbc.connection.username>
                	<jdbc.connection.password>wisedbpassword</jdbc.connection.password>
                	<jdbc.connection.pool_size>10</jdbc.connection.pool_size>
            	</properties>
        	</profile>
			<profile>
			<id>inject-hibernate-details-wisedb-test</id>
				<properties>
					<test.hibernate.dialect>org.hibernate.dialect.MySQLDialect</test.hibernate.dialect>
					<test.jdbc.connection.driver_class>com.mysql.jdbc.Driver</test.jdbc.connection.driver_class>
					<test.jdbc.connection.url>jdbc:mysql://localhost/wisedb</test.jdbc.connection.url>
					<test.jdbc.connection.username>wisedb</test.jdbc.connection.username>
					<test.jdbc.connection.password>wisedbpassword</test.jdbc.connection.password>
					<test.jdbc.connection.pool_size>10</test.jdbc.connection.pool_size>
				</properties>
			</profile>
		</profiles>
		<activeProfiles>
			<activeProfile>inject-hibernate-details-wisedb</activeProfile>
			<activeProfile>inject-hibernate-details-wisedb-test</activeProfile>
		</activeProfiles>
		...
	</settings>   

Note that you should provide two profiles one for the main sources and one for the test resources. Configuration is now stored in jar using `mvn package`.

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
			<version>1.1-SNAPSHOT</version>
		</dependency>

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
