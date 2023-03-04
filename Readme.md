# Lee Shop Project

## Launch instruction using Docker

- Prerequisite - running Docker Desktop
- Run leeshop app
- Open browser: `http://localhost:8080`

### Instruction for manual launch 
- use version before adding Docker files: Commits on Jul 31, 2022

1. Start mysql server 
2. Paste script from leeshop.sql (src/db/ )
3. Execute all
4. Open IDEA and launch web application using Tomcat (my version 9.0.62)
5. Open browser: `http://localhost:8080`

### Override application properties

Create file `leeshop.properties` in the root of the file system (on disc C for Windows)
and write down the application properties.
If `/leeshop.properties` file exists,
application will use it to override all properties that defined at `classpath:application.properties`

### Test users:

* ADMIN: admin@gmail.com
* USER: user@gmail.com
* BLOCKED: blocked@gmail.com
* Default password is: password

