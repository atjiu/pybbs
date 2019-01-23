## Topic Detail

- Address GET `/api/topic/detail`
- Params
  - token: **Optional**, if you pass the token, there will be one more object in the return value, 
    showing that the current topic is not collected by yourself. If you do not fill it, there is no such object.
  - id
- Return Result(Map)

## Create Topic

- Address POST `/api/topic/create`
- Params
  - token
  - title
  - content: [Optional]
  - tags: tags, multiple tags separated by commas, up to 5
- Return Result(Topic)

## Edit Topic

- Address POST `/api/topic/edit`
- Params
  - token
  - id
  - title
  - content: [Optional]
  - tags: tags, multiple tags separated by commas, up to 5
- Return Result(Topic)

## Delete Topic

- Address GET `/api/topic/delete`
- Params
  - token
  - id
- Return Result()

## Like Topic

- Address GET `/api/topic/vote`
- Params
  - token
  - id
- Return Result(int) // Return the total number of likes for the current topic after the like
