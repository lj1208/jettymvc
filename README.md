jettymvc
========

A demo of spring webmvc running on embeded jetty.

* Do not use any xml config, no web.xml and no servlet.xml.
* Very simple that containing one and only one class.
* Support serving dynamic and static pages.

demo
---

* firstly start the web server by following these steps:
 1. download this project
 2. mvn package
 3. java -jar target/jettymvc.jar
* secondly test the web server
 1. http://127.0.0.1:8080/home
 2. http://127.0.0.1:8080/images/autobots.jpeg
