# Problem Statement

## Problem Statement

Small marketing agencies often track clients, campaigns, and billing manually across spreadsheets
or notes, which makes it easy to lose track of which campaigns are running and which invoices are
still unpaid. A simple tool is needed to record clients, the campaigns run for them, and the
invoices raised against them, from the command line, with no database or internet connection
required.

## Scope

This project is a command-line Java application that lets agency staff:

- Add and view clients.
- Create and view campaigns tied to a client.
- Generate invoices for a client and mark them paid.
- View simple reports: total clients, total campaigns, revenue collected, and outstanding dues.

All data is saved to a local text file (`agency_data.txt`) so it persists between runs.

Out of scope: user login/roles, a graphical interface, and multi-currency billing.

## Target Users

Staff at a small marketing agency who need a lightweight way to track clients, campaigns, and
invoices without spreadsheets.

## High-Level Features

1. Client Management
2. Campaign Management
3. Invoicing
4. Reports
