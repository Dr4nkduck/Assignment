use master

CREATE DATABASE GoodsDB

USE GoodsDB

CREATE TABLE product (
    productId INT IDENTITY(1,1) PRIMARY KEY,
    productName NVARCHAR(255) NOT NULL,
    brand NVARCHAR(255) NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    productCategory NVARCHAR(100) NOT NULL,
    status NVARCHAR(50) NOT NULL,
    photo NVARCHAR(255),
    user_email NVARCHAR(255) NOT NULL
);

CREATE TABLE [User] (
    id INT PRIMARY KEY IDENTITY(1,1),
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    phone VARCHAR(15) UNIQUE NOT NULL
);

CREATE TABLE Cart (
    cartId INT PRIMARY KEY IDENTITY(1,1),   
    userId INT NOT NULL,                     
    productId INT NOT NULL,                    
    quantity INT NOT NULL DEFAULT 1,        
    addedAt DATETIME DEFAULT GETDATE(),   
    FOREIGN KEY (userId) REFERENCES [User](id) ON DELETE CASCADE,
    FOREIGN KEY (productId) REFERENCES product(productId) ON DELETE CASCADE
);

SELECT * FROM product
INSERT INTO [User] values('Admin', 'admin@gmail.com', 'admin', '0123456789')

delete FROM [User]
delete from product
SELECT * FROM [User]
DBCC CHECKIDENT ('product', RESEED, 0);

USE GoodsDB;

INSERT INTO product(productName, brand, price, productCategory, status, photo, user_email) 
VALUES 


('Sparkle Doll', 'Hoyoverse', 20.000, 'Doll', 'Available', 'sparkle.jpg', 'admin1@example.com'),
('Miku Doll', 'SEGA', 20.000, 'Doll', 'Available', 'miku.jpg', 'admin2@example.com'),
('Astolfo', 'TypeMoon', 20.000, 'Doll', 'Available', 'astolfo.jpg', 'admin1@example.com'),
(' Klee KeyRing', 'Hoyoverse', 20.000, 'KeyRing', 'Available', 'klee.jpg', 'admin2@example.com'),
('Hoshino KeyRing', 'Yostar', 20.000, 'KeyRing', 'Available', 'hoshino.jpg', 'admin1@example.com'),
('Exodia KeyRing', 'Konami', 20.000, 'KeyRing', 'Available', 'exodia.jpg', 'admin2@example.com'),
('Miku Birthday 2024 Flower Ver.','SEGA', 20.000, 'Figure', 'Available', 'miku2.jpg', 'admin1@example.com'),
('Hitori Gotou Bocchi The Rock 2069', 'Off-brand', 20.000, 'Figure', 'Available', 'bocchi.jpg', 'admin2@example.com');


INSERT INTO Cart (userId, productId, quantity) 
VALUES 
(1, 1, 2),  -- 2 copies of "The Great Gatsby"
(1, 3, 1),  -- 1 copy of "To Kill a Mockingbird"
(1, 5, 1),  -- 1 copy of "Sapiens: A Brief History of Humankind"
(1, 7, 3);  -- 3 copies of "Thinking, Fast and Slow"

SELECT * FROM Cart

