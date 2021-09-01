OBFUSCATION of Food and Beverages Module
========================================

What is Obfuscation?
--------------------
Obfuscation is the obscuring of the intended meaning of communication by making the message difficult to understand, 
usually with confusing and ambiguous language. 
The obfuscation might be either unintentional or intentional (although intent usually is connoted), 
and is accomplished with circumlocution (talking around the subject), the use of jargon (technical language of a profession), 
nd the use of an argot (ingroup language) of limited communicative value to outsiders.

Problem in Obfuscation of FnB module
------------------------------------
The main obstacle faced while obfuscating the FnB module is the attachment of faces APIs.
However proper versioning of Java Faces and few more addition finally resulted in a successful completion.


Steps to obfuscate 
------------------
The main tricks lie in the organization of your code that to be obfuscated.
The initial efforts were to convert it into a Maven based project to easy apply this obfuscation technique.

Now question comes how we can create or modify existing project into a Maven project.
First of all, you need to create a pom.xml with the project name, group-id and artifact name.
A few other things also there but those are optional
However the project standard for FnB is given below.


	<modelVersion>4.0.0</modelVersion>
	<groupId>com.sharobitech</groupId>
	<artifactId>Restaurant</artifactId>
	<version>1.0</version>
	<packaging>war</packaging>
	<name>Restaurant</name>
	<url>http://maven.apache.org</url>


Now the main area comes to managing the dependencies.
This is basically a little headache kind of things.
However you can add any dependency in 	<dependencies> tag/module of Maven.
Any individual entry can be added as show below.

		<dependency>
			<groupId>org.apache.commons</groupId>
			<artifactId>commons-email</artifactId>
			<version>1.2</version>
		</dependency>

So you need a groupId of the component, then the atrifact and the version of that component you want to use.
You need to carefully choose the dependencies to eliminate unwanted dependencies.

Once you you are done with the project, now its the part of starting Obfuscation.
But before that you first need to structure the packages to easy access else it would become a history or essay writting.
Just like club the Controller or Services or even DAO into a package to access by ...<package>.*

Once you are done you can now move again to POM file.
You will find a configuration section under build plugin.
There you need to add proguard configutations and option under options tag.
Various options are available there with -keep,  -keepnames, -keepclassmembers, -keepclassmembernames as per project's packages and classes you wrote.

Once done, you need to invoke the following command to generate the obfuscated war file (you can write a batch file as well)
mvn clean package
cd target\<Component>
jar -cvf <Component.war> .

And ...............

And this is your so waited Obfuwscated war file.

HAPPY OBFUSCATION !!!

