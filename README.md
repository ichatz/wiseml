WiseML
======
WiseML parser based on JibX bindings.

What do I need?
---------------

  * JiBX is one of the fastest XML object mapping frameworks out there
(check out this performance test). Furthermore, it is very
flexible in binding XML structures to objects.

http://www.ibm.com/developerworks/library/x-databdopt2/


Simple execution:
======
mvn clean
mvn package
java -cp target/wiseml-1.0-SNAPSHOT-jar-with-dependencies.jar:target/test-classes wiseml.test.SetupNewNode marshal

and then check out file "output.xml"


Contact:
======
Any feedback will be greatly appreciated, at the GitHub project page
(https://github.com/ichatz/wiseml) or by contacting
Ioannis Chatzigiannakis (ichatz@gmail.com).