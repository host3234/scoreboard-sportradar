# Changelog

## [1.1.0]
### Added
- Validation against empty/null team names and two same teams in the same match

## [1.0.0]
### Added
- JavaDocs for `ScoreBoard` class and `Match` record
- Final version of `README`

### Changed
- Refactored `Match` class into a `record` to ensure immutability
- Score updates are now handled only via `ScoreBoard.updateScore()`, replacing the previous mutable design

## [0.6.0]
### Added
- `ScoreBoard.getMatches()` now offers full functionality, including sorting by total score and recency

## [0.5.0]
### Changed
- `ScoreBoard.startMatch()` and `ScoreBoard.finishMatch()` now perform additional validation and throw exceptions (`TeamAlreadyPlayingException`, `MatchNotFoundException`)

## [0.4.0]
### Added
- `Match.updateScore()` method to change match result (now deprecated)
- `ScoreBoard.finishMatch()` method to remove an ongoing match

## [0.3.0]
### Added
- Initial implementation of `ScoreBoard.startMatch()` and `ScoreBoard.getMatches()`
- Full implementation of `Match` class with teams and scores

## [0.2.0]
### Added
- Initial versions of `ScoreBoard` and `Match` classes

## [0.1.1]
### Changed
- Updated test framework from JUnit 3 to JUnit 5

## [0.1.0]
### Added
- Project initial setup
- Basic `README` and `CHANGELOG` files
