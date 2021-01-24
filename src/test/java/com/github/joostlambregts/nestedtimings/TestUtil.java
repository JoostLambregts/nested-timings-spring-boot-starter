package com.github.joostlambregts.nestedtimings;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import com.github.joostlambregts.nestedtimings.internal.TimingLogger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestUtil {
    private static ListAppender<ILoggingEvent> listAppender;

    public static void setup(){
        // get Logback Logger
        Logger logger = (Logger) LoggerFactory.getLogger(TimingLogger.class);
        logger.detachAndStopAllAppenders();
        // create and start a ListAppender
        listAppender = new ListAppender<>();
        listAppender.start();
        // add the appender to the logger
        // addAppender is outdated now
        logger.addAppender(listAppender);
    }

    public static void checkLogging(List<LogLineSpec> lineSpecs, int index, boolean whitHeader){
        if(lineSpecs.size() == 0 && listAppender.list.size() == 0) return;
        String log = listAppender.list.get(index).getMessage();
        List<String> logLines = Arrays.asList(log.split("\r\n"));
        int offset = 1;
        assertEquals(lineSpecs.size(),logLines.size()-offset);

        for (int i=offset; i<logLines.size();i++){
            LogLineSpec spec = new LogLineSpec(logLines.get(i));
            assertEquals(spec,lineSpecs.get(i-offset));
        }
    }

    public static void teardown(){
        listAppender.stop();
    }

    public static long wait(int millis) throws InterruptedException {
        Instant startTime = Instant.now();
        Thread.sleep(millis);
        return ChronoUnit.MILLIS.between(startTime, Instant.now());
    }

    @ToString
    @Getter @Setter
    public static class LogLineSpec{
        private static final long leniency = 20;
        private int indents;
        private int executions;
        private long millisTotal;
        private long millisAverage;
        private String name;

        public LogLineSpec(int indents, int executions, long millisTotal, String name) {
            this.indents = indents;
            this.executions = executions;
            this.millisTotal = millisTotal;
            this.millisAverage = millisTotal / executions;
            this.name = name;
        }

        public LogLineSpec(String logLine){
            int length = logLine.length();
            logLine = logLine.replace("\t","");
            indents = length - logLine.length();
            String[] splittedLine = logLine.split("\\|");
            name = splittedLine[0];
            executions = Integer.parseInt(splittedLine[1]);
            millisTotal = Long.parseLong(splittedLine[2]);
            millisAverage = Long.parseLong(splittedLine[3]);
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof LogLineSpec)) return false;
            LogLineSpec spec = (LogLineSpec)obj;
            return name.equals(spec.name)
                    && indents == spec.indents
                    && executions == spec.executions
                    && Math.abs(millisTotal - spec.millisTotal) < leniency
                    && Math.abs(millisAverage - spec.millisAverage) < leniency;
        }
    }
}
