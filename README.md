CrashLog Log4J Appender
===========

This is log4j appender for the crashLog service.

Development
-------------

Adding this library to your maven project will require use of my plugin
repository at the moment, this is configures as follows:

    <repositories>
        <repository>
            <id>wolfeidau.repo</id>
            <name>Mark Wolfe's Repository</name>
            <url>http://repo.wolfe.id.au/</url>
            <layout>default</layout>
        </repository>
    </repositories>

Then add this library to your project as a dependency.

    <dependency>
        <groupId>au.id.wolfe</groupId>
        <artifactId>log4j-crashlog-appender</artifactId>
        <version>1.0.1</version>
    </dependency>

Configuration
-------------

To configure this logger using a log4j property file add this fragment.

    log4j.appender.CRASHLOG=au.id.wolfe.log4j.crashlog.CrashLogAppender
    log4j.appender.CRASHLOG.CrashAuthId=[AS PER YOUR ACCOUNT AUTHID]
    log4j.appender.CRASHLOG.CrashAuthKey=[AS PER YOUR ACCOUNT KEY]
    log4j.appender.CRASHLOG.CrashLogURL=http://stdin.crashlog.io/events
    log4j.appender.CRASHLOG.ServiceId=CrashLog
    log4j.appender.CRASHLOG.ProjectName=[PROJECT NAME]
    log4j.appender.CRASHLOG.Threshold=ERROR

Contributors
------------

* [Mark Wolfe](http://github.com/wolfeidau)

# Links

[CrashLog Site](http://crashlog.io)

© Mark Wolfe, 2012, [Apache License, Version 2.0](http://opensource.org/licenses/Apache-2.0)
