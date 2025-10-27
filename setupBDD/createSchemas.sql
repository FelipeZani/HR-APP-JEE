DROP TABLE IF EXISTS 
    Account,
    HaveAssociatedRole,
    HaveAssignedProject,
    PayStub,
    Employee,
    Project,
    RoleEmplpyee,
    Post,
    Department;


CREATE TABLE Department (
    departmentId INT PRIMARY KEY,
    name VARCHAR(255)
);

CREATE TABLE Post (
    postId INT PRIMARY KEY,
    label VARCHAR(255),
    wage DECIMAL(10,2)
);

CREATE TABLE Employee (
    employeeId INT PRIMARY KEY,
    name VARCHAR(255),
    lastname VARCHAR(255),
    departmentId INT,
    postId INT,
    rank VARCHAR(255),
    FOREIGN KEY (departmentId) REFERENCES Department(departmentId),
    FOREIGN KEY (postId) REFERENCES Post(postId)
);

CREATE TABLE Project (
    projectId INT PRIMARY KEY,
    label VARCHAR(255),
    status VARCHAR(255)
);

CREATE TABLE RoleEmplpyee (
    roleId INT PRIMARY KEY,
    label VARCHAR(255),
    permissions VARCHAR(255)
);

CREATE TABLE PayStub (
    payStubId INT PRIMARY KEY,
    total DECIMAL(10,2),
    creationDate DATE,
    date DATE,
    employeeId INT,
    FOREIGN KEY (employeeId) REFERENCES Employee(employeeId)
);

CREATE TABLE HaveAssignedProject (
    employeeId INT,
    projectId INT,
    assignementDate DATE,
    PRIMARY KEY (employeeId, projectId),
    FOREIGN KEY (employeeId) REFERENCES Employee(employeeId),
    FOREIGN KEY (projectId) REFERENCES Project(projectId)
);

CREATE TABLE HaveAssociatedRole (
    employeeId INT,
    id_role INT,
    PRIMARY KEY (employeeId, id_role),
    FOREIGN KEY (employeeId) REFERENCES Employee(employeeId),
    FOREIGN KEY (id_role) REFERENCES Role(roleId)
);

CREATE TABLE Account (
    username VARCHAR(255) PRIMARY KEY,
    password VARCHAR(255),
    firstConnection BOOLEAN,
    employeeId INT UNIQUE,
    FOREIGN KEY (employeeId) REFERENCES Employee(employeeId)
);
