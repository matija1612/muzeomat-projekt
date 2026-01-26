from flask_wtf import FlaskForm
from wtforms import (
    StringField, PasswordField, TextAreaField, IntegerField, FloatField,
    BooleanField, SubmitField
)
from wtforms.validators import DataRequired, Length, NumberRange

class LoginForm(FlaskForm):
    username = StringField('Username', validators=[DataRequired(), Length(min=3, max=80)])
    password = PasswordField('Password', validators=[DataRequired()])
    submit = SubmitField('Login')

class RoomForm(FlaskForm):
    name = StringField('Naziv', validators=[DataRequired(), Length(min=3, max=255)])
    description = TextAreaField('Opis')
    floor_number = IntegerField('Kat', validators=[DataRequired(), NumberRange(min=1, max=100)])
    coordinates_x = FloatField('Koordinata X', validators=[DataRequired()])
    coordinates_y = FloatField('Koordinata Y', validators=[DataRequired()])
    avg_visit_duration = IntegerField('Prosječno trajanje (min)', validators=[DataRequired(), NumberRange(min=1)])
    wheelchair_accessible = BooleanField('Wheelchair pristupačno')
    is_active = BooleanField('Aktivna', default=True)
    submit = SubmitField('Spremi')
