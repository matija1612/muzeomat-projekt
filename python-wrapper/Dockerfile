FROM python:3.11-slim
WORKDIR /app
# Install system dependencies
RUN apt-get update && apt-get install -y \
libpq-dev \
gcc \
&& rm -rf /var/lib/apt/lists/*
# Copy requirements
COPY requirements.txt .
# Install Python packages
RUN pip install --no-cache-dir -r requirements.txt
# Copy application
COPY . .
# Expose port
EXPOSE 5000
# Health check
HEALTHCHECK --interval=30s --timeout=3s \
CMD python -c "import requests; requests.get('http://localhost:5000/admin', timeout=2)"
# Run app
CMD ["python", "run.py"]