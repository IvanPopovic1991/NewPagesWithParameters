package core.listeners;

import core.utils.LoggerUtil;
import org.apache.logging.log4j.Logger;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {

    private static final Logger log =
            LoggerUtil.getLogger(RetryAnalyzer.class);

    private int retryCount = 0;
    private static final int maxRetry = 1;

    @Override
    public boolean retry(ITestResult result) {

        if (retryCount < maxRetry) {

            retryCount++;

            log.warn("Retrying test: {} | Attempt: {}",
                    result.getName(),
                    retryCount);

            return true;
        }

        return false;
    }
}

