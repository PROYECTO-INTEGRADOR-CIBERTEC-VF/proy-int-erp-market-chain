USE master;
GO

IF DB_ID('TiendasGo') IS NOT NULL
BEGIN
    ALTER DATABASE TiendasGo SET SINGLE_USER WITH ROLLBACK IMMEDIATE;
    DROP DATABASE TiendasGo;
END
GO

CREATE DATABASE TiendasGo
ON PRIMARY (
    NAME = TiendasGo_Primary,
    FILENAME = '/var/opt/mssql/tiendasgo_db/Data_Primary/TiendasGo_Main.mdf'
),
FILEGROUP USER_DATA (
    NAME = TiendasGo_UserData,
    FILENAME = '/var/opt/mssql/tiendasgo_db/Data_User/TiendasGo_Data.ndf'
)
LOG ON (
    NAME = TiendasGo_Log,
    FILENAME = '/var/opt/mssql/tiendasgo_db/Logs/TiendasGo_Log.ldf'
);
GO