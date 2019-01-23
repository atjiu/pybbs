## Create Comment

- Address POST `/api/comment/create`
- Params
  - token
  - content
  - topicId
  - commentId
- Return Result(Comment)

## Update Comment

- Address POST `/api/comment/update`
- Params
  - token
  - id
  - content
- Return Result(Comment)

## Delete Comment

- Address GET `/api/comment/delete`
- Params
  - token
  - id
- Return Result()

## Like Comment

- Address GET `/api/comment/vote`
- Params
  - token
  - id
- Return Result(int) Return the total number of comments for the current comment after the like
