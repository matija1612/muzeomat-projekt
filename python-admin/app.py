from flask import Flask, redirect, url_for
from models import db
from routes import admin_bp
from config import Config

def create_app():
    app = Flask(__name__)
    app.config.from_object(Config)

    # Initialize database
    db.init_app(app)

    # Register blueprints
    app.register_blueprint(admin_bp)

    # Root redirect
    @app.route('/')
    def index():
        return redirect(url_for('admin.login'))

    return app

if __name__ == '__main__':
    app = create_app()
    with app.app_context():
        db.create_all()
    app.run(host='0.0.0.0', port=5000, debug=True)
