--Para crear las tablas
CREATE TABLE persona (
	id SERIAL PRIMARY KEY,
	numero_documento VARCHAR(50) NOT NULL,
	nombre_completo VARCHAR(150) NOT NULL,
	tipo_persona VARCHAR(8) NOT NULL CHECK (tipo_persona IN ('JURIDICA', 'FISICA')),
	estado VARCHAR(12) NOT NULL CHECK (estado IN ('ACTIVO', 'FALLECIDO', 'INHABILITADO')),
	score INTEGER NOT NULL CHECK (score BETWEEN 0 AND 1000),
	categoria_riesgo VARCHAR(5) NOT NULL CHECK (categoria_riesgo IN ('BAJO', 'MEDIO', 'ALTO'))
);

--Para insertar algunos productos
INSERT INTO persona (
	numero_documento,
	nombre_completo,
	tipo_persona,
	estado,
	score,
	categoria_riesgo
)
VALUES
	('80012345-6', 'Comercial del Norte S.A.', 'JURIDICA', 'ACTIVO', 850, 'BAJO'),
	('4567890', 'Maria Gonzalez', 'FISICA', 'ACTIVO', 620, 'MEDIO'),
	('90098765-4', 'Inversiones Rivera S.R.L.', 'JURIDICA', 'ACTIVO', 90, 'ALTO'),
	('3214567', 'Carlos Benitez', 'FISICA', 'INHABILITADO', 180, 'ALTO'),
	('5678901', 'Ana Martinez', 'FISICA', 'FALLECIDO', 430, 'MEDIO');
