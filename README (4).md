# Agency Client & Campaign Tracker

A simple command-line Java application to manage clients, campaigns, and invoices for a small
marketing agency. Built for the **Programming in Java** flipped-course evaluation.

## Features / Modules

1. **Client Management** — add a client, view all clients.
2. **Campaign Management** — create a campaign for an existing client, view all campaigns.
3. **Invoicing** — generate an invoice for a client, mark an invoice as paid, view all invoices.
4. **Reports** — total clients, total campaigns, revenue collected, outstanding dues.

## Concepts Demonstrated

- **OOP**: `Client`, `Campaign`, and `Invoice` classes with constructors and `toString()`.
- **Collections**: `ArrayList` used to store clients, campaigns, and invoices in memory.
- **Exception Handling**: a custom `NotFoundException` is thrown and caught when a campaign or
  invoice is created for a client ID that doesn't exist.
- **File I/O**: all data is written to `agency_data.txt` on exit and loaded back on the next run,
  using `BufferedReader`/`BufferedWriter`.
- **Control structures**: `switch` statements drive every menu.

## Prerequisites

A JDK (17 or later). Check with:
```
java -version
javac -version
```

## How to Run

```bash
javac Main.java
java Main
```

That's it — one file, two commands.

## Sample Walkthrough

1. Run the app. Choose `1` (Client Management) -> `1` (Add Client) -> enter a name/industry/email.
   Note the Client ID printed (e.g. `C1`).
2. Choose `2` (Campaign Management) -> `1` (Create Campaign) -> enter that Client ID, a title, and
   a budget.
3. Choose `3` (Invoicing) -> `1` (Generate Invoice) -> enter the Client ID and an amount.
4. Choose `3` -> `2` (Mark Invoice Paid) -> enter the Invoice ID printed above.
5. Choose `4` (Reports) to see revenue collected and outstanding dues update accordingly.
6. Choose `5` to save and exit. Run the app again -- your data is still there.

## Files in This Repository

| File | Purpose |
|---|---|
| `Main.java` | The entire application |
| `README.md` | This file |
| `statement.md` | Problem statement and scope |
| `PROJECT REPORT.pdf` | Full project report with diagrams |
