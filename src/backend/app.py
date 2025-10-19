from flask import Flask
from flask import request
import sqlite3

app = Flask(__name__)

def get_db_connection():
    conn = sqlite3.connect('db_for_server.db')
    conn.row_factory = sqlite3.Row
    return conn

def close_db_connection(conn):
    conn.close()

@app.route('/')
def index():
    return "HELLO <3"

@app.route('/login')
def login():
    handle = request.args.get('handle')
    password = request.args.get('password')
    conn = get_db_connection()
    posts = conn.execute("SELECT * FROM Accounts WHERE handle = ?", (handle,)).fetchone()
    close_db_connection(conn)
    if posts == None or posts['password'] != password:
        return '0'
    return '1'

@app.route('/signup')
def signup():
    handle = request.args.get('handle')
    password = request.args.get('password')
    name = request.args.get('name')
    conn = get_db_connection()
    posts = conn.execute("SELECT * FROM Accounts WHERE handle = ?", (handle,)).fetchone()
    if posts != None:
        close_db_connection(conn)
        return '0'
    conn.execute("INSERT INTO Accounts (handle, name, password) VALUES (?, ?, ?)", \
                 (handle, name, password))
    conn.commit()
    close_db_connection(conn)
    return '1'

@app.route('/get_wished')
def get_wished():
    handle = request.args.get('handle')
    tp = request.args.get('type')
    conn = get_db_connection()
    posts = conn.execute("SELECT * FROM Wishlist WHERE handle = ? AND type = ?", (handle, tp,)).fetchall()
    close_db_connection(conn)
    for i in range(len(posts)):
        posts[i] = list(posts[i])
    return str(posts)

@app.route('/add_wished')
def add_wished():
    handle = request.args.get('handle')
    type = request.args.get('type')
    title = request.args.get('title')
    description = request.args.get('description')
    conn = get_db_connection()
    conn.execute("INSERT INTO Wishlist (handle, type, title, description) VALUES (?, ?, ?, ?)", \
                 (handle, type, title, description,))
    conn.commit()
    close_db_connection(conn)
    return '1'

@app.route('/remove_wished')
def remove_wished():
    id = request.args.get('id')
    conn = get_db_connection()
    conn.execute("DELETE FROM Wishlist WHERE id = ?", (id,))
    conn.commit()
    close_db_connection(conn)
    return '1'

@app.route('/get_cards')
def get_cards():
    handle = request.args.get('handle')
    conn = get_db_connection()
    posts = conn.execute("SELECT * FROM Cards WHERE handle = ?", (handle,)).fetchall()
    close_db_connection(conn)
    for i in range(len(posts)):
        posts[i] = list(posts[i])
    return str(posts)

@app.route('/add_card')
def add_card():
    handle = request.args.get('h')
    title = request.args.get('t')
    photo = request.args.get('ph')
    posad_date = request.args.get('pos')
    poliv_mode = request.args.get('pol')
    udob = request.args.get('udob')
    obrez_date = request.args.get('obrez')
    link = request.args.get('link')
    primech = request.args.get('prim')
    conn = get_db_connection()
    conn.execute("INSERT INTO Cards (handle, title, photo, posad_date, poliv_mode, udob, obrez_date, link, primech) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)", \
                 (handle, title, photo, posad_date, poliv_mode, udob, obrez_date, link, primech,))
    conn.commit()
    close_db_connection(conn)
    return '1'

@app.route('/remove_card')
def remove_card():
    id = request.args.get('id')
    conn = get_db_connection()
    conn.execute("DELETE FROM Cards WHERE id = ?", (id,))
    conn.commit()
    close_db_connection(conn)
    return '1'

@app.route('/get_note')
def get_note():
    handle = request.args.get('handle')
    conn = get_db_connection()
    posts = conn.execute("SELECT * FROM MedNotes WHERE handle = ?", (handle,)).fetchone()
    if posts == None:
        conn.execute("INSERT INTO MedNotes (handle, note) VALUES (?, ?)", (handle, ""))
        conn.commit()
    posts = list(conn.execute("SELECT * FROM MedNotes WHERE handle = ?", (handle,)).fetchone())
    close_db_connection(conn)
    return str(posts)

@app.route('/upd_note')
def upd_note():
    handle = request.args.get('handle')
    note = request.args.get('note')
    conn = get_db_connection()
    conn.execute("DELETE FROM MedNotes WHERE handle = ?", (handle,))
    conn.execute("INSERT INTO MedNotes VALUES (?, ?)", (handle, note))
    conn.commit()
    close_db_connection(conn)
    return '1'

if __name__ == '__main__':
    app.run(host='0.0.0.0')
