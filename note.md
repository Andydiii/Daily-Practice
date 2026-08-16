# OPP

## public/private static/non-static method:
**public/private**: who can call me? \
**static/non-static:** who does the method belong to? Does the method belong to the class or object?

private method: can only be called inside the class

public method: so the method can be called outside the class. so other methods in other class can also call public method.

normal method: has to create the object first before call the method. e.g. has to create a object twosum first and then twosum.main()

static method: no need to create a object first before call the method. for exmaple. we can do TwoSum.main() without creating TwoSum object.


# Java Data Structure

## ArrayList
import java.util.ArrayList;
create a list: `ArrayList<String> lst = new ArratList<>()`
size: `.size()`
get ith element in the list: `lst.get(i)`
add element: `lst.add()`
replace an element: `lst.set(i, "apple")`
update an object: `lst.get(i).fieldName = "newValue"`
check empty: `lst.isEmpty()`

## Scanner
import: `import java.util.Scanner;`
Init scanner: `Scanner scanner = new Scanner(System.in);`
Read a line from user: `String title = scanner.nextLine();`

## string
string comparison: `not use == but use str1.equals(str2)`
comapre with a char will alawys return false.
convert to int: `parseInt(str)`
