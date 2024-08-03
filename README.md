# aws-qbusiness-java-boilerplate
A java boilerplate for 
[AWS Q Business](https://aws.amazon.com/q/business/) 
infrastructure using a 
[custom data source connector](https://docs.aws.amazon.com/amazonq/latest/qbusiness-ug/custom-connector.html).

## Workflows
### Batch Processing Workflow
An AWS Step Function state machine which when executed:
- Manages the AWS Q Business data source sync job lifecycle (start, stop)
- Calls BatchDocument which fetches documents from your persistence, transforms documents into AWS Q Business Documents, and uses [`qbusiness:BatchPutDocument`](https://docs.aws.amazon.com/amazonq/latest/api-reference/API_BatchPutDocument.html) to iteratively index AWS Q Business Documents

This boilerplate demonstrates its flexibility by adding pagination support with the following modifications:
- Added optional parameters `nextToken` and `maxPages`
- Modified BatchDocument in order to:
  - Consume `nextToken` in the request
  - Output a `nextToken` if applicable
- Modified AWS Step Functions state machine in order to recursively increment the `currentPage` for each BatchDocument request

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
    "nextToken": "Paginated next token used for BatchDocument (Use empty string if no nextToken)",
    "startDate": -1 OR valid,
    "endDate": -1
}
```
Optionally, add automation using [AWS EventBridge Scheduler](https://docs.aws.amazon.com/lambda/latest/dg/with-eventbridge-scheduler.html) to trigger `TriggerBatchProcessingStateMachineFunction` on a schedule.

This boilerplate also includes the `startDate` and `endDate` parameters which are `Double` timestamps.
These parameters can be used instead of `nextToken` to search a timeframe instead of a subset.
`startDate` and `endDate` should both be `-1` or both be a valid timestamp range.

## Deployment
- `aws s3api create-bucket --bucket <bucket name>`
- `aws cloudformation package --template-file ./cfn/template.json --s3-bucket <bucket name> --output-template-file ./target/template.json`
- `aws cloudformation deploy --template-file ./target/template.json --capabilities "CAPABILITY_NAMED_IAM" --stack-name <stack name>` and optionally adding `--parameter-overrides IdCInstanceArn=<idc instance arn>`

## License
[LICENSE](/LICENSE)