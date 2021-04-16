# protocol defenition

server works on 6997 port 

when client connected to server it can fetch what ever it needs

### commands set:

### check is user invited


| Field name | Type / Form  | Direction |
| ---------- |:------------ | :-------- |
| Method     | Line / "?\n" | С -> S    |
| Username   | Line         | С -> S    |
| Answer     | Boolean      | S -> C    |



### just ping server

| Field name | Type / Form  | Direction |
| ---------- |:------------ | :-------- |
| Method     | Line / "p\n" | С -> S    |
| Answer     | Boolean      | S -> C    |
