# python-admin/api_client.py
import requests

class JavaBackendClient:
    def __init__(self, base_url='http://localhost:8080'):
        self.base_url = base_url
    
    def create_room(self, room_data):
        # Spreman za Java Backend
        response = requests.post(f'{self.base_url}/api/rooms', json=room_data)
        return response.json()
    
    def optimize_route(self, room_ids, constraints):
        # Spreman za TSP optimizaciju
        payload = {'room_ids': room_ids, 'constraints': constraints}
        response = requests.post(f'{self.base_url}/api/routes/optimize', json=payload)
        return response.json()
