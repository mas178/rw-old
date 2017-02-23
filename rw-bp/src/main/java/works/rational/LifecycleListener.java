package works.rational;

import org.camunda.bpm.engine.delegate.CaseExecutionListener;
import org.camunda.bpm.engine.delegate.DelegateCaseExecution;

import java.util.logging.Logger;

public class LifecycleListener implements CaseExecutionListener {

  private final static Logger LOGGER = Logger.getLogger("LOAN-REQUESTS");

  public void notify(DelegateCaseExecution caseExecution) throws Exception {
    LOGGER.info("Plan Item '"
            + caseExecution.getActivityId()
            + "' labeled '"
            + caseExecution.getActivityName()
            + "' has performed transition: "
            + caseExecution.getEventName());
  }
}
