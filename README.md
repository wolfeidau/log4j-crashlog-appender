This is log4j appender for the crashLog service.

To configure this logger using a property file.

    log4j.appender.CRASHLOG=au.id.wolfe.log4j.crashlog.CrashLogAppender
    log4j.appender.CRASHLOG.CrashAuthId=[AS PER YOUR ACCOUNT AUTHID]
    log4j.appender.CRASHLOG.CrashAuthKey=[AS PER YOUR ACCOUNT KEY]
    log4j.appender.CRASHLOG.CrashLogURL=http://stdin.crashlog.io/events
    log4j.appender.CRASHLOG.ServiceId=CrashLog
    log4j.appender.CRASHLOG.ProjectName=[PROJECT NAME]
    log4j.appender.CRASHLOG.Threshold=ERROR

Links

[CrashLog Site](http://crashlog.io)