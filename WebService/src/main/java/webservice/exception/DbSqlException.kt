package webservice.exception

import java.sql.SQLException

class DbSqlException(val actualException: SQLException) : DatabaseException()
