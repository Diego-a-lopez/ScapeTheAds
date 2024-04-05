from fastapi import FastAPI, HTTPException
from pydantic import BaseModel
from typing import List, Optional
from uuid import UUID, uuid4
from sqlalchemy import create_engine, Column, Integer, String, ForeignKey
from sqlalchemy.orm import sessionmaker, relationship, declarative_base
from sqlalchemy.dialects.postgresql import UUID
import uuid
import os
import uvicorn

BACKEND_HOST = os.getenv("BACKEND_HOST", default="localhost")
BACKEND_PORT = int(os.getenv("BACKEND_PORT", default=8000))

# Create a SQLite database
DATABASE_URL = "sqlite:///./test.db"
engine = create_engine(DATABASE_URL)
SessionLocal = sessionmaker(autocommit=False, autoflush=False, bind=engine)

# Create a base model for SQLAlchemy
Base = declarative_base()

# Define a Highscore model
class Highscore(Base):
    __tablename__ = "highscores"
    id = Column(UUID(as_uuid=True), primary_key=True, default=uuid4, unique=True, nullable=False)
    score = Column(Integer, nullable=False)
    clear_time = Column(Integer, nullable=False)
    date = Column(String, nullable=False)

# Define a User model
class User(Base):
    __tablename__ = "users"
    id = Column(UUID(as_uuid=True), primary_key=True, default=uuid4, unique=True, nullable=False)
    name = Column(String, nullable=False)
    highscore_id = Column(UUID(as_uuid=True), ForeignKey("highscores.id"))
    highscore = relationship("Highscore", back_populates="user", uselist=False)

# Create the database tables
Base.metadata.create_all(bind=engine)

app = FastAPI()

class CreateHighscoreRequest(BaseModel):
    score: int
    clear_time: int
    date: str

class CreateUserRequest(BaseModel):
    name: str
    highscore: CreateHighscoreRequest
    
class HighscoreResponse(BaseModel):
    score: int
    clear_time: int
    date: str

class UserResponse(BaseModel):
    id: str
    name: str
    highscore: HighscoreResponse

# Create a new user
@app.post("/users/", response_model=UserResponse)
def create_user(user: CreateUserRequest):
    db_user = User(name=user.name)
    db_highscore = Highscore(score=user.highscore.score, clear_time=user.highscore.clear_time, date=user.highscore.date)
    db_user.highscore = db_highscore

    db = SessionLocal()
    db.add(db_user)
    db.commit()
    db.refresh(db_user)
    db.close()

    return db_user

# Get a user by ID
@app.get("/users/{user_id}", response_model=UserResponse)
def read_user(user_id: str):
    db = SessionLocal()
    db_user = db.query(User).filter(User.id == user_id).first()
    db.close()

    if not db_user:
        raise HTTPException(status_code=404, detail="User not found")
    return db_user

# Update a user
@app.put("/users/{user_id}", response_model=UserResponse)
def update_user(user_id: str, user: UserResponse):
    db = SessionLocal()
    db_user = db.query(User).filter(User.id == user_id).first()

    if not db_user:
        db.close()
        raise HTTPException(status_code=404, detail="User not found")

    db_user.name = user.name
    db_user.highscore.score = user.highscore.score
    db_user.highscore.clear_time = user.highscore.clear_time
    db_user.highscore.date = user.highscore.date

    db.commit()
    db.refresh(db_user)
    db.close()

    return db_user

# Delete a user
@app.delete("/users/{user_id}", response_model=UserResponse)
def delete_user(user_id: str):
    db = SessionLocal()
    db_user = db.query(User).filter(User.id == user_id).first()

    if not db_user:
        db.close()
        raise HTTPException(status_code=404, detail="User not found")

    db.delete(db_user)
    db.commit()
    db.close()

    return db_user

# Get top N highscores
@app.get("/highscores/", response_model=List[HighscoreResponse])
def read_highscores(n: int):
    db = SessionLocal()
    sorted_highscores = db.query(Highscore).order_by(Highscore.score.desc()).limit(n).all()
    db.close()
    return sorted_highscores

if __name__ == "__main__":
    uvicorn_config = {
        "app": "server:app",
        "host": BACKEND_HOST,
        "port": BACKEND_PORT
    }
    uvicorn.run(**uvicorn_config)
