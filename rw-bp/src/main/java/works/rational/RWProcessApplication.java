package works.rational;

import org.camunda.bpm.application.PostDeploy;
import org.camunda.bpm.application.ProcessApplication;
import org.camunda.bpm.application.impl.ServletProcessApplication;
import org.camunda.bpm.engine.CaseService;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.variable.Variables;

@ProcessApplication("Rational Works Process Application")
public class RWProcessApplication extends ServletProcessApplication {

  @PostDeploy
  public void startCaseInstance(ProcessEngine processEngine) {
    CaseService caseService = processEngine.getCaseService();
    caseService.createCaseInstanceByKey("loan_application",
            Variables.createVariables()
                    .putValue("applicationSufficient", Variables.booleanValue(null))
                    .putValue("rating", Variables.integerValue(null)));
  }
}