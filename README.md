# Acknowledgements
Most of the examples in this repo were copied from Alex Shipilev [https://twitter.com/shipilev] blog and used for educational purposes
All rights belong to author.
- https://shipilev.net/
- https://shipilev.net/blog/2016/close-encounters-of-jmm-kind/
- https://openjdk.java.net/projects/code-tools/jcstress/

# Build and run
- Please download OpendJDK12 from https://adoptopenjdk.net/
- Make sure you have Maven v3.6.x
- `mvn clean install`
- `java -jar target/jcstress.jar -v -t TESTNAME` where `TESTNAME` is regexp selector for tests