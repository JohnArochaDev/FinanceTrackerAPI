-- Create the users table
CREATE TABLE users (
                       id UUID PRIMARY KEY,
                       username VARCHAR(255) NOT NULL,
                       password VARCHAR(255) NOT NULL
);

-- Create the finance table
CREATE TABLE finance (
                         id UUID PRIMARY KEY,
                         total_income DECIMAL(10, 2) NOT NULL,
                         total_expenses DECIMAL(10, 2) NOT NULL,
                         deficit DECIMAL(10, 2) NOT NULL,
                         remaining DECIMAL(10, 2) NOT NULL,
                         total_savings DECIMAL(10, 2) NOT NULL,
                         total_debt DECIMAL(10, 2) NOT NULL,
                         user_id UUID NOT NULL, -- Foreign key to the users table
                         CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Create the chart table
CREATE TABLE chart (
                       id UUID PRIMARY KEY,
                       labels TEXT NOT NULL, -- JSON array of labels
                       finance_id UUID NOT NULL, -- Foreign key to the finance table
                       CONSTRAINT fk_finance FOREIGN KEY (finance_id) REFERENCES finance(id) ON DELETE CASCADE
);

-- Create the dataset table
CREATE TABLE dataset (
                         id UUID PRIMARY KEY,
                         label VARCHAR(255) NOT NULL,
                         data TEXT NOT NULL, -- JSON array of numbers
                         background_color TEXT NOT NULL, -- JSON array or string
                         border_color TEXT NOT NULL, -- JSON array or string
                         border_width INT NOT NULL,
                         chart_id UUID NOT NULL, -- Foreign key to the chart table
                         CONSTRAINT fk_chart FOREIGN KEY (chart_id) REFERENCES chart(id) ON DELETE CASCADE
);

-- Insert a single user into the users table
INSERT INTO users (id, username, password) VALUES
    ('11111111-1111-1111-1111-111111111111', 'john_doe', 'securepassword');

-- Insert data into the finance table
INSERT INTO finance (id, total_income, total_expenses, deficit, remaining, total_savings, total_debt, user_id) VALUES
    ('22222222-2222-2222-2222-222222222222', 4200, 3500, 700, 700, 1000, 5000, '11111111-1111-1111-1111-111111111111');

-- Insert data into the chart table (barData, pieData, radarData)
INSERT INTO chart (id, labels, finance_id) VALUES
                                               ('33333333-3333-3333-3333-333333333333', '["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"]', '22222222-2222-2222-2222-222222222222'),
                                               ('44444444-4444-4444-4444-444444444444', '["Housing", "Debt", "Food", "Transportation", "Dining", "Childcare", "Insurance", "Utilities", "Subscriptions", "Savings"]', '22222222-2222-2222-2222-222222222222'),
                                               ('55555555-5555-5555-5555-555555555555', '["Housing", "Debt", "Food", "Transportation", "Dining", "Childcare", "Insurance", "Utilities", "Subscriptions", "Savings"]', '22222222-2222-2222-2222-222222222222');

-- Insert data into the dataset table for barData
INSERT INTO dataset (id, label, data, background_color, border_color, border_width, chart_id) VALUES
                                                                                                  ('66666666-6666-6666-6666-666666666666', 'Spending', '[100, 200, 300, 400, 500, 600, 700, 800, 900, 1000, 1100, 1200]', '"rgba(213, 62, 79, 1)"', '"rgb(132, 39, 50)"', 1, '33333333-3333-3333-3333-333333333333'),
                                                                                                  ('77777777-7777-7777-7777-777777777777', 'Savings', '[1200, 1100, 1000, 900, 800, 700, 600, 500, 400, 300, 200, 100]', '"rgba(102, 194, 165, 1)"', '"rgb(70, 130, 111)"', 1, '33333333-3333-3333-3333-333333333333');

-- Insert data into the dataset table for pieData
INSERT INTO dataset (id, label, data, background_color, border_color, border_width, chart_id) VALUES
    ('88888888-8888-8888-8888-888888888888', 'Spending', '[1200, 800, 450, 300, 400, 350, 200, 150, 100, 170]', '["#d53e4f", "#f46d43", "#fdae61", "#ffe498", "#ffffcd", "#e6f598", "#abdda4", "#66c2a5", "#3288bd", "#bd32aa"]', '["#343a40", "#343a40", "#343a40", "#343a40", "#343a40", "#343a40", "#343a40", "#343a40", "#343a40", "#343a40"]', 1, '44444444-4444-4444-4444-444444444444');

-- Insert data into the dataset table for radarData
INSERT INTO dataset (id, label, data, background_color, border_color, border_width, chart_id) VALUES
    ('99999999-9999-9999-9999-999999999999', 'Spending', '[1200, 800, 450, 300, 400, 350, 200, 150, 100, 170]', '"rgba(243, 255, 21, 0.2)"', '"rgb(243, 255, 21)"', 1, '55555555-5555-5555-5555-555555555555');