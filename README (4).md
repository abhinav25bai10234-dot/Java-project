# Agency Client & Campaign Tracker

A command-line Java application that helps a small marketing agency keep track of its clients,
the campaigns it runs for them, and the invoices it raises. 

Everything lives in a single file, `Main.java`. There's no database, no internet dependency, and
no build tool — just the Java standard library.

## Table of Contents

- [What This Project Does](#what-this-project-does)
- [Modules / Features](#modules--features)
- [Concepts Demonstrated](#concepts-demonstrated)
- [Project Structure](#project-structure)
- [Prerequisites](#prerequisites)
- [How to Set Up and Run](#how-to-set-up-and-run)
- [Full Walkthrough with Sample Input/Output](#full-walkthrough-with-sample-inputoutput)
- [How Data Is Saved](#how-data-is-saved)
- [Error Handling](#error-handling)
- [Known Limitations](#known-limitations)
- [Troubleshooting](#troubleshooting)

## What This Project Does

Small agencies often track clients, campaigns, and billing across spreadsheets or notes, which
makes it easy to lose track of what's active and what's still unpaid. This app gives agency staff
a simple menu-driven tool, run from a terminal, to:

- Record clients and see the full client list.
- Create campaigns tied to a specific client.
- Generate invoices for a client and mark them as paid once payment comes in.
- Check quick totals: how many clients/campaigns exist, how much revenue has been collected, and
  how much is still outstanding.

All of this data is written to a plain text file (`agency_data.txt`) when you exit, so it's still
there the next time you run the app.

## Modules / Features

| # | Module | What it does |
|---|--------|---------------|
| 1 | **Client Management** | Add a client (name, industry, email); view every client added so far. |
| 2 | **Campaign Management** | Create a campaign for an existing client (title + budget); view all campaigns. |
| 3 | **Invoicing** | Generate an invoice for a client; mark an invoice as paid; view all invoices and their status. |
| 4 | **Reports** | See total clients, total campaigns, revenue collected, and outstanding dues at a glance. |

## Concepts Demonstrated

This project was written to show a working, practical use of core Java ideas covered in the
course:

- **Object-Oriented Programming** — `Client`, `Campaign`, and `Invoice` are modeled as classes with
  their own constructors and a `toString()` method for clean printing. They're declared as
  `static` nested classes inside `Main` to keep everything in one file.
- **Collections** — three `ArrayList`s (`clients`, `campaigns`, `invoices`) hold all the data while
  the program runs.
- **Exception Handling** — a custom checked exception, `NotFoundException`, is thrown by the
  `findClient()` helper whenever someone tries to create a campaign or invoice for a client ID that
  doesn't exist. It's caught in the calling menu method and shown as a friendly message instead of
  crashing the program. `NumberFormatException` is also caught when someone types text where a
  number (like a budget or invoice amount) was expected.
- **File I/O** — `saveData()` and `loadData()` use `BufferedWriter`/`BufferedReader` to persist all
  three lists to `agency_data.txt` and read them back on the next run.
- **Control Structures** — the whole app is driven by a `while` loop and `switch` statements for
  menu navigation.

## Project Structure

```
.
├── Main.java              # The entire application (single file)
├── README.md              # This file
├── statement.md           # Problem statement and scope
├── PROJECT REPORT.pdf     # Full project report with diagrams
└── agency_data.txt        # Created automatically the first time you save (not included upfront)
```

There are no packages and no subfolders — every class (`Main`, `Client`, `Campaign`, `Invoice`,
`NotFoundException`) is defined inside `Main.java`. This keeps the whole project easy to read
top-to-bottom and easy to compile with a single command.

## Prerequisites

You need a Java Development Kit (JDK), version 17 or later. Check whether you already have one:

```bash
java -version
javac -version
```

If either command says "not recognized" or "command not found", install a JDK from
[Adoptium Temurin](https://adoptium.net/) (choose the **JDK** download, not the JRE) and restart
your terminal afterward.

## How to Set Up and Run

1. **Get the code onto your machine** — either clone the repository:
   ```bash
   git clone https://github.com/{your-username}/{your-repo}.git
   cd {your-repo}
   ```
   or simply download `Main.java` directly and put it in its own folder.

2. **Compile it:**
   ```bash
   javac Main.java
   ```
   This produces several `.class` files (one for `Main` and one for each nested class) in the same
   folder.

3. **Run it:**
   ```bash
   java Main
   ```

4. You'll see the main menu:
   ```
   === Agency Client & Campaign Tracker ===

   1. Client Management
   2. Campaign Management
   3. Invoicing
   4. Reports
   5. Save & Exit
   Choose an option:
   ```

## Full Walkthrough with Sample Input/Output

Here's a complete first run, start to finish.

**Step 1 — Add a client**
```
Choose an option: 1

--- Client Management ---
1. Add Client
2. View All Clients
3. Back
Choose an option: 1
Client name: Sample Bakery Co.
Industry: Food & Beverage
Email: hello@samplebakery.example
Client added with ID: C1
```

**Step 2 — Create a campaign for that client**
```
Choose an option: 2

--- Campaign Management ---
1. Create Campaign
2. View All Campaigns
3. Back
Choose an option: 1
Client ID this campaign is for: C1
Campaign title: Festive Season Promotion
Budget (Rs.): 25000
Campaign created with ID: CM1
```

**Step 3 — Generate an invoice**
```
Choose an option: 3

--- Invoicing ---
1. Generate Invoice
2. Mark Invoice Paid
3. View All Invoices
4. Back
Choose an option: 1
Client ID: C1
Invoice amount (Rs.): 12000
Invoice generated with ID: INV1
```

**Step 4 — Mark the invoice as paid**
```
Choose an option: 3
Choose an option: 2
Invoice ID to mark paid: INV1
Invoice INV1 marked as PAID.
```

**Step 5 — Check the reports**
```
Choose an option: 4

--- Reports ---
Total clients: 1
Total campaigns: 1
Revenue collected: Rs. 12000.0
Outstanding dues: Rs. 0.0
```

**Step 6 — Save and exit**
```
Choose an option: 5
Data saved. Goodbye!
```

**Step 7 — Confirm it persisted:** run `java Main` again, choose `1` then `2` (View All Clients) —
`Sample Bakery Co.` should still be there, loaded straight from `agency_data.txt`.

**Trying an invalid client ID** (to see the exception handling in action):
```
Choose an option: 2
Choose an option: 1
Client ID this campaign is for: C99
Error: No client found with ID C99
```
The program doesn't crash — it prints the error and returns to the menu.

## How Data Is Saved

`agency_data.txt` is a plain text file where each line represents one record, prefixed by its
type so `loadData()` knows how to rebuild it:

```
CLIENT,C1,Sample Bakery Co.,Food & Beverage,hello@samplebakery.example
CAMPAIGN,CM1,C1,Festive Season Promotion,ACTIVE,25000.0
INVOICE,INV1,C1,12000.0,true
```

- `CLIENT` lines: id, name, industry, email
- `CAMPAIGN` lines: id, clientId, title, status, budget
- `INVOICE` lines: id, clientId, amount, paid (`true`/`false`)

The file is rewritten completely every time you choose "Save & Exit" (option 5), so it always
reflects the current in-memory state of the three lists.

## Error Handling

| Situation | What happens |
|---|---|
| Creating a campaign/invoice for a client ID that doesn't exist | `NotFoundException` is thrown by `findClient()` and caught in the menu, printing `Error: No client found with ID ...` |
| Typing non-numeric text for a budget or invoice amount | `NumberFormatException` is caught, printing `Invalid budget amount.` or `Invalid amount.` |
| Marking an invoice ID that doesn't exist as paid | Prints `Invoice not found.` (checked with a simple loop, no exception needed here) |
| `agency_data.txt` doesn't exist yet | `loadData()` checks `file.exists()` first and simply skips loading — this is expected on the very first run |

## Known Limitations

These are deliberate simplifications, kept in mind for the "Future Enhancements" section of the
project report:

- No login/authentication — anyone running the app has full access.
- No editing or deleting existing clients/campaigns once created.
- Client/campaign/invoice IDs are simple incrementing counters (`C1`, `C2`, ...) rather than
  UUIDs, so they reset if `agency_data.txt` is deleted.
- The save file uses comma-separated values with no escaping, so a comma typed inside a client
  name or email would break parsing on the next load. Keep names/emails comma-free.

## Troubleshooting

- **`'javac' is not recognized...`** — the JDK isn't installed or isn't on your PATH. Install one
  from [Adoptium Temurin](https://adoptium.net/) and reopen your terminal.
- **`Error: Could not find or load main class Main`** — make sure you're running `java Main` from
  the same folder where `Main.class` was created after compiling, and that you compiled with
  `javac Main.java` first (not typed with a different filename).
- **Data doesn't seem to save** — make sure you exit using option `5` (Save & Exit) from the main
  menu, not by closing the terminal window directly; that skips `saveData()`.
