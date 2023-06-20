USE [hotel-reception]
GO

INSERT INTO [dbo].[Account]
           ([FirstName]
           ,[LastName]
           ,[Email]
           ,[Password]
           ,[Enabled]
           ,[Role]
           ,[PhoneNumber]
           ,[CreatedDate])
     VALUES
           ('Maciej'
           ,'Maciejowski'
           ,'springbooter07@gmail.com'
           ,'$2a$12$H8ad2lD1vPsqBEJ0yQUlru2Z7mzn60AXcnH.QL6Zd2GCte.Hc7t/u'
           ,1
           ,'ADMIN'
           ,'515515515'
           ,'2020-01-16 00:56:48.927')
GO


