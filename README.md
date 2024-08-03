# aws-qbusiness-java-boilerplate
A java boilerplate for 
[AWS Q Business](https://aws.amazon.com/q/business/) 
infrastructure using a 
[custom data source connector](https://docs.aws.amazon.com/amazonq/latest/qbusiness-ug/custom-connector.html).

## Getting Started
This boilerplate has two todos:
- [Fetch persistence w/ nextToken (optional) and startTime (if not -1)](/src/main/java/com/ethanpritchard/example/handlers/BatchDocumentHandler.java)
  - Depending on how your data is persisted, you will have to use an API or a client to fetch it. 
    - If paginated, this API or client will consume `nextToken` and give a new `nextToken`.
- [Convert persistence -> Q Business Document](/src/main/java/com/ethanpritchard/example/handlers/BatchDocumentHandler.java)
  - AWS Q Business requires your data to be a [Document structure](https://docs.aws.amazon.com/amazonq/latest/api-reference/API_Document.html) which supports content types such as PDF, HTML, JSON, and more.

## Workflows
### Batch Processing Workflow
An AWS Step Function state machine which when executed:
- Manages the AWS Q Business data source sync job lifecycle (start, stop)
- Calls BatchDocument which fetches documents from your persistence, transforms documents into AWS Q Business Documents, and uses [`qbusiness:BatchPutDocument`](https://docs.aws.amazon.com/amazonq/latest/api-reference/API_BatchPutDocument.html) to iteratively index AWS Q Business Documents

This boilerplate demonstrates its flexibility by adding pagination support with the following modifications:
- Added parameters `maxPages` `nextToken` `startDate` `endDate`
- Modified BatchDocument in order to:
  - Consume `nextToken` `startDate` `endDate` in the request
  - Output a `nextToken` if applicable
  - Passthrough `startDate` `endDate` into the response
- Modified batch processing state machine in order to recursively increment the `currentPage` for each BatchDocument request
- Modified batch processing state machine in order to recursively call BatchDocument with the refreshed `nextToken` until `currentPage` exceeds `maxPages` or `nextToken` is `""`

For larger workflows, I recommend the
[AWS Step Functions Parallel state](https://docs.aws.amazon.com/step-functions/latest/dg/amazon-states-language-parallel-state.html)
instead of recursion.

![alt](https://i.imgur.com/UxlJf2Y.png)

#### How To Use
Trigger `TriggerBatchProcessingStateMachineFunction` with this input:
```
{
    "applicationId": "Q Business application Id",
    "dataSourceId": "Q Business data source Id",
    "indexId": "Q Business index Id",
    "maxPages": Max nextTokens to consume,
    "nextToken": "" OR Paginated next token,
    "startDate": -1 OR Valid timestamp,
    "endDate": -1 OR Valid timestamp
}
```
There are four configurations of parameters for the `TriggerBatchProcessingStateMachineFunction`:
- `nextToken` is `""`. `startDate`/`endDate` are `-1`
  - Starts at beginning of pagination API
- `nextToken` is `""`. `startDate`/`endDate` are `timestamp`
  - Starts at beginning of timeframe filtered pagination API
- `nextToken` is `paginated next token`. `startDate`/`endDate` are `-1`
  - Starts at pagination next token of pagination API
- `nextToken` is `paginated next token`. `startDate`/`endDate` are `timestamp`
  - Starts at pagination next token of timeframe filtered pagination API

Optionally, add automation using [AWS EventBridge Scheduler](https://docs.aws.amazon.com/lambda/latest/dg/with-eventbridge-scheduler.html) to trigger `TriggerBatchProcessingStateMachineFunction` on a schedule.

The `BatchProcessingStateMachine` will recursively call the pagination API until
- `maxPages` is reached, or
- `nextToken` output from `BatchDocumentLambda` is `""`

## Deployment
- `aws s3api create-bucket --bucket <bucket name>`
- `aws cloudformation package --template-file ./cfn/template.json --s3-bucket <bucket name> --output-template-file ./target/template.json`
- `aws cloudformation deploy --template-file ./target/template.json --capabilities "CAPABILITY_NAMED_IAM" --stack-name <stack name>` and optionally adding `--parameter-overrides IdCInstanceArn=<idc instance arn>`

## License
[LICENSE](/LICENSE)