WiseML
======
WiseML parser based on [JibX](http://www.ibm.com/developerworks/library/x-databdopt2/) bindings.


What do I need?
---------------

  * [JibX](http://www.ibm.com/developerworks/library/x-databdopt2/) is one of the fastest XML object mapping frameworks out there
(check out this performance test). Furthermore, it is very
flexible in binding XML structures to objects.



Simple execution:
======
mvn clean

mvn package

java -cp target/wiseml-1.0-SNAPSHOT-jar-with-dependencies.jar:target/test-classes eu.wisebed.wiseml.test.SetupNewNode marshal

and then check out file "output.xml"


Contact:
======
Any feedback will be greatly appreciated, at the GitHub project page
(https://github.com/ichatz/wiseml) or by contacting
[ichatz](mailto:ichatz@gmail.com)