-- Create the users table
CREATE TABLE users (
                       id UUID PRIMARY KEY,
                       username VARCHAR(255) NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       role VARCHAR(50) NOT NULL DEFAULT 'USER' -- Add the role column with a default value
);

-- Create the finance table
CREATE TABLE finance (
                         id UUID PRIMARY KEY,
                         total_income VARCHAR(255) NOT NULL, -- Changed to VARCHAR to match entity
                         total_expenses VARCHAR(255) NOT NULL, -- Changed to VARCHAR to match entity
                         deficit VARCHAR(255) NOT NULL, -- Changed to VARCHAR to match entity
                         remaining VARCHAR(255) NOT NULL, -- Changed to VARCHAR to match entity
                         total_savings VARCHAR(255) NOT NULL, -- Changed to VARCHAR to match entity
                         total_debt VARCHAR(255) NOT NULL, -- Changed to VARCHAR to match entity
                         user_id UUID, -- Foreign key to the users table, nullable
                         CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Create the chart table
CREATE TABLE chart (
                       id UUID PRIMARY KEY,
                       labels TEXT NOT NULL, -- Changed to TEXT to store JSON array of labels
                       type VARCHAR(50) NOT NULL, -- Enum type stored as VARCHAR
                       finance_id UUID NOT NULL, -- Foreign key to the finance table
                       CONSTRAINT fk_finance FOREIGN KEY (finance_id) REFERENCES finance(id) ON DELETE CASCADE
);

-- Create the dataset table
CREATE TABLE dataset (
                         id UUID PRIMARY KEY,
                         label VARCHAR(255) NOT NULL,
                         data JSON NOT NULL, -- JSON array of numbers
                         background_color TEXT NOT NULL, -- JSON array or string
                         border_color TEXT NOT NULL, -- JSON array or string
                         border_width INT NOT NULL,
                         chart_id UUID NOT NULL, -- Foreign key to the chart table
                         CONSTRAINT fk_chart FOREIGN KEY (chart_id) REFERENCES chart(id) ON DELETE CASCADE
);
