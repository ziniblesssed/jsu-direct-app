import mysql.connector
from mysql.connector import Error
import pandas as pd

def create_server_connection ():
    connection = None
    try:
        connection = mysql.connector.connect(
            host = "76.237.185.1",
            user = "JSUDIRECT",
            passwd = "JSUDIRECTPASSWORD",
            database = "jsudirect"
            )
        print("MySQL Server connection successful")
    except Error as err:
        print(f"Error: '{err}'")
    return connection

def create_database(connection):
    query = "CREATE DATABASE jsudirect"
    cursor = connection.cursor()
    try:
        cursor.execute(query)
        print("Database created successfully")
    except Error as err:
        print(f"Error: '{err}'")

#create_database(connection)
#
#def create_db_connection ():
#    connection = None
#    try:
#        connection = mysql.connector.connect(
#            host = "76.237.185.1",
#            user = "JSUDIRECT",
#            passwd = "JSUDIRECTPASSWORD",
#            database = "jsudirect"
#            )
#        print("MySQL Database connection successful")
#    except Error as err:
#        print(f"Error: '{err}'")
#    return connection
#
#connection = create_db_connection()

def execute_query(connection, query, queryName):
    cursor = connection.cursor()
    try:
        cursor.execute(query)
        connection.commit()
        print("Query "+queryName+" executed successfully")
    except Error as err:
        print(f"Error: '{err}'")

create_user_table = """
CREATE TABLE user (
    username VARCHAR(10) PRIMARY KEY,
    password_hash VARCHAR(100),
    access_level INT
    );
"""

create_destination_table = """
CREATE TABLE destination (
    building_code VARCHAR(4) PRIMARY KEY,
    name VARCHAR(100),
    node_id int,
    facts TEXT
    );
"""

create_route_table = """
CREATE TABLE route (
    origin VARCHAR(4),
    destination VARCHAR(4),
    list_of_edges VARCHAR(1000),
    total_length INT,
    directions TEXT,
    primary key (origin, destination)
    );
"""

pop_destination = """
INSERT INTO destination VALUES
('ADH','Alcohol and Drug Studies',2,'Fact Text'),
('ADMN','Administration Tower',1,'Fact Text'),
('AH','Ayer Hall',8,'Fact Text'),
('ALEX','Alexander Hall',3,'Fact Text'),
('ALUM','Alumni Affairs',4,'Fact Text'),
('ATH1','Athletics Support 1',6,'Fact Text'),
('ATH2','Athlestics Support 2',7,'Fact Text'),
('BASE','Baseball Stadium',11,'Fact Text'),
('BBPF','Baseball Practice Field',10,'Fact Text'),
('BFRH','B F Roberts Hall',9,'Fact Text'),
('BLA','Blackburn Language Arts',12,'Fact Text'),
('CFM','Charles F Moore',16,'Fact Text'),
('COB','College of Business',17,'Fact Text'),
('CPD','Campus Police',15,'Fact Text'),
('CSAN','CSET Annex',19,'Fact Text'),
('CSN','Campbell Suites North',13,'Fact Text'),
('CSS','Campbell Suites South',14,'Fact Text'),
('DRH','Dixon Residence Hall',20,'Fact Text'),
('EC','E Center',22,'Fact Text'),
('EGH','Executive Guest House',23,'Fact Text'),
('ENB','College of Science Engineering and Technology',18,'Fact Text'),
('FDH','F D Hall Music Center',24,'Fact Text'),
('FM','Facilities Management',25,'Fact Text'),
('HDH','Heritage Dining Hall',27,'Fact Text'),
('HTS','H T Sampson Library',26,'Fact Text'),
('IDCN','ID Center',28,'Fact Text'),
('INT','International Programs',29,'Fact Text'),
('JH','Johnson Hall',33,'Fact Text'),
('JHA','Johnson Hall Annex',34,'Fact Text'),
('JHJ','Jospeh H Jackson Building',36,'Fact Text'),
('JHS','Just Hall of Science',37,'Fact Text'),
('JLR','J L Reddix Building',30,'Fact Text'),
('JPSB','John A Peoples Building',32,'Fact Text'),
('JSH','Jones-Sampson Hall',35,'Fact Text'),
('JYW','J Y Woodward Building',31,'Fact Text'),
('LEW','Lee E Williams Center',38,'Fact Text'),
('LIB','Dollye M E Robinson',21,'Fact Text'),
('MWRH','McAllister-Whiteside Residence Hall',39,'Fact Text'),
('OIA','Old Industrial Arts',40,'Fact Text'),
('ONE','One University Place',41,'Fact Text'),
('PSL','Plant Science Lab',42,'Fact Text'),
('REM','Rose Embly McCoy Auditorium',43,'Fact Text'),
('ROTC','Army/Air Force ROTC',5,'Fact Text'),
('SCEN','Student Center',46,'Fact Text'),
('STRC','Structures Lab',45,'Fact Text'),
('STRH','Stewart Residence Hall',44,'Fact Text'),
('TBEA','T B Ellis Annex',47,'Fact Text'),
('TBEB','T B Ellis Building',48,'Fact Text'),
('TRK','Track',49,'Fact Text'),
('TRRH','Transitional Residence Hall',50,'Fact Text'),
('UHC','University Health Center',51,'Fact Text'),
('UR','University Recruiting',52,'Fact Text'),
('WPHR','Walter Payton Health and Recreation Center',53,'Fact Text'),
('ZTH','Z T Hubert',54,'Fact Text');
"""

pop_superadmin_to_user_table = """
INSERT INTO user VALUES
("J00000000", "$pbkdf2-sha256$29000$UEopxdib816LkfKe8x5jjA$VrseG7wpfPiB6rT5Fp6ZCnQoWoFjG1jrBx0fbqfNARQ", 2);
"""

def initialize_db(connection):
    execute_query(connection, create_user_table, "create user table")
    execute_query(connection, create_destination_table, "create destination table")
    execute_query(connection, create_route_table, "create route table")

def read_query(connection, query):
    cursor = connection.cursor()
    result = None
    try: 
        cursor.execute(query)
        result = cursor.fetchall()
        return result
    except Error as err:
        print(f"Error: '{err}'")
    

def is_db_initialized(connection):
    response = read_query(connection, "SHOW TABLES;")
    if(len(response))==3:
        return True
    else:
        return False

def is_destination_table_initialized(connection):
    request = """
    SELECT COUNT(*)
    FROM destination;
    """
    response = read_query(connection, request)
    if(response[0][0] != 54):
        return False;
    else:
        return True;



