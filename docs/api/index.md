## Topic List

- Address GET `/api/` 或者 `/api/index`
- Params
  - pageNo
  - tab: Classification, respectively: `good` `noanswer` `hot` `newest`
- Return Result(IPage<Map>)
 
## Login

- Address POST `/api/login`
- Params
  - username
  - password
- Return Result(User)

## Register

- Address POST `/api/register`
- Params
  - username
  - password
- Return Result(User)
