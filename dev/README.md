# Dev

A development toolkit, such as pull dev image from docker and start, create/drop database and etc.

## Notes

- reset PostgreSQL auto-increment sequence

  ```sql
  ALTER SEQUENCE promotion_record_id_seq RESTART WITH (
    SELECT max(id) + 1 from promotion_record
  );
  ```
