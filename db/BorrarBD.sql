USE master;
GO

-- 1. Forzar el cierre de conexiones activas
ALTER DATABASE TiendasGo_Inv 
SET SINGLE_USER 
WITH ROLLBACK IMMEDIATE;
GO

-- 2. Eliminar la base de datos
-- Esto borra la entrada en el sistema y los archivos físicos (.mdf, .ldf, .ndf)
DROP DATABASE TiendasGo_Inv;
GO

PRINT 'La base de datos TiendasGo_Inv ha sido eliminada exitosamente.';

