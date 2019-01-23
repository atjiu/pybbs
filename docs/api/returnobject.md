## Result

The interface returns an object with only one `Result`

This class is customized in the program, a total of three properties

```java
public class Result {
  
  private Integer code;
  private String description;
  private Object detail;
  
  // getter, setter
}
```

## IPage

If the interface involves pagination, it will return `Result(IPage)`, 
which is to put the encapsulated paging object in the detail object of the Result object, 
and then convert it to json and return it to the front end.

The IPage object is a paging object built into MyBatis-Plus. The properties that the calling interface might use are as follows.

Properties:

- records
- pages
- total
- current
- size

Unfortunately, it does not encapsulate two properties like jpa `last` `next` 
so that you can directly use their values to determine whether it is the first or last page.

However, it is also possible to judge whether it is the first page or the last page by `current` and `pages`.

