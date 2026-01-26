from flask import Blueprint, render_template, redirect, url_for, flash, request, session
from models import db, User, Room, Route
from forms import LoginForm, RoomForm
from werkzeug.security import check_password_hash
from functools import wraps

admin_bp = Blueprint('admin', __name__, url_prefix='/admin')

def login_required(f):
    @wraps(f)
    def decorated_function(*args, **kwargs):
        if 'user_id' not in session:
            flash('Molimo prijavite se.', 'warning')
            return redirect(url_for('admin.login'))
        return f(*args, **kwargs)
    return decorated_function

@admin_bp.route('/login', methods=['GET', 'POST'])
def login():
    form = LoginForm()
    if form.validate_on_submit():
        user = User.query.filter_by(username=form.username.data).first()
        if user and check_password_hash(user.password_hash, form.password.data):
            session['user_id'] = user.id
            session['username'] = user.username
            flash(f'Dobrodošli, {user.username}!', 'success')
            return redirect(url_for('admin.dashboard'))
        else:
            flash('Neispravno korisničko ime ili lozinka.', 'danger')
    return render_template('login.html', form=form)

@admin_bp.route('/logout')
def logout():
    session.clear()
    flash('Uspješno ste se odjavili.', 'info')
    return redirect(url_for('admin.login'))

@admin_bp.route('/dashboard')
@login_required
def dashboard():
    total_rooms = Room.query.count()
    active_rooms = Room.query.filter_by(is_active=True).count()
    total_routes = Route.query.count()
    return render_template(
        'dashboard.html',
        total_rooms=total_rooms,
        active_rooms=active_rooms,
        total_routes=total_routes
    )

@admin_bp.route('/rooms')
@login_required
def rooms():
    all_rooms = Room.query.order_by(Room.floor_number, Room.name).all()
    return render_template('rooms.html', rooms=all_rooms)

@admin_bp.route('/rooms/create', methods=['GET', 'POST'])
@login_required
def create_room():
    form = RoomForm()
    if form.validate_on_submit():
        room = Room(
            name=form.name.data,
            description=form.description.data,
            floor_number=form.floor_number.data,
            coordinates_x=form.coordinates_x.data,
            coordinates_y=form.coordinates_y.data,
            avg_visit_duration=form.avg_visit_duration.data,
            wheelchair_accessible=form.wheelchair_accessible.data,
            is_active=form.is_active.data
        )
        db.session.add(room)
        db.session.commit()
        flash(f'Prostorija "{room.name}" uspješno kreirana!', 'success')
        return redirect(url_for('admin.rooms'))
    return render_template('room_form.html', form=form, title='Nova Prostorija')

@admin_bp.route('/rooms/<int:room_id>/edit', methods=['GET', 'POST'])
@login_required
def edit_room(room_id):
    room = Room.query.get_or_404(room_id)
    form = RoomForm(obj=room)
    if form.validate_on_submit():
        form.populate_obj(room)
        db.session.commit()
        flash(f'Prostorija "{room.name}" uspješno ažurirana!', 'success')
        return redirect(url_for('admin.rooms'))
    return render_template('room_form.html', form=form, title='Uredi Prostoriju')

@admin_bp.route('/rooms/<int:room_id>/deactivate', methods=['POST'])
@login_required
def deactivate_room(room_id):
    room = Room.query.get_or_404(room_id)
    room.is_active = False
    db.session.commit()
    flash(f'Prostorija "{room.name}" deaktivirana.', 'info')
    return redirect(url_for('admin.rooms'))

@admin_bp.route('/statistics')
@login_required
def statistics():
    rooms_by_floor = db.session.query(
        Room.floor_number, 
        db.func.count(Room.id)
    ).group_by(Room.floor_number).all()
    return render_template('statistics.html', rooms_by_floor=rooms_by_floor)

