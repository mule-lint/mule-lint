package org.nuisto.validators

import groovy.util.logging.Slf4j
import org.nuisto.MuleNode

@Slf4j
class HttpRequestValidator extends MuleConfigValidator {
  boolean validate(Node node) {

    boolean returnStatus = true
/*
<set-session-variable variableName="payloadBeforeUntilSuccess" value="#[payload]" doc:name="setPayloadBeforeUntilSuccess"/>
<until-successful maxRetries="${dso.http.max.retries}" doc:name="Until Successful" failureExpression="exception!=null &amp;&amp; (exception.causedBy(java.io.IOException) || exception.causedBy(java.net.ConnectException)  || exception.causedBy(java.net.SocketTimeoutException))" synchronous="true" millisBetweenRetries="${dso.http.milliSecondsBetweenRetries}">
    <http:request config-ref="dsoApiRequestConfiguration" path="${dso.create.api.path}" method="POST" doc:name="DSO_CreateProject">
        <http:request-builder>
            <http:header headerName="Content-Type" value="application/json"/>
            <http:header headerName="Authorization" value="Bearer #[flowVars.dsoToken]"/>
       </http:request-builder>
    </http:request>
</until-successful>

<logger message="system=DSO method=CreateProject action=response projectId=#[flowVars.projectId] invocationId=#[flowVars.invocationId]" level="INFO" category="com.merrillcorp.q2c" doc:name="Logger"/>
<logger message="system=DSO method=CreateProject action=response projectId=#[flowVars.projectId] invocationId=#[flowVars.invocationId] payload=#[payload]" level="DEBUG" category="com.merrillcorp.q2c" doc:name="Logger"/>
        */
    if (!MuleNode.isHttpRequest(node)) {
      return success = false
    }

    Node parent = node.parent()

    if (!MuleNode.isUntilSuccessful(parent)) {
      log.warn('http-request with no until-successful parent {}', node)
      returnStatus = false
    }

    return returnStatus
  }
}
