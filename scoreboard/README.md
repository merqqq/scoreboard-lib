# Scoreboard Service

A scoreboard library that manages live football matches, tracks scores, and provides summaries.

## Features
- Start a new match
- Update the score of an ongoing match
- Finish a match
- Retrieve a summary of ongoing matches, sorted by:
    - **Total score** (descending)
    - **Start time** (most recent first)

## Technologies Used
- **Java**
- **JUnit 5** (for TDD)
- **Maven** (build & dependency management)

## Notes
This is the initial version of the library. 

Next steps I would proceed with would be:
- ask the team for a review
- introduce improvements in case of need.

Ideas for future development of the library:
- undo last score update
- export summary (e.g. to JSON or output file for external use).