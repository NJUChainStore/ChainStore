# WebService

## API

Content-Type: application/json

### POST /data

Add a information into the database.

parameters: body

| Name | Type | Explanation |
| ---- | ---- | ----------- |
| token | string | token for a project |
| info | string | content of the information to add |


responses: 

- 200: Token is valid. The Information is added.

| Name | Type | Explanation |
| --- | --- | -------- |
| blockIndex | number(long) | the block index where the information is added |
| offset | number(int) | the offset of the block where the information is added |

- 403: Token is not valid.

### GET /data

Queries a information from the database.

parameters: query

| Name | Type | Explanation |
| ---- | ---- | ----------- |
| blockIndex | number(long) | the block index |
| offset | number(long) | the offset of a block |
| token | string | token for a project |

responses:

- 200: Token is valid. Returns the information

| Name | Type | Explanation |
| ---- | ---- | ----------- |
| info | string | the content at the blockIndex and offset as specified |

- 403: Token is not valid.