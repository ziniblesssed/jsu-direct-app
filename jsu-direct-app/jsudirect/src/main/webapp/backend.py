# I had to disable automatic reloading, because it was making the SQL server crash

from flask import Flask, jsonify, request, render_template
from flask_cors import CORS, cross_origin
from passlib import hash
import database
app = Flask(__name__)
cors = CORS(app, resources=r'/api/*', headers='Content-Type')


###Backend API Functions Go Here###
###The top two "@" lines need to be set up for the data routing from the...
###frontend to the backend.

@app.route('/api/test', methods=['GET'])
@cross_origin()
def backendTest():
    message = {'Message':'Backend working'}
    return jsonify(message)

@app.route('/api/login/<user_id>', methods=['POST'])
@cross_origin()
def login(user_id):
    data = request.get_json()
    print(user_id)
    print(len(data))
    password = data["password"]
    print(password)
    query = "SELECT * FROM user WHERE username = '" +user_id+"';"
    response = database.read_query(connection, query)
    print(response)
    isGoodPassword = hash.pbkdf2_sha256.verify(password, response[0][1])
    print(isGoodPassword)
    message = {'Message':'Backend working'}
    return jsonify(message)

hashed = hash.pbkdf2_sha256.hash("adminpassword")
print(hashed)


###SQL Function Calls Go Here###
connection = database.create_server_connection()
db_initialized = database.is_db_initialized(connection)

if db_initialized==False:
    database.initialize_db(connection)

destintation_table_intialized = database.is_destination_table_initialized(connection)

if destintation_table_intialized == False:
    database.execute_query(connection, database.pop_destination, "populate destination table")

#database.execute_query(connection, database.pop_superadmin_to_user_table, "admin addition")

print("Database operations complete.")


app.run(debug=True, use_reloader=False)