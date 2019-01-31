## Number of unread notifications

- Address GET `/api/notification/notRead`
- Params
  - token
- Return Result(int) // Returns the number of unread messages

## Mark Notification Read

- Address GET `/api/notification/markRead`
- Params
  - token
- Return Result()

## Notification List

- Address GET `/api/notification/list`
- Params
  - token
- Return Result(Map)
