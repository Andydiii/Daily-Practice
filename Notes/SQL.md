# NOT IN and NOT EXIST
In SQL Server, **NOT IN** compares one expression against a list or a subquery that returns one column: e.g.
`WHERE id NOT IN (SELECT id FROM OtherTable)`

To compare a combination of columns, use **NOT EXISTS**:
**it includes the row from t when it finds a row in o where both col1 and col2 match**
Its like doing: For each row in t, does that row match a row in o such that t.col1 and t.col2 match o.col1 and o.col2, if so, then display that row, if not then we not selct that row.
```sql
WHERE NOT EXISTS (
    SELECT 1
    FROM OtherTable o
    WHERE o.col1 = t.col1
      AND o.col2 = t.col2
)

