import requests
import json
import logging
from dotenv import load_dotenv
from sqlalchemy import create_engine, text
import os
import pandas as pd

logging.basicConfig(level=logging.INFO, format='[%(asctime)s] %(levelname)s - %(message)s', datefmt='%Y-%m-%d %H:%M:%S')

global conn
global db

load_dotenv()
db_host = os.getenv('MYSQL_HOST')
db_port = os.getenv('MYSQL_PORT')
db_user = os.getenv('MYSQL_USERNAME')
db_password = os.getenv('MYSQL_PASSWORD')
db_name = os.getenv('MYSQL_DATABASE')

db_url = f'mysql+pymysql://{db_user}:{db_password}@{db_host}:{db_port}/{db_name}'

db = create_engine(db_url)
conn = db.connect()

def extract_clubs() -> list:
    """ Extracts Madrid clubs data from https://datos.madrid.es/
    Returns: list
    """
    logging.info('extracting Madrid clubs data')

    headers = {
        'accept': 'text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8',
        'accept-language': 'es-ES,es;q=0.5',
    }

    params = {
        'vgnextoid': '00149033f2201410VgnVCM100000171f5a0aRCRD',
        'format': 'json',
        'file': '0',
        'filename': '200186-0-polideportivos',
        'mgmtid': '4a5fbef4b2503410VgnVCM2000000c205a0aRCRD',
        'preview': 'full',
    }

    retries = 2
    response = requests.get('https://datos.madrid.es/portal/site/egob/menuitem.ac61933d6ee3c31cae77ae7784f1a5a0/', params=params, headers=headers)

    while retries > 0 and response.status_code != 200:
        retries -= 1
        logging.warning(f'Retrying request. Retries left: {retries}')
        response = requests.get('https://datos.madrid.es/portal/site/egob/menuitem.ac61933d6ee3c31cae77ae7784f1a5a0/', params=params, headers=headers)

    if retries == 0:
        logging.error('Failed to extract Madrid clubs data')
        raise RuntimeError(f'http get error, status != 200: {response.text}')
    
    raw_data = json.loads(response.text)
    
    return raw_data['@graph']

def process_clubs(raw_data: list) -> pd.DataFrame:
    """ Processes Madrid clubs data
    Args:
        raw_data (list): Raw data extracted from Madrid clubs
    Returns: pd.DataFrame
    """
    logging.info('processing clubs data')

    clubs = []

    for element in raw_data:
        row_data = {}
        row_data['name'] = element['title']
        row_data['district'] = element['address']['district']["@id"].split("/")[-1]
        row_data['postal_code'] = element['address']['postal-code']
        row_data['street_address'] = element['address']['street-address']
        row_data['latitude'] = element['location']['latitude']
        row_data['longitude'] = element['location']['longitude']
        clubs.append(row_data)

    df = pd.DataFrame(clubs)
    return df

def upload_clubs_to_mysql(df: pd.DataFrame):
    """ Uploads Madrid clubs data to MySQL
    Args:
        df (pd.DataFrame): Madrid clubs data
    """
    logging.info('uploading clubs data to MySQL')

    df.to_sql('raw_club', con=conn, if_exists='replace', index=False)

def consolidate_clubs_data():
    """ Consolidates Club table with raw club data table """
    logging.info('consolidating raw clubs data into club table')

    with open ('./mysql/club.sql', 'r') as file:
        query = file.read()
    
    conn.execute(text(query))

def remove_raw_club_table():
    """ Removes raw club data table """
    logging.info('removing raw club data table')

    query = "drop table if exists raw_club;"
    
    conn.execute(text(query))

def main(args):
    raw_data = extract_clubs()
    df = process_clubs(raw_data)
    upload_clubs_to_mysql(df)
    consolidate_clubs_data()
    remove_raw_club_table()

    logging.info('done!')
    conn.close()

if __name__ == "__main__":
    args = vars()
    main(args)
