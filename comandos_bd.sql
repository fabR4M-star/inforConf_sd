--Para crear las tablas
CREATE TABLE persona (
	id SERIAL PRIMARY KEY,
	numero_documento VARCHAR(50) NOT NULL,
	nombre_completo VARCHAR(150) NOT NULL,
	tipo_persona VARCHAR(8) NOT NULL CHECK (tipo_persona IN ('JURIDICA', 'FISICA')),
	estado VARCHAR(12) NOT NULL CHECK (estado IN ('ACTIVO', 'FALLECIDO', 'INHABILITADO')),
	score INTEGER NOT NULL CHECK (score BETWEEN 0 AND 1000)
);

--Para insertar algunas personas
INSERT INTO persona (
	numero_documento,
	nombre_completo,
	tipo_persona,
	estado,
	score
)
VALUES
	('80012345-6', 'Comercial del Norte S.A.', 'JURIDICA', 'ACTIVO', 850),
	('4567890', 'Maria Gonzalez', 'FISICA', 'ACTIVO', 620),
	('90098765-4', 'Inversiones Rivera S.R.L.', 'JURIDICA', 'ACTIVO', 90),
	('3214567', 'Carlos Benitez', 'FISICA', 'INHABILITADO', 180),
	('5678901', 'Ana Martinez', 'FISICA', 'FALLECIDO', 430);

--Para crear la tabla de deudas
CREATE TABLE deuda (
	id SERIAL PRIMARY KEY,
	persona_id INTEGER NOT NULL REFERENCES persona(id),
	entidad VARCHAR(150) NOT NULL,
	monto NUMERIC(12, 2) NOT NULL,
	dias INTEGER NOT NULL
);

--Para insertar algunas deudas
INSERT INTO deuda (persona_id, entidad, monto, dias)
SELECT p.id, d.entidad, d.monto, d.dias
FROM (VALUES
	('80012345-6', 'Banco Nacional', 1250000.00::NUMERIC, 30),
	('80012345-6', 'Cooperativa Central', 450000.00::NUMERIC, 0),
	('4567890', 'Financiera del Sur', 275000.50::NUMERIC, 45),
	('90098765-4', 'Banco Regional', 3200000.00::NUMERIC, 120),
	('3214567', 'Casa de Credito', 85000.00::NUMERIC, 90)
) AS d(numero_documento, entidad, monto, dias)
JOIN persona p ON p.numero_documento = d.numero_documento;
