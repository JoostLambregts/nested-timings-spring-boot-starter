package com.github.joostlambregts.nestedtimings.internal;

import com.github.joostlambregts.nestedtimings.api.interfaces.NestedTiming;
import com.github.joostlambregts.nestedtimings.api.interfaces.NestedTimingConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TimingLogger implements NestedTimingConsumer {
    private final Logger LOGGER = LoggerFactory.getLogger(TimingLogger.class);

    @Override
    public void accept(NestedTiming nestedTiming) {
        printTimings(nestedTiming,"", new StringWrapper());
    }

    private void printTimings(NestedTiming nestedTiming, String indentation, final StringWrapper logStringWrapper) {
        logStringWrapper.append(formatTimingLine(nestedTiming,indentation));

        nestedTiming.getChildren().forEach((name, timer) -> printTimings(timer,indentation + "\t", logStringWrapper));
        if(nestedTiming.getParent() == null){
            LOGGER.debug(logStringWrapper.getValue());
        }
    }

    private String formatTimingLine(NestedTiming nestedTiming, String indentation){
        long elapsedMillis = nestedTiming.getElapsedMillis();
        int executions = nestedTiming.getExecutions();
        return "\r\n" + indentation + nestedTiming.getName() + "|" + executions +"|" + elapsedMillis + "|" + elapsedMillis/executions;
    }

    private static class StringWrapper{
        private String value = "";

        public void append(String s){
            value += s;
        }

        public String getValue() {
            return value;
        }
    }
}
