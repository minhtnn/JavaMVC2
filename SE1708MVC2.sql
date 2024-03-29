DELETE FROM dbo.Product;
DELETE FROM dbo.Registration;
DELETE FROM dbo.[tbl_Order];
DELETE FROM dbo.OrderDetail;
DROP TABLE dbo.Registration;
DROP TABLE dbo.Product;
DROP TABLE dbo.[tbl_Order];
DROP TABLE dbo.OrderDetail;
CREATE TABLE Registration(
	username varchar(20) PRIMARY KEY,
	[password] varchar(30),
	lastname nvarchar(100),
	isAdmin bit
);
CREATE TABLE Product(
	id varchar(8) PRIMARY KEY,
	[name] nvarchar(50),
	quantity int,
	unitPrice float,
	[status] bit
);
CREATE TABLE tbl_Order(
	id varchar(5) PRIMARY KEY,
	oDate DATETIME,
	total float
);
CREATE TABLE OrderDetail(
	id int PRIMARY KEY,
	productID varchar(8) FOREIGN KEY REFERENCES Product(id),
	orderID varchar (5) FOREIGN KEY REFERENCES tbl_Order(id),
	quantity int,
	price float,
	total float
);
INSERT INTO dbo.Registration VALUES
('ngohoang','123',N'Ngô Hoàng',1),
('khoa','123',N'Anh Khoa',1),
('thao','234',N'Thanh Thản', 0),
('phuc','345',N'Minh Phúc',0),
('minh','456',N'Nhật Minh',0),
('hoang','567',N'Hoàng',0);
INSERT INTO dbo.Product VALUES
('TA113',N'Tiếng anh sơ cấp 1',4,12.9,1),
('NB113',N'Nhật bản sơ cấp 1',0,13.9,0),
('NB123',N'Nhật bản sơ cấp 2',5,13.9,1),
('J113',N'Java cho người mới bắt đầu',5,13.9,1),
('TA123',N'Tiếng anh sơ cấp 2',9,12.9,1);

SELECT s.username, s.password, s.lastname, GETDATE() AS CurrentDate,
IIF(s.isAdmin = 1, 'True','False') AS [isAdmin]
FROM dbo.Registration AS s;

UPDATE dbo.Registration
SET isAdmin = 0
WHERE username = 'thao';

UPDATE dbo.Product
SET quantity = quantity - 1
WHERE id = 'NB123';

SELECT * 
FROM dbo.Product AS p;

SELECT * 
FROM dbo.tbl_Order;

INSERT INTO dbo.tbl_Order (id, oDate, total) VALUES
('OO002', GETDATE(), 30.0);

SELECT * 
FROM dbo.OrderDetail;