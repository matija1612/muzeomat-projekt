from app import create_app
from models import db, User, Room
from werkzeug.security import generate_password_hash

app = create_app()

with app.app_context():
    db.create_all()
    print("✅ Database tables created!")

    # Kreiraj admin usera
    admin = User(
        username='admin',
        password_hash=generate_password_hash('password123'),
        role='ADMIN'
    )
    db.session.add(admin)
    
    # Kreiraj test prostorije
    room1 = Room(
        name='Ulazni Hol',
        description='Dobrodošlica u muzej',
        floor_number=1,
        coordinates_x=100.0,
        coordinates_y=100.0,
        avg_visit_duration=5,
        wheelchair_accessible=True,
        is_active=True
    )

    room2 = Room(
        name='Impresionizam',
        description='Djela Moneta, Renoira, Degasa',
        floor_number=2,
        coordinates_x=250.0,
        coordinates_y=200.0,
        avg_visit_duration=20,
        wheelchair_accessible=False,
        is_active=True
    )

    room3 = Room(
        name='Moderna Skulptura',
        description='Rodin, Moore, Giacometti',
        floor_number=2,
        coordinates_x=400.0,
        coordinates_y=300.0,
        avg_visit_duration=15,
        wheelchair_accessible=True,
        is_active=True
    )

    room4 = Room(
        name='Kubizam',
        description='Picasso, Braque',
        floor_number=3,
        coordinates_x=500.0,
        coordinates_y=400.0,
        avg_visit_duration=25,
        wheelchair_accessible=False,
        is_active=True
    )

    db.session.add(room1)
    db.session.add(room2)
    db.session.add(room3)
    db.session.add(room4)

    db.session.commit()

    print("✅ Created 1 admin user + 4 test rooms!")
    print()
    print("Login credentials:")
    print("  Username: admin")
    print("  Password: password123")
