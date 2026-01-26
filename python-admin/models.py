from flask_sqlalchemy import SQLAlchemy
from datetime import datetime

db = SQLAlchemy()

class User(db.Model):
    __tablename__ = 'users'
    
    id = db.Column(db.Integer, primary_key=True)
    username = db.Column(db.String(80), unique=True, nullable=False)
    password_hash = db.Column(db.String(255), nullable=False)
    role = db.Column(db.String(20), default='ADMIN')
    last_login = db.Column(db.DateTime)

    def __repr__(self):
        return f'<User {self.username}>'

class Room(db.Model):
    __tablename__ = 'rooms'
    
    id = db.Column(db.Integer, primary_key=True)
    name = db.Column(db.String(255), unique=True, nullable=False)
    description = db.Column(db.Text)
    floor_number = db.Column(db.Integer, nullable=False)
    coordinates_x = db.Column(db.Float, nullable=False)
    coordinates_y = db.Column(db.Float, nullable=False)
    avg_visit_duration = db.Column(db.Integer, default=15)
    wheelchair_accessible = db.Column(db.Boolean, default=True)
    is_active = db.Column(db.Boolean, default=True)
    created_at = db.Column(db.DateTime, default=datetime.utcnow)

    def __repr__(self):
        return f'<Room {self.name}>'

class Route(db.Model):
    __tablename__ = 'routes'
    
    id = db.Column(db.Integer, primary_key=True)
    muzemat_id = db.Column(db.String(50))
    room_sequence = db.Column(db.String(500))
    total_distance = db.Column(db.Float)
    total_time = db.Column(db.Integer)
    calculation_time_ms = db.Column(db.Integer)
    is_partial = db.Column(db.Boolean, default=False)
    created_at = db.Column(db.DateTime, default=datetime.utcnow)

    def __repr__(self):
        return f'<Route {self.id}>'
