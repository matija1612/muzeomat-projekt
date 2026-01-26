"""
Seed script za demo prezentaciju
Kreira realistične demo podatke
"""
from app import create_app
from models import db, User, Room
from werkzeug.security import generate_password_hash

app = create_app()

with app.app_context():
    # Clear existing data
    db.drop_all()
    db.create_all()
    
    # Admin users
    admin = User(username='admin', password_hash=generate_password_hash('admin123'), role='ADMIN')
    demo = User(username='demo', password_hash=generate_password_hash('demo123'), role='ADMIN')
    
    db.session.add(admin)
    db.session.add(demo)
    
    # Realistic museum rooms
    rooms = [
        # Ground floor (Prizemlje)
        Room(name='Ulazni Hol', description='Recepcija i garderoba', floor_number=0, 
             coordinates_x=50, coordinates_y=50, avg_visit_duration=5, wheelchair_accessible=True, is_active=True),
        Room(name='Suvenirnica', description='Muzejska trgovina', floor_number=0,
             coordinates_x=150, coordinates_y=50, avg_visit_duration=10, wheelchair_accessible=True, is_active=True),
        Room(name='Kafić', description='Muzejski kafić', floor_number=0,
             coordinates_x=250, coordinates_y=50, avg_visit_duration=15, wheelchair_accessible=True, is_active=True),
        
        # First floor (Kat 1)
        Room(name='Antička Grčka', description='Skulpture i keramika iz klasičnog perioda', floor_number=1,
             coordinates_x=100, coordinates_y=150, avg_visit_duration=20, wheelchair_accessible=True, is_active=True),
        Room(name='Rimsko Carstvo', description='Artefakti iz doba Rimske Republike i Carstva', floor_number=1,
             coordinates_x=200, coordinates_y=150, avg_visit_duration=25, wheelchair_accessible=True, is_active=True),
        Room(name='Egipatska Kolekcija', description='Mumije, sarkofazi i hieroglifi', floor_number=1,
             coordinates_x=300, coordinates_y=150, avg_visit_duration=30, wheelchair_accessible=False, is_active=True),
        
        # Second floor (Kat 2)
        Room(name='Renesansa', description='Umjetnost 14.-17. stoljeća', floor_number=2,
             coordinates_x=100, coordinates_y=250, avg_visit_duration=25, wheelchair_accessible=True, is_active=True),
        Room(name='Barok', description='Djela iz 17. i 18. stoljeća', floor_number=2,
             coordinates_x=200, coordinates_y=250, avg_visit_duration=20, wheelchair_accessible=True, is_active=True),
        Room(name='Romantizam', description='Umjetnost 19. stoljeća', floor_number=2,
             coordinates_x=300, coordinates_y=250, avg_visit_duration=20, wheelchair_accessible=True, is_active=True),
        
        # Third floor (Kat 3)
        Room(name='Impresionizam', description='Monet, Renoir, Degas', floor_number=3,
             coordinates_x=100, coordinates_y=350, avg_visit_duration=30, wheelchair_accessible=False, is_active=True),
        Room(name='Kubizam', description='Picasso, Braque, Gris', floor_number=3,
             coordinates_x=200, coordinates_y=350, avg_visit_duration=25, wheelchair_accessible=False, is_active=True),
        Room(name='Moderna Umjetnost', description='Kunst 20. stoljeća', floor_number=3,
             coordinates_x=300, coordinates_y=350, avg_visit_duration=35, wheelchair_accessible=True, is_active=True),
        Room(name='Suvremena Umjetnost', description='Instalacije i digitalna umjetnost', floor_number=3,
             coordinates_x=400, coordinates_y=350, avg_visit_duration=20, wheelchair_accessible=True, is_active=True),
        
        # Temporarily closed
        Room(name='Specijalna Izložba', description='Privremeno zatvoreno za renovaciju', floor_number=2,
             coordinates_x=400, coordinates_y=250, avg_visit_duration=0, wheelchair_accessible=False, is_active=False),
    ]
    
    for room in rooms:
        db.session.add(room)
    
    db.session.commit()
    
    print(f"✅ Demo data created!")
    print(f"   Users: {User.query.count()}")
    print(f"   Rooms: {Room.query.count()}")
    print(f"   Active Rooms: {Room.query.filter_by(is_active=True).count()}")
    print()
    print("Login credentials:")
    print("  admin / admin123")
    print("  demo / demo123")
