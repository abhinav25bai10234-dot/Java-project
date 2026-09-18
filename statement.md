# Problem Statement

## Background

Small marketing and growth agencies typically handle several clients at the same time, each with
one or more campaigns running and invoices that need to be raised and followed up on. When this is
tracked informally — across spreadsheets, notes apps, or chat threads — it becomes easy to lose
track of which campaigns are currently active, which client owes what, and how much money has
actually been collected versus is still outstanding. As the number of clients grows, this informal
tracking breaks down further: there's no single place to check "what's going on right now" without
digging through multiple documents.

## Problem Statement

There is a need for a simple, self-contained tool that a small agency team can run locally to
record clients, the campaigns run for each client, and the invoices raised against them — without
requiring a database server, an internet connection, or any installation beyond a Java runtime.
The tool should let staff add and review this information quickly from a terminal, and it should
remember everything between sessions so nothing has to be re-entered.

## Scope

**In scope** — this project implements a command-line Java application that:

- Records clients (name, industry, contact email) and lists them on demand.
- Creates campaigns tied to a specific client, with a title and a budget.
- Generates invoices for a client and allows marking them as paid.
- Reports simple totals: number of clients, number of campaigns, revenue collected, and
  outstanding dues.
- Persists all of the above to a local text file, so data survives between runs.
- Handles obviously invalid input (an unknown client ID, non-numeric amounts) without crashing.

**Out of scope** — the following were deliberately left out to keep the project focused and
appropriately sized for the course:

- User accounts, login, or role-based permissions.
- Editing or deleting a client/campaign/invoice once created.
- A graphical user interface — the course requires a command-line-executable project.
- Multi-currency support, tax calculation, or payment gateway integration.
- A relational database — plain-text file storage is used instead, which is sufficient at this
  scale and avoids any external dependency.

## Target Users

Staff at a small marketing or growth agency — for example, an account manager or the agency
owner — who currently track clients, campaigns, and billing manually and want a lightweight way to
keep that information in one place without adopting a full accounting or CRM system.

## Assumptions

- Only one person uses the application at a time (no concurrent multi-user access).
- Client, campaign, and invoice records are entered in good faith — the application performs
  basic validation (e.g. checking a client ID exists, checking amounts are numeric) but does not
  attempt to catch every possible bad input.
- All monetary values are in a single currency (displayed as "Rs." in the app), with no conversion
  needed.

## High-Level Features

1. **Client Management** — add a client; view the full client list.
2. **Campaign Management** — create a campaign for an existing client; view all campaigns.
3. **Invoicing** — generate an invoice for a client; mark an invoice as paid; view all invoices.
4. **Reports** — view total clients, total campaigns, revenue collected, and outstanding dues.

## Success Criteria

The project is considered complete when a user can, in a single terminal session or across
multiple sessions:

- Add at least one client and see it listed correctly.
- Create a campaign for that client and see it listed correctly.
- Generate an invoice, mark it paid, and see the reports reflect the change.
- Close the application and reopen it, with all previously entered data still present.
- Attempt an invalid action (e.g. referencing a non-existent client) and see a clear error message
  instead of the program crashing.
