import requests
import json
import logging
from dotenv import load_dotenv
from sqlalchemy import create_engine, text
import os
import pandas as pd
import base64
import hashlib
import boto3

logging.basicConfig(level=logging.INFO, format='[%(asctime)s] %(levelname)s - %(message)s', datefmt='%Y-%m-%d %H:%M:%S')

global db
global conn

load_dotenv()
db_host = os.getenv('MYSQL_HOST')
db_port = os.getenv('MYSQL_PORT')
db_user = os.getenv('MYSQL_USERNAME')
db_password = os.getenv('MYSQL_PASSWORD')
db_name = os.getenv('MYSQL_DATABASE')
s3_endpoint_url = os.getenv('S3_ENDPOINT_URL')
s3_bucket = os.getenv('S3_BUCKET')

s3_client = boto3.client(
    's3',
    endpoint_url=s3_endpoint_url,
    aws_access_key_id='dummy_access_key',
    aws_secret_access_key='dummy_secret_key'
)

db_url = f'mysql+pymysql://{db_user}:{db_password}@{db_host}:{db_port}/{db_name}'
db = create_engine(db_url)

try:
    if 'conn' in globals() and conn is not None:
        conn.close()
        logging.info("Closed previous connection.")
    else:
        logging.info("No previous connection to close.")
except Exception as e:
    logging.error(f"Error closing previous connection: {e}")

def populate_clubs_and_courts() -> pd.DataFrame:
    """ Populate clubs and courts tables on MySQL database """
    logging.info('populating clubs and courts tables')

    with open('./data/clubs.json', 'r', encoding='utf-8') as file:
        clubs = json.load(file)
    
    for club in clubs:
        name = club["name"]
        district = club["district"]
        street_address = club["street_address"]
        latitude = club["latitude"]
        longitude = club["longitude"]
        club_image = None

        # Generate a shortened name using hashlib
        shortened_name = hashlib.sha256(name.encode('utf-8')).hexdigest()[:10]

        # Extract base64 data from the data URL
        image_data_url = club["image"]
        save_club_image(shortened_name, image_data_url)
        
        with db.connect() as conn:
            try:
                transaction = conn.begin()
                query = f"""
                    insert into Image (path, mime_type) values
                    ('{shortened_name}', 'image/jpeg');
                """
                conn.execute(text(query))

                query = f"""
                    select last_insert_id();
                """
                res = conn.execute(text(query))
                club_image = res.fetchone()[0]
                transaction.commit()
            except Exception as e:
                transaction.rollback()
                logging.error(f"an error occurred during the transaction: {e}")

            if club_image is not None:
                query = text("""
                    insert into Club (name, district, street_address, club_image, latitude, longitude)
                    values (:name, :district, :street_address, :club_image, :latitude, :longitude)
                """)
                conn.execute(query, {
                    'name': name,
                    'district': district,
                    'street_address': street_address,
                    'club_image': club_image,
                    'latitude': latitude,
                    'longitude': longitude
                })
                conn.commit()
            else:
                query = text("""
                    insert into Club (name, district, street_address, latitude, longitude)
                    values (:name, :district, :street_address, :latitude, :longitude)
                """)
                conn.execute(query, {
                    'name': name,
                    'district': district,
                    'street_address': street_address,
                    'latitude': latitude,
                    'longitude': longitude
                })
                conn.commit()
            print(query)
            logging.info(f'inserted club {name} into database')
    logging.info('done!')

def save_club_image(name, image_data_url):

    base64_data = image_data_url.split(',')[1]

    # Fix base64 data by adding necessary padding
    missing_padding = len(base64_data) % 4
    if missing_padding:
        base64_data += '=' * (4 - missing_padding)

    # Decode base64 data
    image_data = base64.b64decode(base64_data)

    # Save the image as a JPEG file
    image_path = f"./data/{name}.jpeg"
    with open(image_path, "wb") as file:
        file.write(image_data)

    s3_client.upload_file(image_path, s3_bucket, f"{name}.jpeg")

    # removed local image after upload
    os.remove(image_path)
    
    logging.info(f"uploaded image {name}.jpeg to s3mock")

def main(args):
    populate_clubs_and_courts()

if __name__ == "__main__":
    args = vars()
    main(args)
